package com.lod.movie_extended.injection.module.application;

import android.content.Context;
import android.media.AudioManager;

import com.google.android.exoplayer.util.Util;
import com.lod.movie_extended.R;
import com.lod.movie_extended.data.model.ExtractorRendererBuilder;
import com.lod.movie_extended.data.model.Player;
import com.lod.movie_extended.injection.context.ApplicationContext;
import com.lod.movie_extended.injection.scope.PerApplication;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Жамбыл on 2/23/2016.
 */
@Module
public class AuModule {

    @Provides
    @PerApplication
    Player providePlayer(ExtractorRendererBuilder hlsRendererBuilder, AudioManager audioManager) {
        return new Player(hlsRendererBuilder, audioManager);
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
}
