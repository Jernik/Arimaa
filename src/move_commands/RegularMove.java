package move_commands;

import game.BoardState;
import game.Coordinate;
import piece.AbstractPiece;
import piece.Owner;
import piece.Rabbit;

public class RegularMove extends MoveCommand {

	private BoardState newBoard;
	private Coordinate originalPlace;
	private Coordinate newPlace;

	public RegularMove(BoardState board, Coordinate originalPlace, Coordinate newPlace, Owner turn) {
		this.turn = turn;
		this.originalBoard = board.clone();
		this.newBoard = board;
		this.originalPlace = originalPlace;
		this.newPlace = newPlace;
	}

	@Override
	public BoardState execute() {
		if (!isValidMove()) {
			return this.originalBoard;
		}
		this.newBoard.movePiece(originalPlace, newPlace);
		return newBoard;
	}

	@Override
	public BoardState getOriginalBoard() {
		return originalBoard;
	}

	@Override
	public boolean isValidMove() {
		AbstractPiece piece = this.originalBoard.getPieceAt(originalPlace);
		if (isFrozen(originalPlace)) {
			return false;
		}
		if (this.originalBoard.pieceAt(newPlace)) {
			return false;
		}
		if ((piece instanceof Rabbit)) {
			if (((piece.getOwner() == Owner.values()[0]) && (newPlace.equals(originalPlace.up())))
					|| ((piece.getOwner() == Owner.values()[1]) && (newPlace.equals(originalPlace.down())))) {
				// Cannot move a Rabbit backwards unless it has been dragged
				return false;
			}
		}
		if (!(originalPlace.isOrthogonallyAdjacentTo(newPlace)
				&& this.originalBoard.getPieceAt(originalPlace).getOwner() == this.turn)) {
			return false;
		}
		return true;
	}

	// @Override
	// public boolean isValidMove() {
	// int row = this.newPlace.getX();
	// int column = this.newPlace.getY();
	// if (row >= 0 && row < 8 && column >= 0 && column < 8
	// && !originalBoard.pieceAt(this.newPlace))
	// return true;
	// return false;
	// }

	public Coordinate getOriginalPlace() {
		return this.originalPlace;
	}

}
