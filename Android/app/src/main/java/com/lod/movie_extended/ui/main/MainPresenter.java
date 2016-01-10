package com.lod.movie_extended.ui.main;

import com.lod.movie_extended.data.DataManager;
import com.lod.movie_extended.ui.base.BasePresenter;

import javax.inject.Inject;

/**
 * Created by Жамбыл on 09.01.2016.
 */
public class MainPresenter extends BasePresenter<MainMvpView> {

    @Inject
    DataManager dataManager;

    @Override
    public void attachView(MainMvpView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
    }
}
