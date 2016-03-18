package com.lod.movie_extended.injection.component.activity;

import com.lod.movie_extended.injection.component.application.ApplicationComponent;
import com.lod.movie_extended.injection.module.activity.FilmShowModule;
import com.lod.movie_extended.injection.scope.PerActivity;
import com.lod.movie_extended.ui.activity.filmShow.FilmShowActivity;

import dagger.Component;

/**
 * Created by Жамбыл on 3/17/2016.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = FilmShowModule.class)
public interface FilmShowComponent {

    void inject(FilmShowActivity filmShowActivity);
}
