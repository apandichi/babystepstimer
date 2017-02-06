package net.davidtanzer.babysteps;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

public class BabystepsTimerClockTest {

    private final long secondsInCycle = 20L;

    @InjectMocks
    private BabystepsTimerClock babystepsTimerClock = new BabystepsTimerClockImpl(secondsInCycle);

    @Mock
    private SystemClock systemClock;

    @Before
    public void beforeTest() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testElapsedTimeLessThan5Seconds() {
        setupElapsedTimeInMilliseconds(2000L);
        boolean elapsedTimeBetween5And6Seconds = babystepsTimerClock.elapsedTimeBetween5And6Seconds();
        assertFalse(elapsedTimeBetween5And6Seconds);
    }

    @Test
    public void testElapsedTimeExactly5Seconds() {
        setupElapsedTimeInMilliseconds(5000L);
        boolean elapsedTimeBetween5And6Seconds = babystepsTimerClock.elapsedTimeBetween5And6Seconds();
        assertFalse(elapsedTimeBetween5And6Seconds);
    }

    @Test
    public void testElapsedTimeBetween5And6SecondsLowerBound() {
        setupElapsedTimeInMilliseconds(5001L);
        boolean elapsedTimeBetween5And6Seconds = babystepsTimerClock.elapsedTimeBetween5And6Seconds();
        assertTrue(elapsedTimeBetween5And6Seconds);
    }

    @Test
    public void testElapsedTimeBetween5And6SecondsUpperBound() {
        setupElapsedTimeInMilliseconds(5999L);
        boolean elapsedTimeBetween5And6Seconds = babystepsTimerClock.elapsedTimeBetween5And6Seconds();
        assertTrue(elapsedTimeBetween5And6Seconds);
    }

    @Test
    public void testElapsedTimeExactly6Seconds() {
        setupElapsedTimeInMilliseconds(6000L);
        boolean elapsedTimeBetween5And6Seconds = babystepsTimerClock.elapsedTimeBetween5And6Seconds();
        assertFalse(elapsedTimeBetween5And6Seconds);
    }

    @Test
    public void testTimerCaptionChanged() {
        String remainingTime = "00:19";
        String lastRemainingTime = "00:20";
        boolean timerCaptionChanged = babystepsTimerClock.timerCaptionChanged(remainingTime, lastRemainingTime);
        assertTrue(timerCaptionChanged);
    }

    @Test
    public void testTimerCaptionNotChanged() {
        String remainingTime = "00:20";
        String lastRemainingTime = "00:20";
        boolean timerCaptionChanged = babystepsTimerClock.timerCaptionChanged(remainingTime, lastRemainingTime);
        assertFalse(timerCaptionChanged);
    }

    private void setupElapsedTimeInMilliseconds(long elapsedTimeInMilliseconds) {
        when(systemClock.currentTimeMillis()).thenReturn(0L).thenReturn(elapsedTimeInMilliseconds);
        babystepsTimerClock.resetClock();
        babystepsTimerClock.tick();
    }
}
