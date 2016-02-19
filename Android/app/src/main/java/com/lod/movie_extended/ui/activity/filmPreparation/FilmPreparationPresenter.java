package com.lod.movie_extended.ui.activity.filmPreparation;

import android.widget.Toast;

import com.lod.movie_extended.data.DataManager;
import com.lod.movie_extended.data.model.Player;
import com.lod.movie_extended.data.model.Session;
import com.lod.movie_extended.data.model.Token;
import com.lod.movie_extended.ui.base.BasePresenter;

import rx.Subscriber;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by Жамбыл on 09.01.2016.
 */
public class FilmPreparationPresenter extends BasePresenter<FilmPreparationMvpView> implements Player.Listener {

    DataManager dataManager;
    Session currentSession;
    Player player;

    public FilmPreparationPresenter(DataManager dataManager, Player player) {
        this.dataManager = dataManager;
        this.player = player;
    }

    public void onCreate() {
        player.addListener(this);
    }

    public void onDestroy() {
        player.removeListener(this);
    }

    public void loadSession() {
        dataManager.loadSession("123").subscribe(new Subscriber<Session>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Session session) {
                currentSession = session;
                getMvpView().setLanguagesToRecyclerView();
            }
        });
    }

    public void getToken(String qrCode) {
         dataManager.getToken(qrCode)
                 .subscribeOn(Schedulers.io())
                 .subscribe(new Subscriber<Token>() {
                     @Override
                     public void onCompleted() {

                     }

                     @Override
                     public void onError(Throwable e) {

                     }

                     @Override
                     public void onNext(Token token) {
                         Timber.v(token.getValue());
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

    @Override
    public void onStateChanged(boolean playWhenReady, int playbackState) {
        Timber.v("onStateChanged " + playWhenReady);
    }

    @Override
    public void onError(Exception e) {
        Timber.e("error");
    }

    @Override
    public void onWiredHeadsetNotOn() {
        getMvpView().onShowHeadsetError();
    }
}
