package com.lod.movie_extended.injection.module.fragment;

import com.lod.movie_extended.data.DataManager;
import com.lod.movie_extended.injection.scope.PerFragment;
import com.lod.movie_extended.ui.fragment.subtitles.SubtitlesPresenter;
import com.squareup.otto.Bus;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Жамбыл on 2/18/2016.
 */
@Module
public class SubtitlesModule {

    @PerFragment
    @Provides
    SubtitlesPresenter provideSubtitlesPresenter(DataManager dataManager, Bus bus) {
        return new SubtitlesPresenter(dataManager, bus);
    }
}
