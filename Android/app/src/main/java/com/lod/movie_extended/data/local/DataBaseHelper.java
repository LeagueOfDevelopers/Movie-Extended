package com.lod.movie_extended.data.local;

import com.lod.movie_extended.data.model.Session;
import com.lod.movie_extended.data.model.Token;

/**
 * Created by Жамбыл on 09.01.2016.
 */
public class DataBaseHelper {

    Session session;

    Token token;

    public void saveToken(Token token) {
        this.token = token;
    }

    public void saveSession(Session session) {
        this.session = session;
    }

    public Session getSession(){
        return session;
    }

    public Token getToken() {
        return token;
    }
}
