package com.lod.movie_extended;

import android.app.Application;
import android.content.Context;

import com.lod.movie_extended.injection.component.application.ApplicationComponent;
import com.lod.movie_extended.injection.component.application.DaggerApplicationComponent;
import com.lod.movie_extended.injection.module.application.ApplicationModule;
import com.lod.movie_extended.injection.module.application.AuModule;
import com.lod.movie_extended.util.Logger;

import timber.log.Timber;

/**
 * Created by Жамбыл on 09.01.2016.
 */
public class App extends Application {

    ApplicationComponent mApplicationComponent;
    ApplicationModule applicationModule;
    AuModule audioModule;

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
            audioModule = new AuModule();

            mApplicationComponent = DaggerApplicationComponent.builder()
                    .applicationModule(applicationModule)
                    .auModule(audioModule)
                    .build();
        }
        return mApplicationComponent;
    }
}
