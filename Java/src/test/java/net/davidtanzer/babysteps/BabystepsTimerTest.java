package net.davidtanzer.babysteps;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

public class BabystepsTimerTest {

    private final long secondsInCycle = 20L;
    private final String bodyBackgroundColor = BabystepsTimer.BACKGROUND_COLOR_NEUTRAL;

    @InjectMocks
    private BabystepsTimer babystepsTimer = new BabystepsTimer(secondsInCycle, bodyBackgroundColor);

    @Mock
    private RemainingTimeCaption remainingTimeCaption;

    @Mock
    private HtmlCreator htmlCreator;

    @Mock
    private Clock clock;

    @Mock
    private SoundPlayer soundPlayer;

    @Before
    public void beforeTest() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldGetTimerHtmlBeforeStartingTheTimer() {
        long elapsedTimeInMilliseconds = 0L;
        boolean isTimerRunning = false;
        String timerText = "00:20";
        String timerHtml = "00:20 red false";

        when(clock.getElapsedTime()).thenReturn(elapsedTimeInMilliseconds);
        when(remainingTimeCaption.getRemainingTimeCaption(elapsedTimeInMilliseconds, secondsInCycle)).thenReturn(timerText);
        when(htmlCreator.createTimerHtml(timerText, bodyBackgroundColor, isTimerRunning)).thenReturn(timerHtml);

        String html = babystepsTimer.getTimerHtml(isTimerRunning);

        assertEquals(html, timerHtml);
    }

    @Test
    public void shouldGetTimerHtmlWhenTheTimerIsRunning() {
        long elapsedTimeInMilliseconds = 0L;
        boolean isTimerRunning = true;
        String timerText = "00:20";
        String timerHtml = "00:20 red true";

        when(clock.getElapsedTime()).thenReturn(elapsedTimeInMilliseconds);
        when(remainingTimeCaption.getRemainingTimeCaption(elapsedTimeInMilliseconds, secondsInCycle)).thenReturn(timerText);
        when(htmlCreator.createTimerHtml(timerText, bodyBackgroundColor, isTimerRunning)).thenReturn(timerHtml);

        String html = babystepsTimer.getTimerHtml(isTimerRunning);

        assertEquals(html, timerHtml);
    }

    @Test
    public void shouldGetTimerHtmlWithElapsedTime() {
        long elapsedTimeInMilliseconds = 5000L;
        boolean isTimerRunning = true;
        String timerText = "00:15";
        String timerHtml = "00:15 red true";

        when(clock.getElapsedTime()).thenReturn(elapsedTimeInMilliseconds);
        when(remainingTimeCaption.getRemainingTimeCaption(elapsedTimeInMilliseconds, secondsInCycle)).thenReturn(timerText);
        when(htmlCreator.createTimerHtml(timerText, bodyBackgroundColor, isTimerRunning)).thenReturn(timerHtml);

        String html = babystepsTimer.getTimerHtml(isTimerRunning);

        assertEquals(html, timerHtml);
    }

    @Test
    public void shouldResetTimerClockAndBackgroundColor() {
        assertNotEquals(babystepsTimer.getBodyBackgroundColor(), BabystepsTimer.BACKGROUND_COLOR_PASSED);

        babystepsTimer.reset();

        verify(clock).resetClock();
        assertEquals(babystepsTimer.getBodyBackgroundColor(), BabystepsTimer.BACKGROUND_COLOR_PASSED);
    }

    @Test
    public void tickShouldNotResetBackgroundColorOrPlaySound() {
        assertEquals(babystepsTimer.getBodyBackgroundColor(), BabystepsTimer.BACKGROUND_COLOR_NEUTRAL);
        babystepsTimer.tick();
        assertEquals(babystepsTimer.getBodyBackgroundColor(), BabystepsTimer.BACKGROUND_COLOR_NEUTRAL);
        verifyNoMoreInteractions(soundPlayer);
    }
}
