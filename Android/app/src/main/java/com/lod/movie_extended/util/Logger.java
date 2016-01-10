package com.lod.movie_extended.util;

import android.util.Log;

import timber.log.Timber;

/**
 * Created by Жамбыл on 10.01.2016.
 */
public class Logger extends Timber.DebugTree {

    private static final int MAX_LOG_LENGTH = 4000;
    private static final String AppTag = "AppTag ";

    @Override
    protected void log(int priority, String tag, String message, Throwable t) {
        String finalTag = AppTag + tag;

        if (message.length() < MAX_LOG_LENGTH) {
            if (priority == Log.ASSERT) {
                Log.wtf(finalTag, message);
            } else {
                Log.println(priority, finalTag, message);
            }
            return;
        }
    }
}
