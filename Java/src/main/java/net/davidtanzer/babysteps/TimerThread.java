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

        while(babystepsTimer.timerRunning()) {
            long elapsedTime = System.currentTimeMillis() - babystepsTimer.currentCycleStartTime();

            if(elapsedTime >= babystepsTimer.SECONDS_IN_CYCLE*1000+980) {
                babystepsTimer.currentCycleStartTime(System.currentTimeMillis());
                elapsedTime = System.currentTimeMillis() - babystepsTimer.currentCycleStartTime();
            }
            if(elapsedTime >= 5000 && elapsedTime < 6000 && !babystepsTimer.BACKGROUND_COLOR_NEUTRAL.equals(babystepsTimer.bodyBackgroundColor())) {
                babystepsTimer.bodyBackgroundColor(babystepsTimer.BACKGROUND_COLOR_NEUTRAL);
            }

            String remainingTime = remainingTimeCaption.getRemainingTimeCaption(elapsedTime, BabystepsTimer.SECONDS_IN_CYCLE);
            if(!remainingTime.equals(babystepsTimer.lastRemainingTime())) {
                if(remainingTime.equals("00:10")) {
                    soundPlayer.playSound("2166__suburban-grilla__bowl-struck.wav");
                } else if(remainingTime.equals("00:00")) {
                    soundPlayer.playSound("32304__acclivity__shipsbell.wav");
                    babystepsTimer.bodyBackgroundColor(babystepsTimer.BACKGROUND_COLOR_FAILED);
                }

                babystepsTimer.setText(htmlCreator.createTimerHtml(remainingTime, babystepsTimer.bodyBackgroundColor(), true));
                babystepsTimer.repaint();
                babystepsTimer.lastRemainingTime(remainingTime);
            }
            try {
                sleep(10);
            } catch (InterruptedException e) {
                //We don't really care about this one...
            }
        }
    }
}
