package com.lod.movie_extended.util;

import com.lod.movie_extended.injection.App;
import com.lod.movie_extended.injection.component.application.ApplicationComponent;
import com.lod.movie_extended.injection.component.application.DaggerApplicationComponent;
import com.lod.movie_extended.test.module.application.ApplicationModuleTest;
import com.lod.movie_extended.test.module.application.AudioModuleTest;

import org.bouncycastle.util.test.TestResult;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

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
