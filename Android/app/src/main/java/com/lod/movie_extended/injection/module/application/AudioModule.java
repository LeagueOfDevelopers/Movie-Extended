package com.lod.movie_extended.injection.module.application;

import android.content.Context;

import com.google.android.exoplayer.util.Util;
import com.lod.movie_extended.R;
import com.lod.movie_extended.data.model.Player;
import com.lod.movie_extended.injection.context.ApplicationContext;
import com.lod.movie_extended.injection.scope.PerApplication;
import com.lod.movie_extended.util.HlsRendererBuilder;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Жамбыл on 09.01.2016.
 */
@Module(includes = ApplicationModule.class)
public class AudioModule {

    private String audioUrl;

    public void setAudioUrl(String audioUrl) {
        this.audioUrl = audioUrl;
    }

    @Provides
    @PerApplication
    Player providePlayer(HlsRendererBuilder hlsRendererBuilder) {
        return new Player(hlsRendererBuilder);
    }

    @Provides
    @PerApplication
    HlsRendererBuilder provideHlsRendererBuilder(@ApplicationContext Context context, @Named("userAgent") String userAgent,
                                                 @Named("audioUrl") String audioUrl) {
        return new HlsRendererBuilder(context, userAgent, audioUrl);
    }

    @Provides
    @Named("userAgent")
    String provideUserAgent(@ApplicationContext Context context) {
        return Util.getUserAgent(context, context.getResources().getString(R.string.app_name));
    }

    @Provides
    @Named("audioUrl")
    String provideAudioUri() {
        return audioUrl;
    }

}
