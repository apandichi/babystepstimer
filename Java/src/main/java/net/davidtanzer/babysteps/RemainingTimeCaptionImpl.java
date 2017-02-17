package net.davidtanzer.babysteps;

import java.util.concurrent.TimeUnit;

public class RemainingTimeCaptionImpl implements RemainingTimeCaption {
    @Override
    public String getRemainingTimeCaption(long elapsedTimeInSeconds, long secondsInCycle) {
        long remainingSecondsTotal = secondsInCycle - elapsedTimeInSeconds;
        return getTimeCaption(remainingSecondsTotal);
    }

    @Override
    public String getTimeCaption(long seconds) {
        long remainingMinutes = TimeUnit.SECONDS.toMinutes(seconds);
        long remainingSeconds = seconds - TimeUnit.MINUTES.toSeconds(remainingMinutes);
        return String.format("%02d:%02d", remainingMinutes, remainingSeconds);
    }
}
