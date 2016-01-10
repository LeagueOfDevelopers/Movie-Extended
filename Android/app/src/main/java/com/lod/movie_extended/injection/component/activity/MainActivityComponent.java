package com.lod.movie_extended.injection.component.activity;

import com.lod.movie_extended.injection.component.application.ApplicationComponent;
import com.lod.movie_extended.injection.module.activity.MainActivityModule;
import com.lod.movie_extended.injection.scope.PerActivity;
import com.lod.movie_extended.ui.main.MainActivity;

import dagger.Component;

/**
 * Created by Жамбыл on 09.01.2016.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = MainActivityModule.class)
public interface MainActivityComponent {

    void inject(MainActivity mainActivity);
}
