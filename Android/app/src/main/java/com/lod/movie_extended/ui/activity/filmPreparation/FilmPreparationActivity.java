package com.lod.movie_extended.ui.activity.filmPreparation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.util.Pair;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.lod.movie_extended.injection.App;
import com.lod.movie_extended.R;
import com.lod.movie_extended.data.model.ColorHelper;
import com.lod.movie_extended.events.FilmStarted;
import com.lod.movie_extended.events.LanguageSelected;
import com.lod.movie_extended.injection.component.activity.DaggerFilmPreparationComponent;
import com.lod.movie_extended.injection.component.activity.FilmPreparationComponent;
import com.lod.movie_extended.injection.module.activity.FilmPreparationModule;
import com.lod.movie_extended.ui.base.InjectActivityBase;
import com.lod.movie_extended.ui.base.ComponentGetter;
import com.lod.movie_extended.ui.base.InjectFragmentBase;
import com.lod.movie_extended.ui.base.Presenter;
import com.lod.movie_extended.ui.activity.film.FilmActivity;
import com.lod.movie_extended.ui.fragment.languages.LanguagesFragment;
import com.lod.movie_extended.ui.fragment.remainigTime.RemainingTimeFragment;
import com.lod.movie_extended.ui.view.TransitionHelper;
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
        implements FilmPreparationMvpView, ComponentGetter<FilmPreparationComponent> {

    //region vars
    private static final int LAYOUT = R.layout.activity_film_preparation;

    @Bind(R.id.footer_ll)
    LinearLayout footer;

    @Bind(R.id.progressBar)
    ProgressBar progressBar;

    @Bind(R.id.footer_player_button)
    ImageButton footerPlayerButton;

    @Inject
    FilmPreparationPresenter presenter;
    @Inject
    Bus events;
    @Inject
    FragmentManager fragmentManager;
    @Inject
    ColorHelper colorHelper;

    FilmPreparationComponent component;

    Intent filmActivityIntent;
    private boolean isPlaying;
    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Timber.v("onCreate");
        presenter.onCreate();
        filmActivityIntent = new Intent(this, FilmActivity.class);
        initToolbar();
        initFooter();
        togglePlayerView(presenter.isPlaying());
    }

    private void initFooter() {
        footer.setBackgroundColor(colorHelper.getPosterDarkColor());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Timber.v("onDestroy");
        presenter.onDestroy();
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
        presenter.loadSession();
        updateFooterVisibility();
    }

    @Override
    protected void onPause() {
        Timber.v("onPause");
        super.onPause();
    }

    @OnClick(R.id.footer_ll)
    public void onFooterClick() {
        if(presenter.isFilmTime()) {
            startActivity(filmActivityIntent);
        }
    }

    @OnClick(R.id.footer_player_button)
    public void onFooterPlayerButtonClick() {
        presenter.togglePlayer(isPlaying);
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
        if(component == null) {
            component = DaggerFilmPreparationComponent.builder()
                    .applicationComponent(App.getInstance().getComponent())
                    .filmPreparationModule(new FilmPreparationModule(this))
                    .build();
        }

        return component;
    }

    @Override
    public void setComponent(FilmPreparationComponent component) {
        this.component = component;
    }

    @Override
    public int getContentView() {
        return LAYOUT;
    }

    @Override
    public void inject() {
        ButterKnife.bind(this);
        getComponent().inject(this);
    }

    @Override
    public Bus getBus() {
        return events;
    }

    @Override
    public Presenter getPresenter() {
        return presenter;
    }

    @Override
    public void setLanguagesToRecyclerView() {
        setViewsVisible();
        setFilmFragment(InjectFragmentBase.getNewInstance(LanguagesFragment.class),false,false);
    }

    @Override
    public void onShowHeadsetError() {
        Toast.makeText(this,"headset error",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void togglePlayerView(boolean playWhenReady) {
        if(playWhenReady) {
            isPlaying = true;
            footerPlayerButton.setBackground(getResources().getDrawable(R.mipmap.apollo_holo_dark_pause));
        }
        else {
            isPlaying = false;
            footerPlayerButton.setBackground(getResources().getDrawable(R.mipmap.apollo_holo_dark_play));
        }
    }

    @Subscribe
    public void onLanguageSelected(LanguageSelected event) {
        Timber.v("onLanguageSelected, onLanguageSelected");
        if(presenter.isFilmTime()) {
            startActivity(filmActivityIntent);
        }
        else {
            setFilmFragment(InjectFragmentBase.getNewInstance(RemainingTimeFragment.class),true,false);
        }
    }

    private void setFilmFragment(Fragment fragment, boolean addToBackStack, boolean popBackStack) {
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

    private void setViewsVisible() {
        Timber.v("setting views visible");
        progressBar.setVisibility(View.GONE);
    }
}
