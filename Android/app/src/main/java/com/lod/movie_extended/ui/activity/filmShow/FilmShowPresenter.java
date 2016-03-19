package com.lod.movie_extended.ui.activity.filmShow;

import android.content.Context;

import com.lod.movie_extended.data.DataManager;
import com.lod.movie_extended.data.model.Language;
import com.lod.movie_extended.data.model.NotificationServiceHelper;
import com.lod.movie_extended.data.model.Session;
import com.lod.movie_extended.data.model.Token;
import com.lod.movie_extended.data.model.player.Player;
import com.lod.movie_extended.data.model.player.PlayerListener;
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
    private Context context;

    public FilmShowPresenter(DataManager dataManager, Player player, NotificationServiceHelper notificationServiceHelper, Context context) {
        this.dataManager = dataManager;
        this.player = player;
        this.notificationServiceHelper = notificationServiceHelper;
        this.context = context;
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
                    loadDefaultTrack(session);
                    notificationServiceHelper.start(context);
                }
        });
    }

    private void loadDefaultTrack(Session session) {
        if(!player.hasBeenStarted()) {
            loadAudio(session);
            loadSubtitle(session);
        }
    }

    private void loadAudio(Session session) {
        Language defaultLanguage = session.getFilm().getSelectedSoundLanguage();
        int languageDownloadId = defaultLanguage.getTrackFile().getId();
        String audioUrl = getUrlWithToken(languageDownloadId,session.getToken());
        player.startAudio(audioUrl);
    }

    private void loadSubtitle(Session session) {
        Language defaultLanguage = session.getFilm().getSelectedSoundLanguage();
        int subtitleDownloadId = defaultLanguage.getSubtitle().getId();
        String subtitleUrl = getUrlWithToken(subtitleDownloadId,session.getToken());
        player.setSubtitleUrl(subtitleUrl);
    }

    public boolean isFilmTime() {
        return dataManager.isFilmTime();
    }

    public void setFilmTime(boolean filmTime) {
        dataManager.setFilmTime(filmTime);
    }

    @Override
    public void onStateChanged(boolean playWhenReady) {

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


    private String getUrlWithToken(int id, Token token) {
        return String.format("http://movieextended1.azurewebsites.net/file/get/%d/token/%s",
                id, token.getValue());
    }
}
