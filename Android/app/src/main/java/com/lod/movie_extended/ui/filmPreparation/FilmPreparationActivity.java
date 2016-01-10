package com.lod.movie_extended.ui.filmPreparation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.lod.movie_extended.App;
import com.lod.movie_extended.R;
import com.lod.movie_extended.data.model.Language;
import com.lod.movie_extended.events.FilmStarted;
import com.lod.movie_extended.events.LanguageSelected;
import com.lod.movie_extended.injection.component.activity.DaggerFilmPreparationComponent;
import com.lod.movie_extended.injection.component.activity.FilmPreparationComponent;
import com.lod.movie_extended.injection.module.activity.FilmPreparationModule;
import com.lod.movie_extended.ui.base.ComponentCreator;
import com.lod.movie_extended.ui.base.ComponentGetter;
import com.lod.movie_extended.ui.film.FilmActivity;
import com.lod.movie_extended.ui.languages.LanguagesFragment;
import com.lod.movie_extended.ui.remainigTime.RemainingTimeFragment;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Жамбыл on 09.01.2016.
 */
public class FilmPreparationActivity extends AppCompatActivity
        implements FilmPreparationMvp, ComponentCreator<FilmPreparationComponent>, ComponentGetter<FilmPreparationComponent> {
    private static final int LAYOUT = R.layout.activity_film_preparation;

    @Bind(R.id.progressBar)
    ProgressBar progressBar;

    @Inject
    FilmPreparationPresenter presenter;

    @Inject
    Bus events;
    @Inject
    FragmentManager fragmentManager;

    FilmPreparationComponent component;
    private ActionBar toolbar;
    private boolean isRunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(LAYOUT);
        ButterKnife.bind(this);
        createComponent().inject(this);
        events.register(this);
        initToolbar();
        isRunning = true;
        presenter.attachView(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        isRunning = true;
    }

    @Override
    protected void onPause() {
        isRunning = false;
        super.onPause();
    }

    @Override
    public FilmPreparationComponent createComponent() {
        component = DaggerFilmPreparationComponent.builder()
                .applicationComponent(App.get(this).getComponent())
                .filmPreparationModule(new FilmPreparationModule(this))
                .build();

        return component;
    }

    @Subscribe
    public void onLanguageSelected(LanguageSelected event) {
        setFilmFragment(RemainingTimeFragment.getNewInstance(),true,false);
    }

    @Subscribe
    public void onFilmStarted(FilmStarted event) {
        startActivity(new Intent(this, FilmActivity.class));
    }
    @Override
    public FilmPreparationComponent getComponent() {
        return component;
    }

    @Override
    protected void onDestroy() {
        presenter.detachView();
        events.unregister(this);
        super.onDestroy();
    }

    @Override
    public void setLanguagesToRecyclerView() {
        setViewsVisible();
        setFilmFragment(LanguagesFragment.getNewInstance(),false,false);
    }

    private void setFilmFragment(Fragment fragment, boolean addToBackStack, boolean popBackStack) {
        if(isRunning) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.film_fragment_holder, fragment);

            if(popBackStack) {
                fragmentManager.popBackStack();
            }

            if (addToBackStack) {
                fragmentTransaction.addToBackStack("");
            }

            fragmentTransaction.commit();
            fragmentManager.executePendingTransactions();
        }

    }
    private void setViewsVisible() {
        if(toolbar == null) {
            throw new NullPointerException("toolbar is null");
        }

        progressBar.setVisibility(View.INVISIBLE);
        toolbar.setTitle(presenter.getFilmName());
    }

    private void initToolbar() {
        setSupportActionBar((Toolbar)findViewById(R.id.toolbar));
        toolbar = getSupportActionBar();

        if(toolbar != null) {
            toolbar.setTitle("");
        }
    }

}
