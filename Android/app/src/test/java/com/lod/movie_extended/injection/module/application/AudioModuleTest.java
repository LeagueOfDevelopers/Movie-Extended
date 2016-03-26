package com.lod.movie_extended.injection.module.application;

import android.content.Context;
import android.media.AudioManager;
import android.os.Handler;

import com.google.android.exoplayer.ExoPlayer;
import com.lod.movie_extended.data.model.player.ExtractorRendererBuilder;
import com.lod.movie_extended.data.model.player.Player;
import com.lod.movie_extended.data.model.player.PlayerLogger;
import com.lod.movie_extended.data.model.player.TimeHelper;
import com.lod.movie_extended.injection.context.ApplicationContext;
import com.lod.movie_extended.injection.scope.PerApplication;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by Жамбыл on 3/2/2016.
 */
@Module
public class AudioModuleTest {

    private AudioManager audioManager;
    private TimeHelper timeHelper;
    private ExoPlayer exoPlayer;
    private ExtractorRendererBuilder extractorRendererBuilder;
    private PlayerLogger playerLogger;

    public AudioModuleTest() {
        audioManager = mock(AudioManager.class);
        timeHelper = mock(TimeHelper.class);
        extractorRendererBuilder = mock(ExtractorRendererBuilder.class);
        playerLogger = mock(PlayerLogger.class);
        exoPlayer = ExoPlayer.Factory.newInstance(Player.RENDERER_COUNT, 1000, 5000);
        when(audioManager.isWiredHeadsetOn()).thenReturn(true);
    }

    @Provides
    ExoPlayer provideExoPlayer() {
        return getExoPlayer();
    }

    @Provides
    Player providePlayer() {
        return mock(Player.class);
    }

    @Provides
    ExtractorRendererBuilder provideExtractorRendererBuilder(@ApplicationContext Context context, @Named("userAgent") String userAgent) {
        return extractorRendererBuilder;
    }

    @Provides
    @Named("userAgent")
    String provideUserAgent(@ApplicationContext Context context) {
        return "";
    }

    @Provides
    AudioManager provideAudioManager(@ApplicationContext Context context) {
        return audioManager;
    }

    @Provides
    @PerApplication
    TimeHelper provideTimeHelper() {
        return timeHelper;
    }

    @Provides
    Handler provideHandler() {
        return mock(Handler.class);
    }

    @Provides
    PlayerLogger providePlayerLogger() {
        return playerLogger;
    }


    public ExtractorRendererBuilder getExtractorRendererBuilder() {
        return extractorRendererBuilder;
    }

    public void setExtractorRendererBuilder(ExtractorRendererBuilder extractorRendererBuilder) {
        this.extractorRendererBuilder = extractorRendererBuilder;
    }

    public PlayerLogger getPlayerLogger() {
        return playerLogger;
    }

    public void setPlayerLogger(PlayerLogger playerLogger) {
        this.playerLogger = playerLogger;
    }

    public AudioManager getAudioManager() {
        return audioManager;
    }

    public void setAudioManager(AudioManager audioManager) {
        this.audioManager = audioManager;
    }

    public TimeHelper getTimeHelper() {
        return timeHelper;
    }

    public void setTimeHelper(TimeHelper timeHelper) {
        this.timeHelper = timeHelper;
    }

    public ExoPlayer getExoPlayer() {
        return exoPlayer;
    }

    public void setExoPlayer(ExoPlayer exoPlayer) {
        this.exoPlayer = exoPlayer;
    }

    public void setAudioManagerMock(AudioManager audioManagerMock) {
        this.audioManager =  audioManagerMock;
    }

    public void setTimeHelperMock(TimeHelper timeHelper) {
        this.timeHelper = timeHelper;
    }

    public void setExoPlayerMock(ExoPlayer exoPlayer) {
        this.exoPlayer = exoPlayer;
    }
}
