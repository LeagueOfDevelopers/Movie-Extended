package com.lod.movie_extended.injection.module.application;

import android.content.Context;
import android.media.AudioManager;
import android.os.Handler;

import com.google.android.exoplayer.ExoPlayer;
import com.lod.movie_extended.data.model.player.ExtractorRendererBuilder;
import com.lod.movie_extended.data.model.player.Player;
import com.lod.movie_extended.data.model.player.PlayerLogger;
import com.lod.movie_extended.injection.context.ApplicationContext;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

/**
 * Created by Жамбыл on 3/2/2016.
 */
@Module
public class AudioModuleTest {

    @Provides
    ExoPlayer provideExoPlayer() {
        return mock(ExoPlayer.class);
    }

    @Provides
    Player providePlayer() {
        return mock(Player.class);
    }

    @Provides
    ExtractorRendererBuilder provideExtractorRendererBuilder(@ApplicationContext Context context, @Named("userAgent") String userAgent) {
        return mock(ExtractorRendererBuilder.class);
    }

    @Provides
    @Named("userAgent")
    String provideUserAgent(@ApplicationContext Context context) {
        return "";
    }

    @Provides
    AudioManager provideAudioManager(@ApplicationContext Context context) {
        return mock(AudioManager.class);
    }

    @Provides
    Handler provideHandler() {
        return mock(Handler.class);
    }

    @Provides
    PlayerLogger providePlayerLogger() {
        PlayerLogger playerLogger = mock(PlayerLogger.class);
        doReturn(2).when(playerLogger).test();
        return playerLogger;
    }
}
