package net.davidtanzer.babysteps;

public interface UserInterfaceChangeBroadcaster {

    void addUserInterfaceChangeListener(UserInterfaceChangeListener userInterfaceChangeListener);
    void removeUserInterfaceChangeListener(UserInterfaceChangeListener userInterfaceChangeListener);
}
