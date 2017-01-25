package net.davidtanzer.babysteps;

/**
 * Created by alin on 25.01.2017.
 */
final class TimerThread extends Thread {

    private long SECONDS_IN_CYCLE;


    private boolean timerRunning;
    private long currentCycleStartTime;
    private String lastRemainingTime;
    private final SoundPlayer soundPlayer;
    private final BabystepsTimer ui;

    public TimerThread(SoundPlayer soundPlayer, BabystepsTimer ui, long seconds) {
        this.soundPlayer = soundPlayer;
        this.ui = ui;
        this.SECONDS_IN_CYCLE = seconds;
    }

    public long getSecondsInCycle() {
        return SECONDS_IN_CYCLE;
    }

    @Override
    public void run() {
        timerRunning = true;
        currentCycleStartTime = System.currentTimeMillis();

        while (timerRunning) {
            long elapsedTime = System.currentTimeMillis() - currentCycleStartTime;

            doStuff(elapsedTime);
            try {
                sleep(10);
            } catch (InterruptedException e) {
                //We don't really care about this one...
            }
        }
    }

    private void doStuff(long elapsedTime) {
        if (elapsedTime >= SECONDS_IN_CYCLE * 1000 + 980) {
            currentCycleStartTime = System.currentTimeMillis();
            elapsedTime = System.currentTimeMillis() - currentCycleStartTime;
        }
        if (elapsedTime >= 5000 && elapsedTime < 6000 &&
                !BabystepsTimer.BACKGROUND_COLOR_NEUTRAL.equals(ui.getBodyBackgroundColor())) {
            ui.setNeutralBackgroundColor();
        }

        String remainingTime = ui.getRemainingTimeCaption(elapsedTime);
        if (!remainingTime.equals(lastRemainingTime)) {
            if (remainingTime.equals("00:10")) {
                soundPlayer.invoke("2166__suburban-grilla__bowl-struck.wav");
            } else if (remainingTime.equals("00:00")) {
                soundPlayer.invoke("32304__acclivity__shipsbell.wav");
                ui.setFailedBackgroundColor();
            }

            ui.setRemainingTime(remainingTime);

            lastRemainingTime = remainingTime;
        }
    }

    public void resetTimer() {
        currentCycleStartTime = System.currentTimeMillis();
    }

    public void stopTimer() {
        timerRunning = false;
    }

    public void startTimer() {
        start();
    }

}
