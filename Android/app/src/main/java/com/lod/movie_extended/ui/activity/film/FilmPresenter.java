package com.lod.movie_extended.ui.activity.film;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.graphics.Palette;

import com.google.android.exoplayer.drm.UnsupportedDrmException;
import com.lod.movie_extended.data.DataManager;
import com.lod.movie_extended.data.model.ServiceHelper;
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
    private ServiceHelper serviceHelper;

    public FilmPresenter(DataManager dataManager, Context context, Player player, ServiceHelper serviceHelper) {
        this.dataManager = dataManager;
        this.context = context;
        this.player = player;
        this.serviceHelper = serviceHelper;

        if(!player.hasAudioUrlBeenSet) {
            Timber.v("setting audio url");
            player.startAudio("http://movieextended1.azurewebsites.net/api/file/get/1");
        }
    }

    public void onCreate() {
        player.addListener(this);
        serviceHelper.startNotificationService();
    }

    public void togglePlayer() {
        boolean playing = player.getPlayWhenReady();
        player.setPlayWhenReady(!playing);
    }

    public boolean isPlaying() {
        return player.getPlayWhenReady();
    }


    public void OnDestroy() {
        removeListener();
    }


    @Override
    public void onStateChanged(boolean playWhenReady) {
        getMvpView().togglePlayPauseButtonSlowIfNeed();
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
