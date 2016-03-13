package com.lod.movie_extended.data.model.player;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import timber.log.Timber;

/**
 * Created by Жамбыл on 2/23/2016.
 */
public class PlayerLogger {

    private Player player;
    private final ScheduledExecutorService loggingThread;

    public PlayerLogger() {
        loggingThread = Executors.newSingleThreadScheduledExecutor();
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void startLogging() {
        Timber.v("start Logging");
        int sleepTimeInSeconds = 2;
        loggingThread.scheduleAtFixedRate(new LoggingRunnable(player),0, sleepTimeInSeconds, TimeUnit.SECONDS);
    }

    public void stopLogging() {
        Timber.v("stop Logging");
        loggingThread.shutdown();
    }

    private class LoggingRunnable implements Runnable {
        Player player;

        public LoggingRunnable(Player player) {
            this.player = player;
        }

        @Override
        public void run() {
                Timber.v("player playWhenReady " + player.getPlayWhenReady());
                Timber.v("playerCurrentPosition " + player.getCurrentPosition());
                Timber.v("player buffered percentage" + player.getBufferedPercentage());
                Timber.v("player buffered position" + player.getBufferedPosition());
                Timber.v("player's duration " + player.getDuration());
                Timber.v("subtitles enabled " + player.isSubtitlesEnabled());

                if(checkIfNeedStopLogging()) {
                    startLogging();
                }
        }

        private boolean checkIfNeedStopLogging() {
            return player.getDuration() == player.getCurrentPosition();
        }
    }
}
