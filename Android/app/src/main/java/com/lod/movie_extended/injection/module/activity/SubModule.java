package com.lod.movie_extended.injection.module.activity;

import com.lod.movie_extended.data.DataManager;
import com.lod.movie_extended.data.model.player.Player;
import com.lod.movie_extended.injection.scope.PerActivity;
import com.lod.movie_extended.ui.activity.sub.SubPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Жамбыл on 3/21/2016.
 */
@Module
public class SubModule {

    @Provides
    @PerActivity
    SubPresenter provideSubPresenter(DataManager dataManager, Player player) {
        return new SubPresenter(dataManager,player);
    }
}
