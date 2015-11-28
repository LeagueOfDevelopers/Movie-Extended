package com.lod.movie_extended.bll;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.lod.movie_extended.injection.MyApp;
import com.lod.movie_extended.model.Token;
import com.lod.movie_extended.uil.activity.QrCodeReaderActivity;
import com.squareup.otto.Bus;

import javax.inject.Inject;

/**
 * Created by Жамбыл on 26.11.2015.
 */
public class ServerMock implements IServer {

    private static final String L_TAG = "Server Mock";

    @Inject
    public Bus serverEvents;

    public ServerMock(Context context) {
        MyApp.get(context).getServerComponent().inject(this);
    }

    @Override
    public void sendCode(String code) {
        Log.v(MyApp.TAG + L_TAG," sending code");
    }

    @Override
    public void getLanguages() {

    }

    @Override
    public String getExactTime() {
        return null;
    }

    @Override
    public Token getToken() {
        return null;
    }

    @Override
    public int loadAudioFromTime() {
        return 0;
    }

    @Override
    public int getFilmData() {
        return 0;
    }

    @Override
    public int getState() {
        return 0;
    }
}
