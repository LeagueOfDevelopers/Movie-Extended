package com.lod.movie_extended.injection;


import android.app.Application;
import android.content.Context;

/**
 * Created by Жамбыл on 27.11.2015.
 */
public class MyApp extends Application {

    public static final String TAG = "MyApp1 ";

    MyAppComponent component;
    ServerComponent serverComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        component = DaggerMyAppComponent.builder().myAppModule(new MyAppModule(this)).build();
        serverComponent = DaggerServerComponent.builder().serverModule(new ServerModule(this)).build();
    }

    public static MyApp get(Context context){
        return (MyApp)context.getApplicationContext();
    }

    public MyAppComponent getAppComponent() {
        return component;
    }
    public ServerComponent getServerComponent(){
        return serverComponent;
    }

}
