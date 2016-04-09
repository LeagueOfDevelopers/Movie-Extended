package com.lod.movie_extended.ui.activity.main;

import android.content.Intent;
import android.os.Bundle;

import com.lod.movie_extended.injection.App;
import com.lod.movie_extended.R;
import com.lod.movie_extended.injection.component.activity.DaggerMainComponent;
import com.lod.movie_extended.injection.component.activity.MainComponent;
import com.lod.movie_extended.injection.module.activity.MainModule;
import com.lod.movie_extended.ui.activity.filmShow.FilmShowActivity;
import com.lod.movie_extended.ui.base.BaseActivity;
import com.lod.movie_extended.ui.activity.qrCodeReader.QrCodeReaderActivity;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

public class MainActivity extends BaseActivity<MainComponent> implements MainMvpView {

    private final static int LAYOUT = R.layout.activity_main;
    public static final int FINISH = 1;

    @Inject
    MainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(presenter.hasQrCodeBeenProcessed()) {
            startActivity(new Intent(MainActivity.this, FilmShowActivity.class));
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
        startActivityForResult(new Intent(this, QrCodeReaderActivity.class), FINISH);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == FINISH) {
            finish();
        }
    }

    @Override
    protected MainComponent createComponent() {
        return DaggerMainComponent.builder()
                .applicationComponent(App.getInstance().getComponent())
                .mainModule(new MainModule(this))
                .build();
    }
}
