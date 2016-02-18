package com.lod.movie_extended.injection.module.fragment;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;

import com.lod.movie_extended.data.DataManager;
import com.lod.movie_extended.injection.scope.PerFragment;
import com.lod.movie_extended.ui.fragment.languages.LanguagesAdapter;
import com.lod.movie_extended.ui.fragment.languages.LanguagesPresenter;
import com.squareup.otto.Bus;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Жамбыл on 09.01.2016.
 */
@Module
public class LanguagesModule {

    @PerFragment
    @Provides
    LanguagesPresenter provideLanguagesPresenter(DataManager dataManager, Bus bus) {
        return new LanguagesPresenter(dataManager, bus);
    }

    @Provides
    @PerFragment
    LanguagesAdapter provideLanguagesAdapter(LanguagesPresenter languagesPresenter) {
        return new LanguagesAdapter(languagesPresenter);
    }

    @Provides
    @PerFragment
    LinearLayoutManager provideLinearLayoutManager(Context context) {
        return new LinearLayoutManager(context);
    }
}
