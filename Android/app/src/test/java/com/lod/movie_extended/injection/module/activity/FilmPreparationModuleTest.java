package com.lod.movie_extended.injection.module.activity;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.lod.movie_extended.data.DataManager;
import com.lod.movie_extended.data.model.NotificationServiceHelper;
import com.lod.movie_extended.data.model.player.Player;
import com.lod.movie_extended.injection.scope.PerActivity;
import com.lod.movie_extended.ui.activity.filmPreparation.FilmPreparationPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Жамбыл on 3/3/2016.
 */
@Module
public class FilmPreparationModuleTest {

    AppCompatActivity activity;

    public FilmPreparationModuleTest(AppCompatActivity activity) {
        this.activity = activity;
    }

    @Provides
    @PerActivity
    FilmPreparationPresenter provideFilmPreparationPresenter(DataManager dataManager, Player player, NotificationServiceHelper notificationServiceHelper) {
        return new FilmPreparationPresenter(dataManager, player, notificationServiceHelper);
    }

    @Provides
    @PerActivity
    AppCompatActivity provideAppCompatActivity(){
        return activity;
    }

    @Provides
    @PerActivity
    FragmentManager provideFragmentManager(AppCompatActivity appCompatActivity){
        return appCompatActivity.getSupportFragmentManager();
    }

    @Provides
    @PerActivity
    Context provideContext() {
        return activity;
    }
}
