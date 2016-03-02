package com.lod.movie_extended.injection.component.fragment;

import com.lod.movie_extended.injection.component.activity.FilmPreparationComponent;
import com.lod.movie_extended.test.module.fragment.RemainingTimeModule;
import com.lod.movie_extended.injection.scope.PerFragment;
import com.lod.movie_extended.ui.fragment.remainigTime.RemainingTimeFragment;

import dagger.Component;

/**
 * Created by Жамбыл on 09.01.2016.
 */
@PerFragment
@Component(dependencies = FilmPreparationComponent.class, modules = RemainingTimeModule.class)
public interface RemainingTimeComponent {
    void inject(RemainingTimeFragment remainingTimeFragment);
}
