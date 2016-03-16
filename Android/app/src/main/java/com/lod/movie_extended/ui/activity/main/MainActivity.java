package com.lod.movie_extended.ui.activity.main;

import android.content.Intent;
import android.os.Bundle;

import com.lod.movie_extended.injection.App;
import com.lod.movie_extended.R;
import com.lod.movie_extended.injection.component.activity.DaggerMainComponent;
import com.lod.movie_extended.injection.component.activity.MainComponent;
import com.lod.movie_extended.injection.module.activity.MainModule;
import com.lod.movie_extended.ui.base.ComponentGetter;
import com.lod.movie_extended.ui.base.InjectActivityBase;
import com.lod.movie_extended.ui.activity.filmPreparation.FilmPreparationActivity;
import com.lod.movie_extended.ui.activity.qrCodeReader.QrCodeReaderActivity;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

public class MainActivity extends InjectActivityBase implements MainMvpView, ComponentGetter<MainComponent> {

    private final static int LAYOUT = R.layout.activity_main;

    @Inject
    MainPresenter presenter;

    private MainComponent component;

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
    public int getContentView() {
        return LAYOUT;
    }

    @Override
    public void inject() {
        ButterKnife.bind(this);
        getComponent().inject(this);
    }

    @Override
    public MainPresenter getPresenter() {
        return presenter;
    }

    @OnClick(R.id.qr_code_read_button)
    public void onQrCodeReadButtonClick() {
        Timber.v("starting QrCodeActivity");
        startActivityForResult(new Intent(this, QrCodeReaderActivity.class),1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == 1) {
            finish();
        }
    }

    @Override
    public MainComponent getComponent() {
        if(component == null) {
            component = DaggerMainComponent.builder()
                    .applicationComponent(App.instance().getComponent())
                    .mainModule(new MainModule(this))
                    .build();
        }
        return component;
    }

    @Override
    public void setComponent(MainComponent component) {
        this.component = component;
    }
}
