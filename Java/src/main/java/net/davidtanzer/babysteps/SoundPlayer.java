package net.davidtanzer.babysteps;

public interface SoundPlayer {
    public void playSoundInNewThread(final String url);

    void tryToPlaySound(String url, ErrorPrinter errorPrinter);
}
