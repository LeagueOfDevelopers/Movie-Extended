package com.lod.movie_extended.ui.activity.film;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v7.graphics.Palette;

import com.google.android.exoplayer.drm.UnsupportedDrmException;
import com.lod.movie_extended.R;
import com.lod.movie_extended.data.DataManager;
import com.lod.movie_extended.data.model.ColorHelper;
import com.lod.movie_extended.data.model.Player;
import com.lod.movie_extended.service.PlayerNotificationService;
import com.lod.movie_extended.ui.base.BasePresenter;
import com.lod.movie_extended.util.Constants;

import timber.log.Timber;

/**
 * Created by Жамбыл on 09.01.2016.
 */
public class FilmPresenter extends BasePresenter<FilmMvpView> implements
        Player.Listener {

    private DataManager dataManager;
    private Context context;
    private Player player;
    private long playerPosition;
    private ColorHelper colorHelper;

    @Nullable
    private Palette palette;

    public FilmPresenter(DataManager dataManager, Context context, Player player, ColorHelper colorHelper) {
        this.dataManager = dataManager;
        this.context = context;
        this.player = player;
        this.colorHelper = colorHelper;
        if(player.getAudioUrl().equals(Player.EMPTY_AUDIO_URL)) {
            Timber.v("setting audio url");
            player.setAudioUrl("http://movieextended1.azurewebsites.net/api/file/get/43");
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

    public int getPosterDarkColor() {
        return colorHelper.getPosterDarkColor();
    }

    public int getPosterLightColor() {
        return colorHelper.getPosterLightColor();
    }


    public void OnDestroy() {
        removeListener();
    }


    @Override
    public void onStateChanged(boolean playWhenReady, int playbackState) {
        getMvpView().togglePlayPauseButtonSlowIfNeed();
    }


    private void releasePlayer() {
        Timber.v("releasing player");
        if (player != null) {
            playerPosition = player.getCurrentPosition();
            player.release();
            player = null;
        }
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
