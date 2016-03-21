package com.lod.movie_extended.data.model;

import android.support.annotation.NonNull;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Жамбыл on 3/18/2016.
 */
public class SessionDeserializer {

    public Session get(JsonObject sessionJson) {
        Session session = new Session();
        session.setFilm(deserializeFilm(sessionJson));
        session.setToken(new Token(sessionJson.get("AndroidToken").getAsString()));
        session.setPosterId(sessionJson.getAsJsonObject().get("Poster").getAsJsonObject().get("Id").getAsInt());
        return session;
    }

    private Film deserializeFilm(JsonObject jsonObject) {
        Film film = new Film();
        film.setId(jsonObject.get("Id").getAsInt());
        film.setName(jsonObject.get("Name").getAsString());
        List<List<Language>> languages = deserializeLanguages(jsonObject);
        film.setSoundLanguages(languages.get(0));
        film.setSubtitleLanguages(languages.get(1));
        return film;
    }

    private List<List<Language>> deserializeLanguages(JsonObject jsonObject) {
        List<Language> subLanguages = new ArrayList<>();
        List<Language> soundLanguage = new ArrayList<>();
        JsonArray languagesJson = jsonObject.get("Language").getAsJsonArray();

        for (int i = 0; i < languagesJson.size(); i++) {
            Language language = deserializeLanguage(languagesJson, i);
            addLanguage(subLanguages, soundLanguage, language);
        }

        List<List<Language>> result = new ArrayList<>();
        result.add(soundLanguage);
        result.add(subLanguages);
        return result;
    }

    private void addLanguage(List<Language> subLanguages, List<Language> soundLanguage, Language language) {
        if(language.getTrackFile() != null) {
            soundLanguage.add(language);
        }
        if(language.getSubtitle() != null) {
            subLanguages.add(language);
        }
    }

    @NonNull
    private Language deserializeLanguage(JsonArray languagesJson, int index) {
        Language language = new Language();
        language.setName(languagesJson.get(index).getAsJsonObject().get("Name").getAsString());
        language.setId(languagesJson.get(index).getAsJsonObject().get("Id").getAsInt());
        language.setTrackFile(new Language.TrackFile(languagesJson.get(index)
                .getAsJsonObject().get("TrackFile").getAsJsonObject().get("Id").getAsInt()));
        language.setSubtitle(new Language.Subtitle(languagesJson.get(index)
                .getAsJsonObject().get("Subtitles").getAsJsonObject().get("Id").getAsInt()));
        return language;
    }
}
