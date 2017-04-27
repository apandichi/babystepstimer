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
import java.util.List;
import java.util.Map;

public class BabystepsTimer implements ClockListener, UserInterfaceChangeBroadcaster {

    private boolean timerRunning;

    private SoundPlayer soundPlayer;
    private BabystepsTimerClock babystepsTimerClock;

    private Map<String, String> soundsToPlayAtTime;
    private Map<String, BabystepsTimerState> babystepsTimerStateAtTime;

    private List<UserInterfaceChangeListener> userInterfaceChangeListeners = new ArrayList<>();
    private BabystepsTimerState timerState = BabystepsTimerState.NEUTRAL;

    public BabystepsTimer(BabystepsTimerClock babystepsTimerClock, SoundPlayer soundPlayer, Map<String, String> soundsToPlayAtTime, Map<String, BabystepsTimerState> babystepsTimerStateAtTime) {
        this.babystepsTimerClock = babystepsTimerClock;
        this.soundPlayer = soundPlayer;
        this.soundsToPlayAtTime = soundsToPlayAtTime;
        this.babystepsTimerStateAtTime = babystepsTimerStateAtTime;
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

    public void start() {
        babystepsTimerClock.resetClock();
        startTimer();
    }

    public void stop() {
        stopTimer();
    }

    public void reset() {
        babystepsTimerClock.resetClock();
        timerState = BabystepsTimerState.RESET;
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
        if (babystepsTimerClock.didTimerCaptionChange(remainingTimeBefore, remainingTimeAfter)) {
            playSoundAtTime(remainingTimeAfter);
            changeTimerStateAtTime(remainingTimeAfter);
            broadcastUserInterfaceChangeToListeners();
        }
    }

    private void changeTimerStateAtTime(String remainingTimeAfter) {
        BabystepsTimerState babystepsTimerStateAtTime = this.babystepsTimerStateAtTime.get(remainingTimeAfter);
        if (babystepsTimerStateAtTime != null) {
            timerState = babystepsTimerStateAtTime;
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

    public BabystepsTimerState getTimerState() {
        return timerState;
    }
}
