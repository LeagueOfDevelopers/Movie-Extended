package com.lod.movie_extended.ui.activity.film;

import android.content.Context;

import com.google.android.exoplayer.drm.UnsupportedDrmException;
import com.lod.movie_extended.data.DataManager;
import com.lod.movie_extended.data.model.NotificationServiceHelper;
import com.lod.movie_extended.data.model.player.Player;
import com.lod.movie_extended.data.model.player.PlayerListener;
import com.lod.movie_extended.ui.base.BasePresenter;

import timber.log.Timber;

/**
 * Created by Жамбыл on 09.01.2016.
 */
public class FilmPresenter extends BasePresenter<FilmMvpView> implements
        PlayerListener {

    private DataManager dataManager;
    private Context context;
    private Player player;
    private NotificationServiceHelper notificationServiceHelper;

    public FilmPresenter(DataManager dataManager, Context context, Player player, NotificationServiceHelper notificationServiceHelper) {
        this.dataManager = dataManager;
        this.context = context;
        this.player = player;
        this.notificationServiceHelper = notificationServiceHelper;

        Timber.v("setting audio url");
//        player.setAudioUrl("http://movieextended1.azurewebsites.net/api/file/get/1");
    }

    public void onCreate() {
        player.addListener(this);
        notificationServiceHelper.start();
    }

    public void togglePlayer() {
        boolean playing = player.getPlayWhenReady();
//        player.setPlayWhenReady(!playing);
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

    @Override
    public void onWiredHeadsetOn() {

    }


    public int test() {
        return 0;
    }
}
