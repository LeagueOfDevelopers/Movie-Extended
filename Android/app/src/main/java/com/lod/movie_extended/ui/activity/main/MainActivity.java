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

//        DataBaseHelper dataBaseHelper = new DataBaseHelper();
//        DataManager dataManager = new DataManager(null,dataBaseHelper,null,ServerAPI.Creator.newService());
//        dataManager.setQrCode("00000000-0000-0000-0000-000000000000");
//        dataManager.getSession()
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe(new Subscriber<Session>() {
//                @Override
//                public void onCompleted() {
//                    System.out.print("onCompeled");
//                }
//
//                @Override
//                public void onError(Throwable e) {
//                    System.out.print(e.getCause());
//                }
//
//                @Override
//                public void onNext(Session session) {
//                    Timber.v(session.getFilm().getLanguages().get(0).getName());
//                }
//            });
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
        startActivity(new Intent(this, QrCodeReaderActivity.class));
        finish();
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
