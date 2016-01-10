package com.lod.movie_extended.data;

import com.lod.movie_extended.data.local.DataBaseHelper;
import com.lod.movie_extended.data.local.PreferencesHelper;
import com.lod.movie_extended.data.model.Session;
import com.lod.movie_extended.data.remote.ServerHelper;
import com.lod.movie_extended.injection.scope.PerApplication;

import javax.inject.Inject;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by Жамбыл on 09.01.2016.
 */
@PerApplication
public class DataManager {

    private final ServerHelper serverHelper;
    private final DataBaseHelper dataBaseHelper;
    private PreferencesHelper preferencesHelper;

    @Inject
    public DataManager(ServerHelper serverHelper, DataBaseHelper dataBaseHelper, PreferencesHelper preferencesHelper) {
        this.serverHelper = serverHelper;
        this.dataBaseHelper = dataBaseHelper;
        this.preferencesHelper = preferencesHelper;
    }

    public Observable<Session> loadSession(String code) {
        return serverHelper.loadSession(code).map(session -> {
            dataBaseHelper.saveSession(session);
            return session;
        });
    }


    public Session getSession() {
        return dataBaseHelper.getSession();
    }
}
