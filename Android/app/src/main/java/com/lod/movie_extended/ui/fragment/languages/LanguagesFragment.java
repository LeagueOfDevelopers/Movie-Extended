package com.lod.movie_extended.ui.fragment.languages;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lod.movie_extended.R;
import com.lod.movie_extended.data.model.Film;
import com.lod.movie_extended.data.model.Language;
import com.lod.movie_extended.injection.component.activity.LanguagesPickerComponent;
import com.lod.movie_extended.injection.component.fragment.DaggerLanguagesComponent;
import com.lod.movie_extended.injection.component.fragment.LanguagesComponent;
import com.lod.movie_extended.injection.module.fragment.LanguagesModule;
import com.lod.movie_extended.ui.activity.languagePicker.LanguagePickerView;
import com.lod.movie_extended.ui.base.ComponentGetter;
import com.lod.movie_extended.ui.base.InjectFragmentBase;
import com.lod.movie_extended.ui.base.Presenter;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Жамбыл on 09.01.2016.
 */
public class LanguagesFragment extends InjectFragmentBase implements LanguagesMvpView, ComponentGetter<LanguagesComponent> {

    private static final int LAYOUT = R.layout.fragment_languages;

    @Bind(R.id.recycler_view)
    RecyclerView languagesRecyclerView;

    @Inject
    LanguagesAdapter languagesAdapter;

    @Inject
    LanguagesPresenter presenter;

    private LanguagesComponent component;
    private boolean isAllowedNext;
    private LanguagePickerView languagePickerView;
    private boolean isSound;

    public LanguagesFragment(LanguagePickerView languagePickerView, boolean isSound) {
        this.languagePickerView = languagePickerView;
        this.isSound = isSound;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container,savedInstanceState);
        initRecyclerView();
        return view;
    }

    @Override
    public void allowNext() {
        isAllowedNext = true;
        languagePickerView.notifyLanguageHasBeenPicked();
    }

    @Override
    public void setFilm(Film film) {

    }

    private void initRecyclerView() {
        languagesAdapter.setFilm(presenter.getFilm(), isSound);
        languagesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        languagesRecyclerView.setAdapter(languagesAdapter);
        languagesRecyclerView.setHasFixedSize(true);
    }

    @Override
    public void setLanguages(ArrayList<Language> languages) {

    }

    public boolean isAllowedNext() {
        return isAllowedNext;
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
    public LanguagesComponent getComponent() {
        if(component == null) {
            component = DaggerLanguagesComponent.builder()
                    .languagesPickerComponent((LanguagesPickerComponent) parentComponent)
                    .languagesModule(new LanguagesModule())
                    .build();
        }

        return component;
    }

    @Override
    public void setComponent(LanguagesComponent component) {
        this.component = component;
    }
}
