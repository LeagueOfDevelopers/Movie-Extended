package com.lod.movie_extended.data.model.player;

import android.media.AudioManager;
import android.media.MediaCodec;
import android.net.Uri;
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
import com.lod.movie_extended.injection.App;

import java.util.Date;
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
    @Inject
    TimeHelper timeHelper;

    public Player() {
        Timber.v("constructor");
        App.getInstance().getComponent().inject(this);
        player.addListener(this);
        listeners = new CopyOnWriteArrayList<>();
        disableSubtitles();
        playerLogger.setPlayer(this);
     }

    public boolean setPlayWhenReady(boolean playWhenReady) {
        if(!player.getPlayWhenReady()) {
            player.setPlayWhenReady(true);
            processPlay();
            mute();
        }
        if (playWhenReady && checkHeadset()) {
            return false;
        }

        if(playWhenReady && !getPlayWhenReady()) {
            processPlay();
        }
        else if(!playWhenReady && getPlayWhenReady()){
            processPause();
        }

        maybeReportPlayerState();
        return true;
    }

    public boolean getPlayWhenReady() {
        return isPlaying;
    }

    public void setFilmStartTime(Date filmStartTime) {
        timeHelper.setFilmStartTime(filmStartTime);
    }

    public void startAudioWithSubtitles(Uri audioUrl, Uri subtitlesUrl) {
        if(!playerLogger.isWorking()) {
            startLogging();
        }
        rendererBuilder.startBuildingRenderers(this,audioUrl,subtitlesUrl);
        setPlayWhenReady(true);

        timeHelper.setFilmDuration(player.getDuration());
        maybeReportPlayerState();
    }

    public void setAudioUrl(Uri audioUrl) {
        rendererBuilder.setAudioUrl(audioUrl);
    }

    public void setSubtitleUrl(Uri subtitleUrl) {
        rendererBuilder.setSubtitlesUrl(subtitleUrl);
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

    public void notifyHeadsetNotOn() {
        if(listeners.isEmpty()){
            return;
        }
        for (PlayerListener listener : listeners) {
            listener.onWiredHeadsetNotOn();
        }

        maybeReportPlayerState();
    }

    public void notifyHeadsetOn() {
        if(listeners.isEmpty()){
            return;
        }
        for (PlayerListener listener : listeners) {
            listener.onWiredHeadsetOn();
        }
        maybeReportPlayerState();
    }

    public void onStop() {
        stopLogging();
    }

    private boolean checkHeadset() {
        if(!audioManager.isWiredHeadsetOn()) {
            notifyHeadsetNotOn();
            return true;
        }
        return false;
    }

    private void processPause() {
        mute();
    }

    private void processPlay() {
        long seekTime = timeHelper.getCurrentFilmTime();
        Timber.e("SEEKING TO " + seekTime);
        if(seekTime != -1) {
            player.seekTo(seekTime);
        }
        unmute();
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

    public void seekTo(long to) {
        player.seekTo(to);
    }
}
