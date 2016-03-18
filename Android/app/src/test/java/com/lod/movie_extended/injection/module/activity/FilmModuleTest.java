package com.lod.movie_extended.injection.module.activity;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.lod.movie_extended.data.DataManager;
import com.lod.movie_extended.data.model.NotificationServiceHelper;
import com.lod.movie_extended.data.model.player.Player;
import com.lod.movie_extended.injection.scope.PerActivity;
import com.lod.movie_extended.ui.activity.film.FilmPresenter;

import dagger.Module;
import dagger.Provides;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

/**
 * Created by Жамбыл on 3/3/2016.
 */
@Module
public class FilmModuleTest {

    AppCompatActivity activity;

    public FilmModuleTest(AppCompatActivity activity) {
        this.activity = activity;
    }

    @Provides
    @PerActivity
    FilmPresenter provideFilmPresenter(DataManager dataManager, Player player, NotificationServiceHelper notificationServiceHelper) {
        FilmPresenter filmPresenter = mock(FilmPresenter.class);
        doReturn(1).when(filmPresenter).test();
        return filmPresenter;
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
