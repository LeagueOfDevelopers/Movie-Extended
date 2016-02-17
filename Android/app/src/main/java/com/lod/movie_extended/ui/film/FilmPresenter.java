package com.lod.movie_extended.ui.film;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaCodec;
import android.support.annotation.Nullable;
import android.support.v7.graphics.Palette;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.PopupMenu;

import com.google.android.exoplayer.ExoPlayer;
import com.google.android.exoplayer.MediaCodecTrackRenderer;
import com.google.android.exoplayer.MediaFormat;
import com.google.android.exoplayer.audio.AudioCapabilities;
import com.google.android.exoplayer.audio.AudioCapabilitiesReceiver;
import com.google.android.exoplayer.audio.AudioTrack;
import com.google.android.exoplayer.drm.UnsupportedDrmException;
import com.google.android.exoplayer.metadata.GeobMetadata;
import com.google.android.exoplayer.metadata.PrivMetadata;
import com.google.android.exoplayer.metadata.TxxxMetadata;
import com.google.android.exoplayer.text.Cue;
import com.google.android.exoplayer.util.MimeTypes;
import com.lod.movie_extended.App;
import com.lod.movie_extended.R;
import com.lod.movie_extended.data.DataManager;
import com.lod.movie_extended.data.model.Player;
import com.lod.movie_extended.service.PlayerNotificationService;
import com.lod.movie_extended.ui.base.BasePresenter;
import com.lod.movie_extended.util.Constants;

import java.io.IOException;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import timber.log.Timber;

/**
 * Created by Жамбыл on 09.01.2016.
 */
public class    FilmPresenter extends BasePresenter<FilmMvpView> implements
        Player.Listener {

    private DataManager dataManager;

    private Context context;
    private Player player;

    private long playerPosition;

    @Nullable
    private Palette palette;

    public FilmPresenter(DataManager dataManager,Context context, Player player) {
        this.dataManager = dataManager;
        this.context = context;
        this.player = player;
    }

    public void setAudioUrl(Context context) {
        dataManager.setAudioUrl(context,"http://movieextended1.azurewebsites.net/api/file/get/43");
    }

    public void togglePlayer() {
        boolean playing = player.getPlayWhenReady();
        player.setPlayWhenReady(!playing);
    }

    public void startPlayerNotificationService() {
        Timber.v("starting PlayerNotificationService");
        startServiceWithAction(Constants.ACTION.START_FOREGROUND_ACTION);
    }

    public void preparePlayer(boolean playWhenReady) {
        Timber.v("preparing player");
        player.addListener(this);
        player.seekTo(playerPosition);
        player.prepare();
    }

    public int getPosterDarkColor() {
        return getPosterPalette().getDarkVibrantColor(0);
    }

    public int getPosterLightColor() {
        return getPosterPalette().getLightVibrantColor(0);
    }

    public Palette getPosterPalette() {
        if(palette == null) {
            Bitmap poster = BitmapFactory.decodeResource(context.getResources(),
                    R.mipmap.star_wars);
            palette = Palette.from(poster).generate();
        }
        return palette;
    }

    public void OnDestroy() {
        removeListener();
    }


    @Override
    public void onStateChanged(boolean playWhenReady, int playbackState) {
        getMvpView().togglePlayPauseButton();
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


}
