package com.lod.movie_extended.data.model;

/**
 * Created by Жамбыл on 09.01.2016.
 */
public class Token {

    private String value;

    public Token(String value) {
        if(value == null || value.equals("")){
            throw new IllegalArgumentException();
        }
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
