package com.lod.movie_extended.data;

import com.lod.movie_extended.data.local.DataBaseHelper;
import com.lod.movie_extended.data.model.Film;
import com.lod.movie_extended.data.model.Language;
import com.lod.movie_extended.data.model.Session;
import com.lod.movie_extended.data.remote.ServerHelper;
import com.lod.movie_extended.injection.scope.PerApplication;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import timber.log.Timber;

/**
 * Created by Жамбыл on 09.01.2016.
 */
@PerApplication
public class DataManager {

    private final DataBaseHelper dataBaseHelper;
    private ServerHelper serverHelper;
    private boolean filmTime;
    private boolean qrCodeProcessed;
    private Language soundLanguage;
    private Language subtitlesLanguage;

    @Inject
    public DataManager(DataBaseHelper dataBaseHelper, ServerHelper serverHelper) {
        this.dataBaseHelper = dataBaseHelper;
        this.serverHelper = serverHelper;
    }

    public void setQrCode(String qrCode) {
        dataBaseHelper.setQrCode(qrCode);
        setQrCodeProcessed(true);
    }

    public Observable<Session> getSession() {
        return Observable.create((Observable.OnSubscribe<Session>) subscriber ->
        {
            String qrCode = dataBaseHelper.getQrCode();
            Session sessionFromDatabase = dataBaseHelper.getSession().toBlocking().first();

            if(sessionFromDatabase != null && sessionFromDatabase.getQrCode().equals(qrCode)) {
                Timber.e("getting session from database");
                subscriber.onNext(sessionFromDatabase);
            }
            else {
                Timber.e("getting session from internet");
                Session sessionFromInternet = serverHelper.loadSession(qrCode)
                        .concatMap(dataBaseHelper::saveSession).toBlocking().first();

                setDefaultSelectedLanguages(sessionFromInternet);
                subscriber.onNext(sessionFromInternet);
            }
        });
    }

    private void setDefaultSelectedLanguages(Session session) {
        Film film = session.getFilm();
        film.setSelectedSubtitleLanguage(film.getSubtitleLanguages().get(0));
        film.setSelectedSoundLanguage(film.getSoundLanguages().get(0));
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

    public Language getSubtitlesLanguage() {
        return subtitlesLanguage;
    }

    public void setSubtitlesLanguage(Language subtitlesLanguage) {
        this.subtitlesLanguage = subtitlesLanguage;
    }

    public Language getSoundLanguage() {
        return soundLanguage;
    }

    public void setSoundLanguage(Language soundLanguage) {
        this.soundLanguage = soundLanguage;
    }

    private void setQrCodeProcessed(boolean qrCodeProcessed) {
        this.qrCodeProcessed = qrCodeProcessed;
    }
}
