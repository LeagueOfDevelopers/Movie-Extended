package com.lod.movie_extended.ui.activity.filmShow;

import android.content.Context;
import android.net.Uri;

import com.lod.movie_extended.data.DataManager;
import com.lod.movie_extended.data.model.Language;
import com.lod.movie_extended.data.model.NotificationServiceHelper;
import com.lod.movie_extended.data.model.Session;
import com.lod.movie_extended.data.model.player.Player;
import com.lod.movie_extended.data.model.player.PlayerListener;
import com.lod.movie_extended.data.remote.ServerHelper;
import com.lod.movie_extended.ui.base.BasePresenter;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Жамбыл on 3/17/2016.
 */
public class FilmShowPresenter extends BasePresenter<FilmShowView> implements PlayerListener {

    private DataManager dataManager;
    private Player player;
    private NotificationServiceHelper notificationServiceHelper;
    private Session currentSession;
    private ServerHelper serverHelper;

    public FilmShowPresenter(DataManager dataManager, Player player, NotificationServiceHelper notificationServiceHelper, ServerHelper serverHelper) {
        this.dataManager = dataManager;
        this.player = player;
        this.notificationServiceHelper = notificationServiceHelper;
        this.serverHelper = serverHelper;
        player.addListener(this);
    }

    public void loadSessionIfNeed() {
        if(currentSession==null) {
            loadSession();
        }
    }

    private void loadSession() {
        dataManager.getSession()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Subscriber<Session>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {
                    getMvpView().showError();
                }

                @Override
                public void onNext(Session session) {
                    currentSession = session;
                    getMvpView().setFilm(session.getFilm());
                    player.setFilmStartTime(session.getFilmStartTime());
                    loadDefaultTrack(session);
                    notificationServiceHelper.start();
                }
            });
    }


    public boolean isFilmTime() {
        return dataManager.isFilmTime();
    }

    public void setFilmTime(boolean filmTime) {
        dataManager.setFilmTime(filmTime);
    }

    @Override
    public void onStateChanged(boolean playWhenReady) {
        if(getMvpView()!=null) {
            if (playWhenReady) {
                getMvpView().setPauseView();
            } else {
                getMvpView().setPlayView();
            }
        }
    }

    @Override
    public void onError(Exception e) {
    }

    @Override
    public void onWiredHeadsetNotOn() {
        getMvpView().setHeadsetFooter();
    }

    @Override
    public void onWiredHeadsetOn() {
        getMvpView().setNormalFooter();
    }

    public void togglePlayer() {
        player.setPlayWhenReady(!player.getPlayWhenReady());
    }

    private void loadDefaultTrack(Session session) {
        Uri audioUrl = gerDefaultAudioUrl(session);
        Uri subUrl = getDefaultSubUrl(session);
        player.startAudioWithSubtitles(audioUrl,subUrl);
    }

    private Uri gerDefaultAudioUrl(Session session) {
        Language defaultLanguage = session.getFilm().getSelectedSoundLanguage();
        int languageDownloadId = defaultLanguage.getTrackFile().getId();
        return serverHelper.getDownloadUrl(languageDownloadId,session.getToken());
    }

    private Uri getDefaultSubUrl(Session session) {
        Language defaultLanguage = session.getFilm().getSelectedSoundLanguage();
        int subtitleDownloadId = defaultLanguage.getSubtitle().getId();
        return serverHelper.getDownloadUrl(subtitleDownloadId,session.getToken());
    }
}
