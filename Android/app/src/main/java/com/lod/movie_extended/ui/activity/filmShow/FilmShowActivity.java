package com.lod.movie_extended.ui.activity.filmShow;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.lod.movie_extended.R;
import com.lod.movie_extended.data.model.Film;
import com.lod.movie_extended.injection.App;
import com.lod.movie_extended.injection.component.activity.DaggerFilmShowComponent;
import com.lod.movie_extended.injection.component.activity.FilmShowComponent;
import com.lod.movie_extended.injection.module.activity.FilmShowModule;
import com.lod.movie_extended.ui.activity.languagePicker.LanguagePickerActivity;
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

    @Bind(R.id.film_name_text_view)
    TextView filmNameTextView;

    @Bind(R.id.sound_language_text_view)
    TextView soundLanguageTextView;

    @Bind(R.id.subtitle_language_text_view)
    TextView subtitleLanguageTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.loadSession();
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
        filmNameTextView.setText(film.getName().toUpperCase());
        soundLanguageTextView.setText(film.getSelectedSoundLanguage().getName());
        subtitleLanguageTextView.setText(film.getSelectedSubtitleLanguage().getName());
    }
}
