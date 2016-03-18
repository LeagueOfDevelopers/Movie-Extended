package com.lod.movie_extended.application;

import com.lod.movie_extended.BuildConfig;
import com.lod.movie_extended.data.model.Session;
import com.lod.movie_extended.data.remote.ServerAPI;
import com.lod.movie_extended.data.remote.ServerHelper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import rx.Subscriber;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.fail;
import static org.mockito.Mockito.atMost;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Created by Жамбыл on 3/18/2016.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class ServerHelperTest {

    private ServerHelper serverHelper;
    private String validQrCode = "00000000-0000-0000-0000-000000000000";
    private String invalidString = "";

    @Test
    public void shouldGetData() {
        serverHelper = new ServerHelper(ServerAPI.Creator.newService());
        serverHelper.loadSession(validQrCode).subscribe(new Subscriber<Session>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                fail();
            }

            @Override
            public void onNext(Session session) {
                assertNotNull(session);
                assertNotNull(session.getFilm());
                assertNotNull(session.getFilm().getSoundLanguages());
                assertNotNull(session.getFilm().getSubtitleLanguages());
            }
        });
    }

    @Test
    public void shouldCallServer() {
        ServerAPI serverAPI = mock(ServerAPI.class);
        serverHelper = new ServerHelper(serverAPI);
        serverHelper.loadSession(validQrCode);
        verify(serverAPI,atMost(1)).getData(validQrCode);
    }

    @Test
    public void shouldFailOnInvalidQRCode() {
        serverHelper = new ServerHelper(ServerAPI.Creator.newService());
        serverHelper.loadSession(invalidString).subscribe(new Subscriber<Session>() {
            @Override
            public void onCompleted() {
                fail();
            }

            @Override
            public void onError(Throwable e) {
                //good;
            }

            @Override
            public void onNext(Session session) {
                fail();
            }
        });
    }
}
