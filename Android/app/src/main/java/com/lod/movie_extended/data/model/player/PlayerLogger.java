package com.lod.movie_extended.data.model.player;

import com.lod.movie_extended.data.model.player.Player;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import timber.log.Timber;

/**
 * Created by Жамбыл on 2/23/2016.
 */
public class PlayerLogger {

    Player player;
    ExecutorService loggingThread;
    boolean doLogging;

    public PlayerLogger() {
        loggingThread = Executors.newSingleThreadScheduledExecutor();
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void startLogging() {
        Timber.v("start Logging");
        doLogging = true;
        loggingThread.submit(new LoggingRunnable(player));

    }

    public void stopLogging() {
        Timber.v("stop Logging");
        doLogging = false;
    }

    private class LoggingRunnable implements Runnable {
        int sleepTime = 2000;
        Player player;

        public LoggingRunnable(Player player) {
            this.player = player;
        }

        @Override
        public void run() {
            while(doLogging) {
                Timber.v("player playWhenReady " + player.getPlayWhenReady());
                Timber.v("playerCurrentPosition " + player.getCurrentPosition());
                Timber.v("player buffered percentage" + player.getBufferedPercentage());
                Timber.v("player buffered position" + player.getBufferedPosition());
                Timber.v("player's duration " + player.getDuration());
                Timber.v("subtitles enabled " + player.isSubtitlesEnabled());

                if(checkIfNeedStopLogging()) {
                    break;
                }

                sleep();
            }
        }

        private boolean checkIfNeedStopLogging() {
            return player.getDuration() == player.getCurrentPosition();
        }

        private void sleep() {
            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
