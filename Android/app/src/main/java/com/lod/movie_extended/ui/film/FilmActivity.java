package com.lod.movie_extended.ui.film;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaCodec;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.accessibility.CaptioningManager;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.google.android.exoplayer.ExoPlayer;
import com.google.android.exoplayer.MediaCodecTrackRenderer;
import com.google.android.exoplayer.MediaFormat;
import com.google.android.exoplayer.audio.AudioCapabilities;
import com.google.android.exoplayer.audio.AudioCapabilitiesReceiver;
import com.google.android.exoplayer.audio.AudioTrack;
import com.google.android.exoplayer.drm.UnsupportedDrmException;
import com.google.android.exoplayer.metadata.GeobMetadata;
import com.google.android.exoplayer.metadata.PrivMetadata;
import com.google.android.exoplayer.metadata.TxxxMetadata;
import com.google.android.exoplayer.text.CaptionStyleCompat;
import com.google.android.exoplayer.text.Cue;
import com.google.android.exoplayer.text.SubtitleLayout;
import com.google.android.exoplayer.util.MimeTypes;
import com.google.android.exoplayer.util.Util;
import com.lod.movie_extended.App;
import com.lod.movie_extended.R;
import com.lod.movie_extended.data.model.Player;
import com.lod.movie_extended.service.PlayerNotificationService;
import com.lod.movie_extended.util.Constants;

import java.io.IOException;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Жамбыл on 29.12.2015.
 */

public class FilmActivity extends Activity implements
        Player.Listener, Player.CaptionListener, Player.Id3MetadataListener,
        AudioCapabilitiesReceiver.Listener, Player.InternalErrorListener {

    private static final int LAYOUT = R.layout.activity_film;
    private static final String L_TAG = "FilmActivity";
    private MediaController mediaController;

    @Bind(R.id.controls_root)
    View debugRootView;
    @Bind(R.id.subtitles)
    SubtitleLayout subtitleLayout;
    @Bind(R.id.audio_controls)
    Button audioButton;
    @Bind(R.id.text_controls)
    Button textButton;
    @Bind(R.id.root)
    View root;


    private static final int MENU_GROUP_TRACKS = 1;

    private static final int ID_OFFSET = 2;
    private static final CookieManager defaultCookieManager;

    static {
        defaultCookieManager = new CookieManager();
        defaultCookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ORIGINAL_SERVER);
    }

    protected Player player;

    private long playerPosition;
    private boolean enableBackgroundAudio;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(LAYOUT);
        ButterKnife.bind(this);

        App.get(this).getAudioModule().setAudioUrl("http://stream-redirect.hktoolbar.com/radio-HTTP/cr2-hd.3gp/playlist.m3u8");

        player = App.get(this).getComponent().getPlayer();

        mediaController = new MediaController(this);
        mediaController.setAnchorView(root);

        root.setOnTouchListener((view, motionEvent) -> {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                toggleControlsVisibility();
            } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                view.performClick();
            }
            return true;
        });

        root.setOnKeyListener((v, keyCode, event) -> {
            if (keyCode == KeyEvent.KEYCODE_BACK || keyCode == KeyEvent.KEYCODE_ESCAPE
                    || keyCode == KeyEvent.KEYCODE_MENU) {
                return false;
            }
            return mediaController.dispatchKeyEvent(event);
        });

        CookieHandler currentHandler = CookieHandler.getDefault();
        if (currentHandler != defaultCookieManager) {
            CookieHandler.setDefault(defaultCookieManager);
        }

        AudioCapabilitiesReceiver audioCapabilitiesReceiver = new AudioCapabilitiesReceiver(this, this);
        audioCapabilitiesReceiver.register();
    }

    @Override
    public void onResume() {
        super.onResume();
        configureSubtitleView();

        preparePlayer(true);
        startPlayerService();
    }

    // AudioCapabilitiesReceiver.Listener methods

    @Override
    public void onAudioCapabilitiesChanged(AudioCapabilities audioCapabilities) {
        if (player == null) {
            return;
        }
        boolean playWhenReady = player.getPlayWhenReady();
        releasePlayer();
        preparePlayer(playWhenReady);
    }

    private void startPlayerService() {
        startServiceWithAction(Constants.ACTION.START_FOREGROUND_ACTION);
        startServiceWithAction(Constants.ACTION.PLAY_OR_PAUSE);
    }

    private void startServiceWithAction(String toRightAction) {
        Intent serviceIntent = new Intent(this, PlayerNotificationService.class);
        serviceIntent.setAction(toRightAction);
        startService(serviceIntent);
    }

    // Internal methods
    private void preparePlayer(boolean playWhenReady) {
        player.addListener(this);
        player.setCaptionListener(this);
        player.setMetadataListener(this);
        player.setInternalErrorListener(this);
        player.seekTo(playerPosition);
        mediaController.setMediaPlayer(player.getPlayerControl());
        mediaController.setEnabled(true);

        player.prepare();
        updateButtonVisibilities();

        player.setPlayWhenReady(playWhenReady);
    }

    private void releasePlayer() {
        if (player != null) {
            playerPosition = player.getCurrentPosition();
            player.release();
            player = null;
        }
    }

    // Player.Listener implementation
    @Override
    public void onStateChanged(boolean playWhenReady, int playbackState) {
        if (playbackState == ExoPlayer.STATE_ENDED) {
            showControls();
        }
        updateButtonVisibilities();
    }

    @Override
    public void onError(Exception e) {
        if (e instanceof UnsupportedDrmException) {
            // Special case DRM failures.
            int stringId = 0;
            Toast.makeText(getApplicationContext(), stringId, Toast.LENGTH_LONG).show();
        }
        updateButtonVisibilities();
        showControls();
    }

    // User controls

    private void updateButtonVisibilities() {
        audioButton.setVisibility(haveTracks(Player.TYPE_AUDIO) ? View.VISIBLE : View.GONE);
        textButton.setVisibility(haveTracks(Player.TYPE_TEXT) ? View.VISIBLE : View.GONE);
    }

    private boolean haveTracks(int type) {
        return player != null && player.getTrackCount(type) > 0;
    }

    public void showAudioPopup(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        Menu menu = popup.getMenu();
        menu.add(Menu.NONE, Menu.NONE, Menu.NONE,"enable background audio");
        final MenuItem backgroundAudioItem = menu.findItem(0);
        backgroundAudioItem.setCheckable(true);
        backgroundAudioItem.setChecked(enableBackgroundAudio);
        PopupMenu.OnMenuItemClickListener clickListener = item -> {
            if (item == backgroundAudioItem) {
                enableBackgroundAudio = !item.isChecked();
                return true;
            }
            return false;
        };
        configurePopupWithTracks(popup, clickListener, Player.TYPE_AUDIO);
        popup.show();
    }

    public void showTextPopup(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        configurePopupWithTracks(popup, null, Player.TYPE_TEXT);
        popup.show();
    }

    private void configurePopupWithTracks(PopupMenu popup,
                                          final PopupMenu.OnMenuItemClickListener customActionClickListener,
                                          final int trackType) {
        if (player == null) {
            return;
        }
        int trackCount = player.getTrackCount(trackType);
        if (trackCount == 0) {
            return;
        }
        popup.setOnMenuItemClickListener(item -> (customActionClickListener != null
                && customActionClickListener.onMenuItemClick(item))
                || onTrackItemClick(item, trackType));
        Menu menu = popup.getMenu();
        // ID_OFFSET ensures we avoid clashing with Menu.NONE (which equals 0)
        menu.add(MENU_GROUP_TRACKS, Player.TRACK_DISABLED + ID_OFFSET, Menu.NONE, "off");
        for (int i = 0; i < trackCount; i++) {
            menu.add(MENU_GROUP_TRACKS, i + ID_OFFSET, Menu.NONE,
                    buildTrackName(player.getTrackFormat(trackType, i)));
        }
        menu.setGroupCheckable(MENU_GROUP_TRACKS, true, true);
        menu.findItem(player.getSelectedTrack(trackType) + ID_OFFSET).setChecked(true);
    }

    private static String buildTrackName(MediaFormat format) {
        if (format.adaptive) {
            return "auto";
        }
        String trackName;
        if (MimeTypes.isAudio(format.mimeType)) {
            trackName = joinWithSeparator(joinWithSeparator(joinWithSeparator(buildLanguageString(format),
                    buildAudioPropertyString(format)), buildBitrateString(format)),
                    buildTrackIdString(format));
        } else {
            trackName = joinWithSeparator(joinWithSeparator(buildLanguageString(format),
                    buildBitrateString(format)), buildTrackIdString(format));
        }
        return trackName.length() == 0 ? "unknown" : trackName;
    }

    private static String buildResolutionString(MediaFormat format) {
        return format.width == MediaFormat.NO_VALUE || format.height == MediaFormat.NO_VALUE
                ? "" : format.width + "x" + format.height;
    }

    private static String buildAudioPropertyString(MediaFormat format) {
        return format.channelCount == MediaFormat.NO_VALUE || format.sampleRate == MediaFormat.NO_VALUE
                ? "" : format.channelCount + "ch, " + format.sampleRate + "Hz";
    }

    private static String buildLanguageString(MediaFormat format) {
        return TextUtils.isEmpty(format.language) || "und".equals(format.language) ? ""
                : format.language;
    }

    private static String buildBitrateString(MediaFormat format) {
        return format.bitrate == MediaFormat.NO_VALUE ? ""
                : String.format(Locale.US, "%.2fMbit", format.bitrate / 1000000f);
    }

    private static String joinWithSeparator(String first, String second) {
        return first.length() == 0 ? second : (second.length() == 0 ? first : first + ", " + second);
    }

    private static String buildTrackIdString(MediaFormat format) {
        return format.trackId == MediaFormat.NO_VALUE ? ""
                : String.format(Locale.US, " (%d)", format.trackId);
    }

    private boolean onTrackItemClick(MenuItem item, int type) {
        if (player == null || item.getGroupId() != MENU_GROUP_TRACKS) {
            return false;
        }
        player.setSelectedTrack(type, item.getItemId() - ID_OFFSET);
        return true;
    }

    private void toggleControlsVisibility()  {
        if (mediaController.isShowing()) {
            mediaController.hide();
            debugRootView.setVisibility(View.GONE);
        } else {
            showControls();
        }
    }

    private void showControls() {
        mediaController.show(0);
        debugRootView.setVisibility(View.VISIBLE);
    }

    // Player.CaptionListener implementation

    @Override
    public void onCues(List<Cue> cues) {
        subtitleLayout.setCues(cues);
    }

    // Player.MetadataListener implementation

    @Override
    public void onId3Metadata(Map<String, Object> metadata) {
        for (Map.Entry<String, Object> entry : metadata.entrySet()) {
            if (TxxxMetadata.TYPE.equals(entry.getKey())) {
                TxxxMetadata txxxMetadata = (TxxxMetadata) entry.getValue();
            } else if (PrivMetadata.TYPE.equals(entry.getKey())) {
                PrivMetadata privMetadata = (PrivMetadata) entry.getValue();
            } else if (GeobMetadata.TYPE.equals(entry.getKey())) {
                GeobMetadata geobMetadata = (GeobMetadata) entry.getValue();
            } else {
            }
        }
    }

    private void configureSubtitleView() {
        CaptionStyleCompat style;
        float fontScale;
        if (Util.SDK_INT >= 19) {
            style = getUserCaptionStyleV19();
            fontScale = getUserCaptionFontScaleV19();
        } else {
            style = CaptionStyleCompat.DEFAULT;
            fontScale = 1.0f;
        }
        subtitleLayout.setStyle(style);
        subtitleLayout.setFractionalTextSize(SubtitleLayout.DEFAULT_TEXT_SIZE_FRACTION * fontScale);
    }

    @TargetApi(19)
    private float getUserCaptionFontScaleV19() {
        CaptioningManager captioningManager =
                (CaptioningManager) getSystemService(Context.CAPTIONING_SERVICE);
        return captioningManager.getFontScale();
    }

    @TargetApi(19)
    private CaptionStyleCompat getUserCaptionStyleV19() {
        CaptioningManager captioningManager =
                (CaptioningManager) getSystemService(Context.CAPTIONING_SERVICE);
        return CaptionStyleCompat.createFromCaptionStyle(captioningManager.getUserStyle());
    }

    @Override
    public void onRendererInitializationError(Exception e) {
    }

    @Override
    public void onAudioTrackInitializationError(AudioTrack.InitializationException e) {
    }

    @Override
    public void onAudioTrackWriteError(AudioTrack.WriteException e) {
    }

    @Override
    public void onDecoderInitializationError(MediaCodecTrackRenderer.DecoderInitializationException e) {
    }

    @Override
    public void onCryptoError(MediaCodec.CryptoException e) {
    }

    @Override
    public void onLoadError(int sourceId, IOException e) {
    }

    @Override
    public void onDrmSessionManagerError(Exception e) {
    }
}

