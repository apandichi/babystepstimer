package net.davidtanzer.babysteps;

public interface BabystepsTimerClock {
    void resetClock();

    boolean didTimerCaptionChange(String remainingTime, String lastRemainingTime);

    String getRemainingTimeCaption();

    void tick();

    String timeCaptionForResettingBackgroundColorToNeutral();
}
