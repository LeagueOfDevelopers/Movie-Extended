package com.lod.movie_extended.injection.component.activity;

import android.content.Context;

import com.lod.movie_extended.data.DataManager;
import com.lod.movie_extended.data.model.player.Player;
import com.lod.movie_extended.injection.component.application.ApplicationComponent;
import com.lod.movie_extended.test.module.activity.FilmModule;
import com.lod.movie_extended.injection.scope.PerActivity;
import com.lod.movie_extended.ui.activity.film.FilmActivity;
import com.squareup.otto.Bus;

import dagger.Component;

/**
 * Created by Жамбыл on 09.01.2016.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = FilmModule.class)
public interface FilmComponent{

    Context getContext();
    DataManager getDataManager();
    Bus getBus();
    Player getPlayer();

    void inject(FilmActivity filmActivity);
}
