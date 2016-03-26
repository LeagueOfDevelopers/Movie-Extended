package com.lod.movie_extended.data.local;

import android.support.annotation.Nullable;

import com.lod.movie_extended.data.model.Language;
import com.lod.movie_extended.data.model.Session;

import rx.Observable;
import timber.log.Timber;


/**
 * Created by Жамбыл on 09.01.2016.
 */
public class DataBaseHelper {

    private Session session;
    private String qrCode;
    private Language selectedSoundLanguage;
    private Language selectedSubtitleLanguage;

    public Observable<Session> saveSession(Session session) {
        this.session = session;
        Timber.e("saving session " + session.getToken());
        return Observable.just(session);
    }

    public Observable<Session> getSession(){
        return Observable.just(session);
    }


    public boolean isDataCached() {
        return true;
    }

    public @Nullable String getQrCode() {
        if(qrCode == null) {
            throw new NullPointerException("qrCode is null");
        }
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public void savePreviousSoundLanguage(Language selectedSoundLanguage) {
        this.selectedSoundLanguage = selectedSoundLanguage;
    }

    public void savePreviousSubLanguage(Language selectedSubtitleLanguage) {
        this.selectedSubtitleLanguage = selectedSubtitleLanguage;
    }

    public Language getPreviousSoundLanguage() {
        return selectedSoundLanguage;
    }

    public Language getPreviousSubtitleLanguage() {
        return selectedSubtitleLanguage;
    }
}
