package com.lod.movie_extended.ui.fragment.poster;

import com.lod.movie_extended.R;
import com.lod.movie_extended.injection.component.activity.FilmComponent;
import com.lod.movie_extended.injection.component.fragment.DaggerPosterComponent;
import com.lod.movie_extended.injection.component.fragment.PosterComponent;
import com.lod.movie_extended.injection.module.fragment.PosterModule;
import com.lod.movie_extended.ui.base.ComponentGetter;
import com.lod.movie_extended.ui.base.InjectFragmentBase;
import com.lod.movie_extended.ui.base.Presenter;
import com.squareup.otto.Bus;

import javax.inject.Inject;

import butterknife.ButterKnife;

/**
 * Created by Жамбыл on 2/18/2016.
 */
public class PosterFragment extends InjectFragmentBase
        implements PosterMvpView, ComponentGetter<PosterComponent> {

    private static final int LAYOUT = R.layout.fragment_poster;

    @Inject
    PosterPresenter presenter;

    @Inject
    Bus events;

    private PosterComponent component;

    @Override
    public int getContentView() {
        return LAYOUT;
    }

    @Override
    public void inject() {
        ButterKnife.bind(this,view);
        getComponent().inject(this);
    }

    @Override
    public Presenter getPresenter() {
        return presenter;
    }


    @Override
    public Bus getBus() {
        return events;
    }

    @Override
    public PosterComponent getComponent() {
        if(component == null) {
            component = DaggerPosterComponent.builder()
                    .filmComponent((FilmComponent) parentComponent)
                    .posterModule(new PosterModule())
                    .build();
        }
        return component;
    }

    @Override
    public void setComponent(PosterComponent component) {
        this.component = component;
    }
}
