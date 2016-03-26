package com.lod.movie_extended.ui.fragment.languages;

import com.lod.movie_extended.data.DataManager;
import com.lod.movie_extended.data.model.Film;
import com.lod.movie_extended.data.model.Language;
import com.lod.movie_extended.ui.base.BasePresenter;
import com.lod.movie_extended.ui.fragment.languages.LanguagesMvpView;
import com.squareup.otto.Bus;

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
