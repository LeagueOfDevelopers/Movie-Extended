package com.lod.movie_extended.injection.module.application;

import android.app.Application;
import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Context;
import android.graphics.BitmapFactory;

import com.lod.movie_extended.R;
import com.lod.movie_extended.data.local.DataBaseHelper;
import com.lod.movie_extended.data.local.PreferencesHelper;
import com.lod.movie_extended.data.model.ColorHelper;
import com.lod.movie_extended.data.model.NotificationServiceHelper;
import com.lod.movie_extended.data.remote.ServerAPI;
import com.lod.movie_extended.data.remote.ServerHelper;
import com.lod.movie_extended.injection.context.ApplicationContext;
import com.lod.movie_extended.injection.scope.PerApplication;
import com.lod.movie_extended.receiver.HeadsetEventReceiver;
import com.lod.movie_extended.receiver.PlayerReceiver;
import com.squareup.otto.Bus;

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
    @PerApplication
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
    ServerHelper provideServerHelper(ServerAPI serverAPI) {
        return new ServerHelper(serverAPI);
    }

    @Provides
    @PerApplication
    ServerAPI provideServer() {
        return ServerAPI.Creator.newService();
    }

    @Provides
    @PerApplication
    PreferencesHelper providePreferencesHelper() {
        return new PreferencesHelper();
    }

    @Provides
    @PerApplication
    NotificationManager provideNotificationManager(
            @ApplicationContext Context context) {
        return (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
    }

    @Provides
    @PerApplication
    ComponentName provideComponentName(
            @ApplicationContext Context context) {
        return new ComponentName(context, PlayerReceiver.class);
    }

    @Provides
    @PerApplication
    ColorHelper provideColorHelper(@ApplicationContext Context context) {
        return new ColorHelper(BitmapFactory.decodeResource(context.getResources(),
                R.mipmap.star_wars));
    }

    @Provides
    @PerApplication
    HeadsetEventReceiver provideHeadsetEventReceiver() {
        return new HeadsetEventReceiver();
    }

    @Provides
    @PerApplication
    NotificationServiceHelper provideServiceHelper(@ApplicationContext Context context) {
        return new NotificationServiceHelper(context);
    }
}
