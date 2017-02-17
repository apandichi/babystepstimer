package net.davidtanzer.babysteps;

import java.util.concurrent.TimeUnit;

public class RemainingTimeCaptionImpl implements RemainingTimeCaption {
    @Override
    public String getRemainingTimeCaption(long elapsedTimeInSeconds, long secondsInCycle) {
        long remainingSecondsTotal = secondsInCycle - elapsedTimeInSeconds;
        long remainingMinutes = TimeUnit.SECONDS.toMinutes(remainingSecondsTotal);
        long remainingSeconds = remainingSecondsTotal - TimeUnit.MINUTES.toSeconds(remainingMinutes);
        return String.format("%02d:%02d", remainingMinutes, remainingSeconds);
    }
}
