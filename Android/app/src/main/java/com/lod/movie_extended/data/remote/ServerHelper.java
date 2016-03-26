package com.lod.movie_extended.data.remote;

import android.net.Uri;

import com.lod.movie_extended.data.model.Session;
import com.lod.movie_extended.data.model.SessionDeserializer;
import com.lod.movie_extended.data.model.Token;

import java.util.Date;

import rx.Observable;

/**
 * Created by Жамбыл on 09.01.2016.
 */
public class ServerHelper {

    private ServerAPI serverAPI;
    private SessionDeserializer deserializer;

    public ServerHelper(ServerAPI serverAPI, SessionDeserializer deserializer) {
        this.serverAPI = serverAPI;
        this.deserializer = deserializer;
    }

    public Observable<Session> loadSession(String qrCode) {
        return Observable.create((Observable.OnSubscribe<Session>) subscriber -> {
            serverAPI.getData(qrCode)
                .doOnNext(jsonElements -> {
                    Session session = deserializer.get(jsonElements);
                    session.setQrCode(qrCode);
                    setFilmStartTime(session);
                    subscriber.onNext(session);
                })
                .toBlocking().subscribe();
        });
    }

    private void setFilmStartTime(Session session) {
        Date filmStartTime = null;
        try {
            filmStartTime = serverAPI.getFilmStartTime(session.getFilm().getId()).toBlocking().first();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        session.setFilmStartTime(filmStartTime);
    }

    public Uri getDownloadUrl(int id, Token token) {
        return Uri.parse(String.format("http://movieextended1.azurewebsites.net/file/get/%d/token/%s",
                id, token.getValue()));
    }
}
