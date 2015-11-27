package com.lod.movie_extended.bll;

/**
 * Created by Жамбыл on 26.11.2015.
 */

public interface IServer {

    void sendCode(int code);

    void getLanguages();

    void chooseLanguage();

    int getAudio();

    int getFilmData();

    int getState();
}
