package com.lod.movie_extended.ui.base;

import com.squareup.otto.Bus;

/**
 * Created by Жамбыл on 2/18/2016.
 */
public interface Injector {

    int getContentView();

    void inject();

    Presenter getPresenter();

    Bus getBus();
}
