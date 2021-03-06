package com.lod.movie_extended.injection.component.fragment;

import com.lod.movie_extended.injection.component.activity.FilmPreparationComponent;
import com.lod.movie_extended.injection.module.fragment.LanguagesModule;
import com.lod.movie_extended.injection.scope.PerFragment;
import com.lod.movie_extended.ui.languages.LanguagesFragment;

import dagger.Component;

/**
 * Created by Жамбыл on 09.01.2016.
 */
@PerFragment
@Component(dependencies = FilmPreparationComponent.class, modules = LanguagesModule.class)
public interface LanguagesFragmentComponent {

    void inject(LanguagesFragment languagesFragment);
}
