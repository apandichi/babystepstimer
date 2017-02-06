package net.davidtanzer.babysteps;

import org.junit.Assert;
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
        when(systemClock.currentTimeMillis()).thenReturn(0L).thenReturn(2000L);
        babystepsTimerClock.resetClock();
        babystepsTimerClock.tick();
        boolean elapsedTimeBetween5And6Seconds = babystepsTimerClock.elapsedTimeBetween5And6Seconds();
        assertFalse(elapsedTimeBetween5And6Seconds);
    }

    @Test
    public void testElapsedTimeExactly5Seconds() {
        MockitoAnnotations.initMocks(this);
        when(systemClock.currentTimeMillis()).thenReturn(0L).thenReturn(5000L);
        babystepsTimerClock.resetClock();
        babystepsTimerClock.tick();
        boolean elapsedTimeBetween5And6Seconds = babystepsTimerClock.elapsedTimeBetween5And6Seconds();
        assertFalse(elapsedTimeBetween5And6Seconds);
    }

    @Test
    public void testElapsedTimeBetween5And6SecondsLowerBound() {
        MockitoAnnotations.initMocks(this);
        when(systemClock.currentTimeMillis()).thenReturn(0L).thenReturn(5001L);
        babystepsTimerClock.resetClock();
        babystepsTimerClock.tick();
        boolean elapsedTimeBetween5And6Seconds = babystepsTimerClock.elapsedTimeBetween5And6Seconds();
        assertTrue(elapsedTimeBetween5And6Seconds);
    }

    @Test
    public void testElapsedTimeBetween5And6SecondsUpperBound() {
        MockitoAnnotations.initMocks(this);
        when(systemClock.currentTimeMillis()).thenReturn(0L).thenReturn(5999L);
        babystepsTimerClock.resetClock();
        babystepsTimerClock.tick();
        boolean elapsedTimeBetween5And6Seconds = babystepsTimerClock.elapsedTimeBetween5And6Seconds();
        assertTrue(elapsedTimeBetween5And6Seconds);
    }

    @Test
    public void testElapsedTimeExactly6Seconds() {
        MockitoAnnotations.initMocks(this);
        when(systemClock.currentTimeMillis()).thenReturn(0L).thenReturn(6000L);
        babystepsTimerClock.resetClock();
        babystepsTimerClock.tick();
        boolean elapsedTimeBetween5And6Seconds = babystepsTimerClock.elapsedTimeBetween5And6Seconds();
        assertFalse(elapsedTimeBetween5And6Seconds);
    }


}
