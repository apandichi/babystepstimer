package net.davidtanzer.babysteps;

import java.text.DecimalFormat;

public class RemainingTimeCaptionImpl implements RemainingTimeCaption {
    private static DecimalFormat twoDigitsFormat = new DecimalFormat("00");

    @Override
    public String getRemainingTimeCaption(long elapsedTime) {
        long elapsedSeconds = elapsedTime/1000;
        long remainingSeconds = BabystepsTimer.SECONDS_IN_CYCLE - elapsedSeconds;

        long remainingMinutes = remainingSeconds/60;
        return twoDigitsFormat.format(remainingMinutes)+":"+twoDigitsFormat.format(remainingSeconds-remainingMinutes*60);
    }
}
