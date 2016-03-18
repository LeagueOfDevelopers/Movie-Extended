package com.lod.movie_extended.ui.activity.filmShow;

import com.lod.movie_extended.data.DataManager;
import com.lod.movie_extended.data.model.Film;
import com.lod.movie_extended.data.model.Language;
import com.lod.movie_extended.data.model.Session;
import com.lod.movie_extended.ui.base.BasePresenter;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by Жамбыл on 3/17/2016.
 */
public class FilmShowPresenter extends BasePresenter<FilmShowView> {

    private DataManager dataManager;

    public FilmShowPresenter(DataManager dataManager) {
        this.dataManager = dataManager;
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

                }

                @Override
                public void onNext(Session session) {
                    getMvpView().setFilm(session.getFilm());
                }
            });
    }
}
