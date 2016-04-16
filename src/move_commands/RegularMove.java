package move_commands;

import game.BoardState;
import game.Coordinate;

public class RegularMove implements MoveCommand {

	private BoardState originalBoard;
	private BoardState newBoard;
	private Coordinate originalPlace;
	private Coordinate newPlace;

	public RegularMove(BoardState board, Coordinate originalPlace, Coordinate newPlace) {
		this.originalBoard = board.clone();
		this.newBoard = board;
		this.originalPlace = originalPlace;
		this.newPlace = newPlace;
	}

	@Override
	public BoardState execute() {
		this.newBoard.movePiece(originalPlace, newPlace);
		return newBoard;
	}

	@Override
	public BoardState getOriginalBoard() {
		return originalBoard;
	}

	@Override
	public boolean isValidMove() {
		int row = this.newPlace.getX();
		int column = this.newPlace.getY();
		if (row >= 0 && row < 8 && column >= 0 && column < 8
				&& !originalBoard.pieceAt(this.newPlace))
			return true;
		return false;
	}
	
	public Coordinate getOriginalPlace() {
		return this.originalPlace;
	}

}
