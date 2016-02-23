package com.lod.movie_extended.ui.fragment.subtitles;

import com.google.android.exoplayer.text.Cue;
import com.lod.movie_extended.ui.activity.main.MainMvpView;

import java.util.List;

/**
 * Created by Жамбыл on 2/18/2016.
 */
public interface SubtitlesMvpView extends MainMvpView{
    void onCues(List<Cue> cues);
}
