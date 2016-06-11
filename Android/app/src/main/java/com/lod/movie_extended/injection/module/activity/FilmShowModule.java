package com.lod.movie_extended.injection.module.activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import com.lod.movie_extended.data.DataManager;
import com.lod.movie_extended.data.local.DataBaseHelper;
import com.lod.movie_extended.data.model.NotificationServiceHelper;
import com.lod.movie_extended.data.model.player.Player;
import com.lod.movie_extended.data.remote.ServerHelper;
import com.lod.movie_extended.injection.context.ActivityContext;
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
    @ActivityContext
    Context provideContext() {
        return activity;
    }



}
