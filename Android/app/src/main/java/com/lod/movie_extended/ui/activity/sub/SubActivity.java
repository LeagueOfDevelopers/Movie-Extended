package com.lod.movie_extended.ui.activity.sub;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.google.android.exoplayer.text.Cue;
import com.google.android.exoplayer.text.SubtitleLayout;
import com.lod.movie_extended.R;
import com.lod.movie_extended.injection.App;
import com.lod.movie_extended.injection.component.activity.DaggerSubComponent;
import com.lod.movie_extended.injection.component.activity.SubComponent;
import com.lod.movie_extended.injection.module.activity.SubModule;
import com.lod.movie_extended.ui.base.BaseActivity;
import com.lod.movie_extended.ui.base.Presenter;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import timber.log.Timber;


/**
 * Created by Жамбыл on 3/21/2016.
 */
public class SubActivity extends BaseActivity<SubComponent> implements SubMvpView {

    private final int LAYOUT = R.layout.activity_sub;

    @Inject
    SubPresenter presenter;

    @Bind(R.id.subtitles)
    SubtitleLayout subtitleLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter.onCreate();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

    @Override
    public int getContentView() {
        return LAYOUT;
    }

    @Override
    public void inject() {
        getComponent().inject(this);
        ButterKnife.bind(this);
    }

    @Override
    public Presenter getPresenter() {
        return presenter;
    }

    @Override
    protected SubComponent createComponent() {
        return DaggerSubComponent.builder()
                .applicationComponent(App.getInstance().getComponent())
                .subModule(new SubModule())
                .build();
    }

    @Override
    public void setCues(List<Cue> cues) {
        if(!cues.isEmpty()) {
            try {
                String iso = new String(cues.get(0).text.toString().getBytes("ISO-8859-1"), "UTF-8");
                Timber.v("AS ISO " + iso);

                String utf8 = new String(cues.get(0).text.toString().getBytes("UTF-8"), "UTF-8");
                Timber.v("AS utf8 " + utf8);
                String windows = new String(cues.get(0).text.toString().getBytes("WINDOWS-1256"),"WINDOWS-1256" );
                Timber.v("AS windows " + windows);

                String koi8 = new String(cues.get(0).text.toString().getBytes("KOI8_R"),"UTF-8" );
                Timber.v("AS koi8 " + koi8);

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            Timber.v(cues.get(0).text.toString());
        }

        subtitleLayout.setCues(cues);
    }
}
