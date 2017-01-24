package net.davidtanzer.babysteps;

final class TimerThread extends Thread {

    private BabystepsTimer babystepsTimer;

    public TimerThread(BabystepsTimer babystepsTimer) {
        this.babystepsTimer = babystepsTimer;
    }

    @Override
    public void run() {
        while (babystepsTimer.timerRunning()) {
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
