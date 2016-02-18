package com.lod.movie_extended.ui.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.otto.Bus;

/**
 * Created by Жамбыл on 2/18/2016.
 */
public abstract class InjectFragmentBase extends Fragment implements MvpView, Injector{

    protected Object parentComponent;
    protected View view;

    public static <Y extends Fragment> Fragment getNewInstance(Class<Y> clazz) {
        Y instance = (Y) createInstance(clazz);
        Bundle bundle = new Bundle();
        instance.setArguments(bundle);
        return instance;
    }

    private static Fragment createInstance(Class clazz) {
        Fragment instance = null;
        try {
            instance = (Fragment) clazz.newInstance();
        } catch (java.lang.InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return instance;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ComponentGetter componentGetter = (ComponentGetter) context;
        parentComponent =  componentGetter.getComponent();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(getContentView(), container, false);
        inject();
        getPresenter().attachView(this);

        if(getBus() != null) {
            getBus().register(this);
        }

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        getPresenter().detachView();

        if(getBus()!= null) {
            getBus().unregister(this);
        }
    }

    @Override
    public Bus getBus() {
        return null;
    }
}
