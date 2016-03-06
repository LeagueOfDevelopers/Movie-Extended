package com.lod.movie_extended.data.model;

import com.google.gson.JsonArray;

/**
 * Created by Жамбыл on 09.01.2016.
 */
public class Session {

    private Film film;

    private Token token;
    private long remainingTimeSeconds;
    private JsonArray jsonArray;
    private String qrCode;
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

    public JsonArray getJsonArray() {
        return jsonArray;
    }

    public void setJsonArray(JsonArray jsonArray) {
        this.jsonArray = jsonArray;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }
}
