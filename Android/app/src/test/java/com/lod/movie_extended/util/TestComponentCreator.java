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
    private AudioModuleTest audioModule;

   public TestComponentCreator() {
        audioModule = new AudioModuleTest();
        applicationComponent = DaggerApplicationComponentTest.builder()
                .applicationModuleTest(new ApplicationModuleTest(App.getInstance()))
                .audioModuleTest(audioModule)
                .build();
    }

    public AudioModuleTest getAudioModule() {
        return audioModule;
    }
    public ApplicationComponentTest getComponent() {
        return applicationComponent;
    }
}
