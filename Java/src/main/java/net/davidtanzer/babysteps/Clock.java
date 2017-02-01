package net.davidtanzer.babysteps;

public interface Clock {
    void resetClock();

    long getRemainingSecondsAndResetElapsedTime();

    boolean timerCaptionChanged(String remainingTime, String lastRemainingTime);

    boolean elapsedTimeBetween5And6Seconds();

    String getRemainingTimeCaption();

    void tick();
}
