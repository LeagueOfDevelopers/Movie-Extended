package com.lod.movie_extended.data.model;

import android.content.Context;
import android.content.Intent;

import com.lod.movie_extended.service.PlayerNotificationService;
import com.lod.movie_extended.util.Constants;

/**
 * Created by Жамбыл on 3/2/2016.
 */
public class ServiceHelper {

    Context context;

    public ServiceHelper(Context context) {
        this.context = context;
    }

    public void startNotificationService() {
        startServiceWithAction(Constants.ACTION.START_FOREGROUND_ACTION);
    }

    public void stopNotificationService() {
        startServiceWithAction(Constants.ACTION.STOP_FOREGROUND_ACTION);
    }

    private void startServiceWithAction(String toRightAction) {
        Intent serviceIntent = new Intent(context, PlayerNotificationService.class);
        serviceIntent.setAction(toRightAction);
        context.startService(serviceIntent);
    }
}
