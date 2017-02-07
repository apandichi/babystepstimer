package net.davidtanzer.babysteps;

public interface BabystepsTimerClock {
    void resetClock();

    boolean timerCaptionChanged(String remainingTime, String lastRemainingTime);

    boolean elapsedTimeBetween5And6Seconds();

    String getRemainingTimeCaption();

    void tick();
}
