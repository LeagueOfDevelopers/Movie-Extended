package com.lod.movie_extended.ui.activity.languagePicker;

import android.widget.ListView;

import com.lod.movie_extended.data.DataManager;
import com.lod.movie_extended.data.model.Language;
import com.lod.movie_extended.data.model.Session;
import com.lod.movie_extended.ui.base.BasePresenter;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by Жамбыл on 3/16/2016.
 */
public class LanguagePickerPresenter extends BasePresenter<LanguagePickerView> {

    private DataManager dataManager;

    public LanguagePickerPresenter(DataManager dataManager) {
        this.dataManager = dataManager;
    }
}
