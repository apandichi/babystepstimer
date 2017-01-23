package net.davidtanzer.babysteps;

import java.text.DecimalFormat;

public class RemainingTimeCaptionImpl implements RemainingTimeCaption {
    private static DecimalFormat twoDigitsFormat = new DecimalFormat("00");

    @Override
    public String getRemainingTimeCaption(long elapsedTimeInMilliseconds, long secondsInCycle) {
        long elapsedSeconds = elapsedTimeInMilliseconds/1000;
        long remainingSeconds = secondsInCycle - elapsedSeconds;

        long remainingMinutes = remainingSeconds/60;
        return twoDigitsFormat.format(remainingMinutes)+":"+twoDigitsFormat.format(remainingSeconds-remainingMinutes*60);
    }
}
