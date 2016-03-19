package com.lod.movie_extended.data.remote;

import android.support.annotation.NonNull;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.lod.movie_extended.data.model.Film;
import com.lod.movie_extended.data.model.Language;
import com.lod.movie_extended.data.model.Session;
import com.lod.movie_extended.data.model.Token;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.functions.Action1;
import timber.log.Timber;

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
            serverAPI.getData(qrCode)
                .doOnNext(jsonElements -> {
                    Session session = deserializeSession(jsonElements);
                    session.setQrCode(qrCode);
                    subscriber.onNext(session);
                })
                .toBlocking().subscribe();
        });
    }

    private Session deserializeSession(JsonObject jsonObject) {
        Session session = new Session();
        session.setFilm(deserializeFilm(jsonObject));
        session.setToken(new Token(jsonObject.get("AndroidToken").getAsString()));
        session.setPosterId(jsonObject.getAsJsonObject().get("Poster").getAsJsonObject().get("Id").getAsInt());
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
