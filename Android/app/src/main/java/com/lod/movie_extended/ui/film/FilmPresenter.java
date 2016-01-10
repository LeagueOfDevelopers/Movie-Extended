package com.lod.movie_extended.ui.film;

import android.content.Context;
import android.content.Intent;
import android.media.MediaCodec;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.PopupMenu;

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
import com.google.android.exoplayer.text.Cue;
import com.google.android.exoplayer.util.MimeTypes;
import com.lod.movie_extended.App;
import com.lod.movie_extended.data.DataManager;
import com.lod.movie_extended.data.model.Player;
import com.lod.movie_extended.service.PlayerNotificationService;
import com.lod.movie_extended.ui.base.BasePresenter;
import com.lod.movie_extended.util.Constants;

import java.io.IOException;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Created by Жамбыл on 09.01.2016.
 */
public class FilmPresenter extends BasePresenter<FilmMvpView> implements
        Player.Listener, Player.CaptionListener, Player.Id3MetadataListener,
        AudioCapabilitiesReceiver.Listener, Player.InternalErrorListener {

    DataManager dataManager;

    private Context context;
    Player player;

    private long playerPosition;

    private static final CookieManager defaultCookieManager;

    private static final int MENU_GROUP_TRACKS = 1;

    private static final int ID_OFFSET = 2;

    static {
        defaultCookieManager = new CookieManager();
        defaultCookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ORIGINAL_SERVER);
    }

    public FilmPresenter(DataManager dataManager,Context context, Player player) {
        this.dataManager = dataManager;
        this.context = context;
        this.player = player;

        CookieHandler currentHandler = CookieHandler.getDefault();
        if (currentHandler != defaultCookieManager) {
            CookieHandler.setDefault(defaultCookieManager);
        }

        AudioCapabilitiesReceiver audioCapabilitiesReceiver = new AudioCapabilitiesReceiver(context, this);
        audioCapabilitiesReceiver.register();
    }


    public void startPlayerService() {
        startServiceWithAction(Constants.ACTION.START_FOREGROUND_ACTION);
        startServiceWithAction(Constants.ACTION.PLAY_OR_PAUSE);
    }


    private void startServiceWithAction(String toRightAction) {
        Intent serviceIntent = new Intent(context, PlayerNotificationService.class);
        serviceIntent.setAction(toRightAction);
        context.startService(serviceIntent);
    }

    public void preparePlayer(boolean playWhenReady) {
        player.addListener(this);
        player.setCaptionListener(this);
        player.setMetadataListener(this);
        player.setInternalErrorListener(this);
        player.seekTo(playerPosition);
        getMvpView().getMyMediaController().setMediaPlayer(player.getPlayerControl());
        getMvpView().getMyMediaController().setEnabled(true);

        player.prepare();
        getMvpView().updateButtonVisibilities();

        player.setPlayWhenReady(playWhenReady);
    }

    private void releasePlayer() {
        if (player != null) {
            playerPosition = player.getCurrentPosition();
            player.release();
            player = null;
        }
    }

    @Override
    public void onCues(List<Cue> cues) {
        getMvpView().getSubtitleLayout().setCues(cues);
    }

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

    @Override
    public void onAudioCapabilitiesChanged(AudioCapabilities audioCapabilities) {
        if (player == null) {
            return;
        }
        boolean playWhenReady = player.getPlayWhenReady();
        releasePlayer();
        preparePlayer(playWhenReady);
    }

    @Override
    public void onStateChanged(boolean playWhenReady, int playbackState) {
        if (playbackState == ExoPlayer.STATE_ENDED) {
            getMvpView().showControls();
        }
        getMvpView().updateButtonVisibilities();
    }

    public boolean haveTracks(int type) {
        return player != null && player.getTrackCount(type) > 0;
    }
    @Override
    public void onError(Exception e) {
        if (e instanceof UnsupportedDrmException) {
            // Special case DRM failures.
            int stringId = 0;
        }
        getMvpView().updateButtonVisibilities();
        getMvpView().showControls();
    }

    public void configurePopupWithTracks(PopupMenu popup,
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

}
