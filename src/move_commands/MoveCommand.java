package move_commands;

import java.util.ArrayList;
import java.util.List;

import game.BoardState;
import game.Coordinate;
import piece.Owner;

public abstract class MoveCommand {
	protected Owner turn;
	protected BoardState originalBoard;

	abstract public BoardState execute();

	abstract public boolean isValidMove();

	public Owner getTurn() {
		return turn;
	}

	public BoardState getOriginalBoard() {
		return this.originalBoard;
	}

	protected boolean isFrozen(Coordinate pieceToMove) {
		if (!isNextToStrongerPiece(pieceToMove, this.turn)
				&& isNextToStrongerPiece(pieceToMove, this.getOtherOwner())) {
			return true;
		}
		return false;
	}

	private boolean isNextToStrongerPiece(Coordinate pieceToMove, Owner player) {
		List<Coordinate> checkList = new ArrayList<>();
		checkList.add(pieceToMove.up());
		checkList.add(pieceToMove.down());
		checkList.add(pieceToMove.left());
		checkList.add(pieceToMove.right());
		for (Coordinate coor : checkList) {
			if (this.originalBoard.getPieceAt(coor) != null) {
				if (coor.isValid() && !coor.equals(pieceToMove)
						&& this.originalBoard.getPieceAt(coor).getOwner() == player && this.originalBoard
								.getPieceAt(coor).isStrongerThan(this.originalBoard.getPieceAt(pieceToMove))) {
					return true;
				}
			}
		}
		return false;
	}

	public Owner getOtherOwner() {
		if (turn == Owner.values()[0]) {
			return Owner.values()[1];
		} else {
			return Owner.values()[0];
		}
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof MoveCommand))
			return false;

		MoveCommand moveCommand = (MoveCommand) o;

		return this.eq(moveCommand) && moveCommand.eq(this);
	}

	protected boolean eq(MoveCommand moveCommand) {
		return this.turn.equals(moveCommand.getTurn()) && this.originalBoard.equals(moveCommand.getOriginalBoard());
	}

	@Override
	public int hashCode() {
		return this.turn.hashCode() + this.originalBoard.hashCode();
	}
}
