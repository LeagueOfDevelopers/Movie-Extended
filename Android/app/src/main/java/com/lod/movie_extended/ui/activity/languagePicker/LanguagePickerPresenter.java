package com.lod.movie_extended.ui.activity.languagePicker;

import android.widget.ListView;

import com.lod.movie_extended.data.DataManager;
import com.lod.movie_extended.data.model.Language;
import com.lod.movie_extended.ui.base.BasePresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Жамбыл on 3/16/2016.
 */
public class LanguagePickerPresenter extends BasePresenter<LanguagePickerView> {

    private DataManager dataManager;
    private List<Language> languages;

    public LanguagePickerPresenter(DataManager dataManager) {
        this.dataManager = dataManager;
        languages = new ArrayList<>();

        Language english = new Language();
        english.setName("English");

        Language russian = new Language();
        english.setName("Русский");

        Language ukraine = new Language();
        english.setName("Украинский");

        languages.add(english);
        languages.add(russian);
        languages.add(ukraine);
    }

    public List<Language> getLanguages() {
        return languages;
    }
}
