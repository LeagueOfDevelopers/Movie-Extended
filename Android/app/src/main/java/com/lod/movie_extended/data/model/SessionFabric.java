package com.lod.movie_extended.data.model;

import java.util.ArrayList;

/**
 * Created by Жамбыл on 09.01.2016.
 */
public class SessionFabric {

    public static Session createSession(Token token, FilmBuilder filmBuilder) {
        Session session = new Session();
        session.setToken(token);
        session.setFilm(filmBuilder.getResult());
        return session;
    }
}
