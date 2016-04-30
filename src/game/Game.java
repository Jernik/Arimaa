package game;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Set;

import move_commands.MoveCommand;
import move_commands.RegularMove;
import piece.AbstractPiece;
import piece.Owner;
import piece.Rabbit;

public class Game {
	private ArrayList<MoveCommand> moves = new ArrayList<MoveCommand>();
	public BoardState currentBoard = null;
	private int turnNumber;

	int moveTimer = 0;
	int p1TimeBank = 0;
	int p2TimeBank = 0;
	int turnCounter = 0;

	String p1Name = "Player1";
	String p2Name = "Player2";
	// 0 is nobody, 1 is player1, 2 is player2
	private int winner = 0;
	private int numMoves = 4;
	private int playerTurn = 1;

	/**
	 * Creates a board with a default starting layout
	 */
	public Game() {
		currentBoard = new BoardState();
	}

	public Game(BoardState b) {
		currentBoard = b;
	}

	public BoardState getBoardState() {
		return this.currentBoard;
	}

	public int getMoveTimer() {
		return moveTimer;
	}

	public void setMoveTimer(int moveTimer) {
		this.moveTimer = moveTimer;
	}

	public void setP1Name(String p1Name) {
		this.p1Name = p1Name;
	}

	public void setP2Name(String p2Name) {
		this.p2Name = p2Name;
	}

	public int getTurnNumber() {
		return turnNumber;
	}

	public void setTurnNumber(int turnNumber) {
		this.turnNumber = turnNumber;
	}

	public void incrementTurn() {
		this.turnNumber++;
	}

	public int getTurnCounter() {
		return this.turnCounter;
	}

	public String getP1Name() {
		return this.p1Name;
	}

	public String getP2Name() {
		return this.p2Name;
	}

	public void setWinner(int winner) {
		this.winner = winner;
	}

	public int getNumMoves() {
		return numMoves;
	}

	public int getTurnTimer() {
		return moveTimer;
	}

	public int getWinner() {
		return winner;
	}

	public int getPlayerTurn() {
		return playerTurn;
	}

	public Owner getOwner() {
		return Owner.values()[(getPlayerTurn() - 1)];
	}

	public Owner getOtherOwner() {
		if (getPlayerTurn() == 1) {
			return Owner.values()[1];
		} else {
			return Owner.values()[0];
		}
	}

	public void setPlayerTurn(int playerTurn) {
		this.playerTurn = playerTurn;
	}

	public AbstractPiece getPieceAt(Coordinate coor) {
		return this.currentBoard.getPieceAt(coor);
	}

	// refactor for future pull request
	public boolean checkCoor(int row, int column) {
		return this.checkCoor(new Coordinate(row, column));
	}

	public boolean checkCoor(Coordinate coor) {
		return this.currentBoard.pieceAt(coor);
	}

	public boolean move(MoveCommand m) {
		if (!m.isValidMove()) {
			return false;
		} else {
			this.currentBoard = m.execute();
			this.moves.add(m);
			endMove();
			return true;
		}
	}

	/**
	 * 
	 * @param ownerPiece
	 *            Coordinate of the owner's piece
	 * @param opponentPiece
	 *            Coordinate of the opponent's piece
	 * @param destination
	 *            Coordinate of the position that either the opponent's piece
	 *            will be pushed into or the position the owner's piece will be
	 *            moved into.
	 * @return Returns true when a push or pull with the given 3 Coordinate
	 *         objects would result in a valid move.
	 */

	public boolean pushOrPull(Coordinate ownerPiece, Coordinate opponentPiece, Coordinate destination) {
		// Do you have enough moves?
		if (this.numMoves >= 2) {
			// Is the first piece yours, the second theirs, are they
			// orthonally adjacent, and is your piece higher precedence than
			// theirs?
			if ((this.currentBoard.getPieceAt(ownerPiece)).getOwner() == this.getOwner()
					&& (this.currentBoard.getPieceAt(opponentPiece).getOwner() != this.getOwner())
					&& (ownerPiece.isOrthogonallyAdjacentTo(opponentPiece))
					&& (this.checkStrongerAdjacent(ownerPiece, opponentPiece))) {
				// Is the destination next to your piece?
				if (ownerPiece.isOrthogonallyAdjacentTo(destination)) {
					RegularMove yourPiece = new RegularMove(this.currentBoard, ownerPiece, destination,
							this.getOwner());
					RegularMove theirPiece = new RegularMove(this.currentBoard, opponentPiece, ownerPiece,
							this.getOwner());
					pushOrPullMove(yourPiece, theirPiece);
					return true;
					// Or is it next to their piece?
				} else if (opponentPiece.isOrthogonallyAdjacentTo(destination)) {
					RegularMove theirPiece = new RegularMove(this.currentBoard, opponentPiece, destination,
							this.getOwner());
					RegularMove yourPiece = new RegularMove(this.currentBoard, ownerPiece, opponentPiece,
							this.getOwner());
					pushOrPullMove(theirPiece, yourPiece);
					return true;
				} else
					// Neither, so that isn't a legal move
					return false;
			}
		}
		// Not enough moves remaining
		return false;
	}

	private void pushOrPullMove(MoveCommand m1, MoveCommand m2) {
		this.currentBoard = m1.execute();
		this.currentBoard = m2.execute();
		this.moves.add(m1);
		this.moves.add(m2);
		this.numMoves--;
		endMove();
	}

	/**
	 * This methods checks piece death and victory conditions
	 */
	private void endMove() {
		checkDeaths(new Coordinate(2, 2));
		checkDeaths(new Coordinate(2, 5));
		checkDeaths(new Coordinate(5, 2));
		checkDeaths(new Coordinate(5, 5));
		checkWin();
		numMoves--;
		if (numMoves <= 0) {
			if (getPlayerTurn() == 1) {
				setPlayerTurn(2);
			} else {
				setPlayerTurn(1);
			}
			numMoves = 4;
			turnCounter++;
		}
	}

	/**
	 * checks both rows for rabbits of the opposite side, top row first followed
	 * by the bottom row
	 */
	private void checkWin() {
		if (this.getPlayerTurn() == 0) {
			return;
		}

		//FIXME: I'll delete these comments after they've been acknowledged. 
		
		// Owner lastPlayer = this.getPlayerTurn() == 1 ? Owner.Player1 :
		// Owner.Player2;
		// Doing it ^ this way actually doesn't make sense. Why should we ever
		// check to see if anyone OTHER
		// than player2 has a rabbit in the top row? That's not a win condition
		// for player 1, so lastPlayer
		// should never be capable of being player 1.
		Owner lastPlayer = Owner.Player2;
		for (int i = 0; i < 8; i++) {
			if (this.currentBoard.pieceAt(new Coordinate(i, 0))) {
				if (this.currentBoard.getPieceAt(new Coordinate(i, 0)).equals(new Rabbit(lastPlayer))) {
					winner = 2; // I hate to do this, but using player turn
								// would be inconsistent since winning is not
								// dependent on whose turn it currently is
					return;
				}
			}
		}

		// Owner otherPlayer = this.getPlayerTurn() == 1 ? Owner.Player2 :
		// Owner.Player1;
		// Same thing here.
		Owner otherPlayer = Owner.Player1;
		for (int i = 0; i < 8; i++) {
			if (this.currentBoard.pieceAt(new Coordinate(i, 7))) {
				if (this.currentBoard.getPieceAt(new Coordinate(i, 7)).equals(new Rabbit(otherPlayer))) {
					// Mapping from 1->2, 2->1
					winner = 1; //Same deal here. 
					return;
				}
			}
		}

		boolean player1Exists = false;
		boolean player2Exists = false;
		Set<Coordinate> coors = this.currentBoard.getAllCoordinates();

		for (Coordinate coor : coors) {
			AbstractPiece piece = this.currentBoard.getPieceAt(coor);
			if (piece instanceof Rabbit) {
				if (piece.getOwner() == Owner.Player1) {
					player1Exists = true;
				} else if (piece.getOwner() == Owner.Player2) {
					player2Exists = true;
				}
			}
		}
		if (!player1Exists) {
			winner = 2; //same deal as above
			return;
		}
		if (!player2Exists) {
			winner = 1; //same deal as above
			return;
		}
	}

	/**
	 * Piece death occurs when pieces are on the squares (2,2), (2,5), (5,2),
	 * (5,5), and has no friendly adjacent pieces to it
	 */
	private void checkDeaths(Coordinate toCheck) {
		if (!this.currentBoard.pieceAt((toCheck)))
			return;// an empty piece doesn't need to be checked

		if (checkFriendlyAdjacent(toCheck)) {
			return;
		}
		// no adjacent friendly pieces, remove this one
		this.currentBoard.removePiece(toCheck);
	}

	public boolean checkFriendlyAdjacent(Coordinate coor) {
		AbstractPiece cen = this.getPieceAt(coor);
		AbstractPiece up = this.getPieceAt(coor.up());
		AbstractPiece down = this.getPieceAt(coor.down());
		AbstractPiece left = this.getPieceAt(coor.left());
		AbstractPiece right = this.getPieceAt(coor.right());
		Owner own = cen.getOwner();
		if (up != null) {
			if (up.getOwner() == own)
				return true;
		}
		if (down != null) {
			if (down.getOwner() == own)
				return true;
		}
		if (right != null) {
			if (right.getOwner() == own)
				return true;
		}
		if (left != null) {
			if (left.getOwner() == own)
				return true;
		}
		return false;
	}

	public boolean checkStrongerAdjacent(Coordinate first, Coordinate second) {
		AbstractPiece firstPiece = this.currentBoard.getPieceAt(first);
		AbstractPiece secondPiece = this.currentBoard.getPieceAt(second);
		return (firstPiece.getRank() > secondPiece.getRank())
				&& (this.currentBoard.getPieceAt(first) != this.currentBoard.getPieceAt(second));
	}

	public void undoMove() {
		if (this.numMoves == 4)
			return;

		this.currentBoard = this.moves.get(this.moves.size() - (4 - this.numMoves)).getOriginalBoard();
		this.moves.remove(this.moves.size() - (4 - this.numMoves));

		this.numMoves = 4;
	}

	// doesn't work now, leave for another pull request
	public boolean loadFile(Scanner scanner) {
		scanner.useDelimiter(",");
		BoardState boardToSet = new BoardState(); // so it compiles
		String[] validBoardCharactersArray = { " ", "E", "C", "H", "D", "K", "R", "e", "c", "h", "d", "k", "r" };
		ArrayList<String> vbc = new ArrayList<String>();
		for (String s : validBoardCharactersArray) {
			vbc.add(s);
		}

		for (int i = 0; i < 8; i++) {
			for (int k = 0; k < 8; k++) {
				if (!scanner.hasNext()) {
					scanner.close();
					return false;
				}
				String next = scanner.next();
				if (!vbc.contains(next)) {
					scanner.close();
					return false;
				}
				// boardToSet.setBoardSpace(i, k, next);
			}
		}

		if (!scanner.hasNext()) {
			scanner.close();
			return false;
		}
		int turnCounter = scanner.nextInt();

		if (!scanner.hasNext()) {
			scanner.close();
			return false;
		}
		int turnTimer = scanner.nextInt();

		if (!scanner.hasNext()) {
			scanner.close();
			return false;
		}
		String p1name = scanner.next();

		if (!scanner.hasNext()) {
			scanner.close();
			return false;
		}
		String p2name = scanner.next();

		scanner.close();

		// Successful load! Push all changes to game permanently
		this.currentBoard = boardToSet;
		this.turnCounter = turnCounter;
		this.moveTimer = turnTimer;
		this.p1Name = p1name;
		this.p2Name = p2name;

		if (this.turnCounter % 2 == 1) {
			this.playerTurn = 2;
		} else {
			this.playerTurn = 1;
		}
		return true;
	}

	public boolean saveFile(FileWriter fw) {
		// TODO: Update to use serializable game states instead of char arrays
		if (fw == null)
			return false;
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				String s = ""; // + this.currentBoard.getBoardArray()[i][j] +
								// ",";
				try {
					fw.write(s);
				} catch (IOException e) {
					return false;
				}
			}
		}

		String s2 = "" + this.turnCounter + ",";

		try {
			fw.write(s2);
			fw.write(this.moveTimer + ",");
			fw.write(this.p1Name + ",");
			fw.write(this.p2Name);
			fw.close();
		} catch (IOException e) {
			return false;
		}
		return true;
	}
}
