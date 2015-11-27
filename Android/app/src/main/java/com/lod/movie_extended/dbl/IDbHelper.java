package com.lod.movie_extended.dbl;

import com.lod.movie_extended.model.Language;

import java.util.ArrayList;

/**
 * Created by Жамбыл on 26.11.2015.
 */
public interface IDbHelper {

    void saveFilmData(int data);

    int getFilmData();

    boolean hasCachedData();

    void saveLanguages(ArrayList<Language> languages);

    ArrayList<Language> getLanguages();

    int getCode();
}
