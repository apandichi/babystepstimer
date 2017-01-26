package net.davidtanzer.babysteps;

final class TimerThread extends Thread {

    private boolean timerRunning;

    private BabystepsTimerUserInterface babystepsTimer;

    public TimerThread(BabystepsTimerUserInterface babystepsTimer) {
        this.babystepsTimer = babystepsTimer;
    }

    public void startTimer() {
        timerRunning = true;
        this.start();
    }

    public void stopTimer() {
        timerRunning = false;
    }

    @Override
    public void run() {
        while (timerRunning) {
            babystepsTimer.tick();
            tryToSleep();
        }
    }

    private void tryToSleep() {
        try {
            sleep(10);
        } catch (InterruptedException e) {
            //We don't really care about this one...
        }
    }
}
