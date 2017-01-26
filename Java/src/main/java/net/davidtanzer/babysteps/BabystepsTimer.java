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

	private long currentCycleStartTime;
	private String lastRemainingTime;
	private String bodyBackgroundColor = BACKGROUND_COLOR_NEUTRAL;

	private RemainingTimeCaption remainingTimeCaption = new RemainingTimeCaptionImpl();
    private SoundPlayer soundPlayer = new SoundPlayerImpl();
    private HtmlCreator htmlCreator = new HtmlCreatorImpl();

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

    public String getInitHtml(boolean running) {
        return getStopHtml(running);
    }

    public String getStartHtml(boolean running) {
        return getStopHtml(running);
    }

    public String getStopHtml(boolean running) {
        return getTimerHtml(0L, BACKGROUND_COLOR_NEUTRAL, running);
    }

    public String getTimerHtml(boolean running) {
        return getTimerHtml(getElapsedTime(), bodyBackgroundColor, running);
    }

    public void currentCycleStartTime(long startTime) {
        currentCycleStartTime = startTime;
    }

    public void setBodyBackgroundColor(String color) {
        bodyBackgroundColor = color;
    }

    public String lastRemainingTime() {
        return lastRemainingTime;
    }

    public void lastRemainingTime(String remainingTime) {
        lastRemainingTime = remainingTime;
    }

    public void start() {
        currentCycleStartTime(System.currentTimeMillis());
    }

    public void reset() {
        currentCycleStartTime(System.currentTimeMillis());
        setBodyBackgroundColor(BabystepsTimer.BACKGROUND_COLOR_PASSED);
    }

    public void updateTimerCaption(String remainingTime) {
        lastRemainingTime(remainingTime);
    }

    public void tick() {
        long elapsedTime = resetTimerWhenCycleEnded();
        resetBackgroundColorToNeutral(elapsedTime);
        updateTimerCaptionWithElapsedTime(elapsedTime);
    }

    private void updateTimerCaptionWithElapsedTime(long elapsedTime) {
        String remainingTime = remainingTimeCaption.getRemainingTimeCaption(elapsedTime, BabystepsTimer.SECONDS_IN_CYCLE);
        if (timerCaptionChanged(remainingTime)) {
            playSoundAtTime(remainingTime);
            changeBackgroundColorAtTime(remainingTime);
            updateTimerCaption(remainingTime);
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

    private long resetTimerWhenCycleEnded() {
        long elapsedTime = getElapsedTime();
        boolean timerCycleEnded = elapsedTime >= SECONDS_IN_CYCLE * 1000 + 980;
        if (timerCycleEnded) {
            currentCycleStartTime(System.currentTimeMillis());
            elapsedTime = getElapsedTime();
        }
        return elapsedTime;
    }

    private void resetBackgroundColorToNeutral(long elapsedTime) {
        if (elapsedTimeBetween5And6Seconds(elapsedTime) && backgroundColorIsNotNeutral()) {
            setBodyBackgroundColor(BACKGROUND_COLOR_NEUTRAL);
        }
    }

    private boolean timerCaptionChanged(String remainingTime) {
        return !remainingTime.equals(lastRemainingTime());
    }

    private boolean backgroundColorIsNotNeutral() {
        return !BACKGROUND_COLOR_NEUTRAL.equals(bodyBackgroundColor);
    }

    private boolean elapsedTimeBetween5And6Seconds(long elapsedTime) {
        return 5000 < elapsedTime && elapsedTime < 6000;
    }

    private long getElapsedTime() {
        return System.currentTimeMillis() - currentCycleStartTime;
    }
}
