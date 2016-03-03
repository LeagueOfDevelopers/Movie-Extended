package com.lod.movie_extended.util;

import com.lod.movie_extended.injection.App;
import com.lod.movie_extended.injection.component.application.ApplicationComponentTest;
import com.lod.movie_extended.injection.component.application.DaggerApplicationComponentTest;
import com.lod.movie_extended.injection.module.application.ApplicationModuleTest;
import com.lod.movie_extended.injection.module.application.AudioModuleTest;

/**
 * Created by Жамбыл on 3/2/2016.
 */
public class TestComponentCreator {

    private ApplicationComponentTest applicationComponent;

    public TestComponentCreator() {
        applicationComponent = DaggerApplicationComponentTest.builder()
                .applicationModuleTest(new ApplicationModuleTest(App.instance()))
                .audioModuleTest(new AudioModuleTest())
                .build();
    }

    public ApplicationComponentTest getComponent() {
        return applicationComponent;
    }
}
