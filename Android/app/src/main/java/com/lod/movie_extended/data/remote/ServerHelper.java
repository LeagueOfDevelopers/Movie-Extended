package com.lod.movie_extended.data.remote;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.lod.movie_extended.data.model.Film;
import com.lod.movie_extended.data.model.Language;
import com.lod.movie_extended.data.model.Session;
import com.lod.movie_extended.data.model.Token;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;

/**
 * Created by Жамбыл on 09.01.2016.
 */
public class ServerHelper {

    private ServerAPI serverAPI;

    public ServerHelper(ServerAPI serverAPI) {
        this.serverAPI = serverAPI;
    }

    public Observable<Session> loadSession(String qrCode) {
        return Observable.create((Observable.OnSubscribe<Session>) subscriber -> {
            JsonObject jsonElements = serverAPI.getData(qrCode).toBlocking().first();
            Session session = deserializeSession(jsonElements);
            session.setQrCode(qrCode);
            subscriber.onNext(session);
        });
    }

    private Session deserializeSession(JsonObject jsonObject) {
        Session session = new Session();
        session.setFilm(deserializeFilm(jsonObject));
        session.setToken(new Token(jsonObject.get("AndroidToken").getAsString()));
        session.setPosterId(jsonObject.getAsJsonObject().get("Poster").getAsJsonObject().get("Id").getAsInt());
        return session;
    };

    private Film deserializeFilm(JsonObject jsonObject) {
        Film film = new Film();
        film.setId(jsonObject.get("Id").getAsInt());
        film.setName(jsonObject.get("Name").getAsString());
        List<Language> languages = deserializeLanguages(jsonObject);
        film.setSoundLanguages(languages);
        film.setSubtitleLanguages(languages);
        return film;
    }

    private List<Language> deserializeLanguages(JsonObject jsonObject) {
        List<Language> languages = new ArrayList<>();
        JsonArray languagesJson = jsonObject.get("Language").getAsJsonArray();
        for (int i = 0; i < languagesJson.size(); i++) {
            Language language = new Language();
            language.setName(languagesJson.get(i).getAsJsonObject().get("Name").getAsString());
            language.setId(languagesJson.get(i).getAsJsonObject().get("Id").getAsInt());
            language.setTrackFile(new Language.TrackFile(languagesJson.get(i).getAsJsonObject().get("TrackFile").getAsJsonObject().get("Id").getAsInt()));
            language.setSubtitle(new Language.Subtitle(languagesJson.get(i).getAsJsonObject().get("Subtitles").getAsJsonObject().get("Id").getAsInt()));
            languages.add(language);
        }
        return languages;
    }
}
