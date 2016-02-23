package com.lod.movie_extended.injection.module.activity;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.lod.movie_extended.data.DataManager;
import com.lod.movie_extended.data.model.ColorHelper;
import com.lod.movie_extended.data.model.Player;
import com.lod.movie_extended.injection.scope.PerActivity;
import com.lod.movie_extended.ui.activity.film.FilmPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Жамбыл on 09.01.2016.
 */
@Module
public class FilmModule {

    AppCompatActivity activity;

    public FilmModule(AppCompatActivity activity) {
        this.activity = activity;
    }

    @Provides
    @PerActivity
    FilmPresenter provideFilmPresenter(DataManager dataManager, Player player, ColorHelper colorHelper) {
        return new FilmPresenter(dataManager,activity,player, colorHelper);
    }

    @Provides
    @PerActivity
    FragmentManager provideFragmentManager(AppCompatActivity appCompatActivity){
        return appCompatActivity.getSupportFragmentManager();
    }

    @Provides
    @PerActivity
    AppCompatActivity provideAppCompatActivity(){
        return activity;
    }


    @Provides
    @PerActivity
    Context provideContext() {
        return activity;
    }

}
