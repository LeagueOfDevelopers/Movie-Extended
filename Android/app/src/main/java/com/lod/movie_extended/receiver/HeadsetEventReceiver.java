package com.lod.movie_extended.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.lod.movie_extended.injection.App;
import com.lod.movie_extended.data.model.player.Player;

import javax.inject.Inject;

import timber.log.Timber;

/**
 * Created by Жамбыл on 2/19/2016.
 */
public class HeadsetEventReceiver extends BroadcastReceiver {

    @Inject
    Player player;

    @Override
    public void onReceive(Context context, Intent intent) {
        Timber.v("onReceive");
        App.getInstance().getComponent().inject(this);
        if (intent.getAction().equals(Intent.ACTION_HEADSET_PLUG)) {
            int state = intent.getIntExtra("state", -1);
            switch (state) {
                case 0:
                    Timber.v("Headset is unplugged");
                    if(player.getPlayWhenReady()) {
                        player.setPlayWhenReady(false);
                    }
                    player.notifyHeadsetNotOn();
                    break;
                case 1:
                    Timber.v("Headset is plugged");
                    player.notifyHeadsetOn();
                    break;
            }
        }
    }
}
