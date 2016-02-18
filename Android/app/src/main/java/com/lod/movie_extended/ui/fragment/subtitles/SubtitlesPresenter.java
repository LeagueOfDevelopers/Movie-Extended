package com.lod.movie_extended.ui.fragment.subtitles;

import com.lod.movie_extended.data.DataManager;
import com.lod.movie_extended.ui.base.BasePresenter;
import com.squareup.otto.Bus;

/**
 * Created by Жамбыл on 2/18/2016.
 */
public class SubtitlesPresenter extends BasePresenter<SubtitlesMvpView> {

    private DataManager dataManager;
    private Bus events;

    public SubtitlesPresenter(DataManager dataManager, Bus bus) {
        this.dataManager = dataManager;
        this.events = bus;
    }
}
