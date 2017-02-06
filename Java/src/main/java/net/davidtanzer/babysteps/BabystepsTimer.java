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

import java.util.HashMap;
import java.util.Map;

public class BabystepsTimer {
    public static final String BACKGROUND_COLOR_NEUTRAL = "#ffffff";
    public static final String BACKGROUND_COLOR_FAILED = "#ffcccc";
	public static final String BACKGROUND_COLOR_PASSED = "#ccffcc";

	private String bodyBackgroundColor;

    private SoundPlayer soundPlayer = new SoundPlayerImpl();
    private HtmlCreator htmlCreator = new HtmlCreatorImpl();
    private Clock clock;

    private Map<String, String> soundsToPlayAtTime = new HashMap<>();
    private Map<String, String> colorsToSetAtTime = new HashMap<>();

    public BabystepsTimer(long secondsInCycle, String bodyBackgroundColor) {
        clock = new ClockImpl(secondsInCycle);
        this.bodyBackgroundColor = bodyBackgroundColor;
        soundsToPlayAtTime.put("00:10", "pluck.wav");
        soundsToPlayAtTime.put("00:00", "theetone.wav");
        colorsToSetAtTime.put("00:00", BACKGROUND_COLOR_FAILED);
    }

    public String getTimerHtml(boolean running) {
        String caption = clock.getRemainingTimeCaption();
        return htmlCreator.createTimerHtml(caption, bodyBackgroundColor, running);
    }

    public String getBodyBackgroundColor() {
        return bodyBackgroundColor;
    }

    public void setBodyBackgroundColor(String color) {
        bodyBackgroundColor = color;
    }

    public void start() {
        clock.resetClock();
    }

    public void reset() {
        clock.resetClock();
        setBodyBackgroundColor(BabystepsTimer.BACKGROUND_COLOR_PASSED);
    }

    public void tick() {
        String lastRemainingTime = clock.getRemainingTimeCaption();
        clock.tick();
        String remainingTime = clock.getRemainingTimeCaption();
        resetBackgroundColorToNeutral();
        updateTimerCaptionWithElapsedTime(remainingTime, lastRemainingTime);
    }

    private void updateTimerCaptionWithElapsedTime(String remainingTime, String lastRemainingTime) {
        if (clock.timerCaptionChanged(remainingTime, lastRemainingTime)) {
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
        if (clock.elapsedTimeBetween5And6Seconds() && backgroundColorIsNotNeutral()) {
            setBodyBackgroundColor(BACKGROUND_COLOR_NEUTRAL);
        }
    }

    private boolean backgroundColorIsNotNeutral() {
        return !BACKGROUND_COLOR_NEUTRAL.equals(bodyBackgroundColor);
    }
}
