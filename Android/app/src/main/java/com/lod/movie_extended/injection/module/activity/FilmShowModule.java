package com.lod.movie_extended.injection.module.activity;

import android.support.v7.app.AppCompatActivity;

import com.lod.movie_extended.data.DataManager;
import com.lod.movie_extended.injection.scope.PerActivity;
import com.lod.movie_extended.ui.activity.filmShow.FilmShowPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Жамбыл on 3/17/2016.
 */
@Module
public class FilmShowModule {

    AppCompatActivity activity;

    public FilmShowModule(AppCompatActivity activity) {
        this.activity = activity;
    }

    @Provides
    @PerActivity
    FilmShowPresenter provideFilmShowPresenter(DataManager dataManager) {
        return new FilmShowPresenter(dataManager);
    }
}
