package com.lod.movie_extended.injection.module.activity;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.lod.movie_extended.data.DataManager;
import com.lod.movie_extended.injection.scope.PerActivity;
import com.lod.movie_extended.ui.activity.languagePicker.LanguagePickerPresenter;
import com.lod.movie_extended.ui.activity.languagePicker.LanguagePickerView;
import com.lod.movie_extended.ui.activity.languagePicker.LanguagesViewPagerAdapter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Жамбыл on 3/16/2016.
 */

@Module
public class LanguagesPickerModule {
    private AppCompatActivity mActivity;

    public LanguagesPickerModule(AppCompatActivity activity) {
        mActivity = activity;
    }

    @Provides
    @PerActivity
    AppCompatActivity provideActivity() {
        return mActivity;
    }

    @Provides
    Context providesContext() {
        return mActivity;
    }

    @Provides
    @PerActivity
    LanguagePickerPresenter provideMainPresenter(DataManager dataManager) {
        return new LanguagePickerPresenter(dataManager);
    }

    @Provides
    @PerActivity
    LanguagesViewPagerAdapter provideLanguagesViewPagerAdapter(FragmentManager fragmentManager, AppCompatActivity appCompatActivity) {
        return new LanguagesViewPagerAdapter(fragmentManager, (LanguagePickerView) appCompatActivity);
    }

    @Provides
    @PerActivity
    FragmentManager provideFragmentManager(AppCompatActivity activity) {
        return activity.getSupportFragmentManager();
    }
}
