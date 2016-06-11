package com.lod.movie_extended.injection.component.application;

import android.app.Application;
import android.content.Context;
import android.media.AudioManager;
import android.os.Handler;

import com.google.android.exoplayer.ExoPlayer;
import com.lod.movie_extended.data.DataManager;
import com.lod.movie_extended.data.local.DataBaseHelper;
import com.lod.movie_extended.data.local.PreferencesHelper;
import com.lod.movie_extended.data.model.ColorHelper;
import com.lod.movie_extended.data.model.NotificationServiceHelper;
import com.lod.movie_extended.data.model.player.ExtractorRendererBuilder;
import com.lod.movie_extended.data.model.player.Player;
import com.lod.movie_extended.data.remote.ServerHelper;
import com.lod.movie_extended.injection.App;
import com.lod.movie_extended.injection.context.ApplicationContext;
import com.lod.movie_extended.injection.module.application.ApplicationModule;
import com.lod.movie_extended.injection.module.application.AudioModule;
import com.lod.movie_extended.injection.scope.PerApplication;
import com.lod.movie_extended.receiver.HeadsetEventReceiver;
import com.lod.movie_extended.service.PlayerNotificationService;
import com.lod.movie_extended.data.model.player.PlayerLogger;
import com.lod.movie_extended.ui.activity.filmShow.FilmShowPresenter;
import com.lod.movie_extended.ui.activity.languagePicker.LanguagePickerPresenter;
import com.lod.movie_extended.ui.activity.main.MainPresenter;
import com.lod.movie_extended.ui.activity.qrCodeReader.QrCodeReaderPresenter;
import com.lod.movie_extended.ui.activity.sub.SubPresenter;
import com.squareup.otto.Bus;

import dagger.Component;

/**
 * Created by Жамбыл on 09.01.2016.
 */
@PerApplication
@Component(
        modules = {
                ApplicationModule.class,
                AudioModule.class
        }
)
public interface ApplicationComponent{

    @ApplicationContext
    Context context();

    Application application();

    Bus getEventBus();

    DataManager getDataManager();

    Player getPlayer();

    ExoPlayer getExoPlayer();

    ExtractorRendererBuilder getExtractorRendererBuilder();

    AudioManager getAudioManager();

    Handler getHandler();

    PlayerLogger getPlayerLogger();

    ColorHelper getColorHelper();

    NotificationServiceHelper getServiceHelper();

    ServerHelper getServerHelper();

    DataBaseHelper getDataBaseHelper();

    PreferencesHelper getPreferencesHelper();

    HeadsetEventReceiver getHeadsetEventReceiver();

    FilmShowPresenter getFilmShowPresenter();

    QrCodeReaderPresenter getQrCodeReaderPresenter();

    SubPresenter getSubPresenter();

    MainPresenter getMainPresenter();

    LanguagePickerPresenter getLanguagePickerPresenter();

    void inject(PlayerNotificationService playerNotificationService);

    void inject(HeadsetEventReceiver headsetEventReceiver);

    void inject(Player player);

    void inject(App app);

    final class Initializer {
        public static ApplicationComponent init(App application) {
            ApplicationComponent appGraph = DaggerApplicationComponent.builder().build();
            appGraph.inject(application);
            return appGraph;
        }
    }
}
