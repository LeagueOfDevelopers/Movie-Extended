package com.lod.movie_extended.ui.activity.film;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.graphics.Palette;

import com.google.android.exoplayer.drm.UnsupportedDrmException;
import com.lod.movie_extended.data.DataManager;
import com.lod.movie_extended.data.model.player.Player;
import com.lod.movie_extended.data.model.player.PlayerListener;
import com.lod.movie_extended.service.PlayerNotificationService;
import com.lod.movie_extended.ui.base.BasePresenter;
import com.lod.movie_extended.util.Constants;

import timber.log.Timber;

/**
 * Created by Жамбыл on 09.01.2016.
 */
public class FilmPresenter extends BasePresenter<FilmMvpView> implements
        PlayerListener {

    private DataManager dataManager;
    private Context context;
    private Player player;
    private long playerPosition;    @Nullable
    private Palette palette;

    public FilmPresenter(DataManager dataManager, Context context, Player player) {
        this.dataManager = dataManager;
        this.context = context;
        this.player = player;

        if(!player.hasAudioUrlBeenSet) {
            Timber.v("setting audio url");
            player.startAudio("http://movieextended1.azurewebsites.net/api/file/get/1");
        }
    }

    public void onCreate() {
        player.addListener(this);
        startPlayerNotificationService();
    }

    public void togglePlayer() {
        boolean playing = player.getPlayWhenReady();
        player.setPlayWhenReady(!playing);
    }

    public boolean isPlaying() {
        return player.getPlayWhenReady();
    }

    public void startPlayerNotificationService() {
        Timber.v("starting PlayerNotificationService");
        startServiceWithAction(Constants.ACTION.START_FOREGROUND_ACTION);
    }

    public void OnDestroy() {
        removeListener();
    }


    @Override
    public void onStateChanged(boolean playWhenReady) {
        getMvpView().togglePlayPauseButtonSlowIfNeed();
    }


    private void startServiceWithAction(String toRightAction) {
        Intent serviceIntent = new Intent(context, PlayerNotificationService.class);
        serviceIntent.setAction(toRightAction);
        context.startService(serviceIntent);
    }

    private void removeListener() {
        player.removeListener(this);
    }

    @Override
    public void onError(Exception e) {
        if (e instanceof UnsupportedDrmException) {
            Timber.e(e.getMessage());
        }
    }

    @Override
    public void onWiredHeadsetNotOn() {
        getMvpView().showHeadsetError();
    }


}
