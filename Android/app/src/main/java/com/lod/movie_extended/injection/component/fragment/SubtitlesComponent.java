package com.lod.movie_extended.injection.component.fragment;

import com.lod.movie_extended.injection.component.activity.FilmComponent;
import com.lod.movie_extended.injection.module.fragment.PosterModule;
import com.lod.movie_extended.injection.module.fragment.SubtitlesModule;
import com.lod.movie_extended.injection.scope.PerFragment;
import com.lod.movie_extended.ui.fragment.subtitles.SubtitlesFragment;

import dagger.Component;

/**
 * Created by Жамбыл on 2/18/2016.
 */
@PerFragment
@Component(dependencies = FilmComponent.class, modules = SubtitlesModule.class)
public interface SubtitlesComponent {

    void inject(SubtitlesFragment subtitlesFragment);
}
