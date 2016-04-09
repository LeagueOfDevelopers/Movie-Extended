package com.lod.movie_extended.ui.activity.qrCodeReader;

import com.lod.movie_extended.ui.base.MvpView;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

/**
 * Created by Жамбыл on 09.01.2016.
 */
public interface QrCodeReaderMvp extends MvpView {

    void startFilmPreparationActivity();
    ZXingScannerView getScannerView();
}
