package com.lod.movie_extended.injection.module.application;

import android.app.Application;
import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Context;

import com.lod.movie_extended.data.local.DataBaseHelper;
import com.lod.movie_extended.data.local.PreferencesHelper;
import com.lod.movie_extended.data.model.ColorHelper;
import com.lod.movie_extended.data.model.ServiceHelper;
import com.lod.movie_extended.data.remote.Server;
import com.lod.movie_extended.data.remote.ServerHelper;
import com.lod.movie_extended.injection.context.ApplicationContext;
import com.lod.movie_extended.injection.scope.PerApplication;
import com.lod.movie_extended.receiver.HeadsetEventReceiver;
import com.squareup.otto.Bus;

import dagger.Module;
import dagger.Provides;

import static org.mockito.Mockito.mock;

/**
 * Created by Жамбыл on 3/2/2016.
 */
@Module
public class ApplicationModuleTest{

    public ApplicationModuleTest(Application application) {

    }

    @Provides
    Application provideApplication() {
        return mock(Application.class);
    }

    @Provides
    @ApplicationContext
    @PerApplication
    Context provideContext() {
        return mock(Context.class);
    }

    @Provides
    @PerApplication
    Bus provideEventBus() {
        return mock(Bus.class);
    }

    @Provides
    @PerApplication
    DataBaseHelper provideDataBaseHelper() {
        return mock(DataBaseHelper.class);
    }

    @Provides
    @PerApplication
    ServerHelper provideServerHelper() {
        return mock(ServerHelper.class);
    }

    @Provides
    @PerApplication
    Server provideServer() {
        return mock(Server.class);
    }

    @Provides
    @PerApplication
    PreferencesHelper providePreferencesHelper() {
        return mock(PreferencesHelper.class);
    }

    @Provides
    @PerApplication
    NotificationManager provideNotificationManager(@ApplicationContext Context context) {
        return mock(NotificationManager.class);
    }

    @Provides
    @PerApplication
    ComponentName provideComponentName(@ApplicationContext Context context) {
        return mock(ComponentName.class);
    }

    @Provides
    @PerApplication
    ColorHelper provideColorHelper(@ApplicationContext Context context) {
        return mock(ColorHelper.class);
    }

    @Provides
    @PerApplication
    HeadsetEventReceiver provideHeadsetEventReceiver() {
        return mock(HeadsetEventReceiver.class);
    }

    @Provides
    @PerApplication
    ServiceHelper provideServiceHelper(@ApplicationContext Context context) {
        return mock(ServiceHelper.class);
    }
}
