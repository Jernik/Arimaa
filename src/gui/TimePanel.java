/**
 * Adopted from code provided at http://stackoverflow.com/questions/18926839/timer-stopwatch-gui/18926890#18926890
 */
package gui;

import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JLabel;

import game.Game;
import piece.Owner;

public class TimePanel {
	private JLabel timerLabel;
	Owner playerTurn;
	private Timer updateTimer;

	public TimePanel(GUI gui, Game game, int startTime, JLabel label) {
		playerTurn = game.getPlayerTurn();
		setTimerLabel(label);

		this.updateTimer = new Timer();
		updateTimer.scheduleAtFixedRate(new TimerTask() {
			int s = startTime;

			@Override
			public void run() {
				if (game.getWinner() != Owner.Nobody) {
					updateTimer.cancel();
					return;
				}
				// update Panel text
				if (playerTurn == game.getPlayerTurn()) {
					s--;
				} else {
					s = startTime;
					playerTurn = game.getPlayerTurn();
				}
				if (s == 0) {
					game.setWinner(game.getOtherPlayerTurn());
					gui.rerenderBoard();// to show winner pane
				}
				int displays, m;
				m = s / 60;
				displays = s % 60;
				update(displays, m);
			}
		}, 0, 1000);
	}

	public void update(int s, int minute) {
		String sec = Integer.toString(s);
		String min = Integer.toString(minute);
		if (s < 10) {
			sec = "0" + sec;
		}

		getTimerLabel().setText("<html> <b>" + min + ":" + sec + "</b> </html>");
	}

	public JLabel getTimerLabel() {
		return timerLabel;
	}

	public void setTimerLabel(JLabel timerLabel) {
		this.timerLabel = timerLabel;
	}

	public void stopTimer() {
		this.updateTimer.cancel();
	}
}
