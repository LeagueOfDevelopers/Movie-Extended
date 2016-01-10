package com.lod.movie_extended.data.local;

import com.lod.movie_extended.data.model.Session;

/**
 * Created by Жамбыл on 09.01.2016.
 */
public class DataBaseHelper {

    Session session;

    public void saveSession(Session session) {
        this.session = session;
    }

    public Session getSession(){
        return session;
    }
}
