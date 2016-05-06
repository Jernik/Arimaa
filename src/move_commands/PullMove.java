package move_commands;

import game.BoardState;
import game.Coordinate;
import piece.Owner;

/**
 * Created by millerlj on 4/27/2016.
 */
public class PullMove extends MoveCommand {
	private static final long serialVersionUID = -2023708661926206228L;
	private BoardState newBoard;
	private Coordinate originalPlace;
	private Coordinate newPlace;
	private Coordinate pullPiecePlace;

	public PullMove(BoardState board, Coordinate originalPlace, Coordinate newPlace, Coordinate pullPiecePlace,
			Owner turn) {
		this.turn = turn;
		this.originalBoard = board.clone();
		this.newBoard = board;
		this.originalPlace = originalPlace;
		this.newPlace = newPlace;
		this.pullPiecePlace = pullPiecePlace;
	}

	@Override
	public BoardState execute() {
		return this.originalBoard;
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

	public Coordinate getPullPiecePlace() {
		return this.pullPiecePlace;
	}

	// you should assume that you are given 3 random coordinates, that might or might not be valid
	@Override
	public boolean isValidMove() {
		if (!this.originalPlace.isValid() || !this.newPlace.isValid() || !this.pullPiecePlace.isValid()) {
			return false;
		}
		if (this.originalPlace.equals(this.newPlace) || this.originalPlace.equals(this.pullPiecePlace)
				|| this.newPlace.equals(this.pullPiecePlace)) {
			return false;
		}
		BoardState board = this.originalBoard;
		if (!board.isPieceAt(this.originalPlace) || board.isPieceAt(this.newPlace) || !board.isPieceAt(this.pullPiecePlace)) {
			return false;
		}
		if (!board.getPieceAt(this.originalPlace).getOwner().equals(this.turn)
				|| board.getPieceAt(this.pullPiecePlace).getOwner().equals(this.turn)) {
			return false;
		}

		// TODO finish this
		return true;
	}

	@Override
	public boolean eq(MoveCommand moveCommand) {
		if (!(moveCommand instanceof PullMove)) {
			return false;
		}
		PullMove pullMove = (PullMove) moveCommand;
		return super.eq(pullMove) && this.newBoard.equals(pullMove.getNewBoard())
				&& this.originalPlace.equals(pullMove.getOriginalPlace())
				&& this.newPlace.equals(pullMove.getNewPlace())
				&& this.pullPiecePlace.equals(pullMove.getPullPiecePlace());
	}

	@Override
	public int hashCode() {
		return super.hashCode() + this.newBoard.hashCode() + this.originalPlace.hashCode() + this.newPlace.hashCode()
				+ this.pullPiecePlace.hashCode();
	}
}
