package com.lod.movie_extended.ui.activity.filmShow;

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
import timber.log.Timber;

/**
 * Created by Жамбыл on 3/17/2016.
 */
public class FilmShowPresenter extends BasePresenter<FilmShowView> implements PlayerListener {

    private DataManager dataManager;
    private Player player;
    private NotificationServiceHelper notificationServiceHelper;
    private Session previousSession;
    private ServerHelper serverHelper;

    public FilmShowPresenter(DataManager dataManager, Player player, NotificationServiceHelper notificationServiceHelper, ServerHelper serverHelper) {
        this.dataManager = dataManager;
        this.player = player;
        this.notificationServiceHelper = notificationServiceHelper;
        this.serverHelper = serverHelper;
        player.addListener(this);
    }

    public void loadSession() {
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
                    getMvpView().setFilm(session.getFilm());
                    if(previousSession == null) {
                        loadDefaultTrack(session);
                        player.setFilmStartTime(session.getFilmStartTime());
                    }
                    else {
                        loadAnotherDataIfNeed(session);
                    }
                    notificationServiceHelper.start();
                    previousSession = session;
                }
            });
    }

    private void loadAnotherDataIfNeed(Session session) {
        if(previousSession.getFilm().getSelectedSoundLanguage() != session.getFilm().getSelectedSoundLanguage()) {
           player.setAudioUrl(serverHelper.getDownloadUrl(session.getFilm().getSelectedSoundLanguage().getId(),session.getToken()));
        }
        if(previousSession.getFilm().getSelectedSubtitleLanguage() != session.getFilm().getSelectedSubtitleLanguage()){
            player.setSubtitleUrl(serverHelper.getDownloadUrl(session.getFilm().getSelectedSubtitleLanguage().getId(),session.getToken()));
        }
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
        Uri audioUrl = getDefaultAudioUrl(session);
        Uri subUrl = getDefaultSubUrl(session);
        Timber.e("sub url " + subUrl);
        player.startAudioWithSubtitles(audioUrl,subUrl);
    }

    private Uri getDefaultAudioUrl(Session session) {
        Language defaultLanguage = session.getFilm().getSelectedSoundLanguage();
        int languageDownloadId = defaultLanguage.getTrackFile().getId();
        return serverHelper.getDownloadUrl(languageDownloadId,session.getToken());
    }

    private Uri getDefaultSubUrl(Session session) {
        Language defaultLanguage = session.getFilm().getSelectedSubtitleLanguage();
        int subtitleDownloadId = defaultLanguage.getSubtitle().getId();
        return serverHelper.getDownloadUrl(subtitleDownloadId,session.getToken());
    }
}
