package net.davidtanzer.babysteps;

public interface BabystepsTimerClock {
    void resetClock();

    boolean timerCaptionChanged(String remainingTime, String lastRemainingTime);

    boolean elapsedTimeBetween(long secondsToTheLeft, long secondsToTheRight);

    String getRemainingTimeCaption();

    void tick();
}
