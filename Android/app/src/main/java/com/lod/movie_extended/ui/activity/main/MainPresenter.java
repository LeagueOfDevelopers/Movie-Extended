package com.lod.movie_extended.ui.activity.main;

import com.lod.movie_extended.data.DataManager;
import com.lod.movie_extended.ui.base.BasePresenter;

/**
 * Created by Жамбыл on 09.01.2016.
 */
public class MainPresenter extends BasePresenter<MainMvpView> {

    DataManager dataManager;

    public MainPresenter(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    @Override
    public void attachView(MainMvpView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
    }

    public boolean hasQrCodeBeenProcessed() {
        return dataManager.hasQrCodeBeenProcessed();
    }

    public void setQrCodeProcessed(boolean qrCodePrecessed) {
        dataManager.setQrCodeProcessed(qrCodePrecessed);
    }
}
