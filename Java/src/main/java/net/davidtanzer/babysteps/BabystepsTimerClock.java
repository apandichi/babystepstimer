package net.davidtanzer.babysteps;

public interface BabystepsTimerClock {
    void resetClock();

    boolean didTimerCaptionChange(String remainingTimeBefore, String lastRemainingAfter);

    String getRemainingTimeCaption();

    void tick();

    String timeCaptionForResettingBackgroundColorToNeutral();
}
