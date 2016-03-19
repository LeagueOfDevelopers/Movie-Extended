package com.lod.movie_extended.ui.fragment.languages;

import com.lod.movie_extended.data.DataManager;
import com.lod.movie_extended.data.model.Film;
import com.lod.movie_extended.data.model.Language;
import com.lod.movie_extended.data.model.Session;
import com.lod.movie_extended.events.LanguageSelected;
import com.lod.movie_extended.ui.base.BasePresenter;
import com.lod.movie_extended.ui.fragment.languages.LanguagesMvpView;
import com.squareup.otto.Bus;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Жамбыл on 09.01.2016.
 */
public class LanguagesPresenter extends BasePresenter<LanguagesMvpView> {

    private DataManager dataManager;
    private Bus events;
    private Language selectedLanguage;
    private Film film;
    public LanguagesPresenter(DataManager dataManager, Bus bus) {
        this.dataManager = dataManager;
        this.events = bus;
    }
    public Film getFilm() {
        if(film == null) {
            film = dataManager.getSession().toBlocking().first().getFilm();
        }
        return film;
    }

    public void allowNext() {
        getMvpView().allowNext();
    }
}
