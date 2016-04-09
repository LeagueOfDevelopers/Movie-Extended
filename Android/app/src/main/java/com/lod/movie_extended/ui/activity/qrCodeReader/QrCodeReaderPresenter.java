package com.lod.movie_extended.ui.activity.qrCodeReader;

import com.google.zxing.Result;
import com.lod.movie_extended.data.DataManager;
import com.lod.movie_extended.ui.base.BasePresenter;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

/**
 * Created by Жамбыл on 09.01.2016.
 */
public class QrCodeReaderPresenter extends BasePresenter<QrCodeReaderMvp> implements ZXingScannerView.ResultHandler{

    private final DataManager dataManager;

    public QrCodeReaderPresenter(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    @Override
    public void handleResult(Result result) {
        dataManager.setQrCode(result.getText());
        getMvpView().startFilmPreparationActivity();
    }

    public void startCamera() {
        getMvpView().getScannerView().setResultHandler(this);
        getMvpView().getScannerView().startCamera();
    }

    public void stopCamera() {
        getMvpView().getScannerView().stopCamera();
    }
}
