package com.lod.movie_extended.ui.fragment.poster;

import com.lod.movie_extended.data.DataManager;
import com.lod.movie_extended.ui.base.BasePresenter;
import com.squareup.otto.Bus;

/**
 * Created by Жамбыл on 2/18/2016.
 */
public class PosterPresenter extends BasePresenter<PosterMvpView> {

    private DataManager dataManager;
    private Bus events;

    public PosterPresenter(DataManager dataManager, Bus bus) {
        this.dataManager = dataManager;
        this.events = bus;
    }
}
