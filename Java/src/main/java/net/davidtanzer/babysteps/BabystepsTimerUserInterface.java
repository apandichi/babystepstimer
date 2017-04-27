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
import java.util.HashMap;
import java.util.Map;

public class BabystepsTimerUserInterface implements UserInterfaceChangeListener, BabystepsTimerCommands {

    public static final String BACKGROUND_COLOR_NEUTRAL = "#ffffff";
    public static final String BACKGROUND_COLOR_FAILED = "#ffcccc";
    public static final String BACKGROUND_COLOR_RESET = "#ccffcc";
    private Map<BabystepsTimerState, String> timerStateToColorMap;

    private String bodyBackgroundColor;
    private HtmlCreator htmlCreator;

    private JFrame timerFrame;
	private JTextPane timerPane;

	private BabystepsTimer babystepsTimer;

    public BabystepsTimerUserInterface(BabystepsTimer babystepsTimer, HtmlCreator htmlCreator, String bodyBackgroundColor) {
        this.bodyBackgroundColor = bodyBackgroundColor;
        this.babystepsTimer = babystepsTimer;
        this.htmlCreator = htmlCreator;
        configureTimerStateToColorMap();
    }

    private void configureTimerStateToColorMap() {
        timerStateToColorMap = new HashMap<>();
        timerStateToColorMap.put(BabystepsTimerState.NEUTRAL, BACKGROUND_COLOR_NEUTRAL);
        timerStateToColorMap.put(BabystepsTimerState.FAILED, BACKGROUND_COLOR_FAILED);
        timerStateToColorMap.put(BabystepsTimerState.RESET, BACKGROUND_COLOR_RESET);
    }

    public void init() {
        initUserInterface();
        babystepsTimer.addUserInterfaceChangeListener(this);
	}

    private void initUserInterface() {
        timerFrame = new JFrame("Babysteps Timer");
        timerFrame.setUndecorated(true);
        timerFrame.setSize(250, 120);
        timerFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        timerPane = new JTextPane();
        timerPane.setContentType("text/html");
        timerPane.setText(getTimerHtml());
        timerPane.setEditable(false);
        timerPane.addMouseMotionListener(new BabystepsMouseMotionListener());
        timerPane.addHyperlinkListener(new HyperlinkListenerActingOnBabystepsTimerUserInterface(this));
        timerFrame.getContentPane().add(timerPane);
        timerFrame.setVisible(true);
    }

    private void setText(String text) {
        timerPane.setText(text);
    }

    private void repaint() {
        timerFrame.repaint();
    }

    private void setAlwaysOnTop(boolean onTop) {
        timerFrame.setAlwaysOnTop(onTop);
    }

    private void changeBackgroundColorAtTime() {
        setBodyBackgroundColor(timerStateToColorMap.get(babystepsTimer.getTimerState()));
    }

    private String getTimerHtml() {
        return htmlCreator.createTimerHtml(babystepsTimer.getRemainingTimeCaption(), getBodyBackgroundColor(), babystepsTimer.isTimerRunning());
    }

    @Override
    public void start() {
        babystepsTimer.start();
        setAlwaysOnTop(true);
        setText(getTimerHtml());
        repaint();
    }

    @Override
    public void stop() {
        babystepsTimer.stop();
        setBodyBackgroundColor(BabystepsTimerUserInterface.BACKGROUND_COLOR_NEUTRAL);
        setAlwaysOnTop(false);
        setText(getTimerHtml());
        repaint();
    }

    @Override
    public void reset() {
        setBodyBackgroundColor(BabystepsTimerUserInterface.BACKGROUND_COLOR_RESET);
        babystepsTimer.reset();
    }

    @Override
    public void updateUserInterfaceOnChange() {
        SwingUtilities.invokeLater(this::updateUserInterface);
    }

    private void updateUserInterface() {
        String caption = babystepsTimer.getRemainingTimeCaption();
        changeBackgroundColorAtTime();
        setText(getTimerHtml());
        repaint();
    }

    @Override
    public void quit() {
        System.exit(0);
    }

    public String getBodyBackgroundColor() {
        return bodyBackgroundColor;
    }

    public void setBodyBackgroundColor(String bodyBackgroundColor) {
        this.bodyBackgroundColor = bodyBackgroundColor;
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
