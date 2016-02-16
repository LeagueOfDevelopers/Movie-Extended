package com.lod.movie_extended.ui.film;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.transition.Visibility;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.accessibility.CaptioningManager;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.PopupMenu;

import com.google.android.exoplayer.text.CaptionStyleCompat;
import com.google.android.exoplayer.text.SubtitleLayout;
import com.google.android.exoplayer.util.Util;
import com.lod.movie_extended.App;
import com.lod.movie_extended.R;
import com.lod.movie_extended.data.model.Player;
import com.lod.movie_extended.injection.component.activity.DaggerFilmComponent;
import com.lod.movie_extended.injection.component.activity.FilmComponent;
import com.lod.movie_extended.injection.module.activity.FilmModule;
import com.lod.movie_extended.ui.base.ComponentCreator;
import com.lod.movie_extended.ui.base.InjectActivityBase;
import com.lod.movie_extended.ui.base.Presenter;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import timber.log.Timber;

/**
 * Created by Жамбыл on 29.12.2015.
 */

public class FilmActivity extends InjectActivityBase implements FilmMvpView, ComponentCreator<FilmComponent>{

    private static final int LAYOUT = R.layout.player;

    @Inject
    FilmPresenter presenter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        App.get(this).setAudioUrl("http://movieextended1.azurewebsites.net/api/file/get/43");
        super.onCreate(savedInstanceState);
        setupWindowAnimations();
        Timber.v("onCreate" + presenter.hashCode());
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setupWindowAnimations() {
        Transition transition = buildEnterTransition();

        getWindow().setEnterTransition(transition);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private Visibility buildEnterTransition() {
        Slide enterTransition = new Slide();
        enterTransition.setDuration(300);
        enterTransition.setSlideEdge(Gravity.BOTTOM);
        return enterTransition;
    }

    @Override
    protected int getContentView() {
        return LAYOUT;
    }

    @Override
    public void inject() {
        ButterKnife.bind(this);
        createComponent().inject(this);
    }

    @Override
    protected Presenter getPresenter() {
        return presenter;
    }

    @Override
    public FilmComponent createComponent() {
        return DaggerFilmComponent.builder()
                .filmModule(new FilmModule(this))
                .applicationComponent(App.get(this).getComponent())
                .build();
    }

    @Override
    public void onResume() {
        super.onResume();

        presenter.preparePlayer(true);
        presenter.startPlayerNotificationService();
    }

    @Override
    public SubtitleLayout getSubtitleLayout() {
        return null;
    }

    @Override
    protected void onDestroy() {
        Timber.v("onDestroy");
        super.onDestroy();
        presenter.removeListener();
    }
}

