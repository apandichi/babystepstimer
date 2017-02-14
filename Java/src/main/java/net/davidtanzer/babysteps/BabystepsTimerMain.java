package net.davidtanzer.babysteps;

public class BabystepsTimerMain {
    public static void main(final String[] args) throws InterruptedException {
        BabystepsTimer babystepsTimer = new BabystepsTimer(BabystepsTimer.BACKGROUND_COLOR_NEUTRAL, new BabystepsTimerClockImpl(20, new RemainingTimeCaptionImpl(), new SystemClockImpl()), new SoundPlayerImpl(), new HtmlCreatorImpl());
        BabystepsTimerUserInterface babystepsTimerUserInterface = new BabystepsTimerUserInterface(babystepsTimer);
        babystepsTimerUserInterface.init();
        TimerThread timerThread = new TimerThread(babystepsTimer);
        timerThread.start();
    }
}
