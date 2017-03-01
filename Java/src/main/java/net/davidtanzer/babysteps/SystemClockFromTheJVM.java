package net.davidtanzer.babysteps;

class SystemClockFromTheJVM implements SystemClock {
    @Override
    public long currentTimeMillis() {
        return System.currentTimeMillis();
    }
}
