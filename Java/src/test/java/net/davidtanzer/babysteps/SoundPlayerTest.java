package net.davidtanzer.babysteps;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

public class SoundPlayerTest {
    private SoundPlayer soundPlayer = new SoundPlayerWithClip();

    @Mock
    ErrorPrinter errorPrinter;

    @Test
    public void tryingToPlayInexistentSoundFileWillPrintNull() {
        MockitoAnnotations.initMocks(this);
        soundPlayer.tryToPlaySound("inexistent file", errorPrinter);
        verify(errorPrinter).printError(null);
    }

    @Test
    public void tryingToPlayInexistentThetoneSoundFileWillPrintNull() {
        MockitoAnnotations.initMocks(this);
        soundPlayer.tryToPlaySound("thetone.wav", errorPrinter);
        verify(errorPrinter).printError(null);
    }

    @Test
    public void tryingToPlayPluckSound() {
        MockitoAnnotations.initMocks(this);
        soundPlayer.tryToPlaySound("pluck.wav", errorPrinter);
        verify(errorPrinter, never()).printError(null);
    }

    @Test
    public void tryingToPlayTheetoneSound() {
        MockitoAnnotations.initMocks(this);
        soundPlayer.tryToPlaySound("theetone.wav", errorPrinter);
        verify(errorPrinter, never()).printError(null);
    }
}
