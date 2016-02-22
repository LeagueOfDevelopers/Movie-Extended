package com.lod.movie_extended;

import android.app.Application;
import android.content.Context;

import com.lod.movie_extended.injection.component.application.ApplicationComponent;
import com.lod.movie_extended.injection.component.application.DaggerApplicationComponent;
import com.lod.movie_extended.injection.module.application.ApplicationModule;
import com.lod.movie_extended.injection.module.application.AudioModuleNew;
import com.lod.movie_extended.util.Logger;

import timber.log.Timber;

/**
 * Created by Жамбыл on 09.01.2016.
 */
public class App extends Application {

    ApplicationComponent mApplicationComponent;
    ApplicationModule applicationModule;

    AudioModuleNew audioModule;

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Logger());
            //Fabric.with(this, new Crashlytics());
        }
    }

    public static App get(Context context) {
        return (App) context.getApplicationContext();
    }

    public ApplicationComponent getComponent() {
        if (mApplicationComponent == null) {

            applicationModule = new ApplicationModule(this);
            audioModule = new AudioModuleNew();

            mApplicationComponent = DaggerApplicationComponent.builder()
                    .audioModuleNew(audioModule)
                    .applicationModule(applicationModule)
                    .build();
        }
        return mApplicationComponent;
    }

    public void setComponent(ApplicationComponent applicationComponent) {
        mApplicationComponent = applicationComponent;
    }
}
