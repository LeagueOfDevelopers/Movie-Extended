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
import com.lod.movie_extended.ui.base.ComponentGetter;
import com.lod.movie_extended.ui.base.InjectActivityBase;
import com.lod.movie_extended.ui.base.Presenter;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import timber.log.Timber;


/**
 * Created by Жамбыл on 3/21/2016.
 */
public class SubActivity extends InjectActivityBase implements SubMvpView, ComponentGetter<SubComponent> {

    private final int LAYOUT = R.layout.activity_sub;
    private SubComponent component;

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
    public SubComponent getComponent() {
        if(component ==null){
            component = DaggerSubComponent.builder()
                .applicationComponent(App.getInstance().getComponent())
                .subModule(new SubModule())
                .build();
        }
        return component;
    }

    @Override
    public void setComponent(SubComponent component) {
        this.component = component;
    }

    @Override
    public void setCues(List<Cue> cues) {
        if(!cues.isEmpty()) {
            try {
                String s = new String(cues.get(0).text.toString().getBytes("ISO-8859-1"), "UTF-8");
                Timber.v("AS STRING " + s);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            Timber.v(cues.get(0).text.toString());
        }

        subtitleLayout.setCues(cues);
    }
}
