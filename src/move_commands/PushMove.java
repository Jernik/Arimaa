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

	public BoardState getNewBoard() {
		return this.newBoard;
	}

	public Coordinate getOriginalPlace() {
		return this.originalPlace;
	}

	public Coordinate getNewPlace() {
		return this.newPlace;
	}

	public Coordinate getPushPiecePlace() {
		return this.pushPiecePlace;
	}

	// you should assume that you are given 3 random coordinates, that might or might not be valid 
	@Override
	public boolean isValidMove() {
		if (!this.originalPlace.isValid() || !this.newPlace.isValid() || !this.pushPiecePlace.isValid()) {
			return false;
		}
		if (this.originalPlace.equals(this.newPlace) || this.originalPlace.equals(this.pushPiecePlace)
				|| this.newPlace.equals(this.pushPiecePlace)) {
			return false;
		}
		BoardState board = this.originalBoard;
		if (!board.isPieceAt(this.originalPlace) || !board.isPieceAt(this.newPlace) || board.isPieceAt(this.pushPiecePlace)) {
			return false;
		}
		if (!board.getPieceAt(this.originalPlace).getOwner().equals(this.turn)
				|| board.getPieceAt(this.newPlace).getOwner().equals(this.turn)) {
			return false;
		}
		// TODO finish
		return true;
	}
	
	@Override
	public boolean eq(MoveCommand moveCommand) {
		if (!(moveCommand instanceof PushMove)) {
			return false;
		}
		PushMove pushMove = (PushMove) moveCommand;
		return super.eq(pushMove) && this.newBoard.equals(pushMove.getNewBoard())
				&& this.originalPlace.equals(pushMove.getOriginalPlace())
				&& this.newPlace.equals(pushMove.getNewPlace())
				&& this.pushPiecePlace.equals(pushMove.getPushPiecePlace());
	}
	
	@Override
	public int hashCode() {
		return super.hashCode() + this.newBoard.hashCode() + this.originalPlace.hashCode() + this.newPlace.hashCode() + this.pushPiecePlace.hashCode();
	}
}
