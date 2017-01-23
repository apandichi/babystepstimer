package net.davidtanzer.babysteps;

import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

class BabystepsHyperlinkListener implements HyperlinkListener {
    private BabystepsTimer babystepsTimer;
    public BabystepsHyperlinkListener(BabystepsTimer babystepsTimer) {
        this.babystepsTimer = babystepsTimer;
    }

    @Override
    public void hyperlinkUpdate(final HyperlinkEvent e) {
        if(e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
            String description = e.getDescription();
            if("command://start".equals(description)) {
                babystepsTimer.start();
            } else if("command://stop".equals(description)) {
                babystepsTimer.stop();
            } else  if("command://reset".equals(description)) {
                babystepsTimer.reset();
            } else  if("command://quit".equals(description)) {
                babystepsTimer.quit();
            }
        }
    }
}
