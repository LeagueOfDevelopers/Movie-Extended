package com.lod.movie_extended.injection.component.application;

import com.lod.movie_extended.injection.component.application.ApplicationComponent;
import com.lod.movie_extended.injection.scope.PerApplication;
import com.lod.movie_extended.injection.module.application.ApplicationModuleTest;
import com.lod.movie_extended.injection.module.application.AudioModuleTest;

import dagger.Component;

/**
 * Created by Жамбыл on 3/2/2016.
 */
@PerApplication
@Component(modules = {
        ApplicationModuleTest.class,
        AudioModuleTest.class
})
public interface ApplicationComponentTest extends ApplicationComponent{
}
