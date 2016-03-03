package com.lod.movie_extended.injection.component.activity;

import com.lod.movie_extended.injection.component.application.ApplicationComponent;
import com.lod.movie_extended.injection.module.activity.MainModule;
import com.lod.movie_extended.injection.scope.PerActivity;
import com.lod.movie_extended.ui.activity.main.MainActivity;

import dagger.Component;

/**
 * Created by Жамбыл on 09.01.2016.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = MainModule.class)
public interface MainComponent {

    void inject(MainActivity mainActivity);
}
