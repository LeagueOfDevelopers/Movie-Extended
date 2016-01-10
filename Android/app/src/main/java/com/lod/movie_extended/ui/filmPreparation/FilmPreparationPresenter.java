package com.lod.movie_extended.ui.filmPreparation;

import android.support.annotation.NonNull;

import com.lod.movie_extended.data.DataManager;
import com.lod.movie_extended.data.model.Language;
import com.lod.movie_extended.data.model.Session;
import com.lod.movie_extended.ui.base.BasePresenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;
import rx.functions.Func1;

/**
 * Created by Жамбыл on 09.01.2016.
 */
public class FilmPreparationPresenter extends BasePresenter<FilmPreparationMvp> {

    DataManager dataManager;
    Session currentSession;

    public FilmPreparationPresenter(DataManager dataManager) {
        this.dataManager = dataManager;
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
    public String getFilmName() {
        return currentSession.getFilm().getName();
    }
}
