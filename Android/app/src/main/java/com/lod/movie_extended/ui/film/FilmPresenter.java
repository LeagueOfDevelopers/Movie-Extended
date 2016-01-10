package com.lod.movie_extended.ui.film;

import android.content.Context;
import android.media.MediaCodec;

import com.google.android.exoplayer.ExoPlayer;
import com.google.android.exoplayer.MediaCodecTrackRenderer;
import com.google.android.exoplayer.audio.AudioCapabilities;
import com.google.android.exoplayer.audio.AudioCapabilitiesReceiver;
import com.google.android.exoplayer.audio.AudioTrack;
import com.google.android.exoplayer.drm.UnsupportedDrmException;
import com.google.android.exoplayer.metadata.GeobMetadata;
import com.google.android.exoplayer.metadata.PrivMetadata;
import com.google.android.exoplayer.metadata.TxxxMetadata;
import com.google.android.exoplayer.text.Cue;
import com.lod.movie_extended.App;
import com.lod.movie_extended.data.DataManager;
import com.lod.movie_extended.data.model.Player;
import com.lod.movie_extended.ui.base.BasePresenter;

import java.io.IOException;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.List;
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

    static {
        defaultCookieManager = new CookieManager();
        defaultCookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ORIGINAL_SERVER);
    }

    public FilmPresenter(DataManager dataManager, Context context) {
        this.dataManager = dataManager;
        this.context = context;

        CookieHandler currentHandler = CookieHandler.getDefault();
        if (currentHandler != defaultCookieManager) {
            CookieHandler.setDefault(defaultCookieManager);
        }

        AudioCapabilitiesReceiver audioCapabilitiesReceiver = new AudioCapabilitiesReceiver(context, this);
        audioCapabilitiesReceiver.register();
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
}
