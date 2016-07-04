package com.lod.movie_extended.ui.activity.filmShow;

import com.lod.movie_extended.data.model.Film;
import com.lod.movie_extended.ui.base.MvpView;

/**
 * Created by Жамбыл on 3/17/2016.
 */
public interface FilmShowView extends MvpView {

    void setFilmData(Film film);

    void ServerError();

    void setHeadsetFooter();

    void setNormalFooter();

    void setPlayView();

    void setPauseView();

    void PlayerError(Exception e);
}