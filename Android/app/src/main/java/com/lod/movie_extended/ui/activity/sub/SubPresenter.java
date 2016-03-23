package com.lod.movie_extended.ui.activity.sub;

import com.google.android.exoplayer.text.Cue;
import com.lod.movie_extended.data.DataManager;
import com.lod.movie_extended.data.model.player.CaptionListener;
import com.lod.movie_extended.data.model.player.Player;
import com.lod.movie_extended.data.model.player.PlayerListener;
import com.lod.movie_extended.ui.base.BasePresenter;

import java.util.List;

import timber.log.Timber;

/**
 * Created by Жамбыл on 3/21/2016.
 */
public class SubPresenter extends BasePresenter<SubMvpView> implements CaptionListener {


    private final DataManager dataManager;
    private final Player player;

    public SubPresenter(DataManager dataManager, Player player) {
        this.dataManager = dataManager;
        this.player = player;
    }

    @Override
    public void onCues(List<Cue> cues) {
        Timber.v("onCues");
        getMvpView().setCues(cues);
    }

    public void onCreate() {
        player.setCaptionListener(this);
    }

    public void onDestroy() {
        player.removeCaptionListener();
    }
}
