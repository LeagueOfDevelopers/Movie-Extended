package com.lod.movie_extended.injection.module.activity;

import android.app.Activity;

import com.lod.movie_extended.data.DataManager;
import com.lod.movie_extended.injection.scope.PerActivity;
import com.lod.movie_extended.ui.activity.qrCodeReader.QrCodeReaderPresenter;

import dagger.Module;
import dagger.Provides;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

/**
 * Created by Жамбыл on 3/3/2016.
 */
@Module
public class QrCodeReaderModuleTest {

    Activity activity;

    public QrCodeReaderModuleTest(Activity activity) {
        this.activity = activity;
    }


    @Provides
    ZXingScannerView provideZXingScannerView() {
        return new ZXingScannerView(activity);
    }
}
