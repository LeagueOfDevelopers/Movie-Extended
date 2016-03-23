package com.lod.movie_extended.ui.activity.filmShow;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lod.movie_extended.R;
import com.lod.movie_extended.data.model.Film;
import com.lod.movie_extended.injection.App;
import com.lod.movie_extended.injection.component.activity.DaggerFilmShowComponent;
import com.lod.movie_extended.injection.component.activity.FilmShowComponent;
import com.lod.movie_extended.injection.module.activity.FilmShowModule;
import com.lod.movie_extended.receiver.HeadsetEventReceiver;
import com.lod.movie_extended.ui.activity.languagePicker.LanguagePickerActivity;
import com.lod.movie_extended.ui.activity.sub.SubActivity;
import com.lod.movie_extended.ui.base.ComponentGetter;
import com.lod.movie_extended.ui.base.InjectActivityBase;
import com.lod.movie_extended.ui.base.Presenter;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by Жамбыл on 3/17/2016.
 */
public class FilmShowActivity extends InjectActivityBase implements FilmShowView, ComponentGetter<FilmShowComponent>{

    private static final int LAYOUT = R.layout.activity_film_show;

    private FilmShowComponent component;

    @Inject
    FilmShowPresenter presenter;

    @Inject
    HeadsetEventReceiver headsetEventReceiver;

    @Bind(R.id.film_name_text_view)
    TextView filmNameTextView;

    @Bind(R.id.sound_language_text_view)
    TextView soundLanguageTextView;

    @Bind(R.id.subtitle_language_text_view)
    TextView subtitleLanguageTextView;

    @Bind(R.id.info_text_view)
    TextView infoTextView;

    @Bind(R.id.play_pause_text_view)
    TextView playPauseTextView;

    @Bind(R.id.sub_text_view)
    TextView subTextView;

    @Bind(R.id.headset)
    ImageView headsetImageView;

    @Bind(R.id.blackScreen)
    LinearLayout blackScreen;

    @Bind(R.id.progressBar)
    ProgressBar progressBar;

    @Bind(R.id.play_pause_view)
    RelativeLayout playPauseView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showLoadingScreen();
        playPauseView.setOnClickListener(v -> presenter.togglePlayer());
        subTextView.setOnClickListener(v -> startActivity(new Intent(this, SubActivity.class)));
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.loadSessionIfNeed();
        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_HEADSET_PLUG);
        registerReceiver(headsetEventReceiver,intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(headsetEventReceiver);
    }

    @OnClick(R.id.fab)
    public void onFabClick() {
        startActivity(new Intent(this,LanguagePickerActivity.class));
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
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
    public Presenter getPresenter() {
        return presenter;
    }

    @Override
    public FilmShowComponent getComponent() {
        if(component == null) {
            component = DaggerFilmShowComponent.builder()
                .applicationComponent(App.getInstance().getComponent())
                .filmShowModule(new FilmShowModule(this))
                .build();
        }
        return component;
    }

    @Override
    public void setComponent(FilmShowComponent component) {
        this.component = component;
    }

    @Override
    public void setFilm(Film film) {
        presenter.setFilmTime(true);
        hideLoadingScreen();
        toggleFooter(true);

        filmNameTextView.setText(film.getName().toUpperCase());
        soundLanguageTextView.setText(film.getSelectedSoundLanguage().getName());
        subtitleLanguageTextView.setText(film.getSelectedSubtitleLanguage().getName());
    }

    @Override
    public void showError() {
        Toast.makeText(FilmShowActivity.this, "Server error", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setHeadsetFooter() {
        toggleFooter(false, "Воткни");
    }

    @Override
    public void setNormalFooter() {
        toggleFooter(true);
    }

    @Override
    public void setPlayView() {
        playPauseTextView.setText("PLAY");
    }

    @Override
    public void setPauseView() {
        playPauseTextView.setText("MUTE");
    }

    private void showLoadingScreen() {
        blackScreen.setAlpha(1.0f);
        progressBar.setVisibility(View.VISIBLE);
        progressBar.setProgressDrawable(new ColorDrawable(0xFF000000));
    }

    private void hideLoadingScreen() {
        blackScreen.setAlpha(0.0f);
        progressBar.setVisibility(View.INVISIBLE);
    }

    private void toggleFooter(boolean normalFooterView) {
        toggleFooter(normalFooterView, "");
    }

    private void toggleFooter(boolean normalFooterView, String infoText) {
        if(!infoText.equals("")) {
            infoTextView.setText(infoText);
        }

        if(normalFooterView) {
            infoTextView.setVisibility(View.INVISIBLE);
            playPauseTextView.setVisibility(View.VISIBLE);
            subTextView.setVisibility(View.VISIBLE);
            headsetImageView.setVisibility(View.VISIBLE);
        } else {
            infoTextView.setVisibility(View.VISIBLE);
            playPauseTextView.setVisibility(View.INVISIBLE);
            subTextView.setVisibility(View.INVISIBLE);
            headsetImageView.setVisibility(View.INVISIBLE);
        }
    }
}
