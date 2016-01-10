package com.lod.movie_extended.injection.module.activity;

import android.app.Activity;
import android.content.Context;

import com.lod.movie_extended.data.DataManager;
import com.lod.movie_extended.injection.scope.PerActivity;
import com.lod.movie_extended.ui.film.FilmPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Жамбыл on 09.01.2016.
 */
@Module
public class FilmModule {

    Activity activity;

    public FilmModule(Activity activity) {
        this.activity = activity;
    }

    @Provides
    @PerActivity
    FilmPresenter provideFilmPresenter(DataManager dataManager) {
        return new FilmPresenter(dataManager,activity);
    }
}
