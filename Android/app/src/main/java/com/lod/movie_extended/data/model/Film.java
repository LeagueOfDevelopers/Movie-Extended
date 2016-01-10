package com.lod.movie_extended.data.model;

import android.graphics.Bitmap;

import java.util.ArrayList;

/**
 * Created by Жамбыл on 09.01.2016.
 */
public class Film {

    private ArrayList<Language> languages;
    private Bitmap poster;
    private Language selectedLanguage;
    private String Name;
    public Bitmap getPoster() {
        return poster;
    }

    public void setPoster(Bitmap poster) {
        this.poster = poster;
    }

    public ArrayList<Language> getLanguages() {
        return languages;
    }

    public void setLanguages(ArrayList<Language> languages) {
        this.languages = languages;
    }

    public Language getSelectedLanguage() {
        return selectedLanguage;
    }

    public void setSelectedLanguage(Language selectedLanguage) {
        this.selectedLanguage = selectedLanguage;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
