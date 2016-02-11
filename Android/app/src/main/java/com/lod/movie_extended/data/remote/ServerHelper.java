package com.lod.movie_extended.data.remote;

import android.content.Context;

import com.lod.movie_extended.data.model.FilmBuilder;
import com.lod.movie_extended.data.model.Language;
import com.lod.movie_extended.data.model.Session;
import com.lod.movie_extended.data.model.SessionFabric;
import com.lod.movie_extended.data.model.Token;

import java.util.ArrayList;

import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by Жамбыл on 09.01.2016.
 */
public class ServerHelper {

    Server server;

    public ServerHelper() {
        server = Server.Creator.newService();
    }

    public Observable<Session> loadSession(String code) {
        return Observable.create(new Observable.OnSubscribe<Session>() {
            @Override
            public void call(Subscriber<? super Session> subscriber) {
                FilmBuilder filmBuilder = new FilmBuilder();

                Language russian = new Language();
                Language english = new Language();

                russian.setName("Русский");
                english.setName("English");

                filmBuilder.setName("Звездные войны");
                filmBuilder.addLanguage(russian);
                filmBuilder.addLanguage(english);

                Token token = new Token("token123");

                Session session = SessionFabric.createSession(token,filmBuilder);

                session.setRemainingTimeSeconds(1);

                subscriber.onNext(session);
                subscriber.onCompleted();
            }
        });
    }

    public void sendCode(String code, Context context) {

    }

    public ArrayList<Language> getLanguages() {
        return null;
    }

    public String getExactTime() {
        return null;
    }

    public Token getToken() {
        return null;
    }

    public int loadAudioFromTime() {
        return 0;
    }

    public int getFilmData() {
        return 0;
    }

    public int getState() {
        return 0;
    }
}
