package com.lod.movie_extended.data.model.player;

import java.security.Timestamp;
import java.util.Date;

import timber.log.Timber;

/**
 * Created by Жамбыл on 3/3/2016.
 */
public class TimeHelper {

    private Date filmStartTime;
    private long filmDuration;

    public void setFilmStartTime(Date filmStartTime) {
        Timber.e("setting film start time");
        this.filmStartTime = filmStartTime;
    }

    public long getCurrentFilmTime() {
        Date now = new Date();
        Timber.e("Current time " + now.getTime());
        Timber.e("Film start time " + filmStartTime.getTime());
        if(filmStartTime.getTime() < 10) {
            return -1;
        }
        long dif = now.getTime() - filmStartTime.getTime();
        dif = dif - 3*60*60*1000;
        Timber.e("Dif " + dif);
        return dif;
    }

    private long getCurrentTime() {
        return System.currentTimeMillis();
    }

    public void setFilmDuration(long filmDuration) {
        this.filmDuration = filmDuration;
    }
}
