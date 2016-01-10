package com.lod.movie_extended.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import com.lod.movie_extended.injection.component.activity.FilmComponent;
import com.lod.movie_extended.ui.main.MainPresenter;
import com.squareup.otto.Bus;

import dagger.Component;


/**
 * Created by Жамбыл on 10.01.2016.
 */
public abstract class InjectActivityBase extends AppCompatActivity implements MvpView{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView());
        inject();
        if(getBus() != null) {
            getBus().register(this);
        }
        getPresenter().attachView(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(getBus() != null) {
            getBus().unregister(this);
        }
        getPresenter().detachView();
    }

    protected abstract int getContentView();

    public abstract void inject();

    protected abstract Presenter getPresenter();

    protected Bus getBus() {
        return null;
    }
}
