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

import timber.log.Timber;

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

    public FilmPresenter(DataManager dataManager,Context context, Player player) {
        this.dataManager = dataManager;
        this.context = context;
        this.player = player;
    }

    public void startPlayerNotificationService() {
        Timber.v("starting PlayerNotificationService");
        startServiceWithAction(Constants.ACTION.START_FOREGROUND_ACTION);
        startServiceWithAction(Constants.ACTION.PLAY_OR_PAUSE);
    }

    private void startServiceWithAction(String toRightAction) {
        Intent serviceIntent = new Intent(context, PlayerNotificationService.class);
        serviceIntent.setAction(toRightAction);
        context.startService(serviceIntent);
    }

    public void preparePlayer(boolean playWhenReady) {
        Timber.v("preparing player");
        player.addListener(this);
        player.setCaptionListener(this);
        player.setMetadataListener(this);
        player.setInternalErrorListener(this);
        player.seekTo(playerPosition);

        player.prepare();

        player.setPlayWhenReady(playWhenReady);
    }

    private void releasePlayer() {
        Timber.v("releasing player");
        if (player != null) {
            playerPosition = player.getCurrentPosition();
            player.release();
            player = null;
        }
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
        //TODO set change
    }

    @Override
    public void onError(Exception e) {
        if (e instanceof UnsupportedDrmException) {
            // Special case DRM failures.
            int stringId = 0;
        }
    }

    public void removeListener() {
        player.removeListener(this);
    }

    // region metadata and subtitles
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
    //endregion
    //region Errors
    @Override
    public void onRendererInitializationError(Exception e) {
        Timber.e(e.getMessage());
    }

    @Override
    public void onAudioTrackInitializationError(AudioTrack.InitializationException e) {
        Timber.e(e.getMessage());
    }

    @Override
    public void onAudioTrackWriteError(AudioTrack.WriteException e) {
        Timber.e(e.getMessage());
    }

    @Override
    public void onDecoderInitializationError(MediaCodecTrackRenderer.DecoderInitializationException e) {
        Timber.e(e.getMessage());
    }

    @Override
    public void onCryptoError(MediaCodec.CryptoException e) {
        Timber.e(e.getMessage());
    }

    @Override
    public void onLoadError(int sourceId, IOException e) {
        Timber.e("load error " + e.getMessage());
    }

    @Override
    public void onDrmSessionManagerError(Exception e) {
        Timber.e(e.getMessage());
    }
    //endregion

}
