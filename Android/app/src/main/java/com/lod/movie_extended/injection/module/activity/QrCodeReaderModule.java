package com.lod.movie_extended.injection.module.activity;

import android.app.Activity;
import android.content.Context;

import com.lod.movie_extended.injection.scope.PerActivity;
import com.lod.movie_extended.ui.qrCodeReader.QrCodeReaderPresenter;

import dagger.Module;
import dagger.Provides;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

/**
 * Created by Жамбыл on 09.01.2016.
 */

@Module
public class QrCodeReaderModule {

    Activity activity;

    public QrCodeReaderModule(Activity activity) {
        this.activity = activity;
    }

    @Provides
    @PerActivity
    QrCodeReaderPresenter provideQrCodeReaderPresenter(ZXingScannerView zXingScannerView) {
        return new QrCodeReaderPresenter(zXingScannerView);
    }

    @Provides
    ZXingScannerView provideZXingScannerView() {
        return new ZXingScannerView(activity);
    }
}
