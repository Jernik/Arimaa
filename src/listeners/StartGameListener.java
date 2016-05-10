package listeners;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import game.Game;
import gui.GUI;
import gui.ImagePanel;
import gui.TimePanel;
import piece.Owner;

public class StartGameListener implements ActionListener {

	private final Game game;
	private GUI gui;

	public StartGameListener(GUI gui) {
		this.gui = gui;
		this.game = gui.getGame();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		game.setP1Name(gui.getP1TextField().getText());
		game.setP2Name(gui.getP2TextField().getText());
		game.setMoveTimer((int) gui.getTimerComboBox().getSelectedItem());

		if (game.getP1Name().equals(""))
			game.setP1Name("Player 1");
		if (game.getP2Name().equals(""))
			game.setP2Name("Player 2");

		JFrame settings = gui.getActiveFrames().get(1);
		gui.getActiveFrames().remove(1);
		settings.dispose();

		JFrame mainMenu = gui.getActiveFrames().get(0);
		gui.getActiveFrames().remove(0);
		mainMenu.dispose();

		JFrame gameFrame = new JFrame();
		gui.getActiveFrames().add(gameFrame);
		gameFrame.setTitle("Let's Play!");
		gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		ImagePanel panel = new ImagePanel(new ImageIcon("resources/board.jpg").getImage());
		gui.getActiveFrames().get(0).getContentPane().add(panel);
		gui.getActiveFrames().get(0).pack();
		panel.setVisible(true);
		gui.setGameBoardPanel(panel);

		gui.getGameBoardPanel().addMouseListener(new MovementListener(gui));
		gui.getActiveFrames().get(0).setBackground(Color.BLACK);

		gameFrame.setVisible(true);
		// Set Up Player1 Label
		JLabel p1Label = new JLabel();
		p1Label.setText("<html> <b>Player 1: </b></html>");
		p1Label.setForeground(Color.BLACK);
		Font p1Font = p1Label.getFont();
		p1Label.setFont(new Font(p1Font.getName(), 4, 22));
		p1Label.setSize(110, 25);
		gui.getGameBoardPanel().add(p1Label);
		p1Label.setLocation(675, 25);
		p1Label.setVisible(true);

		// Set Up Player1 name Label
		JLabel p1NameLabel = new JLabel();
		p1NameLabel.setText("<html> <b>" + game.getP1Name() + "</b></html>");
		p1NameLabel.setForeground(Color.BLACK);
		Font p1NameFont = p1NameLabel.getFont();
		p1NameLabel.setFont(new Font(p1NameFont.getName(), 4, 18));
		p1NameLabel.setSize(110, 100);
		gui.getGameBoardPanel().add(p1NameLabel);
		p1NameLabel.setLocation(675, 25);
		p1NameLabel.setVisible(true);

		// Set Up Player2 Label
		JLabel p2Label = new JLabel();
		p2Label.setText("<html> <b>Player 2: </b></html>");
		p2Label.setForeground(Color.BLACK);
		Font p2Font = p2Label.getFont();
		p2Label.setFont(new Font(p2Font.getName(), 4, 22));
		p2Label.setSize(110, 25);
		gui.getGameBoardPanel().add(p2Label);
		p2Label.setLocation(675, 550);
		p2Label.setVisible(true);

		// Set Up Player2 name Label
		JLabel p2NameLabel = new JLabel();
		p2NameLabel.setText("<html> <b>" + game.getP2Name() + "</b></html>");
		p2NameLabel.setForeground(Color.BLACK);
		Font p2NameFont = p2NameLabel.getFont();
		p2NameLabel.setFont(new Font(p2NameFont.getName(), 4, 18));
		p2NameLabel.setSize(110, 100);
		gui.getGameBoardPanel().add(p2NameLabel);
		p2NameLabel.setLocation(675, 550);
		p2NameLabel.setVisible(true);

		// Set up Turn Counter label
		JLabel turnCounterLabel = new JLabel();
		gui.setTurnCountLabel(turnCounterLabel);
		turnCounterLabel.setText("<html> <b>" + "Turn: " + game.getTurnNumber() + "</b></html>");
		turnCounterLabel.setForeground(Color.BLACK);
		Font turnCounterFont = turnCounterLabel.getFont();
		turnCounterLabel.setFont(new Font(turnCounterFont.getName(), 4, 18));
		turnCounterLabel.setSize(110, 25);
		gui.getGameBoardPanel().add(turnCounterLabel);
		turnCounterLabel.setLocation(675, 130);
		turnCounterLabel.setVisible(true);

		// Set up Player Turn label
		JLabel playerTurnLabel = new JLabel();
		gui.setTurnIndicatorLabel(playerTurnLabel);
		if (game.getPlayerTurn() == Owner.Player1) {
			playerTurnLabel.setText("<html> <b>" + game.getP1Name() + "'s turn" + "</b></html>");
		} else {
			playerTurnLabel.setText("<html> <b>" + game.getP2Name() + "'s turn" + "</b></html>");
		}
		playerTurnLabel.setForeground(Color.BLACK);
		Font playerTurnFont = playerTurnLabel.getFont();
		playerTurnLabel.setFont(new Font(playerTurnFont.getName(), 4, 18));
		playerTurnLabel.setSize(110, 50);
		gui.getGameBoardPanel().add(playerTurnLabel);
		playerTurnLabel.setLocation(675, 200);
		playerTurnLabel.setVisible(true);

		// Set up move counter label
		JLabel moveCounterLabel = new JLabel();
		gui.setMoveCountLabel(moveCounterLabel);
		moveCounterLabel.setText("<html> <b>" + "Moves Left: \n" + game.getNumMoves() + "</b></html>");
		moveCounterLabel.setForeground(Color.BLACK);
		Font moveCounterFont = moveCounterLabel.getFont();
		moveCounterLabel.setFont(new Font(moveCounterFont.getName(), 4, 18));
		moveCounterLabel.setSize(110, 50);
		gui.getGameBoardPanel().add(moveCounterLabel);
		moveCounterLabel.setLocation(675, 370);
		moveCounterLabel.setVisible(true);

		// Set up turn timer name label
		JLabel turnTimerNameLabel = new JLabel();
		turnTimerNameLabel.setText("<html> <b>" + "Turn Time:" + "</b></html>");
		turnTimerNameLabel.setForeground(Color.BLACK);
		Font turnTimerNameFont = turnTimerNameLabel.getFont();
		turnTimerNameLabel.setFont(new Font(turnTimerNameFont.getName(), 4, 18));
		turnTimerNameLabel.setSize(110, 25);
		gui.getGameBoardPanel().add(turnTimerNameLabel);
		turnTimerNameLabel.setLocation(675, 450);
		turnTimerNameLabel.setVisible(true);

		// Set up actual timer label
		JLabel turnTimerLabel = new JLabel();
		gui.setTimerLabel(turnTimerLabel);
		turnTimerLabel.setForeground(Color.BLACK);
		Font turnTimerFont = turnTimerLabel.getFont();
		turnTimerLabel.setFont(new Font(turnTimerFont.getName(), 4, 18));
		turnTimerLabel.setSize(110, 25);
		gui.getGameBoardPanel().add(turnTimerLabel);
		turnTimerLabel.setLocation(675, 475);
		turnTimerLabel.setVisible(true);

		// P1 Time Panel
		TimePanel timePanel = new TimePanel(gui, game, (int) gui.getTimerComboBox().getSelectedItem(),
				gui.getTimerLabel());
		gui.setTimer(timePanel);

		// Set up Save Game Button
		JButton saveGameButton = new JButton();
		saveGameButton.setSize(65, 75);
		saveGameButton.setText("Save");
		saveGameButton.setLocation(660, gameFrame.getHeight() / 2 - 75);
		gui.getGameBoardPanel().add(saveGameButton);
		saveGameButton.addActionListener(gui.new SaveGameListener());
		saveGameButton.setVisible(true);

		// Set up Undo Button
		JButton undoButton = new JButton();
		undoButton.setSize(65, 75);
		undoButton.setText("Undo");
		undoButton.setLocation(730, gameFrame.getHeight() / 2 - 75);
		gui.getGameBoardPanel().add(undoButton);
		undoButton.addActionListener(gui.new UndoListener());
		undoButton.setVisible(true);

		gui.renderInitialBoard();
	}
}