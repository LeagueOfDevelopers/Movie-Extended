package com.lod.movie_extended.injection;


import com.lod.movie_extended.bll.IServer;
import com.lod.movie_extended.bll.ServerMock;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Жамбыл on 27.11.2015.
 */
@Module
public class MyAppModule {

    MyApp app;

    public MyAppModule(MyApp app) {
        this.app = app;
    }

    @Provides
    MyApp provideMyApp() {
        return app;
    }
    @Provides
    IServer provideServer(){
        return new ServerMock();
    }
}