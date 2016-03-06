package com.lod.movie_extended.application;

import android.media.AudioManager;

import com.google.android.exoplayer.text.Cue;
import com.lod.movie_extended.BuildConfig;
import com.lod.movie_extended.data.DataManager;
import com.lod.movie_extended.data.local.DataBaseHelper;
import com.lod.movie_extended.data.model.Session;
import com.lod.movie_extended.data.model.player.CaptionListener;
import com.lod.movie_extended.data.model.player.Player;
import com.lod.movie_extended.data.model.player.PlayerListener;
import com.lod.movie_extended.data.model.player.TimeHelper;
import com.lod.movie_extended.data.remote.Server;
import com.lod.movie_extended.injection.App;
import com.lod.movie_extended.injection.module.application.AudioModuleTest;
import com.lod.movie_extended.util.TestComponentCreator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;
import timber.log.Timber;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Жамбыл on 3/2/2016.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class PlayerTest {

    TestComponentCreator testComponentCreator;

    @Before
    public void setUp() {
        testComponentCreator = new TestComponentCreator();
        App.instance().setComponent(testComponentCreator.getComponent());
    }

    @Test
    public void shouldNotPlayWhenHeadsetIsOff() {
        AudioManager audioManager = mock(AudioManager.class);
        when(audioManager.isWiredHeadsetOn()).thenReturn(false);
        testComponentCreator.getAudioModule().setAudioManagerMock(audioManager);
        Player player = new Player();

        boolean isPlaying = player.setPlayWhenReady(true);

        assertEquals(isPlaying,false);
    }

    @Test
    public void shouldPlayWhenHeadsetIsOn() {
        AudioManager audioManager = mock(AudioManager.class);
        when(audioManager.isWiredHeadsetOn()).thenReturn(true);
        testComponentCreator.getAudioModule().setAudioManagerMock(audioManager);
        Player player = new Player();

        boolean isPlaying = player.setPlayWhenReady(true);

        assertEquals(isPlaying,true);
    }

    @Test
    public void shouldSeekProperly() {
        TimeHelper timeHelper = mock(TimeHelper.class);

        when(timeHelper.getCurrentFilmTime()).thenReturn(5000L);
        testComponentCreator.getAudioModule().setTimeHelperMock(timeHelper);
        Player player = new Player();
        player.setPlayWhenReady(true);

        verify(timeHelper,atLeast(1)).getCurrentFilmTime();
    }

    @Test
    public void shouldSeekProperly2() {
        Player player = new Player();
        player.seekTo(1000);
        player.setPlayWhenReady(true);
        assertEquals(player.getCurrentPosition(),1000);
    }

    @Test
    public void getPlayWhenReadyReturnsTrueWhenSetPlayWhenReadyTrue() {
        Player player = new Player();

        player.setPlayWhenReady(true);

        assertEquals(player.getPlayWhenReady(),true);
    }

    @Test
    public void getPlayWhenReadyReturnsFalseWhenSetPlayWhenReadyFalse() {
        Player player = new Player();

        player.setPlayWhenReady(false);

        assertEquals(player.getPlayWhenReady(),false);
    }

    @Test
    public void ProperlyStartAudio() {
        Player player = new Player();
        player.startAudio("");
        AudioModuleTest audioModuleTest = testComponentCreator.getAudioModule();

        verify(audioModuleTest.getExtractorRendererBuilder()).startBuildingRenderers(player,"");
    }

    @Test
    public void shouldReportListenersAfterSetPlayWhenReadyTrue() {
        PlayerListener playerListener = mock(PlayerListener.class);
        Player player = new Player();

        player.addListener(playerListener);
        player.setPlayWhenReady(true);

        verify(playerListener).onStateChanged(true);

        player.setPlayWhenReady(false);

        verify(playerListener).onStateChanged(false);
    }

    @Test
    public void onCuesTest() {
        List<Cue> cues = new ArrayList<>();
        cues.add(new Cue());

        CaptionListener captionListener = mock(CaptionListener.class);
        Player player = new Player();

        player.setCaptionListener(captionListener);
        player.onCues(cues);

        verify(captionListener).onCues(cues);
    }

    @Test
    public void initiallySubtitlesAreDisabled() {
        Player player = new Player();
        assertEquals(player.isSubtitlesEnabled(),false);
    }

    @Test
    public void captionListenerTest() {
        CaptionListener captionListener = mock(CaptionListener.class);
        Player player = new Player();

        player.setCaptionListener(captionListener);

        assertEquals(player.isSubtitlesEnabled(),true);

        player.removeCaptionListener();

        assertEquals(player.isSubtitlesEnabled(),false);
    }
}
