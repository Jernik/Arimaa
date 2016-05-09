package game;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Set;

import move_commands.MoveCommand;
import move_commands.RegularMove;
import piece.AbstractPiece;
import piece.Owner;
import piece.Rabbit;

public class Game implements Serializable {
	private static final long serialVersionUID = -7894027967721918280L;

	private ArrayList<MoveCommand> moves = new ArrayList<MoveCommand>();
	private BoardState currentBoard;
	private int turnNumber;
	private int turnCounter = 0;

	// 0 is nobody, 1 is player1, 2 is player2
	private int playerTurn = 1;
	private int winner = 0;
	private int numMoves = 4;

	private String p1Name = "Player1";
	private String p2Name = "Player2";

	private int moveTimer = 0;

	/**
	 * Creates a board with a default starting layout
	 */
	public Game() {
		this(new BoardState());
	}

	public Game(BoardState b) {
		this.moves = new ArrayList<MoveCommand>();
		this.currentBoard = b;
		this.turnNumber = 0;
		this.turnCounter = 0;

		this.playerTurn = 1;
		this.winner = 0;
		this.numMoves = 4;

		this.p1Name = "Player1";
		this.p2Name = "Player2";

		this.moveTimer = 0;
	}

	public Game(Game g) {
		this.moves = new ArrayList<MoveCommand>(g.getMoves());
		this.currentBoard = new BoardState(g.getBoardState());
		this.turnNumber = g.getTurnNumber();

		this.playerTurn = g.getPlayerTurn();
		this.winner = g.getWinner();
		this.numMoves = g.getNumMoves();

		this.p1Name = g.getP1Name();
		this.p2Name = g.getP2Name();

		this.moveTimer = g.getMoveTimer();
		this.turnCounter = g.getTurnCounter();
	}

	public ArrayList<MoveCommand> getMoves() {
		return this.moves;
	}

	public BoardState getBoardState() {
		return this.currentBoard;
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

	public int getPlayerTurn() {
		return playerTurn;
	}

	public void setPlayerTurn(int playerTurn) {
		this.playerTurn = playerTurn;
	}

	public int getWinner() {
		return winner;
	}

	public void setWinner(int winner) {
		this.winner = winner;
	}

	public int getNumMoves() {
		return numMoves;
	}

	public String getP1Name() {
		return this.p1Name;
	}

	public void setP1Name(String p1Name) {
		this.p1Name = p1Name;
	}

	public String getP2Name() {
		return this.p2Name;
	}

	public void setP2Name(String p2Name) {
		this.p2Name = p2Name;
	}

	public int getMoveTimer() {
		return moveTimer;
	}

	public void setMoveTimer(int moveTimer) {
		this.moveTimer = moveTimer;
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

	public AbstractPiece getPieceAt(Coordinate coor) {
		return this.currentBoard.getPieceAt(coor);
	}

	public boolean isPieceAt(Coordinate coor) {
		return this.currentBoard.isPieceAt(coor);
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
	 *            Coordinate of the position that either the opponent's piece will be pushed into or the position the
	 *            owner's piece will be moved into.
	 * @return Returns true when a push or pull with the given 3 Coordinate objects would result in a valid move.
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
							this.getOwner(), this.numMoves);
					RegularMove theirPiece = new RegularMove(this.currentBoard, opponentPiece, ownerPiece,
							this.getOwner(), this.numMoves);
					pushOrPullMove(yourPiece, theirPiece);
					return true;
					// Or is it next to their piece?
				} else if (opponentPiece.isOrthogonallyAdjacentTo(destination)) {
					RegularMove theirPiece = new RegularMove(this.currentBoard, opponentPiece, destination,
							this.getOwner(), this.numMoves);
					RegularMove yourPiece = new RegularMove(this.currentBoard, ownerPiece, opponentPiece,
							this.getOwner(), this.numMoves);
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
	 * checks both rows for rabbits of the opposite side, top row first followed by the bottom row
	 */
	private void checkWin() {
		if (this.getPlayerTurn() == 0) {
			return;
		}
		Owner playerTwo = Owner.Player2;
		for (int i = 0; i < 8; i++) {
			if (this.currentBoard.isPieceAt(new Coordinate(i, 0))) {
				if (this.currentBoard.getPieceAt(new Coordinate(i, 0)).equals(new Rabbit(playerTwo))) {
					winner = 2;
					return;
				}
			}
		}
		Owner playerOne = Owner.Player1;
		for (int i = 0; i < 8; i++) {
			if (this.currentBoard.isPieceAt(new Coordinate(i, 7))) {
				if (this.currentBoard.getPieceAt(new Coordinate(i, 7)).equals(new Rabbit(playerOne))) {
					winner = 1;
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
			winner = 2;
			return;
		}
		if (!player2Exists) {
			winner = 1;
			return;
		}
	}

	/**
	 * Piece death occurs when pieces are on the squares (2,2), (2,5), (5,2), (5,5), and has no friendly adjacent pieces
	 * to it
	 */
	private void checkDeaths(Coordinate toCheck) {
		if (!this.currentBoard.isPieceAt((toCheck)))
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

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Game)) {
			return false;
		}
		Game g = (Game) o;
		boolean historyEqual = this.moves.equals(g.getMoves()) && this.currentBoard.equals(g.getBoardState())
				&& this.turnNumber == g.getTurnNumber() && this.turnCounter == g.getTurnCounter();
		boolean p1Equal = this.p1Name.equals(g.getP1Name());
		boolean p2Equal = this.p2Name.equals(g.getP2Name());
		boolean turnEqual = this.playerTurn == g.getPlayerTurn() && this.numMoves == g.getNumMoves()
				&& this.winner == g.getWinner();
		boolean timerEqual = this.moveTimer == g.getMoveTimer();

		return timerEqual && turnEqual && p1Equal && p2Equal && historyEqual;
	}

	@Override
	public int hashCode() {
		return this.moves.hashCode() + this.currentBoard.hashCode() + getHashCode(this.turnNumber)
				+ getHashCode(this.turnCounter) + this.p1Name.hashCode() + this.p2Name.hashCode()
				+ getHashCode(this.playerTurn) + getHashCode(this.numMoves) + getHashCode(this.winner)
				+ getHashCode(this.moveTimer);
	}

	// this prevents collisions better with serial numbers
	private int getHashCode(int numb) {
		return Integer.rotateLeft(numb, Integer.BYTES / 2);
	}
}
