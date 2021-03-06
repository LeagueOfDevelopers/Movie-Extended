package com.lod.movie_extended.ui.qrCodeReader;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.lod.movie_extended.App;
import com.lod.movie_extended.injection.component.activity.DaggerQrCodeReaderComponent;
import com.lod.movie_extended.injection.component.activity.QrCodeReaderComponent;
import com.lod.movie_extended.injection.module.activity.QrCodeReaderModule;
import com.lod.movie_extended.ui.base.ComponentCreator;
import com.lod.movie_extended.ui.filmPreparation.FilmPreparationActivity;

import javax.inject.Inject;

/**
 * Created by Жамбыл on 09.01.2016.
 */
public class QrCodeReaderActivity extends AppCompatActivity implements QrCodeReaderMvp, ComponentCreator<QrCodeReaderComponent> {

    @Inject
    QrCodeReaderPresenter presenter;

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        createComponent().inject(this);
        setContentView(presenter.getZXingScannerView());
        presenter.attachView(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.startCamera();
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.stopCamera();
    }

    @Override
    public QrCodeReaderComponent createComponent() {
        return DaggerQrCodeReaderComponent.builder()
                .applicationComponent(App.get(this).getComponent())
                .qrCodeReaderModule(new QrCodeReaderModule(this))
                .build();
    }

    @Override
    public void startFilmPreparationActivity() {
        startActivity(new Intent(this,FilmPreparationActivity.class));
        finish();
    }

    @Override
    protected void onDestroy() {
        presenter.detachView();
        super.onDestroy();
    }
}
