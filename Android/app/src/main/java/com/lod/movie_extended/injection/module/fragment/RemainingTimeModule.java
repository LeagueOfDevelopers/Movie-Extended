package com.lod.movie_extended.injection.module.fragment;

import com.lod.movie_extended.data.DataManager;
import com.lod.movie_extended.injection.scope.PerFragment;
import com.lod.movie_extended.ui.remainigTime.RemainingTimePresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Жамбыл on 09.01.2016.
 */
@Module
public class RemainingTimeModule {

    @Provides
    @PerFragment
    RemainingTimePresenter provideRemainingTimePresenter(DataManager dataManager) {
        return new RemainingTimePresenter(dataManager);
    }
}
