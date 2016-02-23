package com.lod.movie_extended.data.model;

import android.graphics.Bitmap;
import android.support.v7.graphics.Palette;

/**
 * Created by Жамбыл on 2/24/2016.
 */
public class ColorHelper {

    private Palette palette;
    private Bitmap poster;

    public ColorHelper(Bitmap poster) {
        this.poster = poster;
    }

    private Palette getPalette() {
        if(palette == null) {
            palette = Palette.from(poster).generate();
        }
        return palette;
    }

    public int getPosterLightColor(){
        return getPalette().getLightVibrantColor(0);
    }

    public int getPosterDarkColor() {
        return getPalette().getDarkVibrantColor(0);
    }
}
