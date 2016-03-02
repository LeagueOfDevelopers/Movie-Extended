package com.lod.movie_extended.data.model.player;

import com.google.android.exoplayer.text.Cue;

import java.util.List;

/**
 * Created by Жамбыл on 3/2/2016.
 */
public interface CaptionListener {
    void onCues(List<Cue> cues);
}
