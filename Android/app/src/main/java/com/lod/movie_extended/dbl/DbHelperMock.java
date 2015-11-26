package com.lod.movie_extended.dbl;

/**
 * Created by Жамбыл on 26.11.2015.
 */
public class DbHelperMock implements IDbHelper {

    @Override
    public void saveData(int data) {

    }

    @Override
    public boolean hasCachedData() {
        return false;
    }

    @Override
    public int getCachedLanguages() {
        return 0;
    }

    @Override
    public int getCachedFilmData() {
        return 0;
    }

    @Override
    public int getCachedCode() {
        return 0;
    }
}
