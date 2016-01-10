package com.lod.movie_extended.ui.remainigTime;

import com.lod.movie_extended.data.DataManager;
import com.lod.movie_extended.data.model.Session;
import com.lod.movie_extended.ui.base.BasePresenter;

/**
 * Created by Жамбыл on 09.01.2016.
 */
public class RemainingTimePresenter extends BasePresenter<RemainingTimeMvp> {

    private DataManager dataManager;
    private Session currentSession;

    public RemainingTimePresenter(DataManager dataManager) {
        this.dataManager = dataManager;
        currentSession = dataManager.getSession();
    }

    public long getRemainingTime() {
        return currentSession.getRemainingTimeSeconds();
    }
}
