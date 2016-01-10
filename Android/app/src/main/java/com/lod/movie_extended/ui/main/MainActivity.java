package com.lod.movie_extended.ui.main;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.lod.movie_extended.App;
import com.lod.movie_extended.R;
import com.lod.movie_extended.injection.component.activity.DaggerMainActivityComponent;
import com.lod.movie_extended.injection.component.activity.MainActivityComponent;
import com.lod.movie_extended.injection.module.activity.MainActivityModule;
import com.lod.movie_extended.ui.base.ComponentCreator;
import com.lod.movie_extended.ui.qrCodeReader.QrCodeReaderActivity;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements MainMvpView, ComponentCreator<MainActivityComponent> {

    private final static int LAYOUT = R.layout.activity_main;

    @Inject
    MainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(LAYOUT);
        ButterKnife.bind(this);
        createComponent().inject(this);
        presenter.attachView(this);
    }

    @OnClick(R.id.qr_code_read_button)
    public void onQrCodeReadButtonClick() {
        startActivity(new Intent(this, QrCodeReaderActivity.class));
    }

    @Override
    public MainActivityComponent createComponent() {
        return DaggerMainActivityComponent.builder()
                .applicationComponent(App.get(this).getComponent())
                .mainActivityModule(new MainActivityModule(this))
                .build();
    }

    @Override
    protected void onDestroy() {
        presenter.detachView();
        super.onDestroy();
    }
}
