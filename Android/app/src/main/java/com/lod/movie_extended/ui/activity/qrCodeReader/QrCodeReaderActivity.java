package com.lod.movie_extended.ui.activity.qrCodeReader;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.lod.movie_extended.injection.App;
import com.lod.movie_extended.injection.component.activity.DaggerQrCodeReaderComponent;
import com.lod.movie_extended.injection.component.activity.QrCodeReaderComponent;
import com.lod.movie_extended.injection.module.activity.QrCodeReaderModule;
import com.lod.movie_extended.ui.activity.filmShow.FilmShowActivity;
import com.lod.movie_extended.ui.activity.main.MainActivity;

import javax.inject.Inject;

import me.dm7.barcodescanner.zxing.ZXingScannerView;
import timber.log.Timber;

/**
 * Created by Жамбыл on 09.01.2016.
 */
public class QrCodeReaderActivity extends AppCompatActivity
        implements QrCodeReaderMvp{

    @Inject QrCodeReaderPresenter presenter;
    @Inject ZXingScannerView scannerView;

    private QrCodeReaderComponent component;

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        Timber.v("onCreate");
        getComponent().inject(this);
        setContentView(scannerView);
        presenter.attachView(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        Timber.v("onResume, starting camera");
        presenter.startCamera();
    }

    @Override
    public void onPause() {
        super.onPause();
        Timber.v("onPause, stopping camera");
        presenter.stopCamera();
    }

    @Override
    public void startFilmPreparationActivity() {
        Timber.v("starting FilmPreparationActivity");
        startActivity(new Intent(this,FilmShowActivity.class));
        setResult(MainActivity.FINISH);
        finish();
    }

    @Override
    public ZXingScannerView getScannerView() {
        return scannerView;
    }

    @Override
    protected void onDestroy() {
        presenter.detachView();
        super.onDestroy();
    }

    public QrCodeReaderComponent getComponent() {
        if(component == null) {
            component = DaggerQrCodeReaderComponent.builder()
                .applicationComponent(App.getInstance().getComponent())
                .qrCodeReaderModule(new QrCodeReaderModule(this))
                .build();
        }
        return component;
    }

    public void setComponent(QrCodeReaderComponent component) {
        this.component = component;
    }
}