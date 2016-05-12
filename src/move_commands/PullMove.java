package move_commands;

import java.util.ArrayList;

import board.BoardState;
import board.Coordinate;
import game.Game;
import piece.AbstractPiece;

/**
 * Created by millerlj on 4/27/2016.
 */
public class PullMove extends MoveCommand {
	private static final long serialVersionUID = -2023708661926206228L;
	public static final int NUMBER_OF_MOVES = 2;
	private Coordinate pullPiecePosition;

	public PullMove(Game game, Coordinate originalPosition, Coordinate newPosition, Coordinate pullPiecePosition) {
		super(game, originalPosition, newPosition);
		this.pullPiecePosition = pullPiecePosition;
	}

	@Override
	public BoardState execute() {
		if (!this.isValidMove()) {
			return this.originalBoard;
		}
		this.newBoard.movePiece(this.originalPosition, this.newPosition);
		this.newBoard.movePiece(this.pullPiecePosition, this.originalPosition);
		return this.newBoard;
	}

	public Coordinate getPullPiecePlace() {
		return this.pullPiecePosition;
	}

	public int getNumberOfMoves() {
		return NUMBER_OF_MOVES;
	}

	@Override
	public ArrayList<CoordinatePair> getAffectedCoordinates() {
		ArrayList<CoordinatePair> list = super.getAffectedCoordinates();
		if (this.isValidMove()) {
			list.add(new CoordinatePair(this.pullPiecePosition, this.originalPosition));
		}
		return list;
	}

	// you should assume that you are given 3 random coordinates, that might or might not be valid
	@Override
	public boolean isValidMove() {
		if (this.movesLeft < this.getNumberOfMoves()) {
			return false;
		}
		if (!this.originalPosition.isValid() || !this.newPosition.isValid() || !this.pullPiecePosition.isValid()) {
			return false;
		}
		if (this.originalPosition.equals(this.newPosition) || this.originalPosition.equals(this.pullPiecePosition)
				|| this.newPosition.equals(this.pullPiecePosition)) {
			return false;
		}
		if (!this.originalPosition.isOrthogonallyAdjacentTo(this.newPosition)
				|| !this.originalPosition.isOrthogonallyAdjacentTo(this.pullPiecePosition)) {
			return false;
		}

		BoardState board = this.originalBoard;
		if (!board.isPieceAt(this.originalPosition) || board.isPieceAt(this.newPosition)
				|| !board.isPieceAt(this.pullPiecePosition)) {
			return false;
		}
		if (!board.getPieceAt(this.originalPosition).getOwner().equals(this.turn)
				|| board.getPieceAt(this.pullPiecePosition).getOwner().equals(this.turn)) {
			return false;
		}
		AbstractPiece piece = board.getPieceAt(this.originalPosition);
		AbstractPiece pulledPiece = board.getPieceAt(this.pullPiecePosition);
		if (!piece.isStrongerThan(pulledPiece)) {
			return false;
		}
		if (this.originalBoard.isFrozen(this.originalPosition)) {
			return false;
		}
		return true;
	}

	@Override
	public boolean eq(MoveCommand moveCommand) {
		if (!(moveCommand instanceof PullMove)) {
			return false;
		}
		PullMove pullMove = (PullMove) moveCommand;
		return super.eq(pullMove) && this.pullPiecePosition.equals(pullMove.getPullPiecePlace());
	}

	@Override
	public int hashCode() {
		return super.hashCode() + this.pullPiecePosition.hashCode();
	}
}
