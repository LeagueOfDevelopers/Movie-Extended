package com.lod.movie_extended.data.model.player;

/**
 * Created by Жамбыл on 3/2/2016.
 */
public interface PlayerListener {

    void onStateChanged(boolean playWhenReady);
    void onError(Exception e);
    void onWiredHeadsetNotOn();
    void onWiredHeadsetOn();
}
