package com.lod.movie_extended.data;

import android.content.Context;

import com.lod.movie_extended.App;
import com.lod.movie_extended.data.local.DataBaseHelper;
import com.lod.movie_extended.data.local.PreferencesHelper;
import com.lod.movie_extended.data.model.Session;
import com.lod.movie_extended.data.model.Token;
import com.lod.movie_extended.data.remote.Server;
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
    private Server server;
    private boolean filmTime;
    private boolean qrCodeProcessed;

    @Inject
    public DataManager(ServerHelper serverHelper, DataBaseHelper dataBaseHelper, PreferencesHelper preferencesHelper,
                       Server server) {
        this.serverHelper = serverHelper;
        this.dataBaseHelper = dataBaseHelper;
        this.preferencesHelper = preferencesHelper;
        this.server = server;
    }

    public Observable<Token> getToken(String qrCode) {
        return server.getToken(qrCode).map(tokenValue -> {
            Token token = new Token(tokenValue);
            dataBaseHelper.saveToken(token);
            return token;
        });
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

    public boolean isFilmTime() {
        return filmTime;
    }

    public void setFilmTime(boolean filmTime) {
        this.filmTime = filmTime;
    }

    public boolean hasQrCodeBeenProcessed() {
        return qrCodeProcessed;
    }

    public void setQrCodeProcessed(boolean qrCodeProcessed) {
        this.qrCodeProcessed = qrCodeProcessed;
    }
}
