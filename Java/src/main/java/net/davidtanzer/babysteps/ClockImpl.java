package net.davidtanzer.babysteps;

public class ClockImpl implements Clock {
    private final long secondsInCycle;
    private long elapsedTimeInMilliseconds;
    private long currentCycleStartTime;

    private RemainingTimeCaption remainingTimeCaption = new RemainingTimeCaptionImpl();

    public ClockImpl(long secondsInCycle) {
        this.secondsInCycle = secondsInCycle;
    }

    @Override
    public void resetClock() {
        currentCycleStartTime = System.currentTimeMillis();
        elapsedTimeInMilliseconds = 0;
    }

    private long getElapsedTimeInSeconds() {
        return elapsedTimeInMilliseconds / 1000;
    }

    @Override
    public long getRemainingSecondsAndResetElapsedTime() {
        long elapsedTimeInSeconds = getElapsedTimeInSeconds();
        boolean timerCycleEnded = elapsedTimeInSeconds == secondsInCycle;
        if (timerCycleEnded) {
            resetClock();
        }
        long remainingSeconds = secondsInCycle - getElapsedTimeInSeconds();
        return remainingSeconds;
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
        return remainingTimeCaption.getRemainingTimeCaption(elapsedTimeInMilliseconds, secondsInCycle);
    }

    @Override
    public void tick() {
        getRemainingSecondsAndResetElapsedTime();
        long difference = System.currentTimeMillis() - currentCycleStartTime;
        if (difference > 1000) {
            elapsedTimeInMilliseconds = difference;
        }
    }
}
