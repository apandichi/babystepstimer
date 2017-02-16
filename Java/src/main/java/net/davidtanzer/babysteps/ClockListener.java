package net.davidtanzer.babysteps;

public interface ClockListener {
    void tick();
    boolean isListening();
    void tickIfListening();
}
