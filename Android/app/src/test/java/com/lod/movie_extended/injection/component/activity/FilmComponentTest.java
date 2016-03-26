package com.lod.movie_extended.injection.component.activity;

import com.lod.movie_extended.injection.component.application.ApplicationComponentTest;
import com.lod.movie_extended.injection.module.activity.FilmModuleTest;
import com.lod.movie_extended.injection.scope.PerActivity;

import dagger.Component;

/**
 * Created by Жамбыл on 3/3/2016.
 */
@PerActivity
@Component(dependencies = ApplicationComponentTest.class, modules = FilmModuleTest.class)
public interface FilmComponentTest extends FilmComponent{
}
