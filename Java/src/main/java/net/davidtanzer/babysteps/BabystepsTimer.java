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

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class BabystepsTimer {
	public static final String BACKGROUND_COLOR_NEUTRAL = "#ffffff";
	public static final String BACKGROUND_COLOR_FAILED = "#ffcccc";
	public static final String BACKGROUND_COLOR_PASSED = "#ccffcc";

	public static final long SECONDS_IN_CYCLE = 20;

	private JFrame timerFrame;
	private JTextPane timerPane;
	private boolean timerRunning;
	private long currentCycleStartTime;
	private String lastRemainingTime;
	private String bodyBackgroundColor = BACKGROUND_COLOR_NEUTRAL;
	
	private RemainingTimeCaption remainingTimeCaption = new RemainingTimeCaptionImpl();
    private HtmlCreator htmlCreator = new HtmlCreatorImpl();

	public static void main(final String[] args) throws InterruptedException {
        new BabystepsTimer().init();
    }

    public void init() {
		timerFrame = new JFrame("Babysteps Timer");
		timerFrame.setUndecorated(true);

		timerFrame.setSize(250, 120);
		timerFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		timerPane = new JTextPane();
		timerPane.setContentType("text/html");
		timerPane.setText(htmlCreator.createTimerHtml(remainingTimeCaption.getRemainingTimeCaption(0L, BabystepsTimer.SECONDS_IN_CYCLE), BACKGROUND_COLOR_NEUTRAL, false));
		timerPane.setEditable(false);
		timerPane.addMouseMotionListener(new BabystepsMouseMotionListener());
        timerPane.addHyperlinkListener(new BabystepsHyperlinkListener(this));
		timerFrame.getContentPane().add(timerPane);

		timerFrame.setVisible(true);
	}

    public void timerRunning(boolean running) {
        timerRunning = running;
    }

    public void currentCycleStartTime(long startTime) {
        currentCycleStartTime = startTime;
    }

    public boolean timerRunning() {
        return timerRunning;
    }

    public long currentCycleStartTime() {
        return currentCycleStartTime;
    }

    public String getBodyBackgroundColor() {
        return bodyBackgroundColor;
    }

    public void setBodyBackgroundColor(String color) {
        bodyBackgroundColor = color;
    }

    public String lastRemainingTime() {
        return lastRemainingTime;
    }

    public void setText(String text) {
        timerPane.setText(text);
    }

    public void repaint() {
        timerFrame.repaint();
    }

    public void lastRemainingTime(String remainingTime) {
        lastRemainingTime = remainingTime;
    }

    public void setAlwaysOnTop(boolean onTop) {
        timerFrame.setAlwaysOnTop(onTop);
    }

    public void start() {
        timerRunning(true);
        currentCycleStartTime(System.currentTimeMillis());
        String captionTextAtStart = htmlCreator.createTimerHtml(remainingTimeCaption.getRemainingTimeCaption(0L, BabystepsTimer.SECONDS_IN_CYCLE), BabystepsTimer.BACKGROUND_COLOR_NEUTRAL, true);
        setAlwaysOnTop(true);
        setText(captionTextAtStart);
        repaint();
        new TimerThread(this).start();
    }

    public void stop() {
        timerRunning(false);
        setAlwaysOnTop(false);
        setText(htmlCreator.createTimerHtml(remainingTimeCaption.getRemainingTimeCaption(0L, BabystepsTimer.SECONDS_IN_CYCLE), BabystepsTimer.BACKGROUND_COLOR_NEUTRAL, false));
        repaint();
    }

    public void reset() {
        currentCycleStartTime(System.currentTimeMillis());
        setBodyBackgroundColor(BabystepsTimer.BACKGROUND_COLOR_PASSED);
    }

    public void quit() {
        System.exit(0);
    }

    public void updateTimerCaption(String remainingTime) {
        setText(htmlCreator.createTimerHtml(remainingTime, bodyBackgroundColor, true));
        repaint();
        lastRemainingTime(remainingTime);
    }

    private class BabystepsMouseMotionListener implements MouseMotionListener {
        private int lastX;
        private int lastY;

        @Override
        public void mouseMoved(final MouseEvent e) {
            lastX = e.getXOnScreen();
            lastY = e.getYOnScreen();
        }

        @Override
        public void mouseDragged(final MouseEvent e) {
            int x = e.getXOnScreen();
            int y = e.getYOnScreen();

            timerFrame.setLocation(timerFrame.getLocation().x + (x-lastX), timerFrame.getLocation().y + (y-lastY));

            lastX = x;
            lastY = y;
        }
    }
}
