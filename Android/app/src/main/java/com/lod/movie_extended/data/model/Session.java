package com.lod.movie_extended.data.model;

import java.util.Date;

/**
 * Created by Жамбыл on 09.01.2016.
 */
public class Session {

    private Film film;
    private Token token;
    private String qrCode;
    private int posterId;
    private Date filmStartTime;

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    public Film getFilm() {
        return film;
    }

    public void setFilm(Film film) {
        this.film = film;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public void setPosterId(int posterId) {
        this.posterId = posterId;
    }

    public int getPosterId() {
        return posterId;
    }

    public Date getFilmStartTime() {
        return this.filmStartTime;
    }

    public void setFilmStartTime(Date filmStartTime) {
        this.filmStartTime = filmStartTime;
    }
}
