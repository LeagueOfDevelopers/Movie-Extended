package com.lod.movie_extended.test.module.application;

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
    Context provideContext() {
        return mock(Context.class);
    }

    @Provides
    Bus provideEventBus() {
        return mock(Bus.class);
    }

    @Provides
    DataBaseHelper provideDataBaseHelper() {
        return mock(DataBaseHelper.class);
    }

    @Provides
    ServerHelper provideServerHelper() {
        return mock(ServerHelper.class);
    }

    @Provides
    Server provideServer() {
        return mock(Server.class);
    }

    @Provides
    PreferencesHelper providePreferencesHelper() {
        return mock(PreferencesHelper.class);
    }

    @Provides
    NotificationManager provideNotificationManager(@ApplicationContext Context context) {
        return mock(NotificationManager.class);
    }

    @Provides
    ComponentName provideComponentName(@ApplicationContext Context context) {
        return mock(ComponentName.class);
    }

    @Provides
    ColorHelper provideColorHelper(@ApplicationContext Context context) {
        return mock(ColorHelper.class);
    }

    @Provides
    HeadsetEventReceiver provideHeadsetEventReceiver() {
        return mock(HeadsetEventReceiver.class);
    }

    @Provides
    ServiceHelper provideServiceHelper(@ApplicationContext Context context) {
        return mock(ServiceHelper.class);
    }
}
