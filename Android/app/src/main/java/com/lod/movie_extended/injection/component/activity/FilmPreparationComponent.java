package com.lod.movie_extended.injection.component.activity;

import android.content.Context;

import com.lod.movie_extended.data.DataManager;
import com.lod.movie_extended.injection.component.application.ApplicationComponent;
import com.lod.movie_extended.injection.module.activity.FilmPreparationModule;
import com.lod.movie_extended.injection.scope.PerActivity;
import com.lod.movie_extended.ui.activity.filmPreparation.FilmPreparationActivity;
import com.squareup.otto.Bus;

import dagger.Component;

/**
 * Created by Жамбыл on 09.01.2016.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = FilmPreparationModule.class)
public interface FilmPreparationComponent{

    Context getContext();
    DataManager getDataManager();
    Bus getBus();

    void inject(FilmPreparationActivity filmPreparationActivity);
}
