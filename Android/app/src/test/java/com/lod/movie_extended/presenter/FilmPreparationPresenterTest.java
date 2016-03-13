package com.lod.movie_extended.presenter;

import com.lod.movie_extended.data.DataManager;
import com.lod.movie_extended.data.model.ServiceHelper;
import com.lod.movie_extended.data.model.Session;
import com.lod.movie_extended.data.model.player.Player;
import com.lod.movie_extended.ui.activity.filmPreparation.FilmPreparationMvpView;
import com.lod.movie_extended.ui.activity.filmPreparation.FilmPreparationPresenter;
import com.lod.movie_extended.util.RxSchedulersOverrideRule;
import com.lod.movie_extended.util.TestDataFactory;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import rx.Observable;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Жамбыл on 2/11/2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class FilmPreparationPresenterTest {

    @Mock DataManager dataManager;
    @Mock FilmPreparationMvpView filmPreparationMvpView;
    private FilmPreparationPresenter filmPreparationPresenter;
    private Player player;
    private Session session;
    private ServiceHelper serviceHelper;

    @Rule
    public final RxSchedulersOverrideRule mOverrideSchedulersRule = new RxSchedulersOverrideRule();

    @Before
    public void setUp() {
        player = mock(Player.class);
        serviceHelper = mock(ServiceHelper.class);
        filmPreparationPresenter = new FilmPreparationPresenter(dataManager,player,serviceHelper);
        filmPreparationPresenter.attachView(filmPreparationMvpView);

        session = TestDataFactory.getTestSession();
        when(dataManager.getSession()).thenReturn(Observable.just(session));
    }

    @After
    public void after() {
        filmPreparationPresenter.detachView();
    }

    @Test
    public void loadSessionTest_mustCallSetLanguagesToRecyclerView() {
        filmPreparationPresenter.loadSession();
        verify(filmPreparationMvpView).setLanguagesToRecyclerView();
    }

    @Test
    public void getFilmName_mustReturnString() {
        filmPreparationPresenter.loadSession();
        Assert.assertEquals(filmPreparationPresenter.getFilmName(),session.getFilm().getName());
    }
}
