package com.lod.movie_extended.ui.film;

import android.widget.MediaController;

import com.google.android.exoplayer.text.SubtitleLayout;
import com.lod.movie_extended.ui.base.MvpView;

/**
 * Created by Жамбыл on 09.01.2016.
 */
public interface FilmMvpView extends MvpView{

    MediaController getMyMediaController();

    void updateButtonVisibilities();

    void showControls();

    SubtitleLayout getSubtitleLayout();
}
