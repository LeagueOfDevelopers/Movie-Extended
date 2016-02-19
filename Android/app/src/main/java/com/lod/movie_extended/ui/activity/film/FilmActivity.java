package com.lod.movie_extended.ui.activity.film;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.Visibility;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.exoplayer.text.SubtitleLayout;
import com.lod.movie_extended.App;
import com.lod.movie_extended.R;
import com.lod.movie_extended.injection.component.activity.DaggerFilmComponent;
import com.lod.movie_extended.injection.component.activity.FilmComponent;
import com.lod.movie_extended.injection.module.activity.FilmModule;
import com.lod.movie_extended.ui.base.ComponentCreator;
import com.lod.movie_extended.ui.base.ComponentGetter;
import com.lod.movie_extended.ui.base.InjectActivityBase;
import com.lod.movie_extended.ui.base.InjectFragmentBase;
import com.lod.movie_extended.ui.base.Presenter;
import com.lod.movie_extended.ui.fragment.poster.PosterFragment;
import com.lod.movie_extended.ui.view.PlayPauseView;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

/**
 * Created by Жамбыл on 29.12.2015.
 */

public class FilmActivity extends InjectActivityBase implements FilmMvpView,
        ComponentCreator<FilmComponent>, ComponentGetter<FilmComponent> {

    private static final int LAYOUT = R.layout.player;

    @Bind(R.id.play_pause_view)
    PlayPauseView playPauseView;

    @Bind(R.id.player_background_ll)
    LinearLayout background;

    @Inject
    FragmentManager fragmentManager;

    @Inject
    FilmPresenter presenter;

    private FilmComponent component;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Timber.v("onCreate");
        presenter.onCreate();
        initUI();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        Timber.v("onDestroy");
        super.onDestroy();
        presenter.OnDestroy();
    }


    @Override
    public void togglePlayPauseButtonSlowIfNeed() {
        setCorrectDrawable(true);
    }

    @OnClick(R.id.close)
    public void OnCloseButtonClick() {
        finish();
    }

    @OnClick(R.id.show_subtitles)
    public void OnShowSubtitles() {

    }

    private void initUI() {
        setupWindowAnimations();
        initPlayPauseView();
        setStatusBarColor(presenter.getPosterDarkColor());
        setBackgroundLayout(presenter.getPosterLightColor());
        setSubtitlesFragment();
    }

    private void setSubtitlesFragment() {
        setFragment(InjectFragmentBase.getNewInstance(PosterFragment.class));
    }

    private void setFragment(Fragment fragment) {
        Timber.v("setting Fragment");
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.poster_subtitles_fragment_holder, fragment);

        fragmentTransaction.commit();
        fragmentManager.executePendingTransactions();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setStatusBarColor(int color) {
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(color);
    }

    private void setBackgroundLayout(int color) {
        background.setBackgroundColor(color);
    }

    private void initPlayPauseView() {
        setCorrectDrawable(false);
        setOnClickListenerOnPlayPauseView();
    }

    private void setOnClickListenerOnPlayPauseView() {
        playPauseView.setOnClickListener(v -> {
            presenter.togglePlayer();
        });
    }

    private void setCorrectDrawable(boolean slow) {
        if(presenter.isPlaying() == playPauseView.isPlay())
        {
            playPauseView.toggle(slow);
        }
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
    public int getContentView() {
        return LAYOUT;
    }

    @Override
    public void inject() {
        ButterKnife.bind(this);
        createComponent().inject(this);
    }

    @Override
    public Presenter getPresenter() {
        return presenter;
    }

    @Override
    public FilmComponent createComponent() {
        component = DaggerFilmComponent.builder()
                .filmModule(new FilmModule(this))
                .applicationComponent(App.get(this).getComponent())
                .build();

        return component;
    }

    @Override
    public SubtitleLayout getSubtitleLayout() {
        return null;
    }

    @Override
    public void showHeadsetError() {
        Toast.makeText(this,"Headset error",Toast.LENGTH_SHORT).show();
    }

    @Override
    public FilmComponent getComponent() {
        return component;
    }
}

