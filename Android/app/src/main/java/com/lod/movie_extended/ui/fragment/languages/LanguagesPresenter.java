package com.lod.movie_extended.ui.fragment.languages;

import com.lod.movie_extended.data.DataManager;
import com.lod.movie_extended.data.model.Language;
import com.lod.movie_extended.events.LanguageSelected;
import com.lod.movie_extended.ui.base.BasePresenter;
import com.lod.movie_extended.ui.fragment.languages.LanguagesMvpView;
import com.squareup.otto.Bus;

import java.util.ArrayList;

/**
 * Created by Жамбыл on 09.01.2016.
 */
public class LanguagesPresenter extends BasePresenter<LanguagesMvpView> {

    private DataManager dataManager;
    private Bus events;

    public LanguagesPresenter(DataManager dataManager, Bus bus) {
        this.dataManager = dataManager;
        this.events = bus;
    }

    public ArrayList<Language> getLanguages() {
        return dataManager.getSession().toBlocking().first().getFilm().getLanguages();
    }

    public void onLanguageSelected() {
        events.post(new LanguageSelected());
    }
}
