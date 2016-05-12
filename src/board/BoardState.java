package board;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import piece.AbstractPiece;
import piece.Camel;
import piece.Cat;
import piece.Dog;
import piece.Elephant;
import piece.Horse;
import piece.Owner;
import piece.Rabbit;

/**
 * This class represents the states of the board.
 * 
 * @author shellajt
 *
 */

public class BoardState implements Serializable {
	private static final long serialVersionUID = 5283869533255607044L;
	public static final int MAX_BOARD_SIZE = 8;

	private HashMap<Coordinate, AbstractPiece> pieces;

	public BoardState() {
		this.pieces = new HashMap<Coordinate, AbstractPiece>();
		this.setUpDefaultBoardConfiguration();
	}

	public BoardState(BoardState b) {
		this.pieces = new HashMap<Coordinate, AbstractPiece>(b.getPieces());
	}

	public BoardState(HashMap<Coordinate, AbstractPiece> pieces) {
		this.pieces = pieces;
	}

	public HashMap<Coordinate, AbstractPiece> getPieces() {
		return this.pieces;
	}

	private void setUpDefaultBoardConfiguration() {
		this.pieces.put(new Coordinate(0, 0), new Cat(Owner.Player1));
		this.pieces.put(new Coordinate(1, 0), new Dog(Owner.Player1));
		this.pieces.put(new Coordinate(2, 0), new Horse(Owner.Player1));
		this.pieces.put(new Coordinate(3, 0), new Camel(Owner.Player1));
		this.pieces.put(new Coordinate(4, 0), new Elephant(Owner.Player1));
		this.pieces.put(new Coordinate(5, 0), new Horse(Owner.Player1));
		this.pieces.put(new Coordinate(6, 0), new Dog(Owner.Player1));
		this.pieces.put(new Coordinate(7, 0), new Cat(Owner.Player1));

		this.pieces.put(new Coordinate(0, 1), new Rabbit(Owner.Player1));
		this.pieces.put(new Coordinate(1, 1), new Rabbit(Owner.Player1));
		this.pieces.put(new Coordinate(2, 1), new Rabbit(Owner.Player1));
		this.pieces.put(new Coordinate(3, 1), new Rabbit(Owner.Player1));
		this.pieces.put(new Coordinate(4, 1), new Rabbit(Owner.Player1));
		this.pieces.put(new Coordinate(5, 1), new Rabbit(Owner.Player1));
		this.pieces.put(new Coordinate(6, 1), new Rabbit(Owner.Player1));
		this.pieces.put(new Coordinate(7, 1), new Rabbit(Owner.Player1));

		this.pieces.put(new Coordinate(0, 7), new Cat(Owner.Player2));
		this.pieces.put(new Coordinate(1, 7), new Dog(Owner.Player2));
		this.pieces.put(new Coordinate(2, 7), new Horse(Owner.Player2));
		this.pieces.put(new Coordinate(3, 7), new Camel(Owner.Player2));
		this.pieces.put(new Coordinate(4, 7), new Elephant(Owner.Player2));
		this.pieces.put(new Coordinate(5, 7), new Horse(Owner.Player2));
		this.pieces.put(new Coordinate(6, 7), new Dog(Owner.Player2));
		this.pieces.put(new Coordinate(7, 7), new Cat(Owner.Player2));

		this.pieces.put(new Coordinate(0, 6), new Rabbit(Owner.Player2));
		this.pieces.put(new Coordinate(1, 6), new Rabbit(Owner.Player2));
		this.pieces.put(new Coordinate(2, 6), new Rabbit(Owner.Player2));
		this.pieces.put(new Coordinate(3, 6), new Rabbit(Owner.Player2));
		this.pieces.put(new Coordinate(4, 6), new Rabbit(Owner.Player2));
		this.pieces.put(new Coordinate(5, 6), new Rabbit(Owner.Player2));
		this.pieces.put(new Coordinate(6, 6), new Rabbit(Owner.Player2));
		this.pieces.put(new Coordinate(7, 6), new Rabbit(Owner.Player2));
	}

	public Set<Coordinate> getAllCoordinates() {
		return this.pieces.keySet();
	}

	// this method will only move the piece if there is a piece at the old coor, and no piece at the new coor
	// it will return true if it made a move and false if it did not
	public boolean movePiece(Coordinate oldCoor, Coordinate newCoor) {
		if (oldCoor.isValid() && newCoor.isValid() && this.isPieceAt(oldCoor) && !this.isPieceAt(newCoor)) {
			this.pieces.put(newCoor, this.getPieceAt(oldCoor));
			this.pieces.remove(oldCoor);
			return true;
		}
		return false;
	}

	public boolean isPieceAt(Coordinate coor) {
		if (!coor.isValid()) {
			return false;
		}
		return this.pieces.containsKey(coor);
	}

	public AbstractPiece getPieceAt(Coordinate coor) {
		if (!coor.isValid()) {
			return null;
		}
		return this.pieces.get(coor);
	}

	public boolean removePiece(Coordinate coor) {
		if (coor.isValid()) {
			return this.pieces.remove(coor) != null;
		}
		return false;
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof BoardState)) {
			return false;
		}
		BoardState other = (BoardState) o;
		return this.pieces.equals(other.getPieces());
	}

	@Override
	public int hashCode() {
		return this.pieces.hashCode();
	}

	public boolean isFrozen(Coordinate pieceToMove) {
		if (!this.isNextToStrongerPiece(pieceToMove, this.getPieceAt(pieceToMove).getOwner())
				&& this.isNextToStrongerPiece(pieceToMove, this.getPieceAt(pieceToMove).getOwner().getOtherOwner())) {
			return true;
		}
		return false;
	}

	private boolean isNextToStrongerPiece(Coordinate pieceToMove, Owner player) {
		List<Coordinate> checkList = new ArrayList<>();
		checkList.add(pieceToMove.up());
		checkList.add(pieceToMove.down());
		checkList.add(pieceToMove.left());
		checkList.add(pieceToMove.right());
		for (Coordinate coor : checkList) {
			if (isPieceAt(coor)) {
				if (coor.isValid() && getPieceAt(coor).getOwner() == player
						&& getPieceAt(coor).isStrongerThan(getPieceAt(pieceToMove))) {
					return true;
				}
			}
		}
		return false;
	}
}
