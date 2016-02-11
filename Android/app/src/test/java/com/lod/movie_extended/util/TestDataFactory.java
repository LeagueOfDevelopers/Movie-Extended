package com.lod.movie_extended.util;

import com.lod.movie_extended.data.model.FilmBuilder;
import com.lod.movie_extended.data.model.Language;
import com.lod.movie_extended.data.model.Session;
import com.lod.movie_extended.data.model.SessionFabric;
import com.lod.movie_extended.data.model.Token;

import rx.Observable;

/**
 * Created by Жамбыл on 2/11/2016.
 */
public final class TestDataFactory {

    public static Session getTestSession() {
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
        return session;
    }
}
