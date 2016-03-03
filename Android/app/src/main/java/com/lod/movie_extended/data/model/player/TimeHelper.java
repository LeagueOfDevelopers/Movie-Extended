package com.lod.movie_extended.data.model.player;

import java.util.Date;

/**
 * Created by Жамбыл on 3/3/2016.
 */
public class TimeHelper {

    private final Date filmStartTime;
    private long filmDuration;

    public TimeHelper() {
        filmStartTime = new Date();
    }

    public TimeHelper(Date filmStartTime) {
        this.filmStartTime = filmStartTime;
    }

    public long getCurrentFilmTime() {
        return getCurrentTime() - filmStartTime.getTime();
    }

    private long getCurrentTime() {
        return System.currentTimeMillis();
    }

    public void setFilmDuration(long filmDuration) {
        this.filmDuration = filmDuration;
    }
}
