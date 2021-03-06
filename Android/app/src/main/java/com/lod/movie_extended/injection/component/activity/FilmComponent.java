package com.lod.movie_extended.injection.component.activity;

import com.lod.movie_extended.injection.component.application.ApplicationComponent;
import com.lod.movie_extended.injection.module.activity.FilmModule;
import com.lod.movie_extended.injection.module.activity.FilmPreparationModule;
import com.lod.movie_extended.injection.scope.PerActivity;
import com.lod.movie_extended.ui.film.FilmActivity;

import dagger.Component;

/**
 * Created by Жамбыл on 09.01.2016.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = FilmModule.class)
public interface FilmComponent {

    void inject(FilmActivity filmActivity);
}
