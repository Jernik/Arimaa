package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import board.BoardState;
import board.Coordinate;
import game.Game;
import listeners.LoadGameListener;
import listeners.NewGameListener;
import move_commands.CoordinatePair;
import move_commands.MoveCommand;
import piece.AbstractPiece;
import piece.Owner;

public class GUI {
	public static final String SAVE_FOLDER = "save/";
	public static final String SAVE_PATH = SAVE_FOLDER + "game.ser";
	public static final int PLAYER_AI_SLEEP = 500;
	public static final int AI_ONLY_SLEEP = 200;

	private Game game;
	private ArrayList<JFrame> activeFrames;

	private String p1Name;
	private String p2Name;

	private ImagePanel gameBoardPanel;
	private HashMap<Coordinate, ImagePanel> boardPieces;
	private TimePanel timer;

	private JTextField p1TextField;
	private JTextField p2TextField;
	private JCheckBox p1AiCheckBox;
	private JCheckBox p2AiCheckBox;
	private JComboBox<Integer> timerComboBox;
	private JLabel moveCountLabel;
	private JLabel turnCountLabel;
	private JLabel turnIndicatorLabel;
	private JLabel timerLabel;

	public GUI() {
		this.p1Name = "Player 1";
		this.p2Name = "Player 2";
		this.game = new Game();
		this.boardPieces = new HashMap<Coordinate, ImagePanel>();
		this.activeFrames = new ArrayList<JFrame>();
		JFrame mainMenuFrame = new JFrame();
		this.getActiveFrames().add(mainMenuFrame);
		mainMenuFrame.setTitle("Welcome to Arimaa!");
		mainMenuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public GUI(boolean shouldInit) {
		this.p1Name = "Player 1";
		this.p2Name = "Player 2";
		this.game = new Game();
	}

	public static void main(String[] args) {
		GUI g = new GUI();

		// Add MAIN MENU panel with appropriate background image
		ImagePanel panel = new ImagePanel(new ImageIcon("resources/BoardStoneBig.jpg").getImage());
		g.getActiveFrames().get(0).getContentPane().add(panel);
		g.getActiveFrames().get(0).pack();
		panel.setVisible(true);

		// Add the NEW GAME button to the Main Menu
		JButton newGameButton = new JButton();
		newGameButton.setSize(150, 75);
		newGameButton.setText("New Game");
		Font newGameFont = newGameButton.getFont();
		newGameButton.setFont(new Font(newGameFont.getName(), 4, 20));
		newGameButton.setLocation((panel.getWidth() / 4) - 35, (panel.getHeight() / 2) - 37);
		panel.add(newGameButton);
		newGameButton.setVisible(true);

		// Setup ActionListener for NEW GAME button
		newGameButton.addActionListener(new NewGameListener(g));

		// Add the LOAD GAME button to the Main Menu
		JButton loadGameButton = new JButton();
		loadGameButton.setSize(150, 75);
		loadGameButton.setText("Load Game");
		Font loadGameFont = loadGameButton.getFont();
		loadGameButton.setFont(new Font(loadGameFont.getName(), 4, 20));
		loadGameButton.setLocation((panel.getWidth() / 4) * 3 - 110, (panel.getHeight() / 2) - 37);
		panel.add(loadGameButton);
		loadGameButton.setVisible(true);

		// Setup ActionListener for the LOAD GAME button
		loadGameButton.addActionListener(new LoadGameListener(g));

		g.getActiveFrames().get(0).setVisible(true);
	}

	public Game getGame() {
		return game;
	}

	// default scope for testing
	void setGame(Game g) {
		this.game = g;
	}

	public ArrayList<JFrame> getActiveFrames() {
		return activeFrames;
	}

	public String getP1Name() {
		return p1Name;
	}

	public void setP1Name(String p1name) {
		this.p1Name = p1name;
	}

	public String getP2Name() {
		return p2Name;
	}

	public void setP2Name(String p2Name) {
		this.p2Name = p2Name;
	}

	public ImagePanel getGameBoardPanel() {
		return gameBoardPanel;
	}

	public void setGameBoardPanel(ImagePanel gameBoardPanel) {
		this.gameBoardPanel = gameBoardPanel;
	}

	public HashMap<Coordinate, ImagePanel> getBoardPieces() {
		return boardPieces;
	}

	public TimePanel getTimer() {
		return timer;
	}

	public void setTimer(TimePanel timer) {
		this.timer = timer;
	}

	public JTextField getP1TextField() {
		return p1TextField;
	}

	public void setP1TextField(JTextField p1TextField) {
		this.p1TextField = p1TextField;
	}

	public JTextField getP2TextField() {
		return p2TextField;
	}

	public void setP2TextField(JTextField p2TextField) {
		this.p2TextField = p2TextField;
	}

	public JCheckBox getP1AiCheckBox() {
		return this.p1AiCheckBox;
	}

	public void setP1AiCheckBox(JCheckBox checkBox) {
		this.p1AiCheckBox = checkBox;
	}

	public JCheckBox getP2AiCheckBox() {
		return this.p2AiCheckBox;
	}

	public void setP2AiCheckBox(JCheckBox checkBox) {
		this.p2AiCheckBox = checkBox;
	}

	public JComboBox<Integer> getTimerComboBox() {
		return timerComboBox;
	}

	public void setTimerComboBox(JComboBox<Integer> timerComboBox) {
		this.timerComboBox = timerComboBox;
	}

	public JLabel getMoveCountLabel() {
		return moveCountLabel;
	}

	public void setMoveCountLabel(JLabel moveCountLabel) {
		this.moveCountLabel = moveCountLabel;
	}

	public JLabel getTurnCountLabel() {
		return turnCountLabel;
	}

	public void setTurnCountLabel(JLabel turnCountLabel) {
		this.turnCountLabel = turnCountLabel;
	}

	public JLabel getTurnIndicatorLabel() {
		return turnIndicatorLabel;
	}

	public void setTurnIndicatorLabel(JLabel turnIndicatorLabel) {
		this.turnIndicatorLabel = turnIndicatorLabel;
	}

	public JLabel getTimerLabel() {
		return timerLabel;
	}

	public void setTimerLabel(JLabel timerLabel) {
		this.timerLabel = timerLabel;
	}

	public void renderInitialBoard() {
		for (Component comp : this.gameBoardPanel.getComponents()) {
			if (comp instanceof ImagePanel) {
				this.gameBoardPanel.remove(comp);
			}
		}
		this.boardPieces.clear();
		BoardState boardState = this.getGame().getBoardState();
		for (Coordinate coor : boardState.getAllCoordinates()) {
			AbstractPiece piece = boardState.getPieceAt(coor);
			ImagePanel imgPanel = new ImagePanel(piece.getImage());
			this.gameBoardPanel.add(imgPanel);
			imgPanel.setCoordinate(coor);
			imgPanel.setVisible(true);
			this.boardPieces.put(coor, imgPanel);
		}
		moveCountLabel.setText("<html> <b>" + "Moves Left: \n" + getGame().getNumMoves() + "</b></html>");
		turnCountLabel.setText("<html> <b>" + "Turn: " + getGame().getTurnNumber() + "</b></html>");
		if (getGame().getPlayerTurn() == Owner.Player1) {
			turnIndicatorLabel.setText("<html> <b>" + getGame().getP1Name() + "'s turn" + "</b></html>");
		} else {
			turnIndicatorLabel.setText("<html> <b>" + getGame().getP2Name() + "'s turn" + "</b></html>");
		}
	}

	public void rerenderBoard() {
		if (getGame().getWinner() != Owner.Nobody) {
			createWinWindow();
		}

		MoveCommand lastMove = this.game.getLastMove();
		for (CoordinatePair pair : lastMove.getAffectedCoordinates()) {
			Coordinate oldCoor = pair.getFrom();
			Coordinate newCoor = pair.getTo();
			ImagePanel panel = this.boardPieces.get(oldCoor);
			panel.setCoordinate(newCoor);

			this.boardPieces.remove(oldCoor);
			this.boardPieces.put(newCoor, panel);
		}
		for (Coordinate coor : game.getDeadCoors()) {
			this.gameBoardPanel.remove(this.boardPieces.get(coor));
			this.gameBoardPanel.repaint();
			this.boardPieces.remove(coor);
		}
		game.clearDeadCoors();
		moveCountLabel.setText("<html> <b>" + "Moves Left: \n" + getGame().getNumMoves() + "</b></html>");
		turnCountLabel.setText("<html> <b>" + "Turn: " + getGame().getTurnNumber() + "</b></html>");
		if (getGame().getPlayerTurn() == Owner.Player1) {
			turnIndicatorLabel.setText("<html> <b>" + getGame().getP1Name() + "'s turn" + "</b></html>");
		} else {
			turnIndicatorLabel.setText("<html> <b>" + getGame().getP2Name() + "'s turn" + "</b></html>");
		}
	}

	// ACTION LISTENERS
	public class CancelListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			JFrame settingsFrame = getActiveFrames().get(1);
			getActiveFrames().remove(1);
			settingsFrame.dispose();
		}
	}

	public class SaveGameListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			saveFile();
		}
	}

	public class UndoListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			getGame().undoMove();
			renderInitialBoard();
		}
	}

	public void createWinWindow() {
		String playerName = "";
		if (this.getGame().getWinner() == Owner.Player1) {
			playerName = getGame().getP1Name();
		} else {
			playerName = getGame().getP2Name();
		}

		JFrame winnerFrame = new JFrame();
		getActiveFrames().add(winnerFrame);
		winnerFrame.setTitle("Winner!");
		winnerFrame.setLocation(650 / 2 - 324 / 2 + 5, 650 / 2 - 324 / 2 + 44);
		winnerFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		winnerFrame.setVisible(true);

		ImagePanel panel = new ImagePanel(new ImageIcon("resources/BoardStoneBigCropped.jpg").getImage());
		winnerFrame.getContentPane().add(panel);
		winnerFrame.pack();
		panel.setVisible(true);

		// Set Up winner name Label
		JLabel winnerLabel = new JLabel();
		winnerLabel.setText("<html> <div style=\"text-align: center;\"> <b>" + playerName + " Wins!" + "</b></html>");
		winnerLabel.setForeground(Color.WHITE);
		Font winnerFont = winnerLabel.getFont();
		winnerLabel.setFont(new Font(winnerFont.getName(), 4, 24));
		winnerLabel.setSize(150, 150);
		panel.add(winnerLabel);
		winnerLabel.setLocation(winnerFrame.getWidth() / 2 - 75, winnerFrame.getHeight() / 2 - 87);
		winnerLabel.setVisible(true);
	}

	public boolean loadFile(File f) {
		if (!f.exists()) {
			System.out.println("file does not exist");
			return false;
		}
		ObjectInputStream in = null;
		Game newGame = null;
		try {
			in = this.createInputStream(f);
			newGame = (Game) in.readObject();
		} catch (IOException e) {
			System.out.println("Could not load game. Please try again");
			e.printStackTrace();
			return false;
		} catch (ClassNotFoundException | ClassCastException e) {
			System.out.println("Corrupted save file");
			e.printStackTrace();
			return false;
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					return false;
				}
			}
		}
		this.game = newGame;
		return true;
	}

	public boolean saveFile() {
		ObjectOutputStream out = null;
		try {
			out = createOutputStream();
			out.writeObject(this.getGame());
		} catch (IOException e) {
			System.out.println("could not save the game");
			e.printStackTrace();
			return false;
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					return false;
				}
			}
		}
		return true;
	}

	public ObjectOutputStream createOutputStream() throws IOException {
		File d = new File(SAVE_FOLDER);
		if (!d.isDirectory()) {
			d.mkdir();
		}
		File f = new File(SAVE_PATH);
		return new ObjectOutputStream(new FileOutputStream(f));
	}

	public ObjectInputStream createInputStream(File f) throws IOException {
		return new ObjectInputStream(new FileInputStream(f));
	}

	public int getAiSleepTime() {
		return this.game.isAiGame() ? AI_ONLY_SLEEP : PLAYER_AI_SLEEP;
	}
}
