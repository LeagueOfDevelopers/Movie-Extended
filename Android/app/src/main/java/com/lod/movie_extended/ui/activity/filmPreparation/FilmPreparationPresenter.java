package com.lod.movie_extended.ui.activity.filmPreparation;

import com.lod.movie_extended.data.DataManager;
import com.lod.movie_extended.data.model.NotificationServiceHelper;
import com.lod.movie_extended.data.model.player.Player;
import com.lod.movie_extended.data.model.player.PlayerListener;
import com.lod.movie_extended.data.model.Session;
import com.lod.movie_extended.ui.base.BasePresenter;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by Жамбыл on 09.01.2016.
 */
public class FilmPreparationPresenter extends BasePresenter<FilmPreparationMvpView> implements PlayerListener {

    private DataManager dataManager;
    private Session currentSession;
    private Player player;
    private NotificationServiceHelper notificationServiceHelper;

    public FilmPreparationPresenter(DataManager dataManager, Player player, NotificationServiceHelper notificationServiceHelper) {
        this.dataManager = dataManager;
        this.player = player;
        this.notificationServiceHelper = notificationServiceHelper;
    }

    public void onCreate() {
        player.addListener(this);
        loadSession();
    }

    public void onDestroy() {
        player.removeListener(this);
    }

    public void loadSession() {
        Timber.v("loading session");
        dataManager.getSession()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Subscriber<Session>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {

                }

                @Override
                public void onNext(Session session) {
                    Timber.v(session.getFilm().getName());
                    currentSession = session;
                    getMvpView().setLanguagesToRecyclerView();
                }
            });
    }

    public String getFilmName() {
        return currentSession.getFilm().getName();
    }

    public void setFilmTime(boolean filmTime) {
        dataManager.setFilmTime(filmTime);
    }
    public boolean isFilmTime() {
        return dataManager.isFilmTime();
    }
    public boolean isPlaying() {
        return player.getPlayWhenReady();
    }
    @Override
    public void onStateChanged(boolean playWhenReady) {
        getMvpView().togglePlayerView(playWhenReady);
    }

    @Override
    public void onError(Exception e) {
        Timber.e("error");
    }

    @Override
    public void onWiredHeadsetNotOn() {
        getMvpView().onShowHeadsetError();
    }

    @Override
    public void onWiredHeadsetOn() {

    }

    public void togglePlayer(boolean isPlaying) {
//        player.setPlayWhenReady(!isPlaying);
    }
}
