package com.lod.movie_extended.injection.component.activity;

import android.content.Context;

import com.lod.movie_extended.data.DataManager;
import com.lod.movie_extended.injection.component.application.ApplicationComponent;
import com.lod.movie_extended.injection.module.activity.LanguagesPickerModule;
import com.lod.movie_extended.injection.scope.PerActivity;
import com.lod.movie_extended.ui.activity.languagePicker.LanguagePickerActivity;
import com.squareup.otto.Bus;

import dagger.Component;

/**
 * Created by Жамбыл on 3/16/2016.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = LanguagesPickerModule.class)
public interface LanguagesPickerComponent extends BaseComponent {

    Context getContext();
    DataManager getDataManager();
    Bus getBus();

    void inject(LanguagePickerActivity languagePickerActivity);
}
