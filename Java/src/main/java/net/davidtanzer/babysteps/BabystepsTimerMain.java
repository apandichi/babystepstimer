package net.davidtanzer.babysteps;

import java.util.HashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static net.davidtanzer.babysteps.BabystepsTimerState.FAILED;
import static net.davidtanzer.babysteps.BabystepsTimerState.NEUTRAL;
import static net.davidtanzer.babysteps.BabystepsTimerUserInterface.BACKGROUND_COLOR_FAILED;
import static net.davidtanzer.babysteps.BabystepsTimerUserInterface.BACKGROUND_COLOR_NEUTRAL;

public class BabystepsTimerMain {
    public static void main(final String[] args) throws InterruptedException {
        BabystepsTimerClockTrackingElapsedTimeBasedOnCycleStartTime babystepsTimerClock = new BabystepsTimerClockTrackingElapsedTimeBasedOnCycleStartTime(20, new RemainingTimeCaptionWithMinutesAndSeconds(), new SystemClockFromTheJVM());
        String timeCaptionForResettingBackgroundColorToNeutral = babystepsTimerClock.timeCaptionForResettingBackgroundColorToNeutral();

        HashMap<String, String> soundsToPlayAtTime = configureSoundsToPlayAtTime();
        HashMap<String, BabystepsTimerState> babystepsTimerStateAtTime = configureBabystepsTimerStatesAtTime(timeCaptionForResettingBackgroundColorToNeutral);

        BabystepsTimer babystepsTimer = new BabystepsTimer(babystepsTimerClock, new SoundPlayerWithClip(), soundsToPlayAtTime, babystepsTimerStateAtTime);
        configureTimeNotificationMechanism(babystepsTimer);

        BabystepsTimerUserInterface babystepsTimerUserInterface = new BabystepsTimerUserInterface(babystepsTimer, new HtmlCreatorWithMustacheTemplates(), BACKGROUND_COLOR_NEUTRAL);
        babystepsTimerUserInterface.init();
    }

    private static void configureTimeNotificationMechanism(BabystepsTimer babystepsTimer) {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(babystepsTimer::tickIfListening, 0, 10, TimeUnit.MILLISECONDS);
    }

    private static HashMap<String, String> configureSoundsToPlayAtTime() {
        HashMap<String, String> soundsToPlayAtTime = new HashMap<>();
        soundsToPlayAtTime.put("00:10", "pluck.wav");
        soundsToPlayAtTime.put("00:00", "theetone.wav");
        return soundsToPlayAtTime;
    }

    private static HashMap<String, BabystepsTimerState> configureBabystepsTimerStatesAtTime(String timeCaptionForResettingBackgroundColorToNeutral) {
        HashMap<String, BabystepsTimerState> babystepsTimerStateAtTime = new HashMap<>();
        babystepsTimerStateAtTime.put(timeCaptionForResettingBackgroundColorToNeutral, NEUTRAL);
        babystepsTimerStateAtTime.put("00:00", FAILED);
        return babystepsTimerStateAtTime;
    }
}
