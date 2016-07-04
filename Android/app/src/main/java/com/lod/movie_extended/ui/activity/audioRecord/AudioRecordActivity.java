package com.lod.movie_extended.ui.activity.audioRecord;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.lod.movie_extended.injection.App;
import com.lod.movie_extended.injection.component.activity.AudioRecordComponent;
import com.lod.movie_extended.injection.component.activity.DaggerAudioRecordComponent;
import com.lod.movie_extended.injection.module.activity.AudioRecordModule;
import com.lod.movie_extended.ui.base.BaseActivity;
import com.lod.movie_extended.ui.base.Presenter;

import java.io.IOException;

import javax.inject.Inject;

/**
 * Created by Максим on 04.07.2016.
 */
public class AudioRecordActivity extends BaseActivity<AudioRecordComponent> implements AudioRecordView {
    @Inject
    AudioRecordPresenter presenter;

    private static final String LOG_TAG = "AudioRecord";
    private static String mFileName = null;

    private RecordButton mRecordButton = null;
    private MediaRecorder mRecorder = null;

    private PlayButton   mPlayButton = null;
    private MediaPlayer mPlayer = null;
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        LinearLayout ll = new LinearLayout(this);
        mRecordButton = new RecordButton(this);
        ll.addView(mRecordButton,
                new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        0));
        mPlayButton = new PlayButton(this);
        ll.addView(mPlayButton,
                new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        0));
        setContentView(ll);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mRecorder != null) {
            mRecorder.release();
            mRecorder = null;
        }

        if (mPlayer != null) {
            mPlayer.release();
            mPlayer = null;
        }
    }


    public class PlayButton extends Button {
        boolean mStartPlaying = true;

        View.OnClickListener clicker = new View.OnClickListener() {
            public void onClick(View v) {
                onPlay(mStartPlaying);
                if (mStartPlaying) {
                    setText("Stop playing");
                } else {
                    setText("Start playing");
                }
                mStartPlaying = !mStartPlaying;
            }
        };

        public PlayButton(Context ctx) {
            super(ctx);
            setText("Start playing");
            setOnClickListener(clicker);
        }
        private void onPlay(boolean start) {
            if (start) {
                startPlaying();
            } else {
                stopPlaying();
            }
        }
    }

    public class RecordButton extends Button {
        boolean mStartRecording = true;

        OnClickListener clicker = new OnClickListener() {
            public void onClick(View v) {
                onRecord(mStartRecording);
                if (mStartRecording) {
                    setText("Stop recording");
                } else {
                    setText("Start recording");
                }
                mStartRecording = !mStartRecording;
            }
        };

        public RecordButton(Context ctx) {
            super(ctx);
            setText("Start recording");
            setOnClickListener(clicker);
        }
        public void onRecord(boolean start) {
            if (start) {
                startRecording();
            } else {
                stopRecording();
            }
        }
    }

    public void startPlaying() {
        mPlayer = new MediaPlayer();
        try {
            mPlayer.setDataSource(mFileName);
            mPlayer.prepare();
            mPlayer.start();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }
    }

    public void stopPlaying() {
        mPlayer.release();
        mPlayer = null;
    }

    public void startRecording() {
        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mRecorder.setOutputFile(mFileName);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            mRecorder.prepare();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }

        mRecorder.start();
    }

    public void stopRecording() {
        mRecorder.stop();
        mRecorder.release();
        mRecorder = null;
    }



    public AudioRecordActivity() {
        mFileName = Environment.getExternalStorageDirectory().getAbsolutePath();
        mFileName += "/audiorecordtest.3gp";
    }
    @Override
    protected AudioRecordComponent createComponent() {
        return DaggerAudioRecordComponent.builder()
                .applicationComponent(App.getInstance().getComponent())
                .audioRecordModule(new AudioRecordModule(this))
                .build();
    }

    @Override
    public int getContentView() {
        return 0;
    }

    @Override
    public void inject() {
     //  getComponent().inject(this);
    }

    @Override
    public Presenter getPresenter() {
        return presenter;
    }
}
