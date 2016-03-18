package com.lod.movie_extended.injection;

import android.app.Application;

import com.lod.movie_extended.BuildConfig;
import com.lod.movie_extended.R;
import com.lod.movie_extended.injection.component.application.ApplicationComponent;
import com.lod.movie_extended.injection.component.application.DaggerApplicationComponent;
import com.lod.movie_extended.injection.module.application.ApplicationModule;
import com.lod.movie_extended.injection.module.application.AudioModule;
import com.lod.movie_extended.util.Logger;
import timber.log.Timber;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by Жамбыл on 09.01.2016.
 */
public class App extends Application {

    private static App INSTANCE;
    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;
        if (BuildConfig.DEBUG) {
            Timber.plant(new Logger());
        }
    }

    public static App getInstance() {
        return INSTANCE;
    }

    public ApplicationComponent getComponent() {
        if (applicationComponent == null) {

            ApplicationModule applicationModule = new ApplicationModule(this);
            AudioModule audioModule = new AudioModule();

            applicationComponent = DaggerApplicationComponent.builder()
                    .applicationModule(applicationModule)
                    .audioModule(audioModule)
                    .build();
        }
        return applicationComponent;
    }

    public void setComponent(ApplicationComponent applicationComponent) {
        this.applicationComponent = applicationComponent;
    }
}
