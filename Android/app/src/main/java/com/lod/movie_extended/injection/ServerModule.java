package com.lod.movie_extended.injection;

import android.content.Context;

import com.lod.movie_extended.bll.IServer;
import com.lod.movie_extended.bll.ServerMock;
import com.squareup.otto.Bus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Жамбыл on 28.11.2015.
 */
@Module
public class ServerModule {

    Context context;

    public ServerModule(Context context) {
        this.context = context;
    }

    @Provides @Singleton
    IServer provideServer(){
        return new ServerMock(context);
    }

    @Provides @Singleton
    Bus provideServerBus(){
        return new Bus();
    }


}
