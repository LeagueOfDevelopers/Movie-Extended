package com.lod.movie_extended.injection.component.application;

import android.app.Application;
import android.content.Context;

import com.lod.movie_extended.data.DataManager;
import com.lod.movie_extended.data.local.DataBaseHelper;
import com.lod.movie_extended.data.local.PreferencesHelper;
import com.lod.movie_extended.data.model.Player;
import com.lod.movie_extended.data.remote.ServerHelper;
import com.lod.movie_extended.injection.context.ApplicationContext;
import com.lod.movie_extended.injection.module.application.ApplicationModule;
import com.lod.movie_extended.injection.module.application.AudioModule;
import com.lod.movie_extended.injection.scope.PerApplication;
import com.lod.movie_extended.service.PlayerNotificationService;
import com.squareup.otto.Bus;

import dagger.Component;

/**
 * Created by Жамбыл on 09.01.2016.
 */
@PerApplication
@Component(
        modules = {
                ApplicationModule.class,
                AudioModule.class
        }
)
public interface ApplicationComponent{

    @ApplicationContext
    Context context();

    Application application();

    Bus getEventBus();

    DataManager getDataManager();

    Player getPlayer();

    ServerHelper getServerHelper();

    DataBaseHelper getDataBaseHelper();

    PreferencesHelper getPreferencesHelper();

    void inject(PlayerNotificationService playerNotificationService);
}
