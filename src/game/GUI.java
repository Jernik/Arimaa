package game;

import listeners.LoadGameListener;
import listeners.NewGameListener;
import networking.ConnectionHandler;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class GUI {
	public String p1Name;
	public String p2Name;
	public ArrayList<JFrame> activeFrames;
	public Game game;
	public ImagePanel gameBoardPanel = null;
	public ImagePanel[][] boardPieces;
	public JTextField p1TextField;
	public JTextField p2TextField;
	public JComboBox<Integer> timerComboBox;
	public JLabel moveCountLabel;
	public JLabel turnCountLabel;
	public JLabel turnIndicatorLabel;
	public JLabel timerLabel;
	public TimePanel timer;
	private ConnectionHandler connectionHandler;

	public GUI() {
		this.p1Name = "Player 1";
		this.p2Name = "Player 2";
		p2TextField = null;
		p1TextField = null;
//		BoardState b=new BoardState(new char[][] {
//				{ ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
//				{ ' ', ' ', ' ', ' ', 'r', ' ', ' ', ' ' },
//				{ ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
//				{ ' ', 'R', ' ', ' ', ' ', ' ', ' ', ' ' },
//				{ ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
//				{ ' ', 'R', ' ', 'E', ' ', ' ', 'r', ' ' },
//				{ ' ', 'e', ' ', ' ', ' ', ' ', 'C', ' ' },
//				{ ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' }, }, 0);
		this.game = new Game();
		this.boardPieces = new ImagePanel[8][8];
		this.activeFrames = new ArrayList<JFrame>();
		JFrame mainMenuFrame = new JFrame();
		this.activeFrames.add(mainMenuFrame);
		mainMenuFrame.setTitle("Welcome to Arimaa!");
		mainMenuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}


	public String getP1name() {
		return p1Name;
	}

	public void setP1name(String p1name) {
		this.p1Name = p1name;
	}

	public String getP2name() {
		return p2Name;
	}

	public void setP2name(String p2name) {
		this.p2Name = p2name;
	}

	public ArrayList<JFrame> getActiveFrames() {
		return activeFrames;
	}

	public void setActiveFrames(ArrayList<JFrame> frames) {
		this.activeFrames = frames;
	}

	public void renderInitialBoard() {
		if (game.getWinner() != 0)
			createWinWindow();
		char[][] boardArray = this.game.currentBoard.getBoardArray();
		for (int row = 0; row < 8; row++) {
			for (int column = 0; column < 8; column++) {
				char c = boardArray[row][column];
				switch (c) {
				case 'E':
					ImagePanel whiteElephantPanel = new ImagePanel(
							new ImageIcon("resources/White elephant.png")
									.getImage());
					this.gameBoardPanel.add(whiteElephantPanel);
					whiteElephantPanel.setRow(row);
					whiteElephantPanel.setColumn(column);
					whiteElephantPanel.setLocation(
							whiteElephantPanel.getPixelX(),
							whiteElephantPanel.getPixelY());
					whiteElephantPanel.setVisible(true);
					this.boardPieces[row][column] = whiteElephantPanel;
					break;
				case 'C':
					ImagePanel whiteCamelPanel = new ImagePanel(new ImageIcon(
							"resources/White camel.png").getImage());
					this.gameBoardPanel.add(whiteCamelPanel);
					whiteCamelPanel.setRow(row);
					whiteCamelPanel.setColumn(column);
					whiteCamelPanel.setLocation(whiteCamelPanel.getPixelX(),
							whiteCamelPanel.getPixelY());
					whiteCamelPanel.setVisible(true);
					this.boardPieces[row][column] = whiteCamelPanel;
					break;
				case 'H':
					ImagePanel whiteHorsePanel = new ImagePanel(new ImageIcon(
							"resources/White horse.png").getImage());
					this.gameBoardPanel.add(whiteHorsePanel);
					whiteHorsePanel.setRow(row);
					whiteHorsePanel.setColumn(column);
					whiteHorsePanel.setLocation(whiteHorsePanel.getPixelX(),
							whiteHorsePanel.getPixelY());
					whiteHorsePanel.setVisible(true);
					this.boardPieces[row][column] = whiteHorsePanel;
					break;
				case 'D':
					ImagePanel whiteDogPanel = new ImagePanel(new ImageIcon(
							"resources/White dog.png").getImage());
					this.gameBoardPanel.add(whiteDogPanel);
					whiteDogPanel.setRow(row);
					whiteDogPanel.setColumn(column);
					whiteDogPanel.setLocation(whiteDogPanel.getPixelX(),
							whiteDogPanel.getPixelY());
					whiteDogPanel.setVisible(true);
					this.boardPieces[row][column] = whiteDogPanel;
					break;
				case 'K':
					ImagePanel whiteCatPanel = new ImagePanel(new ImageIcon(
							"resources/White cat.png").getImage());
					this.gameBoardPanel.add(whiteCatPanel);
					whiteCatPanel.setRow(row);
					whiteCatPanel.setColumn(column);
					whiteCatPanel.setLocation(whiteCatPanel.getPixelX(),
							whiteCatPanel.getPixelY());
					whiteCatPanel.setVisible(true);
					this.boardPieces[row][column] = whiteCatPanel;
					break;
				case 'R':
					ImagePanel whiteRabbitPanel = new ImagePanel(new ImageIcon(
							"resources/White rabbit.png").getImage());
					this.gameBoardPanel.add(whiteRabbitPanel);
					whiteRabbitPanel.setRow(row);
					whiteRabbitPanel.setColumn(column);
					whiteRabbitPanel.setLocation(whiteRabbitPanel.getPixelX(),
							whiteRabbitPanel.getPixelY());
					whiteRabbitPanel.setVisible(true);
					this.boardPieces[row][column] = whiteRabbitPanel;
					break;
				case 'e':
					ImagePanel blackElephantPanel = new ImagePanel(
							new ImageIcon("resources/Black elephant.png")
									.getImage());
					this.gameBoardPanel.add(blackElephantPanel);
					blackElephantPanel.setRow(row);
					blackElephantPanel.setColumn(column);
					blackElephantPanel.setLocation(
							blackElephantPanel.getPixelX(),
							blackElephantPanel.getPixelY());
					blackElephantPanel.setVisible(true);
					this.boardPieces[row][column] = blackElephantPanel;
					break;
				case 'c':
					ImagePanel blackCamelPanel = new ImagePanel(new ImageIcon(
							"resources/Black camel.png").getImage());
					this.gameBoardPanel.add(blackCamelPanel);
					blackCamelPanel.setRow(row);
					blackCamelPanel.setColumn(column);
					blackCamelPanel.setLocation(blackCamelPanel.getPixelX(),
							blackCamelPanel.getPixelY());
					blackCamelPanel.setVisible(true);
					this.boardPieces[row][column] = blackCamelPanel;
					break;
				case 'h':
					ImagePanel blackHorsePanel = new ImagePanel(new ImageIcon(
							"resources/Black horse.png").getImage());
					this.gameBoardPanel.add(blackHorsePanel);
					blackHorsePanel.setRow(row);
					blackHorsePanel.setColumn(column);
					blackHorsePanel.setLocation(blackHorsePanel.getPixelX(),
							blackHorsePanel.getPixelY());
					blackHorsePanel.setVisible(true);
					this.boardPieces[row][column] = blackHorsePanel;
					break;
				case 'd':
					ImagePanel blackDogPanel = new ImagePanel(new ImageIcon(
							"resources/Black dog.png").getImage());
					this.gameBoardPanel.add(blackDogPanel);
					blackDogPanel.setRow(row);
					blackDogPanel.setColumn(column);
					blackDogPanel.setLocation(blackDogPanel.getPixelX(),
							blackDogPanel.getPixelY());
					blackDogPanel.setVisible(true);
					this.boardPieces[row][column] = blackDogPanel;
					break;
				case 'k':
					ImagePanel blackCatPanel = new ImagePanel(new ImageIcon(
							"resources/Black cat.png").getImage());
					this.gameBoardPanel.add(blackCatPanel);
					blackCatPanel.setRow(row);
					blackCatPanel.setColumn(column);
					blackCatPanel.setLocation(blackCatPanel.getPixelX(),
							blackCatPanel.getPixelY());
					blackCatPanel.setVisible(true);
					this.boardPieces[row][column] = blackCatPanel;
					break;
				case 'r':
					ImagePanel blackRabbitPanel = new ImagePanel(new ImageIcon(
							"resources/Black rabbit.png").getImage());
					this.gameBoardPanel.add(blackRabbitPanel);
					blackRabbitPanel.setRow(row);
					blackRabbitPanel.setColumn(column);
					blackRabbitPanel.setLocation(blackRabbitPanel.getPixelX(),
							blackRabbitPanel.getPixelY());
					blackRabbitPanel.setVisible(true);
					this.boardPieces[row][column] = blackRabbitPanel;
					break;
				default:
				}
			}
		}
	}

	public void renderBoard() {
		for (int i = 0; i < 8; i++) {
			for (int k = 0; k < 8; k++) {
				if (boardPieces[i][k] != null)
					this.gameBoardPanel.remove(this.boardPieces[i][k]);
				this.boardPieces[i][k] = null;
			}
		}
		moveCountLabel.setText("<html> <b>" + "Moves Left: \n"
				+ game.getNumMoves() + "</b></html>");
		turnCountLabel.setText("<html> <b>" + "Turn: " + game.getTurnCounter()
				+ "</b></html>");
		if (game.getPlayerTurn() == 1) {
			turnIndicatorLabel.setText("<html> <b>" + game.getP1Name()
					+ "'s turn" + "</b></html>");
		} else {
			turnIndicatorLabel.setText("<html> <b>" + game.getP2Name()
					+ "'s turn" + "</b></html>");
		}
		renderInitialBoard();
	}

	public void addConnectionHandler(ConnectionHandler connectionHandler) {
		this.connectionHandler = connectionHandler;
	}

	// ACTION LISTENERS
	public class CancelListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			JFrame settingsFrame = activeFrames.get(1);
			activeFrames.remove(1);
			settingsFrame.dispose();
		}
	}



	public class SaveGameListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			File selectedFile = null;
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setCurrentDirectory(new File(System
					.getProperty("user.home")));
			int result = fileChooser.showOpenDialog(gameBoardPanel);
			if (result == JFileChooser.APPROVE_OPTION) {
				selectedFile = fileChooser.getSelectedFile();
				FileWriter fw = null;
				try {
					fw = new FileWriter(selectedFile);
				} catch (IOException e) {
					// Shouldn't ever happen...
					System.out.println("No file selected!");
				}
				game.saveFile(fw);
			}
		}
	}

	public class UndoListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			game.undoMove();
			renderBoard();
		}
	}



	public void createWinWindow() {
		String playerName = "";
		if (this.game.getWinner() == 1)
			playerName = game.getP1Name();
		else if (this.game.getWinner() == 2)
			playerName = game.getP2Name();

		JFrame winnerFrame = new JFrame();
		activeFrames.add(winnerFrame);
		winnerFrame.setTitle("Winner!");
		winnerFrame.setLocation(650 / 2 - 324 / 2 + 5, 650 / 2 - 324 / 2
				+ 44);
		winnerFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		winnerFrame.setVisible(true);

		ImagePanel panel = new ImagePanel(new ImageIcon(
				"resources/BoardStoneBigCropped.jpg").getImage());
		winnerFrame.getContentPane().add(panel);
		winnerFrame.pack();
		panel.setVisible(true);


		// Set Up winner name Label
		JLabel winnerLabel = new JLabel();
		winnerLabel.setText("<html> <div style=\"text-align: center;\"> <b>" + playerName + " Wins!"
				+ "</b></html>");
		winnerLabel.setForeground(Color.WHITE);
		Font winnerFont = winnerLabel.getFont();
		winnerLabel.setFont(new Font(winnerFont.getName(), 4, 24));
		winnerLabel.setSize(150, 150);
		panel.add(winnerLabel);
		winnerLabel
				.setLocation(winnerFrame.getWidth() / 2 - 75, winnerFrame.getHeight() / 2 - 87);
		winnerLabel.setVisible(true);
	}
}

