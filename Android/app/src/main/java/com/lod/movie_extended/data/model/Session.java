package com.lod.movie_extended.data.model;

/**
 * Created by Жамбыл on 09.01.2016.
 */
public class Session {

    private Film film;

    private Token token;
    private long remainingTimeSeconds;

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    public Film getFilm() {
        return film;
    }

    public void setRemainingTimeSeconds(long remainingTimeMs) {
        this.remainingTimeSeconds = remainingTimeMs;
    }

    public long getRemainingTimeSeconds() {
        return remainingTimeSeconds;
    }

    public void setFilm(Film film) {
        this.film = film;
    }

}
