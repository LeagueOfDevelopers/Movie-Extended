package com.lod.movie_extended.ui.activity.filmPreparation;

import com.lod.movie_extended.data.DataManager;
import com.lod.movie_extended.data.model.player.Player;
import com.lod.movie_extended.data.model.player.PlayerListener;
import com.lod.movie_extended.data.model.Session;
import com.lod.movie_extended.data.model.Token;
import com.lod.movie_extended.ui.base.BasePresenter;

import rx.Subscriber;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by Жамбыл on 09.01.2016.
 */
public class FilmPreparationPresenter extends BasePresenter<FilmPreparationMvpView> implements PlayerListener {

    private DataManager dataManager;
    private Session currentSession;
    private Player player;

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
                         Timber.v("token completed");
                     }

                     @Override
                     public void onError(Throwable e) {
                        Timber.e("server request error " + e.getMessage());
                     }

                     @Override
                     public void onNext(Token token) {
                         Timber.v("token " + token.getValue());
                         dataManager.getLanguages()
                                 .subscribeOn(Schedulers.io()).subscribe(new Subscriber<String>() {
                             @Override
                             public void onCompleted() {
                                 Timber.v("onLanguagesCompleted");
                             }

                             @Override
                             public void onError(Throwable e) {
                                 Timber.e("language get error");
                             }

                             @Override
                             public void onNext(String language) {
                                 Timber.v("Language " + language);
                             }
                         });
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

    public void togglePlayer(boolean isPlaying) {
        player.setPlayWhenReady(!isPlaying);
    }
}
