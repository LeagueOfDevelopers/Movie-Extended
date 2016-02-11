package com.lod.movie_extended.ui.languages;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lod.movie_extended.R;
import com.lod.movie_extended.data.model.Language;
import com.lod.movie_extended.injection.component.activity.FilmPreparationComponent;
import com.lod.movie_extended.injection.component.fragment.DaggerLanguagesFragmentComponent;
import com.lod.movie_extended.injection.component.fragment.LanguagesFragmentComponent;
import com.lod.movie_extended.injection.module.fragment.LanguagesModule;
import com.lod.movie_extended.ui.base.ComponentCreator;
import com.lod.movie_extended.ui.base.ComponentGetter;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Жамбыл on 09.01.2016.
 */
public class LanguagesFragmentView extends Fragment implements LanguagesMvpView, ComponentCreator<LanguagesFragmentComponent>{

    private static final int LAYOUT = R.layout.fragment_languages;

    @Bind(R.id.recycler_view)
    RecyclerView languagesRecyclerView;

    @Inject
    LanguagesAdapter languagesAdapter;

    @Inject
    LanguagesPresenter presenter;

    FilmPreparationComponent filmPreparationComponent;

    @Inject
    LinearLayoutManager linearLayoutManager;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ComponentGetter<FilmPreparationComponent> componentGetter = (ComponentGetter<FilmPreparationComponent>) context;
        filmPreparationComponent = componentGetter.getComponent();
    }

    public static LanguagesFragmentView getNewInstance() {
        LanguagesFragmentView instance = new LanguagesFragmentView();
        Bundle bundle = new Bundle();
        instance.setArguments(bundle);
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(LAYOUT, container, false);
        injectFields(view);
        initRecyclerView();
        presenter.attachView(this);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.detachView();
    }

    private void injectFields(View view) {
        ButterKnife.bind(this,view);
        createComponent().inject(this);
    }

    private void initRecyclerView() {
        languagesAdapter.setLanguages(presenter.getLanguages());
        languagesRecyclerView.setLayoutManager(linearLayoutManager);

        languagesRecyclerView.setAdapter(languagesAdapter);
        languagesRecyclerView.setHasFixedSize(true);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public LanguagesFragmentComponent createComponent() {
        return DaggerLanguagesFragmentComponent.builder()
                .filmPreparationComponent(filmPreparationComponent)
                .languagesModule(new LanguagesModule())
                .build();
    }

    @Override
    public void setLanguages(ArrayList<Language> languages) {

    }
}
