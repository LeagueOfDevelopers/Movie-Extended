package com.lod.movie_extended.dbl;

import com.lod.movie_extended.model.Language;

import java.util.ArrayList;

/**
 * Created by Жамбыл on 26.11.2015.
 */
public class DbHelperImpl implements IDbHelper {

    @Override
    public void saveFilmData(int data) {

    }

    @Override
    public int getFilmData() {
        return 0;
    }

    @Override
    public boolean hasCachedData() {
        return false;
    }

    @Override
    public void saveLanguages(ArrayList<Language> languages) {

    }

    @Override
    public ArrayList<Language> getLanguages() {
        return null;
    }

    @Override
    public int getCode() {
        return 0;
    }
}
