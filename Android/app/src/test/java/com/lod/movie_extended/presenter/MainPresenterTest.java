package com.lod.movie_extended.presenter;

import com.lod.movie_extended.data.DataManager;
import com.lod.movie_extended.ui.main.MainMvpView;
import com.lod.movie_extended.ui.main.MainPresenter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertNotNull;

/**
 * Created by Жамбыл on 2/11/2016.
 */

@RunWith(MockitoJUnitRunner.class)
public class MainPresenterTest {

    @Mock MainMvpView mainMvpView;
    @Mock DataManager dataManager;
    private MainPresenter mainPresenter;

    @Before
    public void setUp() {
        mainPresenter = new MainPresenter(dataManager);
        mainPresenter.attachView(mainMvpView);
    }

    @After
    public void after() {
        mainPresenter.detachView();
    }
    @Test
    public void assertDataManagerIsNotNull() {
        assertNotNull(dataManager);
    }
}
