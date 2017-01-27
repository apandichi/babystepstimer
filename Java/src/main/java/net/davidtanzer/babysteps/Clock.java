package net.davidtanzer.babysteps;

public interface Clock {
    void resetClock();

    long getElapsedTime();

    long resetTimerWhenCycleEnded(long secondsInCycle);

    boolean timerCaptionChanged(String remainingTime);

    void updateTimerCaption(String remainingTime);

    boolean elapsedTimeBetween5And6Seconds(long elapsedTime);
}
