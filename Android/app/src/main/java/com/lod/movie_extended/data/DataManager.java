package com.lod.movie_extended.data;

import com.google.gson.Gson;
import com.lod.movie_extended.data.local.DataBaseHelper;
import com.lod.movie_extended.data.local.PreferencesHelper;
import com.lod.movie_extended.data.model.Film;
import com.lod.movie_extended.data.model.Language;
import com.lod.movie_extended.data.model.Session;
import com.lod.movie_extended.data.model.Token;
import com.lod.movie_extended.data.remote.Server;
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
            Session session = dataBaseHelper.getSession().toBlocking().first();
            if(session != null && session.getQrCode().equals(qrCode)) {
                Timber.e("getting session from database");
                subscriber.onNext(session);
            }
            else {
                Timber.e("getting session from internet");
                Session session1 = loadSession(qrCode).toBlocking().first();
                Timber.e(session1.getFilm().getLanguages().get(0).getName());
                Timber.v("asdqwewwqe");
                subscriber.onNext(session1);
                subscriber.onCompleted();
            }
        });
    }

    private Observable<Session> loadSession(String qrCode) {
        return Observable.create((Observable.OnSubscribe<Session>) subscriber -> {
            Session session = new Session();
            session.setQrCode(qrCode);
            Timber.v("on " + qrCode);
            session.setToken(new Token(server.getToken(qrCode).toBlocking().first()));
            Timber.v("token " + session.getToken().getValue());
            server.getMovieLanguages(session.getToken().getValue())
                    .toBlocking()
                    .subscribe(jsonElements -> {
                        ArrayList<Language> langList = new ArrayList<>();
                        for (int i = 0; i < jsonElements.size(); i++) {
                            Language lang = new Gson().fromJson(jsonElements.get(i), Language.class);
                            langList.add(lang);
                        }
                        Film film = new Film();
                        film.setName("Звездные войны");
                        film.setLanguages(langList);
                        session.setFilm(film);
                    });

            subscriber.onNext(session);
        }).concatMap(dataBaseHelper::saveSession);
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
