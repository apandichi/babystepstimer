package net.davidtanzer.babysteps;

import java.util.concurrent.TimeUnit;

public class BabystepsTimerClockImpl implements BabystepsTimerClock {
    private final long secondsInCycle;
    private long elapsedTimeInMilliseconds;
    private long currentCycleStartTime;

    private RemainingTimeCaption remainingTimeCaption;
    private SystemClock systemClock;

    public BabystepsTimerClockImpl(long secondsInCycle, RemainingTimeCaption remainingTimeCaption, SystemClock systemClock) {
        this.secondsInCycle = secondsInCycle;
        this.remainingTimeCaption = remainingTimeCaption;
        this.systemClock = systemClock;
    }

    @Override
    public void resetClock() {
        currentCycleStartTime = systemClock.currentTimeMillis();
        elapsedTimeInMilliseconds = 0;
    }

    @Override
    public boolean timerCaptionChanged(String remainingTime, String lastRemainingTime) {
        return !remainingTime.equals(lastRemainingTime);
    }

    @Override
    public String getRemainingTimeCaption() {
        return remainingTimeCaption.getRemainingTimeCaption(getElapsedTimeInSeconds(), secondsInCycle);
    }

    @Override
    public void tick() {
        resetClockIfTimerCycleEnded();
        changeElapsedTimeIfTimeDifferenceOver1000();
    }

    @Override
    public String timeCaptionForResettingBackgroundColorToNeutral() {
        return remainingTimeCaption.getTimeCaption(secondsInCycle - 5);
    }

    private long getElapsedTimeInSeconds() {
        return TimeUnit.MILLISECONDS.toSeconds(elapsedTimeInMilliseconds);
    }

    private void resetClockIfTimerCycleEnded() {
        long elapsedTimeInSeconds = getElapsedTimeInSeconds();
        boolean timerCycleEnded = elapsedTimeInSeconds == secondsInCycle;
        if (timerCycleEnded) {
            resetClock();
        }
    }

    private void changeElapsedTimeIfTimeDifferenceOver1000() {
        long difference = systemClock.currentTimeMillis() - currentCycleStartTime;
        if (difference > 1000) {
            elapsedTimeInMilliseconds = difference;
        }
    }

}
