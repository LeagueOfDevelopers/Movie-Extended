package com.lod.movie_extended.ui.fragment.languages;

import com.lod.movie_extended.data.DataManager;
import com.lod.movie_extended.data.model.Language;
import com.lod.movie_extended.events.LanguageSelected;
import com.lod.movie_extended.ui.base.BasePresenter;
import com.lod.movie_extended.ui.fragment.languages.LanguagesMvpView;
import com.squareup.otto.Bus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Жамбыл on 09.01.2016.
 */
public class LanguagesPresenter extends BasePresenter<LanguagesMvpView> {

    private DataManager dataManager;
    private Bus events;
    private List<Language> languages;
    private Language selectedLanguage;

    public LanguagesPresenter(DataManager dataManager, Bus bus) {
        this.dataManager = dataManager;
        this.events = bus;

        languages = new ArrayList<>();

        Language english = new Language();
        english.setName("English");

        Language russian = new Language();
        russian.setName("Русский");

        Language ukraine = new Language();
        ukraine.setName("Украинский");

        languages.add(english);
        languages.add(russian);
        languages.add(ukraine);
    }

    public List<Language> getLanguages() {
        return languages;
//        return dataManager.getSession().toBlocking().first().getFilm().getLanguages();
    }

    public void onLanguageSelected() {
        events.post(new LanguageSelected());
    }

    public Language getSelectedLanguage() {
        return selectedLanguage;
    }

    public void setSelectedLanguage(Language selectedLanguage) {
        this.selectedLanguage = selectedLanguage;
        getMvpView().allowNext();
    }
}
