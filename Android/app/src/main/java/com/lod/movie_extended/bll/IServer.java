package com.lod.movie_extended.bll;

import com.lod.movie_extended.model.Token;

/**
 * Created by Жамбыл on 26.11.2015.
 */

public interface IServer {

    void sendCode(String code);

    void getLanguages();

    String getExactTime();

    Token getToken();

    int loadAudioFromTime();

    int getFilmData();

    int getState();
}
