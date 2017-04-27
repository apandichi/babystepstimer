package net.davidtanzer.babysteps;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class BabystepsTimerClockTest {

    private final long secondsInCycle = 20L;

    @InjectMocks
    private BabystepsTimerClock babystepsTimerClock = new BabystepsTimerClockTrackingElapsedTimeBasedOnCycleStartTime(secondsInCycle, new RemainingTimeCaptionWithMinutesAndSeconds(), new SystemClockFromTheJVM());

    @Mock
    private SystemClock systemClock;

    @Mock
    private RemainingTimeCaption remainingTimeCaption;

    @Before
    public void beforeTest() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testTimerCaptionDidChange() {
        String remainingTimeBefore = "00:20";
        String remainingTimeAfter = "00:19";
        boolean timerCaptionChanged = babystepsTimerClock.didTimerCaptionChange(remainingTimeBefore, remainingTimeAfter);
        assertTrue(timerCaptionChanged);
    }

    @Test
    public void testTimerCaptionDidNotChange() {
        String remainingTimeBefore = "00:20";
        String remainingTimeAfter = "00:20";
        boolean timerCaptionChanged = babystepsTimerClock.didTimerCaptionChange(remainingTimeBefore, remainingTimeAfter);
        assertFalse(timerCaptionChanged);
    }

    @Test
    public void testRemainingTimeCaption() {
        long elapsedTimeInSeconds = 10L;
        String expectedCaption = "00:10";
        setupElapsedTimeInMilliseconds(10000L);
        when(remainingTimeCaption.getRemainingTimeCaption(elapsedTimeInSeconds, secondsInCycle)).thenReturn(expectedCaption);

        String caption = babystepsTimerClock.getRemainingTimeCaption();

        assertEquals(expectedCaption, caption);
    }

    @Test
    public void verifyInteractionForGetRemainingTimeCaption() {
        setupElapsedTimeInMilliseconds(10000L);
        babystepsTimerClock.getRemainingTimeCaption();
        verify(remainingTimeCaption).getRemainingTimeCaption(10L, secondsInCycle);
    }

    @Test
    public void testClockIsReset() {
        setupElapsedTimeInMilliseconds(10000L);
        when(remainingTimeCaption.getRemainingTimeCaption(0L, secondsInCycle)).thenReturn("00:00");

        babystepsTimerClock.resetClock();
        String caption = babystepsTimerClock.getRemainingTimeCaption();

        assertEquals("00:00", caption);
    }

    @Test
    public void tickShouldNotChangeCaption() {
        when(remainingTimeCaption.getRemainingTimeCaption(0L, secondsInCycle)).thenReturn("00:20");
        when(remainingTimeCaption.getRemainingTimeCaption(1L, secondsInCycle)).thenReturn("00:19");

        assertEquals("00:20", babystepsTimerClock.getRemainingTimeCaption());
        setupElapsedTimeInMilliseconds(1000L);
        assertEquals("00:20", babystepsTimerClock.getRemainingTimeCaption());
    }

    @Test
    public void tickShouldChangeCaption() {
        when(remainingTimeCaption.getRemainingTimeCaption(0L, secondsInCycle)).thenReturn("00:20");
        when(remainingTimeCaption.getRemainingTimeCaption(1L, secondsInCycle)).thenReturn("00:19");

        assertEquals("00:20", babystepsTimerClock.getRemainingTimeCaption());
        setupElapsedTimeInMilliseconds(1001L);
        assertEquals("00:19", babystepsTimerClock.getRemainingTimeCaption());
    }

    @Test
    public void tickShouldNotResetClockWhenTimerCycleHasNotEnded() {
        setupElapsedTimeInMilliseconds(10000L);
        when(remainingTimeCaption.getRemainingTimeCaption(0L, secondsInCycle)).thenReturn("00:00");
        when(remainingTimeCaption.getRemainingTimeCaption(10L, secondsInCycle)).thenReturn("10:00");

        babystepsTimerClock.tick();
        String caption = babystepsTimerClock.getRemainingTimeCaption();

        assertEquals("10:00", caption);
    }

    @Test
    public void tickShouldResetClockWhenTimerCycleHasEnded() {
        setupElapsedTimeInMilliseconds(20000L);
        when(remainingTimeCaption.getRemainingTimeCaption(0L, secondsInCycle)).thenReturn("00:00");
        when(remainingTimeCaption.getRemainingTimeCaption(20L, secondsInCycle)).thenReturn("20:00");

        babystepsTimerClock.tick();
        String caption = babystepsTimerClock.getRemainingTimeCaption();

        assertEquals("00:00", caption);
    }

    private void setupElapsedTimeInMilliseconds(long elapsedTimeInMilliseconds) {
        when(systemClock.currentTimeMillis()).thenReturn(0L).thenReturn(elapsedTimeInMilliseconds);
        babystepsTimerClock.resetClock();
        babystepsTimerClock.tick();
    }
}
