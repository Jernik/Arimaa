package move_commands;

import board.BoardState;
import board.Coordinate;
import piece.AbstractPiece;
import piece.Owner;

/**
 * Created by millerlj on 4/27/2016.
 */
public class PushMove extends MoveCommand {
	private static final long serialVersionUID = -4958510415349881730L;
	public static final int NUMBER_OF_MOVES = 2;
	private Coordinate pushPiecePosition;

	public PushMove(BoardState board, Coordinate originalPosition, Coordinate newPosition, Coordinate pushPiecePosition,
			Owner turn, int movesLeft) {
		super(board, originalPosition, newPosition, turn, movesLeft);
		this.pushPiecePosition = pushPiecePosition;
	}

	@Override
	public BoardState execute() {
		if (!this.isValidMove()) {
			return this.originalBoard;
		}
		this.newBoard.movePiece(this.newPosition, this.pushPiecePosition);
		this.newBoard.movePiece(this.originalPosition, this.newPosition);
		return this.newBoard;
	}

	public Coordinate getPushPiecePlace() {
		return this.pushPiecePosition;
	}

	public int getNumberOfMoves() {
		return NUMBER_OF_MOVES;
	}
	
	// you should assume that you are given 3 random coordinates, that might or might not be valid
	@Override
	public boolean isValidMove() {
		if (this.movesLeft < this.getNumberOfMoves()) {
			return false;
		}
		if (!this.originalPosition.isValid() || !this.newPosition.isValid() || !this.pushPiecePosition.isValid()) {
			return false;
		}
		if (this.originalPosition.equals(this.newPosition) || this.originalPosition.equals(this.pushPiecePosition)
				|| this.newPosition.equals(this.pushPiecePosition)) {
			return false;
		}
		if (!this.originalPosition.isOrthogonallyAdjacentTo(this.newPosition)
				|| !this.newPosition.isOrthogonallyAdjacentTo(this.pushPiecePosition)) {
			return false;
		}

		BoardState board = this.originalBoard;
		if (!board.isPieceAt(this.originalPosition) || !board.isPieceAt(this.newPosition)
				|| board.isPieceAt(this.pushPiecePosition)) {
			return false;
		}
		if (!board.getPieceAt(this.originalPosition).getOwner().equals(this.turn)
				|| board.getPieceAt(this.newPosition).getOwner().equals(this.turn)) {
			return false;
		}
		AbstractPiece piece = board.getPieceAt(this.originalPosition);
		AbstractPiece pushedPiece = board.getPieceAt(this.newPosition);
		if (!piece.isStrongerThan(pushedPiece)) {
			return false;
		}
		if (this.isFrozen(this.originalPosition)) {
			return false;
		}
		return true;
	}

	@Override
	public boolean eq(MoveCommand moveCommand) {
		if (!(moveCommand instanceof PushMove)) {
			return false;
		}
		PushMove pushMove = (PushMove) moveCommand;
		return super.eq(pushMove) && this.pushPiecePosition.equals(pushMove.getPushPiecePlace());
	}

	@Override
	public int hashCode() {
		return super.hashCode() + this.pushPiecePosition.hashCode();
	}
}
