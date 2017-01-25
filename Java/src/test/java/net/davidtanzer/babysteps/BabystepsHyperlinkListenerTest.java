package net.davidtanzer.babysteps;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.swing.event.HyperlinkEvent;
import java.net.MalformedURLException;
import java.net.URL;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

public class BabystepsHyperlinkListenerTest {

    private URL url;

    @Mock
    BabystepsTimerUserInterface babystepsTimer;

    private BabystepsHyperlinkListener listener;

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
        listener = new BabystepsHyperlinkListener(babystepsTimer);
        try {
            url = new URL("http://start.io");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void eventTypeEnteredShouldNeverInteractWithBabystepsTimer() throws MalformedURLException {
        listener.hyperlinkUpdate(new HyperlinkEvent(this, HyperlinkEvent.EventType.ENTERED, url, "command://start"));
        verifyZeroInteractions(babystepsTimer);
    }

    @Test
    public void eventTypeExitedShouldNeverInteractWithBabystepsTimer() throws MalformedURLException {
        listener.hyperlinkUpdate(new HyperlinkEvent(this, HyperlinkEvent.EventType.EXITED, url, "command://start"));
        verifyZeroInteractions(babystepsTimer);
    }

    @Test
    public void shouldStartBabystepsTimer() throws MalformedURLException {
        listener.hyperlinkUpdate(new HyperlinkEvent(this, HyperlinkEvent.EventType.ACTIVATED, url, "command://start"));
        verify(babystepsTimer).start();
    }

    @Test
    public void shouldStopBabystepsTimer() throws MalformedURLException {
        listener.hyperlinkUpdate(new HyperlinkEvent(this, HyperlinkEvent.EventType.ACTIVATED, url, "command://stop"));
        verify(babystepsTimer).stop();
    }

    @Test
    public void shouldResetBabystepsTimer() throws MalformedURLException {
        listener.hyperlinkUpdate(new HyperlinkEvent(this, HyperlinkEvent.EventType.ACTIVATED, url, "command://reset"));
        verify(babystepsTimer).reset();
    }

    @Test
    public void shouldQuitBabystepsTimer() throws MalformedURLException {
        listener.hyperlinkUpdate(new HyperlinkEvent(this, HyperlinkEvent.EventType.ACTIVATED, url, "command://quit"));
        verify(babystepsTimer).quit();
    }
}
