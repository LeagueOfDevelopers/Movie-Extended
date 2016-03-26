package com.lod.movie_extended.ui.activity.languagePicker;

import com.lod.movie_extended.data.DataManager;
import com.lod.movie_extended.ui.base.BasePresenter;

/**
 * Created by Жамбыл on 3/16/2016.
 */
public class LanguagePickerPresenter extends BasePresenter<LanguagePickerView> {

    private DataManager dataManager;

    public LanguagePickerPresenter(DataManager dataManager) {
        this.dataManager = dataManager;
    }
}
