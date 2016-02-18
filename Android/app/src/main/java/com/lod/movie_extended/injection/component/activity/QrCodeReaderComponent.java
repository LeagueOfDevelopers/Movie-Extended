package com.lod.movie_extended.injection.component.activity;

import com.lod.movie_extended.injection.component.application.ApplicationComponent;
import com.lod.movie_extended.injection.module.activity.QrCodeReaderModule;
import com.lod.movie_extended.injection.scope.PerActivity;
import com.lod.movie_extended.ui.activity.qrCodeReader.QrCodeReaderActivity;

import dagger.Component;

/**
 * Created by Жамбыл on 09.01.2016.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = QrCodeReaderModule.class)
public interface QrCodeReaderComponent {

    void inject(QrCodeReaderActivity qrCodeReaderActivity);
}
