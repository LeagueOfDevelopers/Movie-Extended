package com.lod.movie_extended.data.model;

import android.media.AudioManager;
import android.media.MediaCodec;
import android.os.Handler;

import com.google.android.exoplayer.DummyTrackRenderer;
import com.google.android.exoplayer.ExoPlaybackException;
import com.google.android.exoplayer.ExoPlayer;
import com.google.android.exoplayer.MediaCodecAudioTrackRenderer;
import com.google.android.exoplayer.MediaCodecTrackRenderer;
import com.google.android.exoplayer.MediaFormat;
import com.google.android.exoplayer.TrackRenderer;
import com.google.android.exoplayer.audio.AudioTrack;
import com.google.android.exoplayer.text.Cue;
import com.google.android.exoplayer.text.TextRenderer;
import com.google.android.exoplayer.upstream.BandwidthMeter;
import com.lod.movie_extended.util.PlayerLogger;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import timber.log.Timber;

/**
 * Created by Жамбыл on 09.01.2016.
 */
public class Player implements ExoPlayer.Listener, MediaCodecAudioTrackRenderer.EventListener, TextRenderer {

    // Constants pulled into this class for convenience.
    public static final int STATE_IDLE = ExoPlayer.STATE_IDLE;

    public static final int STATE_PREPARING = ExoPlayer.STATE_PREPARING;
    public static final int STATE_BUFFERING = ExoPlayer.STATE_BUFFERING;
    public static final int STATE_READY = ExoPlayer.STATE_READY;
    public static final int STATE_ENDED = ExoPlayer.STATE_ENDED;
    public static final int RENDERER_COUNT = 2;
    public static final String EMPTY_AUDIO_URL = "empty";

    public static final int TYPE_AUDIO = 0;
    public static final int TYPE_TEXT = 1;
    private static final int RENDERER_BUILDING_STATE_IDLE = 1;

    private static final int RENDERER_BUILDING_STATE_BUILDING = 2;
    private static final int RENDERER_BUILDING_STATE_BUILT = 3;
    private final ExtractorRendererBuilder rendererBuilder;

    private final ExoPlayer player;
    private final Handler mainHandler;
    private final CopyOnWriteArrayList<Listener> listeners;
    private int rendererBuildingState;

    private int lastReportedPlaybackState;
    private boolean lastReportedPlayWhenReady;
    private CaptionListener captionListener;
    private AudioManager audioManager;
    private String audioUrl;
    private long firstCue = 158880;
    private PlayerLogger playerLogger;
    public String getAudioUrl() {
        return audioUrl;
    }


    public interface Listener {
        void onStateChanged(boolean playWhenReady, int playbackState);
        void onError(Exception e);
        void onWiredHeadsetNotOn();
    }

    public interface CaptionListener {
        void onCues(List<Cue> cues);
    }

    public Player(ExtractorRendererBuilder rendererBuilder, AudioManager audioManager) {
        Timber.v("constructor");
        this.rendererBuilder = rendererBuilder;
        this.audioManager = audioManager;
        player = ExoPlayer.Factory.newInstance(RENDERER_COUNT, 1000, 5000);
        player.addListener(this);
        mainHandler = new Handler();
        listeners = new CopyOnWriteArrayList<>();
        lastReportedPlaybackState = STATE_IDLE;
        rendererBuildingState = RENDERER_BUILDING_STATE_IDLE;
        audioUrl = EMPTY_AUDIO_URL;
        playerLogger = new PlayerLogger(this);
        disableSubtitles();
        startLogging();
    }


    public boolean isSubtitlesEnabled() {
        return player.getSelectedTrack(TYPE_TEXT) == ExoPlayer.TRACK_DEFAULT;
    }

    public void disableSubtitles() {
        player.setSelectedTrack(TYPE_TEXT, ExoPlayer.TRACK_DISABLED);
    }

    public void enableSubtitles() {
        player.setSelectedTrack(TYPE_TEXT, ExoPlayer.TRACK_DEFAULT);
    }

    private void stopLogging() {
        playerLogger.stopLogging();
    }

    private void startLogging() {
        playerLogger.startLogging();
    }

    public void setAudioUrl(String audioUrl) {
        this.audioUrl = audioUrl;
        rendererBuilder.setAudiUri(audioUrl);
        prepare();
    }

    public long getBufferedPosition() {
        return player.getBufferedPosition();
    }

    public void addListener(Listener listener) {
        listeners.add(listener);
    }

    public void removeListener(Listener listener) {
        listeners.remove(listener);
    }

    public void setCaptionListener(CaptionListener listener) {
        captionListener = listener;
        enableSubtitles();
    }
    public void removeCaptionListener() {
        captionListener = null;
        disableSubtitles();
    }

    public int getTrackCount(int type) {
        return player.getTrackCount(type);
    }

    public MediaFormat getTrackFormat(int type, int index) {
        return player.getTrackFormat(type, index);
    }

    public int getSelectedTrack(int type) {
        return player.getSelectedTrack(type);
    }

    public void prepare() {
        Timber.v("prepare");
        if (rendererBuildingState == RENDERER_BUILDING_STATE_BUILT) {
            player.stop();
        }
        rendererBuilder.cancel();
        rendererBuildingState = RENDERER_BUILDING_STATE_BUILDING;
        maybeReportPlayerState();
        Timber.v("renderBuilder.buildRenderers");
        rendererBuilder.buildRenderers(this);
        Timber.v("player seek to firstCue " + firstCue);
    }


    public void onRenderers(TrackRenderer[] renderers, BandwidthMeter bandwidthMeter) {
        Timber.v("onRenderers");
        for (int i = 0; i < RENDERER_COUNT; i++) {
            if (renderers[i] == null) {
                // Convert a null renderer to a dummy renderer.
                renderers[i] = new DummyTrackRenderer();
            }
        }
        player.prepare(renderers);
        rendererBuildingState = RENDERER_BUILDING_STATE_BUILT;
    }

    public void onRenderersError(Exception e) {
        for (Listener listener : listeners) {
            listener.onError(e);
        }
        rendererBuildingState = RENDERER_BUILDING_STATE_IDLE;
        maybeReportPlayerState();
    }

    public void onWiredHeadsetNotOn() {
        for (Listener listener : listeners) {
            listener.onWiredHeadsetNotOn();
        }
        maybeReportPlayerState();
    }

    public void setPlayWhenReady(boolean playWhenReady) {
        if(playWhenReady && !audioManager.isWiredHeadsetOn()) {
            onWiredHeadsetNotOn();
            return;
        }
        player.setPlayWhenReady(playWhenReady);
        maybeReportPlayerState();
    }

    public void release() {
        rendererBuilder.cancel();
        rendererBuildingState = RENDERER_BUILDING_STATE_IDLE;
        player.release();
    }

    public int getPlaybackState() {
        if (rendererBuildingState == RENDERER_BUILDING_STATE_BUILDING) {
            return STATE_PREPARING;
        }
        int playerState = player.getPlaybackState();
        if (rendererBuildingState == RENDERER_BUILDING_STATE_BUILT && playerState == STATE_IDLE) {
            // This is an edge case where the renderers are built, but are still being passed to the
            // player's playback thread.
            return STATE_PREPARING;
        }
        return playerState;
    }

    public long getCurrentPosition() {
        return player.getCurrentPosition();
    }

    public long getDuration() {
        return player.getDuration();
    }

    public int getBufferedPercentage() {
        return player.getBufferedPercentage();
    }

    public boolean getPlayWhenReady() {
        return player.getPlayWhenReady();
    }

    public void moveToLeft() {
    }

    public void moveToRight() {
    }

    public Handler getMainHandler() {
        return mainHandler;
    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int state) {
        Timber.v("onPlayerStateChanged " + playWhenReady);
        maybeReportPlayerState();
    }

    @Override
    public void onPlayerError(ExoPlaybackException exception) {
        Timber.e("PLAYER ERROR "  + exception.getMessage());
        rendererBuildingState = RENDERER_BUILDING_STATE_IDLE;
        for (Listener listener : listeners) {
            listener.onError(exception);
        }
    }

    @Override
    public void onPlayWhenReadyCommitted() {
        // Do nothing.
    }

    private void maybeReportPlayerState() {
        boolean playWhenReady = player.getPlayWhenReady();
        int playbackState = getPlaybackState();
        if (lastReportedPlayWhenReady != playWhenReady || lastReportedPlaybackState != playbackState) {
            for (Listener listener : listeners) {
                listener.onStateChanged(playWhenReady, playbackState);
            }
            lastReportedPlayWhenReady = playWhenReady;
            lastReportedPlaybackState = playbackState;
        }
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
    public void onDecoderInitialized(String decoderName, long elapsedRealtimeMs, long initializationDurationMs) {

    }

    @Override
    public void onCues(List<Cue> cues) {
        if(captionListener != null) {
            captionListener.onCues(cues);
        }
    }

}
