package net.davidtanzer.babysteps;

public interface UserInterfaceChangeBroadcaster {

    void registerUserInterfaceChangeListener(UserInterfaceChangeListener userInterfaceChangeListener);
    void unregisterUserInterfaceChangeListener(UserInterfaceChangeListener userInterfaceChangeListener);
}
