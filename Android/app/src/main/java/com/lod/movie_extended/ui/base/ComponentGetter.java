package com.lod.movie_extended.ui.base;

import dagger.Component;

/**
 * Created by Жамбыл on 09.01.2016.
 */
public interface ComponentGetter<T> {
    T getComponent();

    void setComponent(T component);
}
