package net.davidtanzer.babysteps;

public interface BabystepsTimerClock {
    void resetClock();

    boolean timerCaptionChanged(String remainingTime, String lastRemainingTime);

    String getRemainingTimeCaption();

    void tick();

    String timeCaptionForResettingBackgroundColorToNeutral();
}
