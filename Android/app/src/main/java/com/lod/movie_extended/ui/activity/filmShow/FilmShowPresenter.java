package com.lod.movie_extended.ui.activity.filmShow;

import android.net.Uri;

import com.lod.movie_extended.data.DataManager;
import com.lod.movie_extended.data.local.DataBaseHelper;
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
    private ServerHelper serverHelper;
    private DataBaseHelper dataBaseHelper;

    public FilmShowPresenter(DataManager dataManager, Player player,
                             NotificationServiceHelper notificationServiceHelper,
                             ServerHelper serverHelper,
                             DataBaseHelper dataBaseHelper) {
        this.dataManager = dataManager;
        this.player = player;
        this.notificationServiceHelper = notificationServiceHelper;
        this.serverHelper = serverHelper;
        this.dataBaseHelper = dataBaseHelper;
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
                    e.printStackTrace();
                    getMvpView().showError();
                }

                @Override
                public void onNext(Session session) {
                    handleSession(session);
                    notificationServiceHelper.start();
                    onStateChanged(false);
                }
            });
    }

    @Override
    public void onStateChanged(boolean playWhenReady) {
        if(getMvpView() != null) {
            if (player.getPlayWhenReady()) {
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
        if(getMvpView() != null) {
            getMvpView().setHeadsetFooter();
        }
    }

    @Override
    public void onWiredHeadsetOn() {
        if(getMvpView() != null) {
            getMvpView().setNormalFooter();
        }
    }

    public void togglePlayer() {
        player.setPlayWhenReady(!player.getPlayWhenReady());
    }

    public boolean isFilmTime() {
        return dataManager.isFilmTime();
    }

    private void setFilmTime(boolean filmTime) {
        dataManager.setFilmTime(filmTime);
    }

    private void handleSession(Session session) {
        getMvpView().setFilmData(session.getFilm());
        if(dataBaseHelper.getPreviousSoundLanguage() == null) {
            player.setFilmStartTime(session.getFilmStartTime());
            loadDefaultTrack(session);
        }
        else {
            loadAnotherDataIfNeed(session);
        }
        dataBaseHelper.savePreviousSoundLanguage(session.getFilm().getSelectedSoundLanguage());
        dataBaseHelper.savePreviousSubLanguage(session.getFilm().getSelectedSubtitleLanguage());
    }

    private void loadAnotherDataIfNeed(Session session) {
        if(session.getFilm().getSelectedSoundLanguage() != dataBaseHelper.getPreviousSoundLanguage()) {
            player.setAudioUrl(serverHelper.getDownloadUrl(session.getFilm().getSelectedSoundLanguage().getId(),session.getToken()));
            Timber.i("Changing audio url");
        }
        if(session.getFilm().getSelectedSubtitleLanguage() != dataBaseHelper.getPreviousSubtitleLanguage()) {
            player.setSubtitleUrl(serverHelper.getDownloadUrl(session.getFilm().getSelectedSubtitleLanguage().getId(),session.getToken()));
            Timber.i("Changing sub url");
        }
    }

    private void loadDefaultTrack(Session session) {
        Uri audioUrl = getDefaultAudioUrl(session);
        Uri subUrl = getDefaultSubUrl(session);
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
