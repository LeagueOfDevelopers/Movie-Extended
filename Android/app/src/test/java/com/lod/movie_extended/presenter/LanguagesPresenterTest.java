package com.lod.movie_extended.presenter;

import com.lod.movie_extended.data.DataManager;
import com.lod.movie_extended.data.model.Language;
import com.lod.movie_extended.data.model.Session;
import com.lod.movie_extended.ui.languages.LanguagesMvpView;
import com.lod.movie_extended.ui.languages.LanguagesPresenter;
import com.lod.movie_extended.util.RxSchedulersOverrideRule;
import com.lod.movie_extended.util.TestDataFactory;
import com.squareup.otto.Bus;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.doReturn;

/**
 * Created by Жамбыл on 2/11/2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class LanguagesPresenterTest   {

    @Mock LanguagesMvpView languagesMvpView;
    @Mock DataManager dataManager;
    @Mock Bus bus;

    private LanguagesPresenter languagesPresenter;

    @Rule
    public final RxSchedulersOverrideRule mOverrideSchedulersRule = new RxSchedulersOverrideRule();

    @Before
    public void setUp() {
        languagesPresenter = new LanguagesPresenter(dataManager, bus);
        languagesPresenter.attachView(languagesMvpView);
    }

    @After
    public void after() {
        languagesPresenter.detachView();
    }

    @Test
    public void assertGetLanguagesIsNotNull() {
        Session session = TestDataFactory.getTestSession();
        doReturn(session)
                .when(dataManager)
                .getSession();

        ArrayList<Language> languages = languagesPresenter.getLanguages();

        assertNotNull(languages);
    }
}
