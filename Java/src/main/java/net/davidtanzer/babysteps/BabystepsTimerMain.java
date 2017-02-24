package net.davidtanzer.babysteps;

import java.util.HashMap;

import static net.davidtanzer.babysteps.BabystepsTimer.BACKGROUND_COLOR_FAILED;
import static net.davidtanzer.babysteps.BabystepsTimer.BACKGROUND_COLOR_NEUTRAL;

public class BabystepsTimerMain {
    public static void main(final String[] args) throws InterruptedException {
        BabystepsTimerClockImpl babystepsTimerClock = new BabystepsTimerClockImpl(20, new RemainingTimeCaptionImpl(), new SystemClockImpl());
        String timeCaptionForResettingBackgroundColorToNeutral = babystepsTimerClock.timeCaptionForResettingBackgroundColorToNeutral();

        HashMap<String, String> soundsToPlayAtTime = configureSoundsToPlayAtTime();
        HashMap<String, String> colorsToSetAtTime = configureColorsToSetAtTime(timeCaptionForResettingBackgroundColorToNeutral);

        BabystepsTimer babystepsTimer = new BabystepsTimer(BACKGROUND_COLOR_NEUTRAL, babystepsTimerClock, new SoundPlayerImpl(), new HtmlCreatorImpl(), soundsToPlayAtTime, colorsToSetAtTime);
        BabystepsTimerUserInterface babystepsTimerUserInterface = new BabystepsTimerUserInterface(babystepsTimer);
        babystepsTimerUserInterface.init();
    }

    private static HashMap<String, String> configureColorsToSetAtTime(String timeCaptionForResettingBackgroundColorToNeutral) {
        HashMap<String, String> colorsToSetAtTime = new HashMap<>();
        colorsToSetAtTime.put(timeCaptionForResettingBackgroundColorToNeutral, BACKGROUND_COLOR_NEUTRAL);
        colorsToSetAtTime.put("00:00", BACKGROUND_COLOR_FAILED);
        return colorsToSetAtTime;
    }

    private static HashMap<String, String> configureSoundsToPlayAtTime() {
        HashMap<String, String> soundsToPlayAtTime = new HashMap<>();
        soundsToPlayAtTime.put("00:10", "pluck.wav");
        soundsToPlayAtTime.put("00:00", "theetone.wav");
        return soundsToPlayAtTime;
    }
}
