package com.lod.movie_extended.ui.activity.sub;

import com.google.android.exoplayer.text.Cue;
import com.lod.movie_extended.ui.base.MvpView;

import java.util.List;

/**
 * Created by Жамбыл on 3/21/2016.
 */
public interface SubMvpView extends MvpView {
    void setCues(List<Cue> cues);
}
