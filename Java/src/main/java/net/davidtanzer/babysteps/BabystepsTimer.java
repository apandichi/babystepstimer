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

	public static final long SECONDS_IN_CYCLE = 20;

	private String bodyBackgroundColor = BACKGROUND_COLOR_NEUTRAL;

    private RemainingTimeCaption remainingTimeCaption = new RemainingTimeCaptionImpl();
    private SoundPlayer soundPlayer = new SoundPlayerImpl();
    private HtmlCreator htmlCreator = new HtmlCreatorImpl();
    private ClockImpl clock = new ClockImpl();

    private Map<String, String> soundsToPlayAtTime = new HashMap<>();
    private Map<String, String> colorsToSetAtTime = new HashMap<>();

    public BabystepsTimer() {
        soundsToPlayAtTime.put("00:10", "pluck.wav");
        soundsToPlayAtTime.put("00:00", "theetone.wav");
        colorsToSetAtTime.put("00:00", BACKGROUND_COLOR_FAILED);
    }

    private String getTimerHtml(long elapsedTimeInMilliseconds, String backgroundColor, boolean running) {
        String caption = remainingTimeCaption.getRemainingTimeCaption(elapsedTimeInMilliseconds, BabystepsTimer.SECONDS_IN_CYCLE);
        return htmlCreator.createTimerHtml(caption, backgroundColor, running);
    }

    public String getHtmlForTheStopState(boolean running) {
        return getTimerHtml(0L, BACKGROUND_COLOR_NEUTRAL, running);
    }

    public String getTimerHtml(boolean running) {
        return getTimerHtml(clock.getElapsedTime(), bodyBackgroundColor, running);
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
        long elapsedTime = clock.resetTimerWhenCycleEnded(SECONDS_IN_CYCLE);
        resetBackgroundColorToNeutral(elapsedTime);
        updateTimerCaptionWithElapsedTime(elapsedTime);
    }

    private void updateTimerCaptionWithElapsedTime(long elapsedTime) {
        String remainingTime = remainingTimeCaption.getRemainingTimeCaption(elapsedTime, BabystepsTimer.SECONDS_IN_CYCLE);
        if (clock.timerCaptionChanged(remainingTime)) {
            playSoundAtTime(remainingTime);
            changeBackgroundColorAtTime(remainingTime);
            clock.updateTimerCaption(remainingTime);
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

    private void resetBackgroundColorToNeutral(long elapsedTime) {
        if (clock.elapsedTimeBetween5And6Seconds(elapsedTime) && backgroundColorIsNotNeutral()) {
            setBodyBackgroundColor(BACKGROUND_COLOR_NEUTRAL);
        }
    }

    private boolean backgroundColorIsNotNeutral() {
        return !BACKGROUND_COLOR_NEUTRAL.equals(bodyBackgroundColor);
    }
}
