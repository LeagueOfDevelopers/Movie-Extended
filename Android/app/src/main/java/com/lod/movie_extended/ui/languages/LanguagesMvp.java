package com.lod.movie_extended.ui.languages;

import com.lod.movie_extended.data.model.Language;
import com.lod.movie_extended.ui.base.MvpView;

import java.util.ArrayList;

/**
 * Created by Жамбыл on 09.01.2016.
 */
public interface LanguagesMvp extends MvpView {

    void setLanguages(ArrayList<Language> languages);
}
