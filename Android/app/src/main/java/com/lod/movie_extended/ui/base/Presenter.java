package com.lod.movie_extended.ui.base;

/**
 * Created by Жамбыл on 09.01.2016.
 */
public interface Presenter<V extends MvpView> {

    void attachView(V mvpView);

    void detachView();
}
