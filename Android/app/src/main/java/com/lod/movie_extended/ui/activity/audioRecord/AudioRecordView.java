package com.lod.movie_extended.ui.activity.audioRecord;

import com.lod.movie_extended.ui.base.MvpView;

/**
 * Created by Максим on 04.07.2016.
 */
public interface AudioRecordView extends MvpView {

    void startPlaying();
    void stopPlaying();
    void startRecording();
    void stopRecording();

}
