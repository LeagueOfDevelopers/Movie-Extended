package com.lod.movie_extended.data.model;

import android.graphics.Bitmap;

import java.util.ArrayList;

/**
 * Created by Жамбыл on 09.01.2016.
 */
public class FilmBuilder {

    private Film result;

    private ArrayList<Language> languages;

    public FilmBuilder() {
        result = new Film();
        languages = new ArrayList<>();
    }

    public void addLanguage(Language language) {
        languages.add(language);
    }

    public void setName(String Name) {
        result.setName(Name);
    }

    public void setPoster(Bitmap bitmap) {
        result.setPoster(bitmap);
    }

    public Film getResult() {
        result.setLanguages(languages);
        return result;
    }
}
