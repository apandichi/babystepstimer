package net.davidtanzer.babysteps;

public class BabystepsTimerClockImpl implements BabystepsTimerClock {
    private final long secondsInCycle;
    private long elapsedTimeInMilliseconds;
    private long currentCycleStartTime;

    private RemainingTimeCaption remainingTimeCaption = new RemainingTimeCaptionImpl();
    private SystemClock systemClock = new SystemClockImpl();

    public BabystepsTimerClockImpl(long secondsInCycle) {
        this.secondsInCycle = secondsInCycle;
    }

    @Override
    public void resetClock() {
        currentCycleStartTime = systemClock.currentTimeMillis();
        elapsedTimeInMilliseconds = 0;
    }

    private long getElapsedTimeInSeconds() {
        return elapsedTimeInMilliseconds / 1000;
    }

    private void resetClockIfTimerCycleEnded() {
        long elapsedTimeInSeconds = getElapsedTimeInSeconds();
        boolean timerCycleEnded = elapsedTimeInSeconds == secondsInCycle;
        if (timerCycleEnded) {
            resetClock();
        }
    }

    @Override
    public boolean timerCaptionChanged(String remainingTime, String lastRemainingTime) {
        return !remainingTime.equals(lastRemainingTime);
    }

    @Override
    public boolean elapsedTimeBetween5And6Seconds() {
        return 5000 < elapsedTimeInMilliseconds && elapsedTimeInMilliseconds < 6000;
    }

    @Override
    public String getRemainingTimeCaption() {
        long elapsedTimeInSeconds = elapsedTimeInMilliseconds / 1000;
        return remainingTimeCaption.getRemainingTimeCaption(elapsedTimeInSeconds, secondsInCycle);
    }

    @Override
    public void tick() {
        resetClockIfTimerCycleEnded();
        long difference = systemClock.currentTimeMillis() - currentCycleStartTime;
        if (difference > 1000) {
            elapsedTimeInMilliseconds = difference;
        }
    }

}
