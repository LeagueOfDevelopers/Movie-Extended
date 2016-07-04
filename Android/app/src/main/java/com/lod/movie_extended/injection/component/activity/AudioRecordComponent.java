package com.lod.movie_extended.injection.component.activity;

import com.lod.movie_extended.injection.component.application.ApplicationComponent;
import com.lod.movie_extended.injection.module.activity.AudioRecordModule;
import com.lod.movie_extended.injection.scope.PerActivity;
import com.lod.movie_extended.ui.activity.audioRecord.AudioRecordActivity;

import dagger.Component;

/**
 * Created by Максим on 04.07.2016.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class,modules = AudioRecordModule.class)
public interface AudioRecordComponent extends BaseComponent {
   // void inject (AudioRecordActivity audioRecordActivity);
}
