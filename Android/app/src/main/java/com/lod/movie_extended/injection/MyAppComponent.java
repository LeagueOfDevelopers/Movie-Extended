package com.lod.movie_extended.injection;


import com.lod.movie_extended.uil.activity.NewActivity;

import dagger.Component;

/**
 * Created by Жамбыл on 27.11.2015.
 */
@Component(modules = {MyAppModule.class})
public interface MyAppComponent {

    void inject(NewActivity newActivity);
}