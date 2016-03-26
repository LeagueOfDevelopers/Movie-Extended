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
    private boolean isWorking;

    public PlayerLogger() {
        loggingThread = Executors.newSingleThreadScheduledExecutor();
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public boolean isWorking() {
        return isWorking;
    }

    public void startLogging() {
        Timber.v("start Logging");
        isWorking = true;
        int sleepTimeInSeconds = 2;
        loggingThread.scheduleAtFixedRate(new LoggingRunnable(player),0, sleepTimeInSeconds, TimeUnit.SECONDS);
    }

    public void stopLogging() {
        Timber.v("stop Logging");
        isWorking = false;
        loggingThread.shutdown();
    }

    private class LoggingRunnable implements Runnable {
        Player player;
        boolean lastPlayWhenReady;
        long lastCurrentPosition;
        int lastBufferedPercentage;
        long lastBufferedPosition;
        long lastDuration;
        boolean lastIsSubtitlesEnabled;
        boolean newData;

        public LoggingRunnable(Player player) {
            this.player = player;
        }

        @Override
        public void run() {
            checkIfHasNewData();
            if(newData) {
                log();
                newData = false;
            }
            setNewData();
            if(checkIfNeedStopLogging()) {
                stopLogging();
            }
        }

        private void checkIfHasNewData() {
            if(lastPlayWhenReady != player.getPlayWhenReady() ||
            lastCurrentPosition != player.getCurrentPosition() ||
            lastBufferedPercentage != player.getBufferedPercentage() ||
            lastBufferedPosition != player.getBufferedPosition() ||
            lastDuration != player.getDuration() ||
            lastIsSubtitlesEnabled != player.isSubtitlesEnabled()){
                newData = true;
            }
        }

        private void setNewData() {
            lastPlayWhenReady = player.getPlayWhenReady();
            lastCurrentPosition = player.getCurrentPosition();
            lastBufferedPercentage = player.getBufferedPercentage();
            lastBufferedPosition = player.getBufferedPosition();
            lastDuration = player.getDuration();
            lastIsSubtitlesEnabled = player.isSubtitlesEnabled();
        }

        private void log() {
            Timber.v("player playWhenReady " + player.getPlayWhenReady());
            Timber.v("playerCurrentPosition " + player.getCurrentPosition());
            Timber.v("player buffered percentage" + player.getBufferedPercentage());
            Timber.v("player buffered position" + player.getBufferedPosition());
            Timber.v("player's duration " + player.getDuration());
            Timber.v("subtitles enabled " + player.isSubtitlesEnabled());
        }

        private boolean checkIfNeedStopLogging() {
            return player.getDuration()!= -1 && player.getDuration() <= player.getCurrentPosition();
        }
    }
}
