package com.lod.movie_extended.injection;


import android.app.Application;
import android.content.Context;

/**
 * Created by Жамбыл on 27.11.2015.
 */
public class MyApp  extends Application {

    MyAppComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        component = DaggerMyAppComponent.builder().myAppModule(new MyAppModule(this)).build();
    }

    public static MyApp get(Context context){
        return (MyApp)context.getApplicationContext();
    }

    public MyAppComponent getComponent() {
        return component;
    }

}
