package com.lod.movie_extended.injection.component.activity;

import com.lod.movie_extended.injection.component.application.ApplicationComponent;
import com.lod.movie_extended.injection.module.activity.SubModule;
import com.lod.movie_extended.injection.scope.PerActivity;
import com.lod.movie_extended.ui.activity.sub.SubActivity;

import dagger.Component;

/**
 * Created by Жамбыл on 3/21/2016.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = SubModule.class)
public interface SubComponent {

    void inject(SubActivity subActivity);
}
