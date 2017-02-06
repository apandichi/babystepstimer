package net.davidtanzer.babysteps;

public interface BabystepsTimerClock {
    void resetClock();

    long getRemainingSecondsAndResetElapsedTime();

    boolean timerCaptionChanged(String remainingTime, String lastRemainingTime);

    boolean elapsedTimeBetween5And6Seconds();

    String getRemainingTimeCaption();

    void tick();
}
