package com.lod.movie_extended.data.local;

import android.support.annotation.Nullable;

import com.lod.movie_extended.data.model.Session;
import com.lod.movie_extended.data.model.Token;

import rx.Observable;
import rx.Subscriber;
import timber.log.Timber;


/**
 * Created by Жамбыл on 09.01.2016.
 */
public class DataBaseHelper {

    private Session session;
    private String qrCode;

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
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }
}
