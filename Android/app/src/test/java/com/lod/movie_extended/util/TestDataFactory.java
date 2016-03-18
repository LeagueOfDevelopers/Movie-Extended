package com.lod.movie_extended.util;

import com.lod.movie_extended.data.model.Film;
import com.lod.movie_extended.data.model.Language;
import com.lod.movie_extended.data.model.Session;
import com.lod.movie_extended.data.model.Token;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Жамбыл on 2/11/2016.
 */
public final class TestDataFactory {

    public static Session getTestSession() {
        Session session  = new Session();
        Film film = new Film();
        film.setName("Mad Max");
        List<Language> languages = new ArrayList<>();

        Language english = new Language();
        english.setName("English");

        Language russian = new Language();
        russian.setName("Русский");

        Language ukraine = new Language();
        ukraine.setName("Украинский");

        languages.add(english);
        languages.add(russian);
        languages.add(ukraine);

        List<Language> subs = new ArrayList<>();

        Language english2 = new Language();
        english2.setName("English");

        Language russian2 = new Language();
        russian2.setName("Русский");

        subs.add(english2);
        subs.add(russian2);

        film.setSubtitleLanguages(subs);
        film.setSoundLanguages(languages);

        film.setSelectedSoundLanguage(english);
        film.setSelectedSubtitleLanguage(english2);
        session.setFilm(film);
        return session;
    }
}
