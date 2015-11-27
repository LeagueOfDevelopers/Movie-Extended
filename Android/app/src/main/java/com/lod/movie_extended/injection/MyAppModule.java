package com.lod.movie_extended.injection;


import android.content.Context;

import com.lod.movie_extended.bll.IServer;
import com.lod.movie_extended.bll.ServerMock;
import com.lod.movie_extended.uil.activity.QrCodeReaderActivity;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

/**
 * Created by Жамбыл on 27.11.2015.
 */
@Module
public class MyAppModule {

    MyApp app;

    public MyAppModule(MyApp app) {
        this.app = app;
    }

    @Provides
    MyApp provideMyApp() {
        return app;
    }
    @Provides
    IServer provideServer(){
        return new ServerMock();
    }

    @Provides
    ZXingScannerView provideZXingScannerView(){
        return new ZXingScannerView(app);
    }
}