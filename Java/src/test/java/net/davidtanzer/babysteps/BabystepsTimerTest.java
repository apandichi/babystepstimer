package net.davidtanzer.babysteps;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class BabystepsTimerTest {

    @InjectMocks
    private BabystepsTimer babystepsTimer = new BabystepsTimer(20, "#ffffff");

    @Mock
    private RemainingTimeCaption remainingTimeCaption;

    @Mock
    private HtmlCreator htmlCreator;

    @Mock
    private Clock clock;

    @Before
    public void beforeTest() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldGetTimerHtmlBeforeStartingTheTimer() {
        when(clock.getElapsedTime()).thenReturn(0L);
        when(remainingTimeCaption.getRemainingTimeCaption(0L, 20L)).thenReturn("00:20");
        when(htmlCreator.createTimerHtml("00:20", "#ffffff", false)).thenReturn("00:20 red false");

        String html = babystepsTimer.getTimerHtml(false);

        assertEquals(html, "00:20 red false");
    }

    @Test
    public void shouldGetTimerHtmlWhenTheTimerIsRunning() {
        when(clock.getElapsedTime()).thenReturn(0L);
        when(remainingTimeCaption.getRemainingTimeCaption(0L, 20L)).thenReturn("00:20");
        when(htmlCreator.createTimerHtml("00:20", "#ffffff", true)).thenReturn("00:20 red true");

        String html = babystepsTimer.getTimerHtml(true);

        assertEquals(html, "00:20 red true");
    }

    @Test
    public void shouldGetTimerHtmlWithElapsedTime() {
        when(clock.getElapsedTime()).thenReturn(5000L);
        when(remainingTimeCaption.getRemainingTimeCaption(5000L, 20L)).thenReturn("00:15");
        when(htmlCreator.createTimerHtml("00:15", "#ffffff", true)).thenReturn("00:15 red true");

        String html = babystepsTimer.getTimerHtml(true);

        assertEquals(html, "00:15 red true");
    }
}
