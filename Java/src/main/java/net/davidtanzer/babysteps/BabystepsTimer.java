/*  Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
*/

package net.davidtanzer.babysteps;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class BabystepsTimer implements ClockListener, UserInterfaceChangeBroadcaster {

    private String bodyBackgroundColor;
    private boolean timerRunning;

    private SoundPlayer soundPlayer;
    private HtmlCreator htmlCreator;
    private BabystepsTimerClock babystepsTimerClock;

    private Map<String, String> soundsToPlayAtTime;

    private List<UserInterfaceChangeListener> userInterfaceChangeListeners = new ArrayList<>();

    public BabystepsTimer(String bodyBackgroundColor, BabystepsTimerClock babystepsTimerClock, SoundPlayer soundPlayer, HtmlCreator htmlCreator, Map<String, String> soundsToPlayAtTime) {
        this.bodyBackgroundColor = bodyBackgroundColor;
        this.babystepsTimerClock = babystepsTimerClock;
        this.soundPlayer = soundPlayer;
        this.htmlCreator = htmlCreator;
        this.soundsToPlayAtTime = soundsToPlayAtTime;
        configureTimeNotificationMechanism();
    }

    private void configureTimeNotificationMechanism() {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(this::tickIfListening, 0, 10, TimeUnit.MILLISECONDS);
    }

    private void startTimer() {
        timerRunning = true;
    }

    private void stopTimer() {
        timerRunning = false;
    }

    public boolean isTimerRunning() {
        return timerRunning;
    }

    @Override
    public void tickIfListening() {
        if (isTimerRunning()) {
            tick();
        }
    }

    public String getRemainingTimeCaption() {
        return babystepsTimerClock.getRemainingTimeCaption();
    }

    public String getBodyBackgroundColor() {
        return bodyBackgroundColor;
    }

    public void setBodyBackgroundColor(String color) {
        bodyBackgroundColor = color;
    }

    public void start() {
        babystepsTimerClock.resetClock();
        startTimer();
    }

    public void stop() {
        stopTimer();
        setBodyBackgroundColor(BabystepsTimerUserInterface.BACKGROUND_COLOR_NEUTRAL);
    }

    public void reset() {
        babystepsTimerClock.resetClock();
        setBodyBackgroundColor(BabystepsTimerUserInterface.BACKGROUND_COLOR_PASSED);
        broadcastUserInterfaceChangeToListeners();
    }

    @Override
    public void tick() {
        String remainingTimeBeforeTick = getRemainingTimeCaption();
        babystepsTimerClock.tick();
        String remainingTimeAfterTick = getRemainingTimeCaption();
        updateTimerCaptionWithElapsedTime(remainingTimeBeforeTick, remainingTimeAfterTick);
    }

    private void broadcastUserInterfaceChangeToListeners() {
        for (UserInterfaceChangeListener userInterfaceChangeListener : userInterfaceChangeListeners) {
            userInterfaceChangeListener.updateUserInterfaceOnChange();
        }
    }

    private void updateTimerCaptionWithElapsedTime(String remainingTimeBefore, String remainingTimeAfter) {
        if (babystepsTimerClock.timerCaptionChanged(remainingTimeBefore, remainingTimeAfter)) {
            playSoundAtTime(remainingTimeAfter);
            broadcastUserInterfaceChangeToListeners();
        }
    }

    private void playSoundAtTime(String remainingTime) {
        String soundToPlay = soundsToPlayAtTime.get(remainingTime);
        if (soundToPlay != null) {
            soundPlayer.playSoundInNewThread(soundToPlay);
        }
    }

    @Override
    public void addUserInterfaceChangeListener(UserInterfaceChangeListener userInterfaceChangeListener) {
        userInterfaceChangeListeners.add(userInterfaceChangeListener);
    }

    @Override
    public void removeUserInterfaceChangeListener(UserInterfaceChangeListener userInterfaceChangeListener) {
        userInterfaceChangeListeners.remove(userInterfaceChangeListener);
    }
}
