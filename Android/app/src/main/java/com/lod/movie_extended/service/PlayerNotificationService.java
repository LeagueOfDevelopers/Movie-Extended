package com.lod.movie_extended.service;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaMetadataRetriever;
import android.media.RemoteControlClient;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.RemoteViews;

import com.lod.movie_extended.App;
import com.lod.movie_extended.R;
import com.lod.movie_extended.data.DataManager;
import com.lod.movie_extended.data.model.Player;
import com.lod.movie_extended.ui.activity.filmPreparation.FilmPreparationActivity;
import com.lod.movie_extended.util.Constants;

import javax.inject.Inject;

import timber.log.Timber;

/**
 * Created by Жамбыл on 09.01.2016.
 */
public class PlayerNotificationService extends Service implements Player.Listener {

    Notification notification;
    RemoteControlClientCompat remoteControlClientCompat;

    @Inject
    Player player;

    @Inject
    NotificationManager notificationManager;

    @Inject
    AudioManager audioManager;

    @Inject
    ComponentName mediaButtonReceiverComponent;

    @Inject
    DataManager dataManager;

    RemoteViews smallView, bigView;

    @Override
    public void onCreate() {
        super.onCreate();
        App.get(getApplicationContext()).getComponent().inject(this);
        player.addListener(this);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void registerRemoteControlClient() {
        Timber.v("registerRemoteControlClient");
        Intent intent = new Intent(Intent.ACTION_MEDIA_BUTTON);
        intent.setComponent(mediaButtonReceiverComponent);
        remoteControlClientCompat = new RemoteControlClientCompat(
                PendingIntent.getBroadcast(this /*context*/,0 /*requestCode, ignored*/, intent /*intent*/, 0 /*flags*/));

        RemoteControlHelper.registerRemoteControlClient(audioManager,remoteControlClientCompat);

        remoteControlClientCompat.setPlaybackState(RemoteControlClient.PLAYSTATE_PLAYING);

        remoteControlClientCompat.setTransportControlFlags(
                RemoteControlClient.FLAG_KEY_MEDIA_PLAY |
                        RemoteControlClient.FLAG_KEY_MEDIA_PAUSE |
                        RemoteControlClient.FLAG_KEY_MEDIA_NEXT |
                        RemoteControlClient.FLAG_KEY_MEDIA_STOP);

        remoteControlClientCompat.editMetadata(true)
                .putString(MediaMetadataRetriever.METADATA_KEY_ARTIST, "artist")
                .putString(MediaMetadataRetriever.METADATA_KEY_ALBUM, "album")
                .putString(MediaMetadataRetriever.METADATA_KEY_TITLE, "title")
                .putLong(MediaMetadataRetriever.METADATA_KEY_DURATION, 12)
                .putBitmap(RemoteControlClientCompat.MetadataEditorCompat.METADATA_KEY_ARTWORK, Constants.getDefaultAlbumArt(this))
                .apply();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        switch (intent.getAction()) {
            case Constants.ACTION.START_FOREGROUND_ACTION:
                createAndShowNotification(false);
                registerRemoteControlClient();
                break;

            case Constants.ACTION.PLAY_OR_PAUSE:
                if (!getIsPlaying()) {
                    play();
                } else {
                    pause();
                }
                break;

            case Constants.ACTION.MOVE_TO_LEFT:
                moveToLeft();
                break;

            case Constants.ACTION.MOVE_TO_RIGHT:
                moveToRight();
                break;
        }

        return START_NOT_STICKY;
    }

    @SuppressLint("NewApi")
    public void play() {
        Timber.v("notification play");
        player.setPlayWhenReady(true);
        createAndShowNotification(true);
        setPlayOrPauseImage();
    }

    public void pause() {
        Timber.v("notification pause");
        player.setPlayWhenReady(false);
        stopForeground(false);
        setPlayOrPauseImage();
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void setPlayOrPauseImage() {
        if(getIsPlaying()) {
            bigView.setImageViewResource(R.id.status_bar_play, R.mipmap.apollo_holo_dark_pause);
            smallView.setImageViewResource(R.id.status_bar_play, R.mipmap.apollo_holo_dark_pause);
            notification.flags = Notification.FLAG_ONGOING_EVENT;
        }
        else {
            bigView.setImageViewResource(R.id.status_bar_play, R.mipmap.apollo_holo_dark_play);
            smallView.setImageViewResource(R.id.status_bar_play, R.mipmap.apollo_holo_dark_play);
            notification.flags &= ~ Notification.FLAG_ONGOING_EVENT;
        }
        notification.contentView = smallView;
        notification.bigContentView = bigView;
        notificationManager.notify(Constants.NOTIFICATION_ID.FOREGROUND_SERVICE,notification);
    }

    public void moveToLeft() {
        player.moveToLeft();
    }

    public void moveToRight() {
        player.moveToRight();
    }

    public boolean getIsPlaying() {
        return player.getPlayWhenReady();
    }

    public void createAndShowNotification(boolean foreground) {
        smallView = getRemoteViews(R.layout.notification_player_small);
        bigView = getRemoteViews(R.layout.notification_player_big);
        Notification notification = getNotification(smallView,bigView);
        if(foreground) {
            startForeground(Constants.NOTIFICATION_ID.FOREGROUND_SERVICE, notification);
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @NonNull
    private Notification getNotification(RemoteViews smallView, RemoteViews bigView) {

        notification = new Notification.Builder(this)
                .setSmallIcon(R.mipmap.star_wars)
                .setContentIntent(getMainPendingIntent())
                .setContent(smallView)
                .setPriority(Notification.PRIORITY_MAX)
                .build();

        notification.flags = Notification.FLAG_AUTO_CANCEL;
        notification.bigContentView = bigView;

        return notification;
    }

    private void setForeground(boolean isForeground) {
        if(isForeground) {
            notification.flags = Notification.FLAG_FOREGROUND_SERVICE;
        }
        else {
            notification.flags = Notification.FLAG_NO_CLEAR;
        }

        notificationManager.notify(Constants.NOTIFICATION_ID.FOREGROUND_SERVICE,notification);
    }
    private RemoteViews getRemoteViews(@LayoutRes int layoutId) {
        RemoteViews view = new RemoteViews(getPackageName(),layoutId);
        setOnPendingClickListeners(view);
        setTextViewText(view);
        view.setImageViewBitmap(R.id.status_bar_album_art, Constants.getDefaultAlbumArt(this));
        if(getIsPlaying()) {
            view.setImageViewResource(R.id.status_bar_play, R.mipmap.apollo_holo_dark_pause);
        }
        else {
            view.setImageViewResource(R.id.status_bar_play, R.mipmap.apollo_holo_dark_play);
        }
        return view;
    }
    private void setTextViewText(RemoteViews view) {
        view.setTextViewText(R.id.status_bar_track_name, dataManager.getSession().getFilm().getName());
//        view.setTextViewText(R.id.status_bar_artist_name, "Artist Name");
//        view.setTextViewText(R.id.status_bar_album_name, "Album Name");
    }

    private void setOnPendingClickListeners(RemoteViews view) {
        view.setOnClickPendingIntent(R.id.status_bar_play, getPendingIntent(Constants.ACTION.PLAY_OR_PAUSE));
        view.setOnClickPendingIntent(R.id.status_bar_next, getPendingIntent(Constants.ACTION.MOVE_TO_RIGHT));
        view.setOnClickPendingIntent(R.id.status_bar_prev, getPendingIntent(Constants.ACTION.MOVE_TO_LEFT));
    }

    private PendingIntent getMainPendingIntent() {
        Intent intent = new Intent(this, FilmPreparationActivity.class);
        intent.setAction(Constants.ACTION.MAIN_ACTION);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        return PendingIntent.getActivity(this, 0, intent, 0);
    }

    private PendingIntent getPendingIntent(String action) {
        Intent intent = new Intent(this, PlayerNotificationService.class);
        intent.setAction(action);
        return PendingIntent.getService(this, 0, intent, 0);
    }

    @Override
    public void onStateChanged(boolean playWhenReady, int playbackState) {
        setPlayOrPauseImage();
        if(!playWhenReady) {
            stopForeground(true);
        }
    }

    @Override
    public void onError(Exception e) {

    }

    @Override
    public void onWiredHeadsetNotOn() {
        //do nothing
    }
}

