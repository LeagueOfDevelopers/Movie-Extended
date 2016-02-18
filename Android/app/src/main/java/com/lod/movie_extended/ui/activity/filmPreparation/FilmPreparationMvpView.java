package com.lod.movie_extended.ui.activity.filmPreparation;

import com.lod.movie_extended.ui.base.MvpView;

/**
 * Created by Жамбыл on 09.01.2016.
 */
public interface FilmPreparationMvpView extends MvpView {

    void updateFooterVisibility();

    void setLanguagesToRecyclerView();
}
