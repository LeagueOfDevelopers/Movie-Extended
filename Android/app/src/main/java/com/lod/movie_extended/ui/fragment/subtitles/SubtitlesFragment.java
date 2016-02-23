package com.lod.movie_extended.ui.fragment.subtitles;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.exoplayer.text.Cue;
import com.google.android.exoplayer.text.SubtitleLayout;
import com.lod.movie_extended.R;
import com.lod.movie_extended.injection.component.activity.FilmComponent;
import com.lod.movie_extended.injection.component.activity.FilmPreparationComponent;
import com.lod.movie_extended.injection.component.fragment.DaggerSubtitlesComponent;
import com.lod.movie_extended.injection.component.fragment.SubtitlesComponent;
import com.lod.movie_extended.injection.module.fragment.SubtitlesModule;
import com.lod.movie_extended.ui.base.ComponentCreator;
import com.lod.movie_extended.ui.base.ComponentGetter;
import com.lod.movie_extended.ui.base.InjectFragmentBase;
import com.lod.movie_extended.ui.base.Presenter;
import com.squareup.otto.Bus;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Жамбыл on 2/18/2016.
 */
public class SubtitlesFragment extends InjectFragmentBase
        implements SubtitlesMvpView,ComponentCreator<SubtitlesComponent> {

    private static final int LAYOUT = R.layout.fragment_subtitles;

    @Inject
    SubtitlesPresenter presenter;

    @Bind(R.id.subtitles)
    SubtitleLayout subtitleLayout;

    @Inject
    Bus events;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater,container,savedInstanceState);
    }

    @Override
    public void onCues(List<Cue> cues) {
        subtitleLayout.setCues(cues);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.onDestroy();
    }

    @Override
    public SubtitlesComponent createComponent() {
        return DaggerSubtitlesComponent.builder()
                .filmComponent((FilmComponent) parentComponent)
                .subtitlesModule(new SubtitlesModule())
                .build();
    }

    @Override
    public int getContentView() {
        return LAYOUT;
    }

    @Override
    public void inject() {
        ButterKnife.bind(this,view);
        createComponent().inject(this);
    }

    @Override
    public Presenter getPresenter() {
        return presenter;
    }

    @Override
    public Bus getBus() {
        return events;
    }
}
