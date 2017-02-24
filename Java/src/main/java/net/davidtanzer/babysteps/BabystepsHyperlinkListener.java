package net.davidtanzer.babysteps;

import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import java.util.HashMap;
import java.util.Map;

class BabystepsHyperlinkListener implements HyperlinkListener {
    private BabystepsTimerUserInterface babystepsTimer;
    Map<String, Runnable> mapHyperlinkToFunction = new HashMap<>();

    public BabystepsHyperlinkListener(BabystepsTimerUserInterface babystepsTimer) {
        this.babystepsTimer = babystepsTimer;
        initializeMapHyperlinkToFunction();
    }

    private void initializeMapHyperlinkToFunction() {
        mapHyperlinkToFunction.put("command://start", () -> babystepsTimer.start());
        mapHyperlinkToFunction.put("command://stop", () -> babystepsTimer.stop());
        mapHyperlinkToFunction.put("command://reset", () -> babystepsTimer.reset());
        mapHyperlinkToFunction.put("command://quit", () -> babystepsTimer.quit());
    }

    @Override
    public void hyperlinkUpdate(final HyperlinkEvent e) {
        if(e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
            String hyperlink = e.getDescription();
            mapHyperlinkToFunction.get(hyperlink).run();
        }
    }
}
