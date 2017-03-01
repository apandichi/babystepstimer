package net.davidtanzer.babysteps;

import javax.sound.sampled.Clip;

import static javax.sound.sampled.AudioSystem.getAudioInputStream;
import static javax.sound.sampled.AudioSystem.getClip;

public class SoundPlayerWithClip implements SoundPlayer {
    @Override
    public synchronized void playSoundInNewThread(String url) {
        new Thread(() -> {
            ErrorPrinter errorPrinter = System.err::println;
            tryToPlaySound(url, errorPrinter);
        }).start();
    }

    @Override
    public void tryToPlaySound(String url, ErrorPrinter errorPrinter) {
        try {
            Clip clip = getClip();
            clip.open(getAudioInputStream(this.getClass().getResourceAsStream("/" + url)));
            clip.start();
        } catch (Exception e) {
            errorPrinter.printError(e.getMessage());
        }
    }

}
