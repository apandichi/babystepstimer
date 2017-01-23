package net.davidtanzer.babysteps;

import javax.sound.sampled.Clip;

import static javax.sound.sampled.AudioSystem.getAudioInputStream;
import static javax.sound.sampled.AudioSystem.getClip;

public class SoundPlayerImpl implements SoundPlayer {
    @Override
    public synchronized void playSound(String url) {
        new Thread(() -> {
            try {
                Clip clip = getClip();
                clip.open(getAudioInputStream(this.getClass().getResourceAsStream("/" + url)));
                clip.start();
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }).start();
    }
}
