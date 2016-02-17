package com.lod.movie_extended.ui.filmPreparation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.util.Pair;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.lod.movie_extended.App;
import com.lod.movie_extended.R;
import com.lod.movie_extended.events.FilmStarted;
import com.lod.movie_extended.events.LanguageSelected;
import com.lod.movie_extended.injection.component.activity.DaggerFilmPreparationComponent;
import com.lod.movie_extended.injection.component.activity.FilmPreparationComponent;
import com.lod.movie_extended.injection.module.activity.FilmPreparationModule;
import com.lod.movie_extended.ui.base.InjectActivityBase;
import com.lod.movie_extended.ui.base.ComponentCreator;
import com.lod.movie_extended.ui.base.ComponentGetter;
import com.lod.movie_extended.ui.base.Presenter;
import com.lod.movie_extended.ui.film.FilmActivity;
import com.lod.movie_extended.ui.languages.LanguagesFragmentView;
import com.lod.movie_extended.ui.remainigTime.RemainingTimeFragment;
import com.lod.movie_extended.util.TransitionHelper;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

/**
 * Created by Жамбыл on 09.01.2016.
 */
public class FilmPreparationActivity extends InjectActivityBase
        implements FilmPreparationMvpView, ComponentCreator<FilmPreparationComponent>,
        ComponentGetter<FilmPreparationComponent> {

    //region vars
    private static final int LAYOUT = R.layout.activity_film_preparation;

    @Bind(R.id.footer_ll)
    LinearLayout footer;

    @Bind(R.id.progressBar)
    ProgressBar progressBar;

    @Inject
    FilmPreparationPresenter presenter;

    @Inject
    Bus events;

    @Inject
    FragmentManager fragmentManager;

    FilmPreparationComponent component;

    private boolean isRunning;

    Intent filmActivityIntent;
    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Timber.v("onCreate");
        isRunning = true;
        presenter.loadSession();
        presenter.getToken("qwe");
        filmActivityIntent = new Intent(this, FilmActivity.class);
        initToolbar();
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Movie-Extended");
    }

    @Override
    protected void onResume() {
        Timber.v("onResume");
        super.onResume();
        updateFooterVisibility();
        isRunning = true;
    }

    @Override
    protected void onPause() {
        Timber.v("onPause");
        isRunning = false;
        super.onPause();
    }

    @OnClick(R.id.footer_ll)
    public void onFooterClick() {
        if(presenter.isFilmTime()) {
            startActivity(filmActivityIntent);
        }
    }

    @Override
    public void updateFooterVisibility() {
        if(presenter.isFilmTime()) {
            footer.setVisibility(View.VISIBLE);
        }
        else {
            footer.setVisibility(View.GONE);
        }
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
    public void onFilmStarted(FilmStarted event) {
        Timber.v("starting FilmActivity");

        startActivity(filmActivityIntent);
        fragmentManager.popBackStack();

        presenter.setFilmTime(true);
        updateFooterVisibility();
    }

    @SuppressWarnings("unchecked")
    void transitionTo(Intent i) {
        final Pair<View, String>[] pairs = TransitionHelper.createSafeTransitionParticipants(this, true);
        ActivityOptionsCompat transitionActivityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(this, pairs);
        startActivity(i, transitionActivityOptions.toBundle());
    }

    @Override
    public FilmPreparationComponent getComponent() {
        return component;
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
    protected Bus getBus() {
        return events;
    }

    @Override
    protected Presenter getPresenter() {
        return presenter;
    }

    @Override
    public void setLanguagesToRecyclerView() {
        setViewsVisible();
        setFilmFragment(LanguagesFragmentView.getNewInstance(),false,false);
    }

    @Subscribe
    public void onLanguageSelected(LanguageSelected event) {
        Timber.v("onLanguageSelected, onLanguageSelected");
        if(presenter.isFilmTime()) {
            startActivity(filmActivityIntent);
        }
        else {
            setFilmFragment(RemainingTimeFragment.getNewInstance(),true,false);
        }
    }

    private void setFilmFragment(Fragment fragment, boolean addToBackStack, boolean popBackStack) {
        if(isRunning) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.film_fragment_holder, fragment);

            if(popBackStack) {
                Timber.v("popping fragment back stack");
                fragmentManager.popBackStack();
            }

            if (addToBackStack) {
                Timber.v("adding to backStack fragment" + fragment.toString());
                fragmentTransaction.addToBackStack(fragment.toString());
            }

            fragmentTransaction.commit();
            fragmentManager.executePendingTransactions();
        }
    }

    private void setViewsVisible() {
        Timber.v("setting views visible");
        progressBar.setVisibility(View.GONE);
    }
}
