package net.davidtanzer.babysteps;

public class ClockImpl implements Clock {
    private long currentCycleStartTime;
    private String lastRemainingTime;

    @Override
    public void resetClock() {
        currentCycleStartTime(System.currentTimeMillis());
    }

    @Override
    public long getElapsedTime() {
        return System.currentTimeMillis() - currentCycleStartTime;
    }

    @Override
    public long resetTimerWhenCycleEnded(long secondsInCycle) {
        long elapsedTime = getElapsedTime();
        boolean timerCycleEnded = elapsedTime >= secondsInCycle * 1000 + 980;
        if (timerCycleEnded) {
            resetClock();
            elapsedTime = getElapsedTime();
        }
        return elapsedTime;
    }

    @Override
    public boolean timerCaptionChanged(String remainingTime) {
        return !remainingTime.equals(lastRemainingTime());
    }

    private void currentCycleStartTime(long startTime) {
        currentCycleStartTime = startTime;
    }

    private String lastRemainingTime() {
        return lastRemainingTime;
    }

    private void lastRemainingTime(String remainingTime) {
        lastRemainingTime = remainingTime;
    }

    @Override
    public void updateTimerCaption(String remainingTime) {
        lastRemainingTime(remainingTime);
    }

    @Override
    public boolean elapsedTimeBetween5And6Seconds(long elapsedTime) {
        return 5000 < elapsedTime && elapsedTime < 6000;
    }
}
