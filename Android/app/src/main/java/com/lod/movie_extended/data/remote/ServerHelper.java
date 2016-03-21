package com.lod.movie_extended.data.remote;

import android.net.Uri;
import android.support.annotation.NonNull;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.lod.movie_extended.data.model.Film;
import com.lod.movie_extended.data.model.Language;
import com.lod.movie_extended.data.model.Session;
import com.lod.movie_extended.data.model.SessionDeserializer;
import com.lod.movie_extended.data.model.Token;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit.http.Url;
import rx.Observable;
import rx.functions.Action1;
import timber.log.Timber;

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
        Date filmStartTime=null;
        try {
            filmStartTime = serverAPI.getFilmStartTime(session.getFilm().getId()).toBlocking().first();
        }
        catch (Exception ignored) {
        }
        session.setFilmStartTime(filmStartTime);
    }

    public Uri getDownloadUrl(int id, Token token) {
        return Uri.parse(String.format("http://movieextended1.azurewebsites.net/file/get/%d/token/%s",
                id, token.getValue()));
    }
}
