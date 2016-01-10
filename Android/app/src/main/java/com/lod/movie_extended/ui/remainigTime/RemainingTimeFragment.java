package com.lod.movie_extended.ui.remainigTime;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lod.movie_extended.R;
import com.lod.movie_extended.events.FilmStarted;
import com.lod.movie_extended.injection.component.activity.FilmPreparationComponent;
import com.lod.movie_extended.injection.component.fragment.DaggerRemainingTimeFragmentComponent;
import com.lod.movie_extended.injection.component.fragment.RemainingTimeFragmentComponent;
import com.lod.movie_extended.injection.module.fragment.RemainingTimeModule;
import com.lod.movie_extended.ui.base.ComponentCreator;
import com.lod.movie_extended.ui.base.ComponentGetter;
import com.squareup.otto.Bus;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Жамбыл on 09.01.2016.
 */
public class RemainingTimeFragment extends Fragment
        implements RemainingTimeMvp, ComponentCreator<RemainingTimeFragmentComponent> {

    private static final int LAYOUT = R.layout.fragment_remaining_time;
    private FilmPreparationComponent filmPreparationComponent;

    @Bind(R.id.remaining_time_text_view)
    TextView remainingTimeTextView;

    @Bind(R.id.selected_language_text_view)
    TextView selectedLanguageTextView;


    @Inject
    RemainingTimePresenter presenter;

    @Inject
    Bus events;

    long remainingTime;

    Handler handler;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ComponentGetter<FilmPreparationComponent> componentGetter = (ComponentGetter<FilmPreparationComponent>) context;
        filmPreparationComponent = componentGetter.getComponent();
    }

    public static RemainingTimeFragment getNewInstance() {
        RemainingTimeFragment instance = new RemainingTimeFragment();
        Bundle bundle = new Bundle();
        instance.setArguments(bundle);
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(LAYOUT, container, false);
        injectFields(view);
        presenter.attachView(this);
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

    private void injectFields(View view) {
        ButterKnife.bind(this,view);
        createComponent().inject(this);
    }

    @Override
    public void onDestroyView() {
        presenter.detachView();
        super.onDestroyView();
    }

    @Override
    public RemainingTimeFragmentComponent createComponent() {
        return DaggerRemainingTimeFragmentComponent.builder()
                .filmPreparationComponent(filmPreparationComponent)
                .remainingTimeModule(new RemainingTimeModule())
                .build();
    }
}
