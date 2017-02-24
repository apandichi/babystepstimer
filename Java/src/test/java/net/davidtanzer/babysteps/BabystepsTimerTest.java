package net.davidtanzer.babysteps;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

public class BabystepsTimerTest {

    private final String bodyBackgroundColor = BabystepsTimer.BACKGROUND_COLOR_NEUTRAL;

    private HashMap<String, String> soundsToPlayAtTime = new HashMap<>();

    @InjectMocks
    private BabystepsTimer babystepsTimer;

    @Mock
    private HtmlCreator htmlCreator;

    @Mock
    private BabystepsTimerClock babystepsTimerClock;

    @Mock
    private SoundPlayer soundPlayer;

    @Before
    public void beforeTest() {
        MockitoAnnotations.initMocks(this);
        babystepsTimer = new BabystepsTimer(bodyBackgroundColor, babystepsTimerClock, soundPlayer, htmlCreator, soundsToPlayAtTime);
        assertEquals(babystepsTimer.getBodyBackgroundColor(), BabystepsTimer.BACKGROUND_COLOR_NEUTRAL);
        configureSoundsToPlayAtTime();
    }

    private void configureSoundsToPlayAtTime() {
        soundsToPlayAtTime.put("00:10", "pluck.wav");
        soundsToPlayAtTime.put("00:00", "theetone.wav");
    }

    @Test
    public void shouldGetTimerHtmlBeforeStartingTheTimer() {
        boolean isTimerRunning = false;
        String timerText = "00:20";
        String timerHtml = "00:20 red false";

        when(babystepsTimerClock.getRemainingTimeCaption()).thenReturn(timerText);
        when(htmlCreator.createTimerHtml(timerText, bodyBackgroundColor, isTimerRunning)).thenReturn(timerHtml);

        String html = babystepsTimer.getTimerHtml();

        assertEquals(html, timerHtml);
    }

    @Test
    public void shouldGetTimerHtmlWhenTheTimerIsRunning() {
        boolean isTimerRunning = true;
        String timerText = "00:20";
        String timerHtml = "00:20 red true";

        when(babystepsTimerClock.getRemainingTimeCaption()).thenReturn(timerText);
        when(htmlCreator.createTimerHtml(timerText, bodyBackgroundColor, isTimerRunning)).thenReturn(timerHtml);

        babystepsTimer.start();
        String html = babystepsTimer.getTimerHtml();

        assertEquals(html, timerHtml);
    }

    @Test
    public void shouldGetTimerHtmlWithElapsedTime() {
        boolean isTimerRunning = false;
        String timerText = "00:15";
        String timerHtml = "00:15 red true";

        when(babystepsTimerClock.getRemainingTimeCaption()).thenReturn(timerText);
        when(htmlCreator.createTimerHtml(timerText, bodyBackgroundColor, isTimerRunning)).thenReturn(timerHtml);

        String html = babystepsTimer.getTimerHtml();

        assertEquals(html, timerHtml);
    }

    @Test
    public void shouldResetTimerClockAndBackgroundColor() {
        assertNotEquals(babystepsTimer.getBodyBackgroundColor(), BabystepsTimer.BACKGROUND_COLOR_PASSED);

        babystepsTimer.reset();

        verify(babystepsTimerClock).resetClock();
        assertEquals(babystepsTimer.getBodyBackgroundColor(), BabystepsTimer.BACKGROUND_COLOR_PASSED);
    }

    @Test
    public void tickShouldNotResetBackgroundColorOrPlayAnySound() {
        babystepsTimer.tick();
        assertEquals(babystepsTimer.getBodyBackgroundColor(), BabystepsTimer.BACKGROUND_COLOR_NEUTRAL);
        verifyNoMoreInteractions(soundPlayer);
    }

    @Test
    public void tickShouldNotResetBackgroundColorOrPlaySoundWhenBackgroundColorIsNotNeutral() {
        babystepsTimer.setBodyBackgroundColor(BabystepsTimer.BACKGROUND_COLOR_FAILED);
        babystepsTimer.tick();
        assertEquals(babystepsTimer.getBodyBackgroundColor(), BabystepsTimer.BACKGROUND_COLOR_FAILED);
        verifyNoMoreInteractions(soundPlayer);
    }

    @Test
    public void tickShouldUpdateRemainingTimeCaptionButShouldNotResetColorOrPlayAnySound() {
        String remainingTime = "00:19";

        when(babystepsTimerClock.getRemainingTimeCaption()).thenReturn(remainingTime);
        babystepsTimer.tick();

        assertEquals(babystepsTimer.getBodyBackgroundColor(), BabystepsTimer.BACKGROUND_COLOR_NEUTRAL);
        verifyNoMoreInteractions(soundPlayer);
    }

    @Test
    public void tickShouldPlaySoundAndChangeBackgroundColorWhenRemainingTimeIsZero() {
        String remainingTime = "00:00";
        String soundAtTimeZero = "theetone.wav";

        when(babystepsTimerClock.getRemainingTimeCaption()).thenReturn(remainingTime);
        when(babystepsTimerClock.timerCaptionChanged(any(), any())).thenReturn(true);
        babystepsTimer.tick();

        assertEquals(babystepsTimer.getBodyBackgroundColor(), BabystepsTimer.BACKGROUND_COLOR_FAILED);
        verify(soundPlayer).playSoundInNewThread(soundAtTimeZero);
    }

    @Test
    public void tickShouldPlaySoundWhenRemainingTimeIstenSeconds() {
        String remainingTime = "00:10";
        String soundAtTimeZero = "pluck.wav";

        when(babystepsTimerClock.getRemainingTimeCaption()).thenReturn(remainingTime);
        when(babystepsTimerClock.timerCaptionChanged(any(), any())).thenReturn(true);
        babystepsTimer.tick();

        assertEquals(babystepsTimer.getBodyBackgroundColor(), BabystepsTimer.BACKGROUND_COLOR_NEUTRAL);
        verify(soundPlayer).playSoundInNewThread(soundAtTimeZero);
    }

    @Test
    public void clockShouldBeResetWhenTimerIsStarted() {
        babystepsTimer.start();
        verify(babystepsTimerClock).resetClock();
    }

}
