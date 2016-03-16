package game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class GUI {
	private String p1Name;
	private String p2Name;
	private ArrayList<JFrame> activeFrames;
	private Game game;
	private ImagePanel gameBoardPanel = null;
	private ImagePanel[][] boardPieces;
	private JTextField p1TextField;
	private JTextField p2TextField;
	private JComboBox<Integer> timerComboBox;
	private JLabel moveCountLabel;
	private JLabel turnCountLabel;
	private JLabel turnIndicatorLabel;
	private JLabel timerLabel;
	private TimePanel timer;

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

	public static void main(String[] args) {
		GUI g = new GUI();

		// Add MAIN MENU panel with appropriate background image
		ImagePanel panel = new ImagePanel(new ImageIcon(
				"resources/BoardStoneBig.jpg").getImage());
		g.activeFrames.get(0).getContentPane().add(panel);
		g.activeFrames.get(0).pack();
		panel.setVisible(true);

		// Add the NEW GAME button to the Main Menu
		JButton newGameButton = new JButton();
		newGameButton.setSize(150, 75);
		newGameButton.setText("New Game");
		Font newGameFont = newGameButton.getFont();
		newGameButton.setFont(new Font(newGameFont.getName(), 4, 20));
		newGameButton.setLocation((panel.getWidth() / 4) - 35,
				(panel.getHeight() / 2) - 37);
		panel.add(newGameButton);
		newGameButton.setVisible(true);

		// Setup ActionListener for NEW GAME button
		newGameButton.addActionListener(g.new newGameListener());

		// Add the LOAD GAME button to the Main Menu
		JButton loadGameButton = new JButton();
		loadGameButton.setSize(150, 75);
		loadGameButton.setText("Load Game");
		Font loadGameFont = loadGameButton.getFont();
		loadGameButton.setFont(new Font(loadGameFont.getName(), 4, 20));
		loadGameButton.setLocation((panel.getWidth() / 4) * 3 - 110,
				(panel.getHeight() / 2) - 37);
		panel.add(loadGameButton);
		loadGameButton.setVisible(true);

		// Setup ActionListener for the LOAD GAME button
		loadGameButton.addActionListener(g.new loadGameListener());

		g.activeFrames.get(0).setVisible(true);
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

	private void renderInitialBoard() {
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

	protected void renderBoard() {
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

	// ACTION LISTENERS
	private class newGameListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			JFrame settingsFrame = new JFrame();
			activeFrames.add(settingsFrame);
			settingsFrame.setTitle("New Game Options");
			settingsFrame.setLocation(650 / 2 - 324 / 2 + 5, 650 / 2 - 324 / 2
					+ 44);
			settingsFrame.setVisible(true);

			ImagePanel panel = new ImagePanel(new ImageIcon(
					"resources/BoardStoneBigCropped.jpg").getImage());
			settingsFrame.getContentPane().add(panel);
			settingsFrame.pack();
			panel.setVisible(true);

			// Set up Player 1 Name Label and Text Field
			JLabel p1NameLabel = new JLabel();

			// On Mac, the bolded text causes layout issues
			p1NameLabel.setText("<html><b>Player 1 Name:</b><html>");
			p1NameLabel.setForeground(Color.WHITE);
			Font p1NameFont = p1NameLabel.getFont();
			p1NameLabel.setFont(new Font(p1NameFont.getName(), 4, 14));
			p1NameLabel.setSize(110, 25);
			panel.add(p1NameLabel);
			p1NameLabel.setLocation(
					panel.getWidth() / 2 - p1NameLabel.getWidth(),
					panel.getHeight() / 2 - p1NameLabel.getHeight() * 2);

			p1NameLabel.setVisible(true);

			JTextField p1NameField = new JTextField();
			p1NameField.setSize(110, 25);
			Font p1FieldFont = p1NameField.getFont();
			p1NameLabel.setFont(new Font(p1FieldFont.getName(), 4, 14));
			panel.add(p1NameField);
			p1NameField.setLocation(panel.getWidth() / 2, panel.getHeight() / 2
					- p1NameField.getHeight() * 2);
			p1TextField = p1NameField;
			p1NameField.setVisible(true);

			// Set up Player 2 Name Label and Text Field
			JLabel p2NameLabel = new JLabel();
			p2NameLabel.setText("<html><b>Player 2 Name:</b></hmtl>");
			p2NameLabel.setForeground(Color.WHITE);
			Font p2NameFont = p2NameLabel.getFont();
			p2NameLabel.setFont(new Font(p2NameFont.getName(), 4, 14));
			p2NameLabel.setSize(110, 25);
			panel.add(p2NameLabel);
			p2NameLabel.setLocation(
					panel.getWidth() / 2 - p2NameLabel.getWidth(),
					panel.getHeight() / 2 - p2NameLabel.getHeight());
			p2NameLabel.setVisible(true);

			JTextField p2NameField = new JTextField();
			p2NameField.setSize(110, 25);
			Font p2FieldFont = p2NameField.getFont();
			p2NameLabel.setFont(new Font(p2FieldFont.getName(), 4, 14));
			panel.add(p2NameField);
			p2NameField.setLocation(panel.getWidth() / 2, panel.getHeight() / 2
					- p2NameField.getHeight());
			p2TextField = p2NameField;
			p2NameField.setVisible(true);

			// Set up Turn Timer Label and Text Field
			JLabel turnTimerLabel = new JLabel();
			turnTimerLabel.setText("<html><b>Turn Timer:</b></html>");
			turnTimerLabel.setForeground(Color.WHITE);
			Font turnTimerFont = turnTimerLabel.getFont();
			turnTimerLabel.setFont(new Font(turnTimerFont.getName(), 4, 14));
			turnTimerLabel.setSize(110, 25);
			panel.add(turnTimerLabel);
			turnTimerLabel.setLocation(
					panel.getWidth() / 2 - turnTimerLabel.getWidth(),
					panel.getHeight() / 2);
			turnTimerLabel.setVisible(true);

			Integer[] turnTimerPresets = { 30, 45, 60, 75, 90, 105, 120, 135,
					150, 165, 180 };
			JComboBox<Integer> turnTimerComboBox = new JComboBox<Integer>(
					turnTimerPresets);
			timerComboBox = turnTimerComboBox;
			turnTimerComboBox.setEditable(false);
			turnTimerComboBox.setSize(110, 25);
			panel.add(turnTimerComboBox);
			turnTimerComboBox.setLocation(panel.getWidth() / 2,
					panel.getHeight() / 2);
			turnTimerComboBox.setVisible(true);

			// Set up Start Game Button
			JButton startGameButton = new JButton();
			startGameButton.setSize(110, 25);
			startGameButton.setText("Start Game");
			startGameButton.setLocation((panel.getWidth() / 2)
					- startGameButton.getWidth(), (panel.getHeight() / 2)
					+ (2 * startGameButton.getHeight()));
			panel.add(startGameButton);
			startGameButton.addActionListener(new startGameListener());
			startGameButton.setVisible(true);

			// Set up Cancel Button
			JButton cancelButton = new JButton();
			cancelButton.setSize(110, 25);
			cancelButton.setText("Cancel");
			cancelButton.setLocation((panel.getWidth() / 2),
					(panel.getHeight() / 2) + (2 * cancelButton.getHeight()));
			panel.add(cancelButton);
			cancelButton.addActionListener(new cancelListener());
			cancelButton.setVisible(true);
		}
	}

	private class loadGameListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setCurrentDirectory(new File(System
					.getProperty("user.home")));
			int result = fileChooser.showOpenDialog(activeFrames.get(0));
			if (result == JFileChooser.APPROVE_OPTION) {
				File selectedFile = fileChooser.getSelectedFile();
				try {
					loadGame(selectedFile);
					p1Name = game.getP1Name();
					p2Name = game.getP2Name();
				} catch (FileNotFoundException e1) {
					System.out.println("Load game failed!");
				}
			}
		}

		private void loadGame(File file) throws FileNotFoundException {
			Scanner scanner = new Scanner(file);
			if (game.loadFile(scanner)) {
				JFrame mainMenu = activeFrames.get(0);
				activeFrames.remove(0);
				mainMenu.dispose();

				JFrame gameFrame = new JFrame();
				activeFrames.add(gameFrame);
				gameFrame.setTitle("Let's Play!");
				gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

				ImagePanel panel = new ImagePanel(new ImageIcon(
						"resources/board.jpg").getImage());
				activeFrames.get(0).getContentPane().add(panel);
				activeFrames.get(0).pack();
				panel.setVisible(true);
				gameBoardPanel = panel;

				gameBoardPanel.addMouseListener(new MovementListener());
				activeFrames.get(0).setBackground(Color.BLACK);

				gameFrame.setVisible(true);

				// Set Up Player1 Label
				JLabel p1Label = new JLabel();
				p1Label.setText("<html> <b>Player 1: </b></html>");
				p1Label.setForeground(Color.BLACK);
				Font p1Font = p1Label.getFont();
				p1Label.setFont(new Font(p1Font.getName(), 4, 22));
				p1Label.setSize(110, 25);
				gameBoardPanel.add(p1Label);
				p1Label.setLocation(675, 25);
				p1Label.setVisible(true);

				// Set Up Player1 name Label
				JLabel p1NameLabel = new JLabel();
				p1NameLabel.setText("<html> <b>" + game.p1Name + "</b></html>");
				p1NameLabel.setForeground(Color.BLACK);
				Font p1NameFont = p1NameLabel.getFont();
				p1NameLabel.setFont(new Font(p1NameFont.getName(), 4, 18));
				p1NameLabel.setSize(110, 100);
				gameBoardPanel.add(p1NameLabel);
				p1NameLabel.setLocation(675, 25);
				p1NameLabel.setVisible(true);

				// Set Up Player2 Label
				JLabel p2Label = new JLabel();
				p2Label.setText("<html> <b>Player 2: </b></html>");
				p2Label.setForeground(Color.BLACK);
				Font p2Font = p2Label.getFont();
				p2Label.setFont(new Font(p2Font.getName(), 4, 22));
				p2Label.setSize(110, 25);
				gameBoardPanel.add(p2Label);
				p2Label.setLocation(675, 550);
				p2Label.setVisible(true);

				// Set Up Player2 name Label
				JLabel p2NameLabel = new JLabel();
				p2NameLabel.setText("<html> <b>" + game.p2Name + "</b></html>");
				p2NameLabel.setForeground(Color.BLACK);
				Font p2NameFont = p2NameLabel.getFont();
				p2NameLabel.setFont(new Font(p2NameFont.getName(), 4, 18));
				p2NameLabel.setSize(110, 100);
				gameBoardPanel.add(p2NameLabel);
				p2NameLabel.setLocation(675, 550);
				p2NameLabel.setVisible(true);

				// Set up Turn Counter label
				JLabel turnCounterLabel = new JLabel();
				turnCountLabel = turnCounterLabel;
				turnCounterLabel.setText("<html> <b>" + "Turn: "
						+ game.getTurnCounter() + "</b></html>");
				turnCounterLabel.setForeground(Color.BLACK);
				Font turnCounterFont = turnCounterLabel.getFont();
				turnCounterLabel.setFont(new Font(turnCounterFont.getName(), 4,
						18));
				turnCounterLabel.setSize(110, 25);
				gameBoardPanel.add(turnCounterLabel);
				turnCounterLabel.setLocation(675, 130);
				turnCounterLabel.setVisible(true);

				// Set up Player Turn label
				JLabel playerTurnLabel = new JLabel();
				turnIndicatorLabel = playerTurnLabel;
				if (game.getPlayerTurn() == 1) {
					playerTurnLabel.setText("<html> <b>" + game.getP1Name()
							+ "'s turn" + "</b></html>");
				} else {
					playerTurnLabel.setText("<html> <b>" + game.getP2Name()
							+ "'s turn" + "</b></html>");
				}
				playerTurnLabel.setForeground(Color.BLACK);
				Font playerTurnFont = playerTurnLabel.getFont();
				playerTurnLabel.setFont(new Font(playerTurnFont.getName(), 4,
						18));
				playerTurnLabel.setSize(110, 50);
				gameBoardPanel.add(playerTurnLabel);
				playerTurnLabel.setLocation(675, 200);
				playerTurnLabel.setVisible(true);

				// Set up move counter label
				JLabel moveCounterLabel = new JLabel();
				moveCountLabel = moveCounterLabel;
				moveCounterLabel.setText("<html> <b>" + "Moves Left: \n"
						+ game.getNumMoves() + "</b></html>");
				moveCounterLabel.setForeground(Color.BLACK);
				Font moveCounterFont = moveCounterLabel.getFont();
				moveCounterLabel.setFont(new Font(moveCounterFont.getName(), 4,
						18));
				moveCounterLabel.setSize(110, 50);
				gameBoardPanel.add(moveCounterLabel);
				moveCounterLabel.setLocation(675, 370);
				moveCounterLabel.setVisible(true);

				// Set up turn timer name label
				JLabel turnTimerNameLabel = new JLabel();
				turnTimerNameLabel.setText("<html> <b>" + "Turn Time:"
						+ "</b></html>");
				turnTimerNameLabel.setForeground(Color.BLACK);
				Font turnTimerNameFont = turnTimerNameLabel.getFont();
				turnTimerNameLabel.setFont(new Font(
						turnTimerNameFont.getName(), 4, 18));
				turnTimerNameLabel.setSize(110, 25);
				gameBoardPanel.add(turnTimerNameLabel);
				turnTimerNameLabel.setLocation(675, 450);
				turnTimerNameLabel.setVisible(true);

				// Set up actual timer label
				JLabel turnTimerLabel = new JLabel();
				timerLabel = turnTimerLabel;
				turnTimerLabel.setForeground(Color.BLACK);
				Font turnTimerFont = turnTimerLabel.getFont();
				turnTimerLabel
						.setFont(new Font(turnTimerFont.getName(), 4, 18));
				turnTimerLabel.setSize(110, 25);
				gameBoardPanel.add(turnTimerLabel);
				turnTimerLabel.setLocation(675, 475);
				turnTimerLabel.setVisible(true);

				// P1 Time Panel
				TimePanel timePanel = new TimePanel(GUI.this, game,
						game.getTurnTimer(), timerLabel);
				timer = timePanel;

				// Set up Save Game Button
				JButton saveGameButton = new JButton();
				saveGameButton.setSize(65, 75);
				saveGameButton.setText("Save");
				saveGameButton.setLocation(660, gameFrame.getHeight() / 2 - 75);
				gameBoardPanel.add(saveGameButton);
				saveGameButton.addActionListener(new SaveGameListener());
				saveGameButton.setVisible(true);

				// Set up Undo Button
				JButton undoButton = new JButton();
				undoButton.setSize(65, 75);
				undoButton.setText("Undo");
				undoButton.setLocation(730, gameFrame.getHeight() / 2 - 75);
				gameBoardPanel.add(undoButton);
				undoButton.addActionListener(new UndoListener());
				undoButton.setVisible(true);

				renderInitialBoard();
			} else {
				System.err.println("Invalid game state");
			}
		}
	}

	private class cancelListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			JFrame settingsFrame = activeFrames.get(1);
			activeFrames.remove(1);
			settingsFrame.dispose();
		}
	}

	private class startGameListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			game.p1Name = p1TextField.getText();
			game.p2Name = p2TextField.getText();
			game.moveTimer = (int) timerComboBox.getSelectedItem();

			if (game.p1Name.equals(""))
				game.p1Name = "Player 1";
			if (game.p2Name.equals(""))
				game.p2Name = "Player 2";

			JFrame settings = activeFrames.get(1);
			activeFrames.remove(1);
			settings.dispose();

			JFrame mainMenu = activeFrames.get(0);
			activeFrames.remove(0);
			mainMenu.dispose();

			JFrame gameFrame = new JFrame();
			activeFrames.add(gameFrame);
			gameFrame.setTitle("Let's Play!");
			gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

			ImagePanel panel = new ImagePanel(new ImageIcon(
					"resources/board.jpg").getImage());
			activeFrames.get(0).getContentPane().add(panel);
			activeFrames.get(0).pack();
			panel.setVisible(true);
			gameBoardPanel = panel;

			gameBoardPanel.addMouseListener(new MovementListener());
			activeFrames.get(0).setBackground(Color.BLACK);

			gameFrame.setVisible(true);
			// Set Up Player1 Label
			JLabel p1Label = new JLabel();
			p1Label.setText("<html> <b>Player 1: </b></html>");
			p1Label.setForeground(Color.BLACK);
			Font p1Font = p1Label.getFont();
			p1Label.setFont(new Font(p1Font.getName(), 4, 22));
			p1Label.setSize(110, 25);
			gameBoardPanel.add(p1Label);
			p1Label.setLocation(675, 25);
			p1Label.setVisible(true);

			// Set Up Player1 name Label
			JLabel p1NameLabel = new JLabel();
			p1NameLabel.setText("<html> <b>" + game.p1Name + "</b></html>");
			p1NameLabel.setForeground(Color.BLACK);
			Font p1NameFont = p1NameLabel.getFont();
			p1NameLabel.setFont(new Font(p1NameFont.getName(), 4, 18));
			p1NameLabel.setSize(110, 100);
			gameBoardPanel.add(p1NameLabel);
			p1NameLabel.setLocation(675, 25);
			p1NameLabel.setVisible(true);

			// Set Up Player2 Label
			JLabel p2Label = new JLabel();
			p2Label.setText("<html> <b>Player 2: </b></html>");
			p2Label.setForeground(Color.BLACK);
			Font p2Font = p2Label.getFont();
			p2Label.setFont(new Font(p2Font.getName(), 4, 22));
			p2Label.setSize(110, 25);
			gameBoardPanel.add(p2Label);
			p2Label.setLocation(675, 550);
			p2Label.setVisible(true);

			// Set Up Player2 name Label
			JLabel p2NameLabel = new JLabel();
			p2NameLabel.setText("<html> <b>" + game.p2Name + "</b></html>");
			p2NameLabel.setForeground(Color.BLACK);
			Font p2NameFont = p2NameLabel.getFont();
			p2NameLabel.setFont(new Font(p2NameFont.getName(), 4, 18));
			p2NameLabel.setSize(110, 100);
			gameBoardPanel.add(p2NameLabel);
			p2NameLabel.setLocation(675, 550);
			p2NameLabel.setVisible(true);

			// Set up Turn Counter label
			JLabel turnCounterLabel = new JLabel();
			turnCountLabel = turnCounterLabel;
			turnCounterLabel.setText("<html> <b>" + "Turn: "
					+ game.getTurnCounter() + "</b></html>");
			turnCounterLabel.setForeground(Color.BLACK);
			Font turnCounterFont = turnCounterLabel.getFont();
			turnCounterLabel
					.setFont(new Font(turnCounterFont.getName(), 4, 18));
			turnCounterLabel.setSize(110, 25);
			gameBoardPanel.add(turnCounterLabel);
			turnCounterLabel.setLocation(675, 130);
			turnCounterLabel.setVisible(true);

			// Set up Player Turn label
			JLabel playerTurnLabel = new JLabel();
			turnIndicatorLabel = playerTurnLabel;
			if (game.getPlayerTurn() == 1) {
				playerTurnLabel.setText("<html> <b>" + game.getP1Name()
						+ "'s turn" + "</b></html>");
			} else {
				playerTurnLabel.setText("<html> <b>" + game.getP2Name()
						+ "'s turn" + "</b></html>");
			}
			playerTurnLabel.setForeground(Color.BLACK);
			Font playerTurnFont = playerTurnLabel.getFont();
			playerTurnLabel.setFont(new Font(playerTurnFont.getName(), 4, 18));
			playerTurnLabel.setSize(110, 50);
			gameBoardPanel.add(playerTurnLabel);
			playerTurnLabel.setLocation(675, 200);
			playerTurnLabel.setVisible(true);

			// Set up move counter label
			JLabel moveCounterLabel = new JLabel();
			moveCountLabel = moveCounterLabel;
			moveCounterLabel.setText("<html> <b>" + "Moves Left: \n"
					+ game.getNumMoves() + "</b></html>");
			moveCounterLabel.setForeground(Color.BLACK);
			Font moveCounterFont = moveCounterLabel.getFont();
			moveCounterLabel
					.setFont(new Font(moveCounterFont.getName(), 4, 18));
			moveCounterLabel.setSize(110, 50);
			gameBoardPanel.add(moveCounterLabel);
			moveCounterLabel.setLocation(675, 370);
			moveCounterLabel.setVisible(true);

			// Set up turn timer name label
			JLabel turnTimerNameLabel = new JLabel();
			turnTimerNameLabel.setText("<html> <b>" + "Turn Time:"
					+ "</b></html>");
			turnTimerNameLabel.setForeground(Color.BLACK);
			Font turnTimerNameFont = turnTimerNameLabel.getFont();
			turnTimerNameLabel.setFont(new Font(turnTimerNameFont.getName(), 4,
					18));
			turnTimerNameLabel.setSize(110, 25);
			gameBoardPanel.add(turnTimerNameLabel);
			turnTimerNameLabel.setLocation(675, 450);
			turnTimerNameLabel.setVisible(true);

			// Set up actual timer label
			JLabel turnTimerLabel = new JLabel();
			timerLabel = turnTimerLabel;
			turnTimerLabel.setForeground(Color.BLACK);
			Font turnTimerFont = turnTimerLabel.getFont();
			turnTimerLabel.setFont(new Font(turnTimerFont.getName(), 4, 18));
			turnTimerLabel.setSize(110, 25);
			gameBoardPanel.add(turnTimerLabel);
			turnTimerLabel.setLocation(675, 475);
			turnTimerLabel.setVisible(true);

			// P1 Time Panel
			TimePanel timePanel = new TimePanel(GUI.this, game,
					(int) timerComboBox.getSelectedItem(), timerLabel);
			timer = timePanel;

			// Set up Save Game Button
			JButton saveGameButton = new JButton();
			saveGameButton.setSize(65, 75);
			saveGameButton.setText("Save");
			saveGameButton.setLocation(660, gameFrame.getHeight() / 2 - 75);
			gameBoardPanel.add(saveGameButton);
			saveGameButton.addActionListener(new SaveGameListener());
			saveGameButton.setVisible(true);

			// Set up Undo Button
			JButton undoButton = new JButton();
			undoButton.setSize(65, 75);
			undoButton.setText("Undo");
			undoButton.setLocation(730, gameFrame.getHeight() / 2 - 75);
			gameBoardPanel.add(undoButton);
			undoButton.addActionListener(new UndoListener());
			undoButton.setVisible(true);

			renderInitialBoard();
		}
	}

	private class SaveGameListener implements ActionListener {

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

	private class UndoListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			game.undoMove();
			renderBoard();
		}
	}

	private class MovementListener implements MouseListener {
		ImagePanel selectedPiece;
		ImagePanel secondSelectedPiece;

		private MovementListener() {
			this.selectedPiece = null;
			this.secondSelectedPiece = null;
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			// Not needed
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// Not needed
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// Not needed
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// Not needed
		}

		@Override
		public void mousePressed(MouseEvent e) {
			int sourceX = (int) e.getPoint().getX();
			int sourceY = (int) e.getPoint().getY();

			// Get rid of X and Y ASAP!!!
			int rowClicked = (sourceY - 10) / 80;
			int columnClicked = (sourceX - 10) / 80;

			// Beginning movement, nothing yet selected
			// Selecting piece to interact with
			if (clickOnBoard(rowClicked, columnClicked)) {

				// No piece has been selected yet
				if (noPieceSelectedAndPieceClicked(rowClicked, columnClicked)) {
					this.selectedPiece = boardPieces[rowClicked][columnClicked];
				}

				// If a piece is selected and an empty space is clicked
				// AKA move
				else if (noSelectedPieceAndEmptySpaceClicked(rowClicked,
						columnClicked)) {
					int calculatedDirection = moveDirection(selectedPiece,
							rowClicked, columnClicked);
					// Using move to check for valid move
					if (game.move(selectedPiece.getRow(),
							selectedPiece.getColumn(), calculatedDirection)) {
						renderBoard();
					}
					this.selectedPiece = null;
					this.secondSelectedPiece = null;

				}

				// Piece already selected, clicked a second piece
				else if (pieceSelectedAndSecondPieceClicked(rowClicked,
						columnClicked)) {
					this.secondSelectedPiece = boardPieces[rowClicked][columnClicked];

					// Piece selected, Second piece selected, empty square
					// selected
				} else if (twoPieceSelectedAndEmptySapceClicked(rowClicked,
						columnClicked)) {

					if (checkForPull(rowClicked, columnClicked)) {
						int calculatedDirection = moveDirection(selectedPiece,
								rowClicked, columnClicked);

						if (game.pull(this.selectedPiece.getRow(),
								this.selectedPiece.getColumn(),
								this.secondSelectedPiece.getRow(),
								this.secondSelectedPiece.getColumn(),
								calculatedDirection)) {
							renderBoard();

						}
						this.selectedPiece = null;
						this.secondSelectedPiece = null;

					} else if (checkForPush(rowClicked, columnClicked)) {
						int calculatedDirection1 = moveDirectionOnePush(
								selectedPiece, secondSelectedPiece);

						int calculatedDirection2 = moveDirectionTwoPush(
								secondSelectedPiece, rowClicked, columnClicked);

						if (game.push(this.selectedPiece.getRow(),
								this.selectedPiece.getColumn(),
								calculatedDirection1, calculatedDirection2)) {
							renderBoard();

						}
						this.selectedPiece = null;
						this.secondSelectedPiece = null;
					}
				}

				// Invalid selection, clear data
				else {
					this.selectedPiece = null;
					this.secondSelectedPiece = null;
				}
			}

		}

		private int moveDirectionTwoPush(ImagePanel secondSelectedPiece2,
				int rowClicked, int columnClicked) {
			if (secondSelectedPiece.getRow() - 1 == rowClicked
					&& secondSelectedPiece.getColumn() == columnClicked)
				return 0;
			else if (secondSelectedPiece.getColumn() + 1 == columnClicked
					&& secondSelectedPiece.getRow() == rowClicked)
				return 1;
			else if (secondSelectedPiece.getRow() + 1 == rowClicked
					&& secondSelectedPiece.getColumn() == columnClicked)
				return 2;
			else if (secondSelectedPiece.getColumn() - 1 == columnClicked
					&& secondSelectedPiece.getRow() == rowClicked)
				return 3;
			return -1; // Shouldn't ever happen
		}

		private int moveDirectionOnePush(ImagePanel selectedPiece2,
				ImagePanel secondSelectedPiece2) {
			if (selectedPiece.getRow() - 1 == secondSelectedPiece.getRow()
					&& selectedPiece.getColumn() == secondSelectedPiece
							.getColumn())
				return 0;
			else if (selectedPiece.getColumn() + 1 == secondSelectedPiece
					.getColumn()
					&& selectedPiece.getRow() == secondSelectedPiece.getRow())
				return 1;
			else if (selectedPiece.getRow() + 1 == secondSelectedPiece.getRow()
					&& selectedPiece.getColumn() == secondSelectedPiece
							.getColumn())
				return 2;
			else if (selectedPiece.getColumn() - 1 == secondSelectedPiece
					.getColumn()
					&& selectedPiece.getRow() == secondSelectedPiece.getRow())
				return 3;
			return -1; // Shouldn't ever happen
		}

		private boolean twoPieceSelectedAndEmptySapceClicked(int rowClicked,
				int columnClicked) {
			return this.selectedPiece != null
					&& this.secondSelectedPiece != null
					&& boardPieces[rowClicked][columnClicked] == null;
		}

		private boolean pieceSelectedAndSecondPieceClicked(int rowClicked,
				int columnClicked) {
			return this.selectedPiece != null
					&& this.secondSelectedPiece == null
					&& boardPieces[rowClicked][columnClicked] != null
					&& this.selectedPiece != boardPieces[rowClicked][columnClicked];
		}

		private int moveDirection(ImagePanel selectedPiece2, int rowClicked,
				int columnClicked) {
			if (selectedPiece.getRow() - 1 == rowClicked
					&& selectedPiece.getColumn() == columnClicked)
				return 0;
			else if (selectedPiece.getColumn() + 1 == columnClicked
					&& selectedPiece.getRow() == rowClicked)
				return 1;
			else if (selectedPiece.getRow() + 1 == rowClicked
					&& selectedPiece.getColumn() == columnClicked)
				return 2;
			else if (selectedPiece.getColumn() - 1 == columnClicked
					&& selectedPiece.getRow() == rowClicked)
				return 3;
			return -1; // Please never happen...
		}

		private boolean noSelectedPieceAndEmptySpaceClicked(int rowClicked,
				int columnClicked) {
			return this.selectedPiece != null
					&& this.secondSelectedPiece == null
					&& boardPieces[rowClicked][columnClicked] == null;
		}

		private boolean noPieceSelectedAndPieceClicked(int rowClicked,
				int columnClicked) {
			return boardPieces[rowClicked][columnClicked] != null
					&& this.selectedPiece == null
					&& this.secondSelectedPiece == null;
		}

		private boolean clickOnBoard(int rowClicked, int columnClicked) {
			return rowClicked <= 7 && rowClicked >= 0 && columnClicked <= 7
					&& columnClicked >= 0;
		}

		private boolean checkForPush(int rowClicked, int columnClicked) {
			if (this.secondSelectedPiece.getRow() + 1 == rowClicked
					&& this.secondSelectedPiece.getColumn() == columnClicked) {
				return true;
			}
			if (this.secondSelectedPiece.getRow() - 1 == rowClicked
					&& this.secondSelectedPiece.getColumn() == columnClicked) {
				// numMoves-=2;
				return true;
			}
			if (this.secondSelectedPiece.getRow() == rowClicked
					&& this.secondSelectedPiece.getColumn() + 1 == columnClicked) {
				// numMoves-=2;
				return true;
			}
			if (this.secondSelectedPiece.getRow() == rowClicked
					&& this.secondSelectedPiece.getColumn() - 1 == columnClicked) {
				// numMoves-=2;
				return true;
			}
			return false;
		}

		private boolean checkForPull(int rowClicked, int columnClicked) {
			if (this.selectedPiece.getRow() + 1 == rowClicked
					&& this.selectedPiece.getColumn() == columnClicked) {
				// numMoves-=2;
				return true;
			}
			if (this.selectedPiece.getRow() - 1 == rowClicked
					&& this.selectedPiece.getColumn() == columnClicked) {
				// numMoves-=2;
				return true;
			}
			if (this.selectedPiece.getRow() == rowClicked
					&& this.selectedPiece.getColumn() + 1 == columnClicked) {
				// numMoves--;
				return true;
			}
			if (this.selectedPiece.getRow() == rowClicked
					&& this.selectedPiece.getColumn() - 1 == columnClicked) {
				// numMoves--;
				return true;
			}
			return false;
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

class ImagePanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7315460075240330922L;
	private Image img;
	private int row;
	private int column;

	public ImagePanel(String img) {
		this(new ImageIcon(img).getImage());
	}

	public ImagePanel(Image img) {
		this.img = img;
		Dimension size = new Dimension(img.getWidth(null), img.getHeight(null));
		setPreferredSize(size);
		setMinimumSize(size);
		setMaximumSize(size);
		setSize(size);
		setLayout(null);
	}

	public void paintComponent(Graphics g) {
		g.drawImage(img, 0, 0, null);
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getRow() {
		return this.row;
	}

	public void setColumn(int column) {
		this.column = column;
	}

	public int getColumn() {
		return this.column;
	}

	public int getPixelX() {
		return this.column * 80 + 10;
	}

	public int getPixelY() {
		return this.row * 80 + 10;
	}

}