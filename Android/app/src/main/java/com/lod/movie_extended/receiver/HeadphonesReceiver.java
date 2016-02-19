package com.lod.movie_extended.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;

import com.lod.movie_extended.App;
import com.lod.movie_extended.data.model.Player;

import javax.inject.Inject;

import timber.log.Timber;

/**
 * Created by Жамбыл on 2/19/2016.
 */
public class HeadphonesReceiver extends BroadcastReceiver {

    @Inject
    Player player;

    public HeadphonesReceiver(Context context) {
//        App.get(context).getComponent().inject(this);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_HEADSET_PLUG)) {
            int state = intent.getIntExtra("state", -1);
            switch (state) {
                case 0:
                    Timber.v("Headset is unplugged");
                    player.setPlayWhenReady(false);
                    break;
                case 1:
                    Timber.v("Headset is plugged");
                    break;
                default:
                    Timber.v("I have no idea what the headset state is");
            }
        }
    }
}
