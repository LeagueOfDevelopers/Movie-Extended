package com.lod.movie_extended.data.model.player;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaCodec;
import android.os.Handler;

import com.google.android.exoplayer.DummyTrackRenderer;
import com.google.android.exoplayer.ExoPlaybackException;
import com.google.android.exoplayer.ExoPlayer;
import com.google.android.exoplayer.MediaCodecAudioTrackRenderer;
import com.google.android.exoplayer.MediaCodecTrackRenderer;
import com.google.android.exoplayer.TrackRenderer;
import com.google.android.exoplayer.audio.AudioTrack;
import com.google.android.exoplayer.text.Cue;
import com.google.android.exoplayer.text.TextRenderer;
import com.google.android.exoplayer.upstream.BandwidthMeter;
import com.lod.movie_extended.App;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.inject.Inject;

import timber.log.Timber;

/**
 * Created by Жамбыл on 09.01.2016.
 */
public class Player implements ExoPlayer.Listener, MediaCodecAudioTrackRenderer.EventListener, TextRenderer {

    public static final int RENDERER_COUNT = 2;

    public static final int TYPE_AUDIO = 0;
    public static final int TYPE_TEXT = 1;
    public boolean hasAudioUrlBeenSet;
    private final CopyOnWriteArrayList<PlayerListener> listeners;
    private boolean lastReportedPlayWhenReady;
    private CaptionListener captionListener;
    private TrackRenderer audioRenderer;
    private boolean isPlaying;

    @Inject
    ExtractorRendererBuilder rendererBuilder;
    @Inject
    ExoPlayer player;
    @Inject
    Handler mainHandler;
    @Inject
    AudioManager audioManager;
    @Inject
    PlayerLogger playerLogger;

    public Player(Context context) {
        Timber.v("constructor");
        App.get(context).getComponent().inject(this);
        player.addListener(this);
        listeners = new CopyOnWriteArrayList<>();
        playerLogger.setPlayer(this);
        disableSubtitles();
    }

    public void setPlayWhenReady(boolean playWhenReady) {
        if(playWhenReady && !audioManager.isWiredHeadsetOn()) {
            onWiredHeadsetNotOn();
            return;
        }
        if(playWhenReady) {
            if(!player.getPlayWhenReady()) {
                player.setPlayWhenReady(true);
            }
            unmute();
        }
        else {
            mute();
        }
        maybeReportPlayerState();
    }

    public boolean getPlayWhenReady() {
        return isPlaying;
    }

    public void startAudio(String audioUrl) {
        rendererBuilder.startBuildingRenderers(this,audioUrl);
        startLogging();
    }

    public boolean isSubtitlesEnabled() {
        return player.getSelectedTrack(TYPE_TEXT) == ExoPlayer.TRACK_DEFAULT;
    }

    public long getBufferedPosition() {
        return player.getBufferedPosition();
    }

    public void addListener(PlayerListener listener) {
        listeners.add(listener);
    }

    public void removeListener(PlayerListener listener) {
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

    public void onRenderers(TrackRenderer[] renderers, BandwidthMeter bandwidthMeter) {
        Timber.v("onRenderers");
        for (int i = 0; i < RENDERER_COUNT; i++) {
            if (renderers[i] == null) {
                renderers[i] = new DummyTrackRenderer();
            }
        }
        audioRenderer = renderers[Player.TYPE_AUDIO];
        player.prepare(renderers);
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

    public Handler getMainHandler() {
        return mainHandler;
    }

    private void onWiredHeadsetNotOn() {
        for (PlayerListener listener : listeners) {
            listener.onWiredHeadsetNotOn();
        }
        maybeReportPlayerState();
    }


    private void mute() {
        isPlaying = false;
        setVolume(0f);
    }

    private void disableSubtitles() {
        player.setSelectedTrack(TYPE_TEXT, ExoPlayer.TRACK_DISABLED);
    }

    private void enableSubtitles() {
        player.setSelectedTrack(TYPE_TEXT, ExoPlayer.TRACK_DEFAULT);
    }

    private void setVolume(float volume) {
        player.sendMessage(audioRenderer,MediaCodecAudioTrackRenderer.MSG_SET_VOLUME, volume);
    }

    private void unmute() {
        isPlaying = true;
        setVolume(1f);
    }

    private void maybeReportPlayerState() {
        boolean playWhenReady = isPlaying;
        if (lastReportedPlayWhenReady != playWhenReady) {
            for (PlayerListener listener : listeners) {
                listener.onStateChanged(playWhenReady);
            }
            lastReportedPlayWhenReady = playWhenReady;
        }
    }

    private void stopLogging() {
        playerLogger.stopLogging();
    }

    private void startLogging() {
        playerLogger.startLogging();
    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int state) {
        Timber.v("onPlayerStateChanged " + playWhenReady);
        maybeReportPlayerState();
    }

    @Override
    public void onPlayerError(ExoPlaybackException exception) {
        Timber.e("PLAYER ERROR "  + exception.getMessage());
        for (PlayerListener listener : listeners) {
            listener.onError(exception);
        }
    }

    @Override
    public void onPlayWhenReadyCommitted() {
        // Do nothing.
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
