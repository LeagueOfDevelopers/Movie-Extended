package com.lod.movie_extended.presenter;

import com.lod.movie_extended.data.DataManager;
import com.lod.movie_extended.data.model.Session;
import com.lod.movie_extended.ui.activity.filmPreparation.FilmPreparationMvpView;
import com.lod.movie_extended.ui.activity.filmPreparation.FilmPreparationPresenter;
import com.lod.movie_extended.util.TestDataFactory;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import rx.Observable;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

/**
 * Created by Жамбыл on 2/11/2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class FilmPreparationPresenterTest {

    @Mock DataManager dataManager;
    @Mock FilmPreparationMvpView filmPreparationMvpView;
    private FilmPreparationPresenter filmPreparationPresenter;

    Session session;

    @Before
    public void setUp() {
        filmPreparationPresenter = new FilmPreparationPresenter(dataManager,null,null);
        filmPreparationPresenter.attachView(filmPreparationMvpView);

        session = TestDataFactory.getTestSession();

        doReturn(session)
                .when(dataManager)
                .getSession();

    }

    @After
    public void after() {
        filmPreparationPresenter.detachView();
    }

    @Test
    public void loadSessionTest_mustCallSetLanguagesToRecyclerView() {

        verify(filmPreparationMvpView).setLanguagesToRecyclerView();
    }

    @Test
    public void getFilmName_mustReturnString() {
        Assert.assertEquals(filmPreparationPresenter.getFilmName(),session.getFilm().getName());
    }
}
