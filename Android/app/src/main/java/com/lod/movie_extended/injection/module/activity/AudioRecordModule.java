package com.lod.movie_extended.injection.module.activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import com.lod.movie_extended.injection.scope.PerActivity;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Максим on 04.07.2016.
 */
@Module
public class AudioRecordModule {
    AppCompatActivity activity;

    public AudioRecordModule (AppCompatActivity activity) {
        this.activity = activity;
    }
    @Provides
    @PerActivity
    AppCompatActivity provideActivity() {
        return activity;
    }

    @Provides
    Context providesContext() {
        return activity;
    }
}
