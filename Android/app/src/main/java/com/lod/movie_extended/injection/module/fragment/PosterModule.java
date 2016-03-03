package com.lod.movie_extended.injection.module.fragment;

import com.lod.movie_extended.data.DataManager;
import com.lod.movie_extended.injection.scope.PerFragment;
import com.lod.movie_extended.ui.fragment.poster.PosterPresenter;
import com.squareup.otto.Bus;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Жамбыл on 2/18/2016.
 */
@Module
public class PosterModule {

    @PerFragment
    @Provides
    PosterPresenter providePosterPresenter(DataManager dataManager, Bus bus) {
        return new PosterPresenter(dataManager, bus);
    }
}
