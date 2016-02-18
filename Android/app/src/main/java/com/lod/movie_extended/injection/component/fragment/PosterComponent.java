package com.lod.movie_extended.injection.component.fragment;

import com.lod.movie_extended.injection.component.activity.FilmComponent;
import com.lod.movie_extended.injection.component.activity.FilmPreparationComponent;
import com.lod.movie_extended.injection.module.fragment.LanguagesModule;
import com.lod.movie_extended.injection.module.fragment.PosterModule;
import com.lod.movie_extended.injection.scope.PerFragment;
import com.lod.movie_extended.ui.fragment.poster.PosterFragment;

import dagger.Component;

/**
 * Created by Жамбыл on 2/18/2016.
 */
@PerFragment
@Component(dependencies = FilmComponent.class, modules = PosterModule.class)
public interface PosterComponent {

    void inject(PosterFragment posterFragment);
}
