package com.lod.movie_extended.injection;

import com.lod.movie_extended.bll.ServerMock;
import com.lod.movie_extended.uil.activity.FilmPreparationActivity;
import com.lod.movie_extended.uil.activity.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Жамбыл on 28.11.2015.
 */

@Singleton
@Component(modules = ServerModule.class)
public interface ServerComponent {
    void inject(ServerMock server);
    void inject(FilmPreparationActivity filmPreparationActivity);
    void inject(MainActivity mainActivity);
}
