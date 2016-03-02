package com.lod.movie_extended.ui.fragment.languages;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lod.movie_extended.R;
import com.lod.movie_extended.data.model.Language;
import com.lod.movie_extended.injection.component.activity.FilmPreparationComponent;
import com.lod.movie_extended.injection.component.fragment.DaggerLanguagesComponent;
import com.lod.movie_extended.injection.component.fragment.LanguagesComponent;
import com.lod.movie_extended.test.module.fragment.LanguagesModule;
import com.lod.movie_extended.ui.base.ComponentCreator;
import com.lod.movie_extended.ui.base.InjectFragmentBase;
import com.lod.movie_extended.ui.base.Presenter;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Жамбыл on 09.01.2016.
 */
public class LanguagesFragment extends InjectFragmentBase implements LanguagesMvpView, ComponentCreator<LanguagesComponent>{

    private static final int LAYOUT = R.layout.fragment_languages;

    @Bind(R.id.recycler_view)
    RecyclerView languagesRecyclerView;

    @Inject
    LanguagesAdapter languagesAdapter;

    @Inject
    LanguagesPresenter presenter;

    @Inject
    LinearLayoutManager linearLayoutManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container,savedInstanceState);
        initRecyclerView();
        return view;
    }

    private void initRecyclerView() {
        languagesAdapter.setLanguages(presenter.getLanguages());
        languagesRecyclerView.setLayoutManager(linearLayoutManager);

        languagesRecyclerView.setAdapter(languagesAdapter);
        languagesRecyclerView.setHasFixedSize(true);
    }

    @Override
    public LanguagesComponent createComponent() {
        return DaggerLanguagesComponent.builder()
                .filmPreparationComponent((FilmPreparationComponent) parentComponent)
                .languagesModule(new LanguagesModule())
                .build();
    }

    @Override
    public void setLanguages(ArrayList<Language> languages) {

    }

    @Override
    public int getContentView() {
        return LAYOUT;
    }

    @Override
    public void inject() {
        ButterKnife.bind(this,view);
        createComponent().inject(this);
    }

    @Override
    public Presenter getPresenter() {
        return presenter;
    }
}
