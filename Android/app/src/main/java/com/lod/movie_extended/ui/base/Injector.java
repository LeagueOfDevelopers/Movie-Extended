package com.lod.movie_extended.ui.base;

import com.lod.movie_extended.injection.component.activity.BaseComponent;
import com.squareup.otto.Bus;

/**
 * Created by Жамбыл on 2/18/2016.
 */
public interface Injector<T extends BaseComponent> {

    T getComponent();

    void setComponent(T component);

    int getContentView();

    void inject();

    Presenter getPresenter();

    Bus getBus();
}
