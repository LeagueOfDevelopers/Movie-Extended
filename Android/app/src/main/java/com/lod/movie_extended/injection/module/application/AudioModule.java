package com.lod.movie_extended.injection.module.application;

import android.content.Context;
import android.net.Uri;

import com.google.android.exoplayer.util.Util;
import com.lod.movie_extended.R;
import com.lod.movie_extended.data.model.Player;
import com.lod.movie_extended.injection.context.ApplicationContext;
import com.lod.movie_extended.injection.scope.PerApplication;
import com.lod.movie_extended.data.model.ExtractorRendererBuilder;
import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import timber.log.Timber;

/**
 * Created by Жамбыл on 09.01.2016.
 */
@Module(includes = ApplicationModule.class)
public class AudioModule {


    @Provides
    @PerApplication
    Player providePlayer(ExtractorRendererBuilder hlsRendererBuilder) {
        return new Player(hlsRendererBuilder);
    }

    @Provides
    @PerApplication
    ExtractorRendererBuilder provideHlsRendererBuilder(@ApplicationContext Context context,
                                                       @Named("userAgent") String userAgent) {
        return new ExtractorRendererBuilder(context, userAgent);
    }

    @Provides
    @Named("userAgent")
    String provideUserAgent(@ApplicationContext Context context) {
        return Util.getUserAgent(context, context.getResources().getString(R.string.app_name));
    }

}
