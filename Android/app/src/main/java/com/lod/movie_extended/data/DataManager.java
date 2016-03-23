package com.lod.movie_extended.data;

import com.lod.movie_extended.data.local.DataBaseHelper;
import com.lod.movie_extended.data.model.Film;
import com.lod.movie_extended.data.model.Language;
import com.lod.movie_extended.data.model.Session;
import com.lod.movie_extended.data.remote.ServerHelper;
import com.lod.movie_extended.injection.scope.PerApplication;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.functions.Action1;
import timber.log.Timber;

/**
 * Created by Жамбыл on 09.01.2016.
 */
@PerApplication
public class DataManager {

    private final DataBaseHelper dataBaseHelper;
    private final ServerHelper serverHelper;
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
        return Observable.create((Observable.OnSubscribe<Session>) subscriber ->
        {
            String qrCode = dataBaseHelper.getQrCode();
            Session sessionFromDatabase = dataBaseHelper.getSession().toBlocking().first();

            if(sessionFromDatabase != null && sessionFromDatabase.getQrCode().equals(qrCode)) {
                Timber.i("getting session from database");
                subscriber.onNext(sessionFromDatabase);
            }
            else {
                Timber.i("getting session from internet");
                serverHelper.loadSession(qrCode)
                .doOnNext(session -> {
                    dataBaseHelper.saveSession(session);
                    setDefaultSelectedLanguages(session);
                    subscriber.onNext(session);
                })
                .toBlocking().subscribe();
            }
        });
    }

    private void setDefaultSelectedLanguages(Session session) {
        Film film = session.getFilm();
        film.setSelectedSubtitleLanguage(film.getSubtitleLanguages().get(1));
        film.setSelectedSoundLanguage(film.getSoundLanguages().get(0));
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
