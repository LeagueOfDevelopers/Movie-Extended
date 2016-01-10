package com.lod.movie_extended;

import android.app.Application;
import android.content.Context;

import com.crashlytics.android.Crashlytics;
import com.lod.movie_extended.injection.component.application.ApplicationComponent;
import com.lod.movie_extended.injection.component.application.DaggerApplicationComponent;
import com.lod.movie_extended.injection.module.application.ApplicationModule;
import com.lod.movie_extended.injection.module.application.AudioModule;
import com.lod.movie_extended.util.Logger;

import io.fabric.sdk.android.Fabric;
import timber.log.Timber;

/**
 * Created by Жамбыл on 09.01.2016.
 */
public class App extends Application {

    ApplicationComponent mApplicationComponent;
    ApplicationModule applicationModule;

    AudioModule audioModule;

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Logger());
            Fabric.with(this, new Crashlytics());
        }
    }

    public static App get(Context context) {
        return (App) context.getApplicationContext();
    }

    public ApplicationComponent getComponent() {
        if (mApplicationComponent == null) {

            applicationModule = new ApplicationModule(this);
            audioModule = new AudioModule();

            mApplicationComponent = DaggerApplicationComponent.builder()
                    .audioModule(audioModule)
                    .applicationModule(applicationModule)
                    .build();
        }
        return mApplicationComponent;
    }

    public void setAudioUrl(String url) {
        audioModule.setAudioUrl(url);
    }

    public void setComponent(ApplicationComponent applicationComponent) {
        mApplicationComponent = applicationComponent;
    }
}
