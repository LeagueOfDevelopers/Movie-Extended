package com.lod.movie_extended;

import com.lod.movie_extended.data.model.player.Player;
import com.lod.movie_extended.injection.App;
import com.lod.movie_extended.util.TestComponentCreator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

/**
 * Created by Жамбыл on 3/2/2016.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class PlayerTest {

    private Player player;

    @Before
    public void setUp() {
        App.instance().setComponent(new TestComponentCreator().getComponent());
        player = new Player();
    }

    @Test
    public void asd() {
        player.setPlayWhenReady(true);
        assert player.asd() == 2;
    }

}
