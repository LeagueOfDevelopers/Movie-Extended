package com.lod.movie_extended.application;

import com.lod.movie_extended.BuildConfig;
import com.lod.movie_extended.data.DataManager;
import com.lod.movie_extended.data.local.DataBaseHelper;
import com.lod.movie_extended.data.model.Session;
import com.lod.movie_extended.data.remote.ServerAPI;
import com.lod.movie_extended.data.remote.ServerHelper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import rx.Observable;
import rx.Subscriber;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.fail;
import static org.mockito.Mockito.atMost;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Жамбыл on 3/6/2016.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class DataManagerTest {

    private DataManager dataManager;
    private DataBaseHelper dataBaseHelper;
    private ServerHelper serverHelper;
    private Session session;
    String qrCode = "00000000-0000-0000-0000-000000000000";

    @Before
    public void before() {
        dataBaseHelper = mock(DataBaseHelper.class);
        serverHelper = mock(ServerHelper.class);
        session = mock(Session.class);
        when(session.getQrCode()).thenReturn(qrCode);
        dataManager = new DataManager(dataBaseHelper, serverHelper);
    }

    @Test
    public void shouldSaveQrCodeToDatabase() {
        dataManager.setQrCode(qrCode);
        verify(dataBaseHelper).setQrCode(qrCode);
    }

    @Test
    public void shouldGetQrCodeFromDatabase() {
        DataBaseHelper dataBaseHelper = new DataBaseHelper();
        dataManager = new DataManager(dataBaseHelper, serverHelper);
        dataManager.setQrCode(qrCode);

        assertEquals(dataBaseHelper.getQrCode(),qrCode);
    }

    @Test
    public void shouldGetSessionFromDatabase() {
        DataBaseHelper dataBaseHelper = spy(new DataBaseHelper());
        when(dataBaseHelper.getSession()).thenReturn(Observable.just(session));
        DataManager dataManager = new DataManager(dataBaseHelper,new ServerHelper(ServerAPI.Creator.newService(), null));
        dataManager.setQrCode(qrCode);

        dataManager.getSession().subscribe(new Subscriber<Session>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                fail();
            }

            @Override
            public void onNext(Session ses) {
                assertEquals(ses,session);
            }
        });
        verify(dataBaseHelper,never()).saveSession(session);
    }

    @Test
    public void shouldGetSessionFromInternet() {
        DataBaseHelper dataBaseHelper = spy(new DataBaseHelper());
        when(dataBaseHelper.getSession()).thenReturn(Observable.just(null));
        DataManager dataManager = new DataManager(dataBaseHelper, new ServerHelper(ServerAPI.Creator.newService(), null));
        dataManager.setQrCode(qrCode);
        dataManager.getSession().subscribe(new Subscriber<Session>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                fail();
            }

            @Override
            public void onNext(Session session) {
                assertSession(session);
            }
        });
        verify(dataBaseHelper,atMost(1)).saveSession(session);
    }

    private void assertSession(Session session) {
        assertNotNull(session);
        assertNotNull(session.getFilm());
        assertNotNull(session.getFilm().getSoundLanguages());
        assertNotNull(session.getFilm().getSubtitleLanguages());
    }

    @Test
    public void shouldGetSessionFromDataBaseAfterGettingFromInternet() {
        DataBaseHelper dataBaseHelper = spy(new DataBaseHelper());
        when(dataBaseHelper.getSession()).thenReturn(Observable.just(null));
        DataManager dataManager = new DataManager(dataBaseHelper, new ServerHelper(ServerAPI.Creator.newService(), null));
        dataManager.setQrCode(qrCode);
        //from internet
        dataManager.getSession().subscribe(new Subscriber<Session>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                fail();
            }

            @Override
            public void onNext(Session session) {
                assertSession(session);
            }
        });
        verify(dataBaseHelper,atMost(1)).saveSession(session);

        //from internet
        dataManager.getSession().subscribe(new Subscriber<Session>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                fail();
            }

            @Override
            public void onNext(Session session) {
                assertSession(session);
            }
        });
        verify(dataBaseHelper,atMost(1)).saveSession(session);
    }

    @Test
    public void shouldFailWithoutQrCode() {
        dataManager = new DataManager(new DataBaseHelper(), serverHelper);
        dataManager.getSession().subscribe(new Subscriber<Session>() {
            @Override
            public void onCompleted() {
                fail();
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Session session) {
                fail();
            }
        });
    }
}
