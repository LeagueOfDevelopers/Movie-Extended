package com.lod.movie_extended.injection.module.activity;

import android.app.Activity;
import android.content.Context;

import com.lod.movie_extended.data.DataManager;
import com.lod.movie_extended.injection.context.ActivityContext;
import com.lod.movie_extended.injection.scope.PerActivity;
import com.lod.movie_extended.ui.activity.main.MainPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Жамбыл on 3/3/2016.
 */
@Module
public class MainModuleTest {

    private Activity mActivity;

    public MainModuleTest(Activity activity) {
        mActivity = activity;
    }

    @Provides
    Activity provideActivity() {
        return mActivity;
    }

    @Provides
    @ActivityContext
    Context providesContext() {
        return mActivity;
    }

    @Provides
    @PerActivity
    MainPresenter provideMainPresenter(DataManager dataManager) {
        return new MainPresenter(dataManager);
    }
}
