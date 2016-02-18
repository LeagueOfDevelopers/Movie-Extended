package com.lod.movie_extended.ui.film;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.Visibility;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.google.android.exoplayer.text.SubtitleLayout;
import com.lod.movie_extended.App;
import com.lod.movie_extended.R;
import com.lod.movie_extended.injection.component.activity.DaggerFilmComponent;
import com.lod.movie_extended.injection.component.activity.FilmComponent;
import com.lod.movie_extended.injection.module.activity.FilmModule;
import com.lod.movie_extended.ui.base.ComponentCreator;
import com.lod.movie_extended.ui.base.InjectActivityBase;
import com.lod.movie_extended.ui.base.Presenter;
import com.lod.movie_extended.util.PlayPauseView;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

/**
 * Created by Жамбыл on 29.12.2015.
 */

public class FilmActivity extends InjectActivityBase implements FilmMvpView, ComponentCreator<FilmComponent>{

    private static final int LAYOUT = R.layout.player;

    @Bind(R.id.play_pause_view)
    PlayPauseView playPauseView;

    @Bind(R.id.player_background_ll)
    LinearLayout background;

    @Inject
    FilmPresenter presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        App.get(this).setAudioUrl("http://movieextended1.azurewebsites.net/api/file/get/43");
        super.onCreate(savedInstanceState);
        presenter.onCreate();
        setupWindowAnimations();
        Timber.v("onCreate" + presenter.hashCode());
        initUI();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        Timber.v("onDestroy");
        super.onDestroy();
        presenter.OnDestroy();
    }

    private void initUI() {
        initPlayPauseView();
        setStatusBarColor(presenter.getPosterDarkColor());
        setBackgroundLayout(presenter.getPosterLightColor());
    }

    @OnClick(R.id.close)
    public void OnCloseButtonClick() {
        finish();
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

    @Override
    public void togglePlayPauseButtonSlowIfNeed() {
        setCorrectDrawable(true);
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
    public SubtitleLayout getSubtitleLayout() {
        return null;
    }
}

