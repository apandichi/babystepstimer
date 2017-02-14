package net.davidtanzer.babysteps;

public class BabystepsTimerMain {
    public static void main(final String[] args) throws InterruptedException {
        BabystepsTimer babystepsTimer = new BabystepsTimer(20, BabystepsTimer.BACKGROUND_COLOR_NEUTRAL);
        TimerThread timerThread = new TimerThread(babystepsTimer);
        BabystepsTimerUserInterface babystepsTimerUserInterface = new BabystepsTimerUserInterface(babystepsTimer, timerThread);
        babystepsTimerUserInterface.init();
    }
}
