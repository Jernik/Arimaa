package move_commands;

import game.BoardState;
import game.Coordinate;
import piece.Owner;

/**
 * Created by millerlj on 4/27/2016.
 */
public class PushMove extends MoveCommand {
	private BoardState newBoard;
	private Coordinate originalPlace;
	private Coordinate newPlace;
	private Coordinate pushPiecePlace;

	public PushMove(BoardState board, Coordinate originalPlace, Coordinate newPlace, Coordinate pushPiecePlace,
			Owner turn) {
		this.turn = turn;
		this.originalBoard = board.clone();
		this.newBoard = board;
		this.originalPlace = originalPlace;
		this.newPlace = newPlace;
		this.pushPiecePlace = pushPiecePlace;
	}

	@Override
	public BoardState execute() {
		return null;
	}

	@Override
	public BoardState getOriginalBoard() {
		return null;
	}

	// you should assume that you are given 3 random coordinates, that might or might not be valid 
	@Override
	public boolean isValidMove() {
		return false;
	}
}
