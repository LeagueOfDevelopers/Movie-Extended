package com.lod.movie_extended.util;

import com.lod.movie_extended.data.model.Player;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import timber.log.Timber;

/**
 * Created by Жамбыл on 2/23/2016.
 */
public class PlayerLogger {

    Player player;
    ExecutorService loggingThread;
    Future currentTask;

    public PlayerLogger(Player player) {
        Timber.v("constructor");
        this.player = player;
        loggingThread = Executors.newSingleThreadScheduledExecutor();
    }

    public void startLogging() {
        Timber.v("start Logging");
        currentTask = loggingThread.submit(new LoggingRunnable(player));
    }

    public void stopLogging() {
        Timber.v("stop Logging");
        currentTask.cancel(true);
//        loggingThread.shutdownNow();
    }

    private class LoggingRunnable implements Runnable {
        int sleepTime = 2000;
        Player player;

        public LoggingRunnable(Player player) {
            this.player = player;
        }

        @Override
        public void run() {
            while(!Thread.interrupted()) {
                Timber.v("player playWhenReady " + player.getPlayWhenReady());
                Timber.v("playerCurrentPosition " + player.getCurrentPosition());
                Timber.v("player buffered percentage" + player.getBufferedPercentage());
                Timber.v("player buffered position" + player.getBufferedPosition());
                Timber.v("player's duration " + player.getDuration());

                sleep();
            }
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
