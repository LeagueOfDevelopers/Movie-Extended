package com.lod.movie_extended.ui.fragment.subtitles;

import com.google.android.exoplayer.text.Cue;
import com.lod.movie_extended.data.DataManager;
import com.lod.movie_extended.data.model.player.CaptionListener;
import com.lod.movie_extended.data.model.player.Player;
import com.lod.movie_extended.ui.base.BasePresenter;
import com.squareup.otto.Bus;

import java.util.List;

import timber.log.Timber;

/**
 * Created by Жамбыл on 2/18/2016.
 */
public class SubtitlesPresenter extends BasePresenter<SubtitlesMvpView> implements CaptionListener {

    private DataManager dataManager;
    private Bus events;
    private Player player;

    public SubtitlesPresenter(DataManager dataManager, Bus bus, Player player) {
        this.dataManager = dataManager;
        this.events = bus;
        this.player = player;
        setCaptionListener();
    }

    public void onDestroy() {
        removeCaptionListener();
    }

    private void removeCaptionListener() {
        player.removeCaptionListener();
    }

    private void setCaptionListener() {
        Timber.v("setting caption listener");
        player.setCaptionListener(this);
    }

    @Override
    public void onCues(List<Cue> cues) {
        getMvpView().onCues(cues);
    }
}
