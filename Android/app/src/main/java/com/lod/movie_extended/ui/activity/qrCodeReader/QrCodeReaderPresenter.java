package com.lod.movie_extended.ui.activity.qrCodeReader;

import android.view.View;

import com.google.zxing.Result;
import com.lod.movie_extended.data.DataManager;
import com.lod.movie_extended.ui.base.BasePresenter;

import javax.inject.Inject;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

/**
 * Created by Жамбыл on 09.01.2016.
 */
public class QrCodeReaderPresenter extends BasePresenter<QrCodeReaderMvp> implements ZXingScannerView.ResultHandler{

    DataManager dataManager;

    public ZXingScannerView scannerView;

    public QrCodeReaderPresenter(DataManager dataManager, ZXingScannerView scannerView) {
        this.dataManager = dataManager;
        this.scannerView = scannerView;
    }

    public View getZXingScannerView() {
        return scannerView;
    }

    @Override
    public void handleResult(Result result) {
        dataManager.setQrCode("00000000-0000-0000-0000-000000000000");
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
