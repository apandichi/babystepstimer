package net.davidtanzer.babysteps;

class SystemClockImpl implements SystemClock {
    @Override
    public long currentTimeMillis() {
        return System.currentTimeMillis();
    }
}
