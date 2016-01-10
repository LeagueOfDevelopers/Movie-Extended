package com.lod.movie_extended.ui.qrCodeReader;

import android.content.Intent;
import android.view.View;

import com.google.zxing.Result;
import com.lod.movie_extended.data.DataManager;
import com.lod.movie_extended.ui.base.BasePresenter;
import com.lod.movie_extended.ui.filmPreparation.FilmPreparationActivity;

import javax.inject.Inject;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

/**
 * Created by Жамбыл on 09.01.2016.
 */
public class QrCodeReaderPresenter extends BasePresenter<QrCodeReaderMvp> implements ZXingScannerView.ResultHandler{

    @Inject
    DataManager dataManager;

    public ZXingScannerView scannerView;

    public QrCodeReaderPresenter(ZXingScannerView scannerView) {
        this.scannerView = scannerView;
    }

    public View getZXingScannerView() {
        return scannerView;
    }

    @Override
    public void handleResult(Result result) {
        getMvpView().startFilmPreparationActivity();
    }

    public void startCamera() {
        scannerView.setResultHandler(this);
        scannerView.startCamera();
    }

    public void stopCamera() {
        scannerView.stopCamera();
    }
}
