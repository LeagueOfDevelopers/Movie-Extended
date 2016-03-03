package com.lod.movie_extended.injection.module.application;

import android.content.Context;
import android.media.AudioManager;
import android.os.Handler;

import com.google.android.exoplayer.ExoPlayer;
import com.google.android.exoplayer.util.Util;
import com.lod.movie_extended.R;
import com.lod.movie_extended.data.model.player.ExtractorRendererBuilder;
import com.lod.movie_extended.data.model.player.Player;
import com.lod.movie_extended.data.model.player.TimeHelper;
import com.lod.movie_extended.injection.context.ApplicationContext;
import com.lod.movie_extended.injection.scope.PerApplication;
import com.lod.movie_extended.data.model.player.PlayerLogger;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Жамбыл on 2/23/2016.
 */
@Module
public class AudioModule {

    @Provides
    @PerApplication
    ExoPlayer provideExoPlayer() {
        return ExoPlayer.Factory.newInstance(Player.RENDERER_COUNT, 1000, 5000);
    }

    @Provides
    @PerApplication
    Player providePlayer() {
        return new Player();
    }

    @Provides
    @PerApplication
    ExtractorRendererBuilder provideExtractorRendererBuilder(@ApplicationContext Context context,
                                                             @Named("userAgent") String userAgent) {
        return new ExtractorRendererBuilder(context, userAgent);
    }

    @Provides
    @PerApplication
    @Named("userAgent")
    String provideUserAgent(@ApplicationContext Context context) {
        return Util.getUserAgent(context, context.getResources().getString(R.string.app_name));
    }


    @Provides
    @PerApplication
    AudioManager provideAudioManager(
            @ApplicationContext Context context) {
        return (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
    }

    @Provides
    @PerApplication
    TimeHelper provideTimeHelper() {
        return new TimeHelper();
    }

    @Provides
    @PerApplication
    Handler provideHandler() {
        return new Handler();
    }

    @Provides
    @PerApplication
    PlayerLogger providePlayerLogger() {
        return new PlayerLogger();
    }
}
