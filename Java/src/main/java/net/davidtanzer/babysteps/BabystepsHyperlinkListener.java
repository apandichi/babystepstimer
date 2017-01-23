package net.davidtanzer.babysteps;

import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

class BabystepsHyperlinkListener implements HyperlinkListener {
    private BabystepsTimer babystepsTimer;
    private HtmlCreator htmlCreator = new HtmlCreatorImpl();
    private RemainingTimeCaption remainingTimeCaption = new RemainingTimeCaptionImpl();

    public BabystepsHyperlinkListener(BabystepsTimer babystepsTimer) {
        this.babystepsTimer = babystepsTimer;
    }

    @Override
    public void hyperlinkUpdate(final HyperlinkEvent e) {
        if(e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
            if("command://start".equals(e.getDescription())) {
                babystepsTimer.setAlwaysOnTop(true);
                babystepsTimer.setText(htmlCreator.createTimerHtml(remainingTimeCaption.getRemainingTimeCaption(0L), BabystepsTimer.BACKGROUND_COLOR_NEUTRAL, true));
                babystepsTimer.repaint();
                new TimerThread(babystepsTimer).start();
            } else if("command://stop".equals(e.getDescription())) {
                babystepsTimer.timerRunning(false);
                babystepsTimer.setAlwaysOnTop(false);
                babystepsTimer.setText(htmlCreator.createTimerHtml(remainingTimeCaption.getRemainingTimeCaption(0L), BabystepsTimer.BACKGROUND_COLOR_NEUTRAL, false));
                babystepsTimer.repaint();
            } else  if("command://reset".equals(e.getDescription())) {
                babystepsTimer.currentCycleStartTime(System.currentTimeMillis());
                babystepsTimer.bodyBackgroundColor(BabystepsTimer.BACKGROUND_COLOR_PASSED);
            } else  if("command://quit".equals(e.getDescription())) {
                System.exit(0);
            }
        }
    }
}
