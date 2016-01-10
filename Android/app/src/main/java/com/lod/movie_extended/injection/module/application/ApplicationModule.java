package com.lod.movie_extended.injection.module.application;

import android.app.Application;
import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Context;
import android.media.AudioManager;

import com.lod.movie_extended.data.local.DataBaseHelper;
import com.lod.movie_extended.data.local.PreferencesHelper;
import com.lod.movie_extended.data.remote.ServerHelper;
import com.lod.movie_extended.injection.context.ApplicationContext;
import com.lod.movie_extended.injection.scope.PerApplication;
import com.lod.movie_extended.receiver.PlayerReceiver;
import com.squareup.otto.Bus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Жамбыл on 09.01.2016.
 */
@Module
public class ApplicationModule {

    protected final Application mApplication;

    public ApplicationModule(Application application) {
        mApplication = application;
    }

    @Provides
    Application provideApplication() {
        return mApplication;
    }

    @Provides
    @ApplicationContext
    Context provideContext() {
        return mApplication;
    }

    @Provides
    @PerApplication
    Bus provideEventBus() {
        return new Bus();
    }

    @Provides
    @PerApplication
    DataBaseHelper provideDataBaseHelper() {
        return new DataBaseHelper();
    }

    @Provides
    @PerApplication
    ServerHelper provideServerHelper() {
        return new ServerHelper();
    }

    @Provides
    @PerApplication
    PreferencesHelper providePrefrencesHelper() {
        return new PreferencesHelper();
    }

    @Provides
    NotificationManager provideNotificationManager(
            @ApplicationContext Context context) {
        return (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
    }

    @Provides
    AudioManager provideAudioManager(
            @ApplicationContext Context context) {
        return (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
    }

    @Provides
    ComponentName provideComponentName(
            @ApplicationContext Context context) {
        return new ComponentName(context, PlayerReceiver.class);
    }

}
