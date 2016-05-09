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
		if (!this.originalPosition.isValid() || !this.newPosition.isValid()) {
			return false;
		}
		if (this.originalPosition.equals(this.newPosition)) {
			return false;
		}
		if (!originalPosition.isOrthogonallyAdjacentTo(newPosition)) {
			return false;
		}
		BoardState board = this.originalBoard;
		if (!board.isPieceAt(this.originalPosition) || board.isPieceAt(this.newPosition)) {
			return false;
		}
		if (board.getPieceAt(this.originalPosition).getOwner() != this.turn) {
			return false;
		}
		AbstractPiece piece = board.getPieceAt(originalPosition);
		if ((piece instanceof Rabbit)) {
			if (((piece.getOwner() == Owner.Player1) && (newPosition.equals(originalPosition.up())))
					|| ((piece.getOwner() == Owner.Player2) && (newPosition.equals(originalPosition.down())))) {
				// Cannot move a Rabbit backwards unless it has been dragged
				return false;
			}
		}
		if (isFrozen(originalPosition)) {
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
