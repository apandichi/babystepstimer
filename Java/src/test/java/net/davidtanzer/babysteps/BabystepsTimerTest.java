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

    @Before
    public void beforeTest() {
        MockitoAnnotations.initMocks(this);
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

        String html = babystepsTimer.getHtmlForTheStopState(running);

        assertEquals(html, stopHtml);
    }
}
