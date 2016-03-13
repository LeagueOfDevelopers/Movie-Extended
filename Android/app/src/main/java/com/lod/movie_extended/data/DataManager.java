package com.lod.movie_extended.data;

import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.lod.movie_extended.data.local.DataBaseHelper;
import com.lod.movie_extended.data.model.Film;
import com.lod.movie_extended.data.model.Language;
import com.lod.movie_extended.data.model.Session;
import com.lod.movie_extended.data.model.Token;
import com.lod.movie_extended.data.remote.ServerAPI;
import com.lod.movie_extended.data.remote.ServerHelper;
import com.lod.movie_extended.injection.scope.PerApplication;

import java.util.ArrayList;

import javax.inject.Inject;

import rx.Observable;
import timber.log.Timber;

/**
 * Created by Жамбыл on 09.01.2016.
 */
@PerApplication
public class DataManager {

    private final DataBaseHelper dataBaseHelper;
    private ServerHelper serverHelper;
    private boolean filmTime;
    private boolean qrCodeProcessed;

    @Inject
    public DataManager(DataBaseHelper dataBaseHelper, ServerHelper serverHelper) {
        this.dataBaseHelper = dataBaseHelper;
        this.serverHelper = serverHelper;
    }

    public void setQrCode(String qrCode) {
        dataBaseHelper.setQrCode(qrCode);
        setQrCodeProcessed(true);
    }

    public Observable<Session> getSession() {
        Timber.e("Observable.create");
        return Observable.create((Observable.OnSubscribe<Session>) subscriber ->
        {
            Timber.v("getting session");
            String qrCode = dataBaseHelper.getQrCode();
            Session sessionFromDatabase = dataBaseHelper.getSession().toBlocking().first();
            if(sessionFromDatabase != null && sessionFromDatabase.getQrCode().equals(qrCode)) {
                Timber.e("getting session from database");
                subscriber.onNext(sessionFromDatabase);
            }
            else {
                Timber.e("getting session from internet");
                Session sessionFromInternet = serverHelper.loadSession(qrCode).concatMap(dataBaseHelper::saveSession)
                                                                                                .toBlocking().first();
                subscriber.onNext(sessionFromInternet);
            }
        });
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

    private void setQrCodeProcessed(boolean qrCodeProcessed) {
        this.qrCodeProcessed = qrCodeProcessed;
    }

}
