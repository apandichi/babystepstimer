package net.davidtanzer.babysteps;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;

import static net.davidtanzer.babysteps.BabystepsTimerUserInterface.BACKGROUND_COLOR_FAILED;
import static net.davidtanzer.babysteps.BabystepsTimerUserInterface.BACKGROUND_COLOR_NEUTRAL;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

public class BabystepsTimerTest {

    private final String bodyBackgroundColor = BabystepsTimerUserInterface.BACKGROUND_COLOR_NEUTRAL;
    private HashMap<String, String> colorsToSetAtTime = new HashMap<>();
    private HashMap<String, String> soundsToPlayAtTime = new HashMap<>();

    @InjectMocks
    private BabystepsTimer babystepsTimer;

    @Mock
    private HtmlCreator htmlCreator;

    @Mock
    private BabystepsTimerClock babystepsTimerClock;

    @Mock
    private SoundPlayer soundPlayer;

    @Mock
    private UserInterfaceChangeListener userInterfaceChangeListener;

    @Before
    public void beforeTest() {
        MockitoAnnotations.initMocks(this);
        babystepsTimer = new BabystepsTimer(bodyBackgroundColor, babystepsTimerClock, soundPlayer, soundsToPlayAtTime);
        assertEquals(babystepsTimer.getBodyBackgroundColor(), BabystepsTimerUserInterface.BACKGROUND_COLOR_NEUTRAL);
        configureSoundsToPlayAtTime();
        configureColorsToSetAtTime();
    }

    private void configureColorsToSetAtTime() {
        colorsToSetAtTime.put("00:15", BACKGROUND_COLOR_NEUTRAL);
        colorsToSetAtTime.put("00:00", BACKGROUND_COLOR_FAILED);
    }

    private void configureSoundsToPlayAtTime() {
        soundsToPlayAtTime.put("00:10", "pluck.wav");
        soundsToPlayAtTime.put("00:00", "theetone.wav");
    }

    @Test
    public void shouldResetTimerClockAndBackgroundColor() {
        assertNotEquals(babystepsTimer.getBodyBackgroundColor(), BabystepsTimerUserInterface.BACKGROUND_COLOR_PASSED);
        babystepsTimer.addUserInterfaceChangeListener(userInterfaceChangeListener);

        babystepsTimer.reset();

        verify(babystepsTimerClock).resetClock();
        verify(userInterfaceChangeListener).updateUserInterfaceOnChange();
    }

    @Test
    public void tickShouldNotResetBackgroundColorOrPlayAnySound() {
        babystepsTimer.tick();
        assertEquals(babystepsTimer.getBodyBackgroundColor(), BabystepsTimerUserInterface.BACKGROUND_COLOR_NEUTRAL);
        verifyNoMoreInteractions(soundPlayer);
    }

    @Test
    public void tickShouldNotResetBackgroundColorOrPlaySoundWhenBackgroundColorIsNotNeutral() {
        babystepsTimer.setBodyBackgroundColor(BabystepsTimerUserInterface.BACKGROUND_COLOR_FAILED);
        babystepsTimer.addUserInterfaceChangeListener(userInterfaceChangeListener);

        babystepsTimer.tick();

        assertEquals(babystepsTimer.getBodyBackgroundColor(), BabystepsTimerUserInterface.BACKGROUND_COLOR_FAILED);
        verifyNoMoreInteractions(userInterfaceChangeListener);
        verifyNoMoreInteractions(soundPlayer);
    }

    @Test
    public void tickShouldUpdateRemainingTimeCaptionButShouldNotResetColorOrPlayAnySound() {
        String remainingTime = "00:19";
        when(babystepsTimerClock.getRemainingTimeCaption()).thenReturn(remainingTime);
        babystepsTimer.addUserInterfaceChangeListener(userInterfaceChangeListener);

        babystepsTimer.tick();

        assertEquals(babystepsTimer.getBodyBackgroundColor(), BabystepsTimerUserInterface.BACKGROUND_COLOR_NEUTRAL);
        verifyNoMoreInteractions(userInterfaceChangeListener);
        verifyNoMoreInteractions(soundPlayer);
    }

    @Test
    public void tickShouldPlaySoundAndChangeBackgroundColorWhenRemainingTimeIsZero() {
        String remainingTime = "00:00";
        String soundAtTimeZero = "theetone.wav";

        when(babystepsTimerClock.getRemainingTimeCaption()).thenReturn(remainingTime);
        when(babystepsTimerClock.timerCaptionChanged(any(), any())).thenReturn(true);
        babystepsTimer.addUserInterfaceChangeListener(userInterfaceChangeListener);

        babystepsTimer.tick();

        verify(userInterfaceChangeListener).updateUserInterfaceOnChange();
        verify(soundPlayer).playSoundInNewThread(soundAtTimeZero);
    }

    @Test
    public void tickShouldPlaySoundWhenRemainingTimeIsTenSeconds() {
        String remainingTime = "00:10";
        String soundAtTimeZero = "pluck.wav";

        when(babystepsTimerClock.getRemainingTimeCaption()).thenReturn(remainingTime);
        when(babystepsTimerClock.timerCaptionChanged(any(), any())).thenReturn(true);
        babystepsTimer.addUserInterfaceChangeListener(userInterfaceChangeListener);

        babystepsTimer.tick();

        verify(userInterfaceChangeListener).updateUserInterfaceOnChange();
        assertEquals(babystepsTimer.getBodyBackgroundColor(), BabystepsTimerUserInterface.BACKGROUND_COLOR_NEUTRAL);
        verify(soundPlayer).playSoundInNewThread(soundAtTimeZero);
    }

    @Test
    public void clockShouldBeResetWhenTimerIsStarted() {
        babystepsTimer.start();
        verify(babystepsTimerClock).resetClock();
    }

}
