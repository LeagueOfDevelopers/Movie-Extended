package com.lod.movie_extended.data.remote;

import android.content.Context;
import android.support.annotation.NonNull;

import com.lod.movie_extended.data.model.Film;
import com.lod.movie_extended.data.model.FilmBuilder;
import com.lod.movie_extended.data.model.Language;
import com.lod.movie_extended.data.model.Session;
import com.lod.movie_extended.data.model.SessionFabric;
import com.lod.movie_extended.data.model.Token;

import java.util.ArrayList;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by Жамбыл on 09.01.2016.
 */
public class ServerHelper {

    private ServerAPI serverAPI;

    public ServerHelper() {
        serverAPI = ServerAPI.Creator.newService();
    }

    public Observable<Session> loadSession(String qrCode) {
        return Observable.create((Observable.OnSubscribe<Session>) subscriber -> {
            Session session = new Session();
            session.setQrCode(qrCode);
            session.setToken(new Token(serverAPI.getToken(qrCode).toBlocking().first()));
            session.setFilm(loadFilm(session));
            subscriber.onNext(session);
        });
    }

    @NonNull
    private Film loadFilm(Session session) {
        Film film = new Film();
        film.setName("Звездные войны");

        film.setLanguages(null);
        return film;
    }
}
