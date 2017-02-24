package net.davidtanzer.babysteps;

import java.util.HashMap;

public class BabystepsTimerMain {
    public static void main(final String[] args) throws InterruptedException {
        HashMap<String, String> soundsToPlayAtTime = configureSoundsToPlayAtTime();
        BabystepsTimer babystepsTimer = new BabystepsTimer(BabystepsTimer.BACKGROUND_COLOR_NEUTRAL, new BabystepsTimerClockImpl(20, new RemainingTimeCaptionImpl(), new SystemClockImpl()), new SoundPlayerImpl(), new HtmlCreatorImpl(), soundsToPlayAtTime);
        BabystepsTimerUserInterface babystepsTimerUserInterface = new BabystepsTimerUserInterface(babystepsTimer);
        babystepsTimerUserInterface.init();
    }

    private static HashMap<String, String> configureSoundsToPlayAtTime() {
        HashMap<String, String> soundsToPlayAtTime = new HashMap<>();
        soundsToPlayAtTime.put("00:10", "pluck.wav");
        soundsToPlayAtTime.put("00:00", "theetone.wav");
        return soundsToPlayAtTime;
    }
}
