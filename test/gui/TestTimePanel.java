package gui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.lang.reflect.Field;

import javax.swing.JLabel;

import org.junit.Test;

import game.Game;
import gui.GUI;
import gui.TimePanel;
import piece.Owner;

public class TestTimePanel {

	@Test
	public void testInitializes() {
		TimePanel tp = new TimePanel(new GUI(false), new Game(), 15, new JLabel());
		assertNotNull(tp);
		tp.stopTimer();
	}
	
	@Test
	public void testUpdate() {
		TimePanel tp = new TimePanel(new GUI(false), new Game(), 15, new JLabel());
		//tp.update(5, 0);
		try {
			Thread.sleep(50);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals("<html> <b>" + 0 + ":" + 14 + "</b> </html>", tp.getTimerLabel().getText());
		tp.stopTimer();
	}
	
	@Test
	public void testCancelTimer(){
		GUI gui=new GUI(false);
		Game game=new Game();
		TimePanel tp= new TimePanel(gui, game, 1, new JLabel());
		game.setWinner(Owner.Player1);
		try {
			Thread.sleep(1100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals(Owner.Player1, game.getWinner());
		tp.stopTimer();
	}

	@Test
	public void testSwitchMove(){
		GUI gui=new GUI(false);
		Game game=new Game();
		TimePanel tp= new TimePanel(gui, game, 9, new JLabel());

		game.incrementTurn();
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		Field playerTurn = null;
		try {
			playerTurn = TimePanel.class.getDeclaredField("playerTurn");
		} catch (NoSuchFieldException | SecurityException e1) {
			// TODO Auto-generated catch block
			//e1.printStackTrace();
		}
		playerTurn.setAccessible(true);
		Owner fieldValue = Owner.Nobody;
		try {
			fieldValue = (Owner)playerTurn.get(tp);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		assertEquals(Owner.Player2,  fieldValue);
		tp.stopTimer();
	}
}
