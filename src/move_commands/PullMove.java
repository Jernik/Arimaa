package move_commands;

import game.BoardState;
import game.Coordinate;
import piece.Owner;

/**
 * Created by millerlj on 4/27/2016.
 */
public class PullMove extends MoveCommand {
	private static final long serialVersionUID = -2023708661926206228L;
	public static final int NUMBER_OF_MOVES = 2;
	private Coordinate pullPiecePosition;

	public PullMove(BoardState board, Coordinate originalPosition, Coordinate newPosition, Coordinate pullPiecePosition,
			Owner turn, int movesLeft) {
		super(board, originalPosition, newPosition, turn, movesLeft);
		this.pullPiecePosition = pullPiecePosition;
	}

	@Override
	public BoardState execute() {
		return this.originalBoard;
	}

	public Coordinate getPullPiecePlace() {
		return this.pullPiecePosition;
	}

	// you should assume that you are given 3 random coordinates, that might or might not be valid
	@Override
	public boolean isValidMove() {
		if (!this.originalPosition.isValid() || !this.newPosition.isValid() || !this.pullPiecePosition.isValid()) {
			return false;
		}
		if (this.originalPosition.equals(this.newPosition) || this.originalPosition.equals(this.pullPiecePosition)
				|| this.newPosition.equals(this.pullPiecePosition)) {
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

		// TODO finish this
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
