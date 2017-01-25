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

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.text.DecimalFormat;

import javax.swing.JFrame;
import javax.swing.JTextPane;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

public class BabystepsTimer {
	public static final String BACKGROUND_COLOR_NEUTRAL = "#ffffff";
	public static final String BACKGROUND_COLOR_FAILED = "#ffcccc";
	public static final String BACKGROUND_COLOR_PASSED = "#ccffcc";


	public static JFrame timerFrame;
	public static JTextPane timerPane;

	public static String bodyBackgroundColor = BACKGROUND_COLOR_NEUTRAL;
	
	private static DecimalFormat twoDigitsFormat = new DecimalFormat("00");

	private static TimerThread timer = new TimerThread();

	public static void main(final String[] args) throws InterruptedException {
		timerFrame = new JFrame("Babysteps Timer");
		timerFrame.setUndecorated(true);

		timerFrame.setSize(250, 120);
		timerFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		timerPane = new JTextPane();
		timerPane.setContentType("text/html");
		timerPane.setText(createTimerHtml(getRemainingTimeCaption(0L), BACKGROUND_COLOR_NEUTRAL, false));
		timerPane.setEditable(false);
		timerPane.addMouseMotionListener(new MouseMotionListener() {
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
		});
		timerPane.addHyperlinkListener(new HyperlinkListener() {
			@Override
			public void hyperlinkUpdate(final HyperlinkEvent e) {
				if(e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
					if("command://start".equals(e.getDescription())) {
						timerFrame.setAlwaysOnTop(true);
						timerPane.setText(createTimerHtml(getRemainingTimeCaption(0L), BACKGROUND_COLOR_NEUTRAL, true));
						timerFrame.repaint();
						timer.startTimer();
					} else if("command://stop".equals(e.getDescription())) {
						timer.stopTimer();
						timerFrame.setAlwaysOnTop(false);
						timerPane.setText(createTimerHtml(getRemainingTimeCaption(0L), BACKGROUND_COLOR_NEUTRAL, false));
						timerFrame.repaint();
					} else  if("command://reset".equals(e.getDescription())) {
						timer.resetTimer();
						bodyBackgroundColor=BACKGROUND_COLOR_PASSED;
					} else  if("command://quit".equals(e.getDescription())) {
						System.exit(0);
					}
				}
			}
		});
		timerFrame.getContentPane().add(timerPane);

		timerFrame.setVisible(true);
	}

	public static String getRemainingTimeCaption(final long elapsedTime) {
		long elapsedSeconds = elapsedTime/1000;
		long remainingSeconds = TimerThread.SECONDS_IN_CYCLE - elapsedSeconds;
		
		long remainingMinutes = remainingSeconds/60;
		return twoDigitsFormat.format(remainingMinutes)+":"+twoDigitsFormat.format(remainingSeconds-remainingMinutes*60);
	}

	public static String createTimerHtml(final String timerText, final String bodyColor, final boolean running) {
		return new HTMLRenderer(timerText, bodyColor, running).invoke();
	}

	public static synchronized void playSound(final String url) {
		new SoundPlayer(url).invoke();
	}

}
