package move_commands;

import game.BoardState;
import game.Coordinate;
import piece.AbstractPiece;
import piece.Owner;
import piece.Rabbit;

public class RegularMove extends MoveCommand {
	public static final int NUMBER_OF_MOVES = 1;
	private static final long serialVersionUID = 4434841689278271636L;

	public RegularMove(BoardState board, Coordinate originalPosition, Coordinate newPosition, Owner turn,
			int movesLeft) {
		super(board, originalPosition, newPosition, turn, movesLeft);
	}

	@Override
	public BoardState execute() {
		if (!isValidMove()) {
			return this.originalBoard;
		}
		this.newBoard.movePiece(originalPosition, newPosition);
		return newBoard;
	}

	@Override
	public boolean isValidMove() {
		if (!this.originalBoard.isPieceAt(this.originalPosition) || this.originalBoard.isPieceAt(this.newPosition)) {
			return false;
		}
		AbstractPiece piece = this.originalBoard.getPieceAt(originalPosition);
		if (isFrozen(originalPosition)) {
			return false;
		}
		if (this.originalBoard.isPieceAt(newPosition)) {
			return false;
		}
		if ((piece instanceof Rabbit)) {
			if (((piece.getOwner() == Owner.values()[0]) && (newPosition.equals(originalPosition.up())))
					|| ((piece.getOwner() == Owner.values()[1]) && (newPosition.equals(originalPosition.down())))) {
				// Cannot move a Rabbit backwards unless it has been dragged
				return false;
			}
		}
		if (!(originalPosition.isOrthogonallyAdjacentTo(newPosition)
				&& this.originalBoard.getPieceAt(originalPosition).getOwner() == this.turn)) {
			return false;
		}
		return true;
	}

	@Override
	public boolean eq(MoveCommand moveCommand) {
		if (!(moveCommand instanceof RegularMove)) {
			return false;
		}
		RegularMove regularMove = (RegularMove) moveCommand;
		return super.eq(regularMove);
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}
}
