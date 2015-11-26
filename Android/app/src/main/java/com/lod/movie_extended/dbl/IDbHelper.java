package com.lod.movie_extended.dbl;

/**
 * Created by Жамбыл on 26.11.2015.
 */
public interface IDbHelper {

    void saveData(int data);

    boolean hasCachedData();

    int getCachedLanguages();

    int getCachedFilmData();

    int getCachedCode();
}
