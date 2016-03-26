package com.lod.movie_extended.ui.base;

/**
 * Created by Жамбыл on 09.01.2016.
 */
public interface ComponentGetter<T> {
    T getComponent();

    void setComponent(T component);
}
