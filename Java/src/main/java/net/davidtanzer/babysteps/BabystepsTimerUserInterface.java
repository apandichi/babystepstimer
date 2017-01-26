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

public class BabystepsTimerUserInterface {

	private JFrame timerFrame;
	private JTextPane timerPane;

	private BabystepsTimer babystepsTimer = new BabystepsTimer();
    private TimerThread timerThread;

    public static void main(final String[] args) throws InterruptedException {
        new BabystepsTimerUserInterface().init();
    }

    public void init() {
		timerFrame = new JFrame("Babysteps Timer");
		timerFrame.setUndecorated(true);
		timerFrame.setSize(250, 120);
		timerFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		timerPane = new JTextPane();
		timerPane.setContentType("text/html");
        timerPane.setText(babystepsTimer.getStopHtml(false));
		timerPane.setEditable(false);
		timerPane.addMouseMotionListener(new BabystepsMouseMotionListener());
        timerPane.addHyperlinkListener(new BabystepsHyperlinkListener(this));
		timerFrame.getContentPane().add(timerPane);
		timerFrame.setVisible(true);
	}


    public void setText(String text) {
        timerPane.setText(text);
    }

    public void repaint() {
        timerFrame.repaint();
    }

    public void setAlwaysOnTop(boolean onTop) {
        timerFrame.setAlwaysOnTop(onTop);
    }

    public void start() {
        babystepsTimer.start();
        timerThread = new TimerThread(this);
        timerThread.startTimer();
        String htmlAtStart = babystepsTimer.getStopHtml(true);
        setAlwaysOnTop(true);
        setText(htmlAtStart);
        repaint();
    }

    public void stop() {
        timerThread.stopTimer();
        String htmlAtStart = babystepsTimer.getStopHtml(false);
        setAlwaysOnTop(false);
        setText(htmlAtStart);
        repaint();
    }

    public void reset() {
        babystepsTimer.reset();
    }

    public void tick() {
        babystepsTimer.tick();
        SwingUtilities.invokeLater(() -> {
            setText(babystepsTimer.getTimerHtml(true));
            repaint();
        });
    }

    public void quit() {
        System.exit(0);
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
