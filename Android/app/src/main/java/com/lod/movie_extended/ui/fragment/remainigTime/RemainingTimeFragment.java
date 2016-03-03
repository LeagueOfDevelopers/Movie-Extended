package com.lod.movie_extended.ui.fragment.remainigTime;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lod.movie_extended.R;
import com.lod.movie_extended.events.FilmStarted;
import com.lod.movie_extended.injection.component.activity.FilmPreparationComponent;
import com.lod.movie_extended.injection.component.fragment.DaggerRemainingTimeComponent;
import com.lod.movie_extended.injection.component.fragment.RemainingTimeComponent;
import com.lod.movie_extended.injection.module.fragment.RemainingTimeModule;
import com.lod.movie_extended.ui.base.ComponentGetter;
import com.lod.movie_extended.ui.base.InjectFragmentBase;
import com.lod.movie_extended.ui.base.Presenter;
import com.squareup.otto.Bus;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Жамбыл on 09.01.2016.
 */
public class RemainingTimeFragment extends InjectFragmentBase
        implements RemainingTimeMvp, ComponentGetter<RemainingTimeComponent> {

    private static final int LAYOUT = R.layout.fragment_remaining_time;

    @Bind(R.id.remaining_time_text_view)
    TextView remainingTimeTextView;

    @Bind(R.id.selected_language_text_view)
    TextView selectedLanguageTextView;

    @Inject
    RemainingTimePresenter presenter;

    @Inject
    Bus events;

    private long remainingTime;

    private Handler handler;

    private RemainingTimeComponent component;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater,container,savedInstanceState);
        remainingTime = presenter.getRemainingTime();
        handler = new Handler(Looper.getMainLooper());
        new Thread(() -> {
            while (remainingTime > 0) {
                handler.post(() -> onTimeChanged(--remainingTime));
                try {
                    Thread.sleep(1000); // 1 second
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            handler.post(() -> events.post(new FilmStarted()));
        }).start();
        return view;
    }

    private void onTimeChanged(long remainingTime){
        this.remainingTime = remainingTime;
        String remainingTimeString = remainingTime + " секунд";
        remainingTimeTextView.setText(remainingTimeString);
    }

    @Override
    public void onDestroyView() {
        presenter.detachView();
        super.onDestroyView();
    }

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
    public RemainingTimeComponent getComponent() {
        if(component == null) {
            component = DaggerRemainingTimeComponent.builder()
                    .filmPreparationComponent((FilmPreparationComponent) parentComponent)
                    .remainingTimeModule(new RemainingTimeModule())
                    .build();
        }
        return component;
    }

    @Override
    public void setComponent(RemainingTimeComponent component) {
        this.component = component;
    }
}
