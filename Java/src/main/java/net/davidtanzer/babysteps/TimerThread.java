package net.davidtanzer.babysteps;

final class TimerThread extends Thread {

    private boolean timerRunning;

    private ClockListener clockListener;

    public TimerThread(ClockListener clockListener) {
        this.clockListener = clockListener;
    }

    public void startTimer() {
        timerRunning = true;
    }

    public void stopTimer() {
        timerRunning = false;
    }

    @Override
    public void run() {
        while (true) {
            if (timerRunning) {
                clockListener.tick();
            }
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
