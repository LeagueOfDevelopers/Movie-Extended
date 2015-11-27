package com.lod.movie_extended.bll;

import android.util.Log;

/**
 * Created by Жамбыл on 26.11.2015.
 */
public class ServerMock implements IServer {

    private static final String TAG = "MyApplication";

    @Override
    public void sendCode(int code) {

    }

    @Override
    public void getLanguages() {

    }

    @Override
    public void chooseLanguage() {
        Log.v(TAG,"choosing language");
    }

    @Override
    public int getAudio() {
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
