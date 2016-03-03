package com.lod.movie_extended.ui.fragment.subtitles;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.exoplayer.text.Cue;
import com.google.android.exoplayer.text.SubtitleLayout;
import com.lod.movie_extended.R;
import com.lod.movie_extended.injection.component.activity.FilmComponent;
import com.lod.movie_extended.injection.component.fragment.DaggerSubtitlesComponent;
import com.lod.movie_extended.injection.component.fragment.SubtitlesComponent;
import com.lod.movie_extended.injection.module.fragment.SubtitlesModule;
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
        implements SubtitlesMvpView, ComponentGetter<SubtitlesComponent> {

    private static final int LAYOUT = R.layout.fragment_subtitles;

    @Inject
    SubtitlesPresenter presenter;

    @Bind(R.id.subtitles)
    SubtitleLayout subtitleLayout;

    @Inject
    Bus events;

    private SubtitlesComponent component;

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
    public int getContentView() {
        return LAYOUT;
    }

    @Override
    public void inject() {
        ButterKnife.bind(this,view);
        getComponent().inject(this);
    }

    @Override
    public Presenter getPresenter() {
        return presenter;
    }

    @Override
    public Bus getBus() {
        return events;
    }

    @Override
    public SubtitlesComponent getComponent() {
        if(component == null) {
            component = DaggerSubtitlesComponent.builder()
                    .filmComponent((FilmComponent) parentComponent)
                    .subtitlesModule(new SubtitlesModule())
                    .build();
        }
        return component;
    }

    @Override
    public void setComponent(SubtitlesComponent component) {
        this.component = component;
    }
}
