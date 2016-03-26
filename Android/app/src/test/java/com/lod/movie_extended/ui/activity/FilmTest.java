package com.lod.movie_extended.ui.activity;

import android.support.v7.app.AppCompatActivity;

import com.lod.movie_extended.BuildConfig;
import com.lod.movie_extended.injection.component.activity.DaggerFilmComponentTest;
import com.lod.movie_extended.injection.module.activity.FilmModuleTest;
import com.lod.movie_extended.ui.activity.film.FilmActivity;
import com.lod.movie_extended.util.TestComponentCreator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

/**
 * Created by Жамбыл on 3/3/2016.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class FilmTest {

    private FilmActivity filmActivity;

    @Before
    public void setUp() {
        filmActivity = Robolectric.setupActivity(FilmActivity.class);
        filmActivity.setComponent(DaggerFilmComponentTest.builder()
                .applicationComponentTest(new TestComponentCreator().getComponent())
                .filmModuleTest(new FilmModuleTest(new AppCompatActivity()))
                .build());
        filmActivity.inject();
    }

    @Test
    public void init() {
    }
}
