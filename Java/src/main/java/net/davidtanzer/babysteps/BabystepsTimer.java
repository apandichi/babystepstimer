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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BabystepsTimer implements ClockListener, UserInterfaceChangeBroadcaster {
    public static final String BACKGROUND_COLOR_NEUTRAL = "#ffffff";
    public static final String BACKGROUND_COLOR_FAILED = "#ffcccc";
	public static final String BACKGROUND_COLOR_PASSED = "#ccffcc";

	private String bodyBackgroundColor;
    private boolean timerRunning;

    private SoundPlayer soundPlayer;
    private HtmlCreator htmlCreator;
    private BabystepsTimerClock babystepsTimerClock;

    private Map<String, String> soundsToPlayAtTime = new HashMap<>();
    private Map<String, String> colorsToSetAtTime = new HashMap<>();

    private List<UserInterfaceChangeListener> userInterfaceChangeListeners = new ArrayList<>();

    public BabystepsTimer(String bodyBackgroundColor, BabystepsTimerClock babystepsTimerClock, SoundPlayer soundPlayer, HtmlCreator htmlCreator) {
        this.bodyBackgroundColor = bodyBackgroundColor;
        this.babystepsTimerClock = babystepsTimerClock;
        this.soundPlayer = soundPlayer;
        this.htmlCreator = htmlCreator;
        configSoundsAndColorsForTimestamps();
    }

    private void configSoundsAndColorsForTimestamps() {
        soundsToPlayAtTime.put("00:10", "pluck.wav");
        soundsToPlayAtTime.put("00:00", "theetone.wav");
        colorsToSetAtTime.put("00:00", BACKGROUND_COLOR_FAILED);
    }

    public void startTimer() {
        timerRunning = true;
    }

    public void stopTimer() {
        timerRunning = false;
    }

    public boolean isListening() {
        return timerRunning;
    }

    public String getTimerHtml() {
        String caption = babystepsTimerClock.getRemainingTimeCaption();
        return htmlCreator.createTimerHtml(caption, bodyBackgroundColor, timerRunning);
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
    }

    public void reset() {
        babystepsTimerClock.resetClock();
        setBodyBackgroundColor(BabystepsTimer.BACKGROUND_COLOR_PASSED);
    }

    @Override
    public void tick() {
        String lastRemainingTime = babystepsTimerClock.getRemainingTimeCaption();
        babystepsTimerClock.tick();
        String remainingTime = babystepsTimerClock.getRemainingTimeCaption();
        resetBackgroundColorToNeutral();
        updateTimerCaptionWithElapsedTime(remainingTime, lastRemainingTime);
        broadcastUserInrerfaceChangeToListeners();
    }

    private void broadcastUserInrerfaceChangeToListeners() {
        for (UserInterfaceChangeListener userInterfaceChangeListener : userInterfaceChangeListeners) {
            userInterfaceChangeListener.updateUserInterfaceOnChange();
        }
    }

    private void updateTimerCaptionWithElapsedTime(String remainingTime, String lastRemainingTime) {
        if (babystepsTimerClock.timerCaptionChanged(remainingTime, lastRemainingTime)) {
            playSoundAtTime(remainingTime);
            changeBackgroundColorAtTime(remainingTime);
        }
    }

    private void changeBackgroundColorAtTime(String remainingTime) {
        String colorToSet = colorsToSetAtTime.get(remainingTime);
        if (colorToSet != null) {
            setBodyBackgroundColor(colorToSet);
        }
    }

    private void playSoundAtTime(String remainingTime) {
        String soundToPlay = soundsToPlayAtTime.get(remainingTime);
        if (soundToPlay != null) {
            soundPlayer.playSoundInNewThread(soundToPlay);
        }
    }

    private void resetBackgroundColorToNeutral() {
        if (babystepsTimerClock.elapsedTimeBetween5And6Seconds() && backgroundColorIsNotNeutral()) {
            setBodyBackgroundColor(BACKGROUND_COLOR_NEUTRAL);
        }
    }

    private boolean backgroundColorIsNotNeutral() {
        return !BACKGROUND_COLOR_NEUTRAL.equals(bodyBackgroundColor);
    }

    @Override
    public void registerUserInterfaceChangeListener(UserInterfaceChangeListener userInterfaceChangeListener) {
        userInterfaceChangeListeners.add(userInterfaceChangeListener);
    }

    @Override
    public void unregisterUserInterfaceChangeListener(UserInterfaceChangeListener userInterfaceChangeListener) {
        userInterfaceChangeListeners.remove(userInterfaceChangeListener);
    }
}
