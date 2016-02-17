package com.lod.movie_extended.ui.main;

import android.content.Intent;
import android.os.Bundle;

import com.lod.movie_extended.App;
import com.lod.movie_extended.R;
import com.lod.movie_extended.injection.component.activity.DaggerMainActivityComponent;
import com.lod.movie_extended.injection.component.activity.MainActivityComponent;
import com.lod.movie_extended.injection.module.activity.MainActivityModule;
import com.lod.movie_extended.ui.base.InjectActivityBase;
import com.lod.movie_extended.ui.base.ComponentCreator;
import com.lod.movie_extended.ui.filmPreparation.FilmPreparationActivity;
import com.lod.movie_extended.ui.filmPreparation.FilmPreparationPresenter;
import com.lod.movie_extended.ui.qrCodeReader.QrCodeReaderActivity;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

public class MainActivity extends InjectActivityBase implements MainMvpView, ComponentCreator<MainActivityComponent> {

    private final static int LAYOUT = R.layout.activity_main;

    @Inject
    MainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Timber.v("onCreate");

        if(presenter.hasQrCodeBeenProcessed()) {
            startActivity(new Intent(MainActivity.this, FilmPreparationActivity.class));
            finish();
        }
    }

    @Override
    protected int getContentView() {
        return LAYOUT;
    }

    @Override
    public void inject() {
        ButterKnife.bind(this);
        createComponent().inject(this);
    }

    @Override
    protected MainPresenter getPresenter() {
        return presenter;
    }

    @OnClick(R.id.qr_code_read_button)
    public void onQrCodeReadButtonClick() {
        Timber.v("starting QrCodeActivity");
        startActivity(new Intent(this, QrCodeReaderActivity.class));
        finish();
        presenter.setQrCodeProcessed(true);
    }

    @Override
    public MainActivityComponent createComponent() {
        return DaggerMainActivityComponent.builder()
                .applicationComponent(App.get(this).getComponent())
                .mainActivityModule(new MainActivityModule(this))
                .build();
    }
}
