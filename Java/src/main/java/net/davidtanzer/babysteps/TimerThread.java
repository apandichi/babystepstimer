package net.davidtanzer.babysteps;

final class TimerThread extends Thread {

    private RemainingTimeCaption remainingTimeCaption = new RemainingTimeCaptionImpl();
    private SoundPlayer soundPlayer = new SoundPlayerImpl();
    private HtmlCreator htmlCreator = new HtmlCreatorImpl();
    private BabystepsTimer babystepsTimer;

    public TimerThread(BabystepsTimer babystepsTimer) {
        this.babystepsTimer = babystepsTimer;
    }

    @Override
    public void run() {
        babystepsTimer.timerRunning(true);
        babystepsTimer.currentCycleStartTime(System.currentTimeMillis());

        while (babystepsTimer.timerRunning()) {
            long elapsedTime = getElapsedTime();

            boolean timerCycleEnded = elapsedTime >= babystepsTimer.SECONDS_IN_CYCLE * 1000 + 980;
            if (timerCycleEnded) {
                babystepsTimer.currentCycleStartTime(System.currentTimeMillis());
                elapsedTime = getElapsedTime();
            }
            if (elapsedTimeBetween5And6Seconds(elapsedTime) && backgroundColorIsNotNeutral()) {
                babystepsTimer.setBodyBackgroundColor(babystepsTimer.BACKGROUND_COLOR_NEUTRAL);
            }

            String remainingTime = remainingTimeCaption.getRemainingTimeCaption(elapsedTime, BabystepsTimer.SECONDS_IN_CYCLE);
            if (timerCaptionChanged(remainingTime)) {
                if (remainingTime.equals("00:10")) {
                    soundPlayer.playSoundInNewThread("pluck.wav");
                } else if (remainingTime.equals("00:00")) {
                    soundPlayer.playSoundInNewThread("theetone.wav");
                    babystepsTimer.setBodyBackgroundColor(babystepsTimer.BACKGROUND_COLOR_FAILED);
                }

                babystepsTimer.updateTimerCaption(remainingTime);

            }
            try {
                sleep(10);
            } catch (InterruptedException e) {
                //We don't really care about this one...
            }
        }
    }

    private boolean timerCaptionChanged(String remainingTime) {
        return !remainingTime.equals(babystepsTimer.lastRemainingTime());
    }

    private boolean backgroundColorIsNotNeutral() {
        return !babystepsTimer.BACKGROUND_COLOR_NEUTRAL.equals(babystepsTimer.getBodyBackgroundColor());
    }

    private boolean elapsedTimeBetween5And6Seconds(long elapsedTime) {
        return 5000 < elapsedTime && elapsedTime < 6000;
    }

    private long getElapsedTime() {
        return System.currentTimeMillis() - babystepsTimer.currentCycleStartTime();
    }
}
