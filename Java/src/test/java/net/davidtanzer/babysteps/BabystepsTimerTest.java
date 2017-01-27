package net.davidtanzer.babysteps;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static net.davidtanzer.babysteps.BabystepsTimer.BACKGROUND_COLOR_NEUTRAL;
import static net.davidtanzer.babysteps.BabystepsTimer.SECONDS_IN_CYCLE;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class BabystepsTimerTest {

    @InjectMocks
    private BabystepsTimer babystepsTimer;

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
        boolean running = false;
        String html = babystepsTimer.getTimerHtml(running);
        assertEquals(html, "00:20");
    }

    @Test
    public void shouldGetHtmlForTheStopState() {
        boolean running = true;
        String stopHtml = "stop html";
        String timeCaption = "timer text";
        String bodyColor = BACKGROUND_COLOR_NEUTRAL;
        long elapsedTimeInMilliseconds = 0L;

        when(remainingTimeCaption.getRemainingTimeCaption(elapsedTimeInMilliseconds, SECONDS_IN_CYCLE)).thenReturn(timeCaption);
        when(htmlCreator.createTimerHtml(timeCaption, bodyColor, running)).thenReturn(stopHtml);

        String html = babystepsTimer.getTimerHtml(running);

        assertEquals(html, stopHtml);
    }

    @Test
    public void shouldGetHtmlForTheOtherTimerStates() {
        boolean running = true;
        String stopHtml = "html for other timer states";
        String timeCaption = "00:44";
        String bodyColor = "a color";
        long elapsedTimeInMilliseconds = 2000L;

        when(clock.getElapsedTime()).thenReturn(elapsedTimeInMilliseconds);
        when(remainingTimeCaption.getRemainingTimeCaption(elapsedTimeInMilliseconds, SECONDS_IN_CYCLE)).thenReturn(timeCaption);
        when(htmlCreator.createTimerHtml(timeCaption, bodyColor, running)).thenReturn(stopHtml);

        babystepsTimer.setBodyBackgroundColor(bodyColor);
        String html = babystepsTimer.getTimerHtml(running);

        assertEquals(html, stopHtml);
    }
}
