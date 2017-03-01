package net.davidtanzer.babysteps;

import java.util.HashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static net.davidtanzer.babysteps.BabystepsTimerUserInterface.BACKGROUND_COLOR_FAILED;
import static net.davidtanzer.babysteps.BabystepsTimerUserInterface.BACKGROUND_COLOR_NEUTRAL;

public class BabystepsTimerMain {
    public static void main(final String[] args) throws InterruptedException {
        BabystepsTimerClockTrackingElapsedTimeBasedOnCycleStartTime babystepsTimerClock = new BabystepsTimerClockTrackingElapsedTimeBasedOnCycleStartTime(20, new RemainingTimeCaptionWithMinutesAndSeconds(), new SystemClockFromTheJVM());
        String timeCaptionForResettingBackgroundColorToNeutral = babystepsTimerClock.timeCaptionForResettingBackgroundColorToNeutral();

        HashMap<String, String> soundsToPlayAtTime = configureSoundsToPlayAtTime();
        HashMap<String, String> colorsToSetAtTime = configureColorsToSetAtTime(timeCaptionForResettingBackgroundColorToNeutral);

        BabystepsTimer babystepsTimer = new BabystepsTimer(babystepsTimerClock, new SoundPlayerWithClip(), soundsToPlayAtTime);
        configureTimeNotificationMechanism(babystepsTimer);

        BabystepsTimerUserInterface babystepsTimerUserInterface = new BabystepsTimerUserInterface(babystepsTimer, colorsToSetAtTime, new HtmlCreatorWithMustacheTemplates(), BACKGROUND_COLOR_NEUTRAL);
        babystepsTimerUserInterface.init();
    }

    private static void configureTimeNotificationMechanism(BabystepsTimer babystepsTimer) {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(babystepsTimer::tickIfListening, 0, 10, TimeUnit.MILLISECONDS);
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
