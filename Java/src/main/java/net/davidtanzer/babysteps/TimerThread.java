package net.davidtanzer.babysteps;

final class TimerThread extends Thread {

    private ClockListener clockListener;

    public TimerThread(ClockListener clockListener) {
        this.clockListener = clockListener;
    }

    @Override
    public void run() {
        while (true) {
            if (clockListener.isTimerRunning()) {
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
