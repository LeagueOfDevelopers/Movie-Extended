package com.lod.movie_extended.application;

import com.lod.movie_extended.BuildConfig;
import com.lod.movie_extended.data.DataManager;
import com.lod.movie_extended.data.local.DataBaseHelper;
import com.lod.movie_extended.data.local.PreferencesHelper;
import com.lod.movie_extended.data.model.ServiceHelper;
import com.lod.movie_extended.data.model.Session;
import com.lod.movie_extended.data.remote.Server;
import com.lod.movie_extended.data.remote.ServerHelper;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import rx.Observable;
import rx.Subscriber;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.mock;
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
    private ServerHelper serverHelper;
    private DataBaseHelper dataBaseHelper;
    private PreferencesHelper preferencesHelper;
    private Server server;
    private Session session;
    String qrCode = "qrCode";

    @Before
    public void before() {
        serverHelper = mock(ServerHelper.class);
        dataBaseHelper = mock(DataBaseHelper.class);
        preferencesHelper = mock(PreferencesHelper.class);
        server = mock(Server.class);
        session = mock(Session.class);
        when(session.getQrCode()).thenReturn("00000000-0000-0000-0000-000000000000");
        dataManager = new DataManager(serverHelper,dataBaseHelper,preferencesHelper,server);
    }

    @Test
    public void shouldSaveQrCodeToDatabase() {
        dataManager.setQrCode(qrCode);
        verify(dataBaseHelper).setQrCode(qrCode);
    }

    @Test
    public void shouldGetQrCodeFromDatabase() {
        DataBaseHelper dataBaseHelper = new DataBaseHelper();
        dataManager = new DataManager(serverHelper,dataBaseHelper,preferencesHelper,server);
        dataManager.setQrCode(qrCode);

        assertEquals(dataBaseHelper.getQrCode(),qrCode);
    }

    @Test
    public void shouldGetSessionFromDatabase() {
        DataBaseHelper dataBaseHelper = spy(new DataBaseHelper());
        when(dataBaseHelper.getSession()).thenReturn(Observable.just(session));
        DataManager dataManager = new DataManager(null,dataBaseHelper,null, Server.Creator.newService());
        dataManager.setQrCode("00000000-0000-0000-0000-000000000000");

        dataManager
                .getSession()
                .subscribe(new Subscriber<Session>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        throw new RuntimeException();
                    }

                    @Override
                    public void onNext(Session session) {
                        Assert.assertNotNull(session);
                    }
                });
    }

    @Test
    public void shouldGetSessionFromInternet() {
        DataBaseHelper dataBaseHelper = spy(new DataBaseHelper());
        when(dataBaseHelper.getSession()).thenReturn(Observable.just(null));
        DataManager dataManager = new DataManager(null,dataBaseHelper,null, Server.Creator.newService());
        dataManager.setQrCode("00000000-0000-0000-0000-000000000000");
        dataManager
                .getSession()
                .subscribe(new Subscriber<Session>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        throw new RuntimeException();
                    }

                    @Override
                    public void onNext(Session session) {
                        Assert.assertNotNull(session);
                    }
                });
    }
}
