package net.davidtanzer.babysteps;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;

import static net.davidtanzer.babysteps.BabystepsTimerState.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

public class BabystepsTimerTest {

    private HashMap<String, BabystepsTimerState> babystepsTimerStateAtTime = new HashMap<>();
    private HashMap<String, String> soundsToPlayAtTime = new HashMap<>();

    @InjectMocks
    private BabystepsTimer babystepsTimer;

    @Mock
    private BabystepsTimerClock babystepsTimerClock;

    @Mock
    private SoundPlayer soundPlayer;

    @Mock
    private UserInterfaceChangeListener userInterfaceChangeListener;

    @Before
    public void beforeTest() {
        MockitoAnnotations.initMocks(this);
        babystepsTimer = new BabystepsTimer(babystepsTimerClock, soundPlayer, soundsToPlayAtTime, babystepsTimerStateAtTime);
        babystepsTimer.addUserInterfaceChangeListener(userInterfaceChangeListener);
        configureSoundsToPlayAtTime();
        configureColorsToSetAtTime();
    }

    private void configureColorsToSetAtTime() {
        babystepsTimerStateAtTime.put("00:15", NEUTRAL);
        babystepsTimerStateAtTime.put("00:00", FAILED);
    }

    private void configureSoundsToPlayAtTime() {
        soundsToPlayAtTime.put("00:10", "pluck.wav");
        soundsToPlayAtTime.put("00:00", "theetone.wav");
    }

    @Test
    public void shouldResetTimerClockAndBroadcastUserInterfaceChange() {
        assertEquals(babystepsTimer.getTimerState(), NEUTRAL);

        babystepsTimer.reset();

        assertEquals(babystepsTimer.getTimerState(), RESET);
        verify(babystepsTimerClock).resetClock();
        verify(userInterfaceChangeListener).updateUserInterfaceOnChange();
    }

    @Test
    public void shouldStopTimerClockAndBroadcastUserInterfaceChange() {
        babystepsTimer.start();
        assertEquals(babystepsTimer.isTimerRunning(), true);

        babystepsTimer.stop();

        assertEquals(babystepsTimer.getTimerState(), STOP);
        assertEquals(babystepsTimer.isTimerRunning(), false);
        verify(userInterfaceChangeListener).updateUserInterfaceOnChange();
    }

    @Test
    public void tickShouldNotPlayAnySoundOrBroadcastUserInterfaceChangeWhenTimerCaptionDidNotChange() {
        when(babystepsTimerClock.didTimerCaptionChange(any(), any())).thenReturn(false);

        babystepsTimer.tick();

        verifyNoMoreInteractions(userInterfaceChangeListener);
        verifyNoMoreInteractions(soundPlayer);
    }

    @Test
    public void babystepsTimerStateIsNeutralAfterTimerIsConstructed() {
        assertEquals(babystepsTimer.getTimerState(), NEUTRAL);
    }

    @Test
    public void tickShouldChangeTimerStateFromFailedToNeutralWhenRemainingTimeIs15Seconds() {
        setupTimerInFailedState();

        String remainingTime = "00:15";
        when(babystepsTimerClock.getRemainingTimeCaption()).thenReturn(remainingTime);
        when(babystepsTimerClock.didTimerCaptionChange(any(), any())).thenReturn(true);

        babystepsTimer.tick();

        assertEquals(babystepsTimer.getTimerState(), NEUTRAL);
    }

    private void setupTimerInFailedState() {
        when(babystepsTimerClock.getRemainingTimeCaption()).thenReturn("00:00");
        when(babystepsTimerClock.didTimerCaptionChange(any(), any())).thenReturn(true);
        babystepsTimer.tick();
        assertEquals(babystepsTimer.getTimerState(), FAILED);
    }

    @Test
    public void tickShouldChangeTimerStateToFailedWhenRemainingTimeIsZero() {
        String remainingTime = "00:00";
        when(babystepsTimerClock.getRemainingTimeCaption()).thenReturn(remainingTime);
        when(babystepsTimerClock.didTimerCaptionChange(any(), any())).thenReturn(true);

        babystepsTimer.tick();

        assertEquals(babystepsTimer.getTimerState(), FAILED);
    }

    @Test
    public void tickShouldPlaySoundAndChangeBackgroundColorWhenRemainingTimeIsZero() {
        String remainingTime = "00:00";
        String soundAtTimeZero = "theetone.wav";

        when(babystepsTimerClock.getRemainingTimeCaption()).thenReturn(remainingTime);
        when(babystepsTimerClock.didTimerCaptionChange(any(), any())).thenReturn(true);

        babystepsTimer.tick();

        verify(userInterfaceChangeListener).updateUserInterfaceOnChange();
        verify(soundPlayer).playSoundInNewThread(soundAtTimeZero);
    }

    @Test
    public void tickShouldPlaySoundWhenRemainingTimeIsTenSeconds() {
        String remainingTime = "00:10";
        String soundAtTimeZero = "pluck.wav";

        when(babystepsTimerClock.getRemainingTimeCaption()).thenReturn(remainingTime);
        when(babystepsTimerClock.didTimerCaptionChange(any(), any())).thenReturn(true);

        babystepsTimer.tick();

        verify(userInterfaceChangeListener).updateUserInterfaceOnChange();
        verify(soundPlayer).playSoundInNewThread(soundAtTimeZero);
    }

    @Test
    public void clockShouldBeResetWhenTimerIsStarted() {
        babystepsTimer.start();
        verify(babystepsTimerClock).resetClock();
    }

}
