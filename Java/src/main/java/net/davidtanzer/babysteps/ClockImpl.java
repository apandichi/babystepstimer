package net.davidtanzer.babysteps;

public class ClockImpl {
    private long currentCycleStartTime;
    private String lastRemainingTime;

    public void resetClock() {
        currentCycleStartTime(System.currentTimeMillis());
    }

    public long getElapsedTime() {
        return System.currentTimeMillis() - currentCycleStartTime;
    }

    public long resetTimerWhenCycleEnded(long secondsInCycle) {
        long elapsedTime = getElapsedTime();
        boolean timerCycleEnded = elapsedTime >= secondsInCycle * 1000 + 980;
        if (timerCycleEnded) {
            resetClock();
            elapsedTime = getElapsedTime();
        }
        return elapsedTime;
    }

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

    public void updateTimerCaption(String remainingTime) {
        lastRemainingTime(remainingTime);
    }

    public boolean elapsedTimeBetween5And6Seconds(long elapsedTime) {
        return 5000 < elapsedTime && elapsedTime < 6000;
    }
}
