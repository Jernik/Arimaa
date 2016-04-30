package move_commands;

import game.BoardState;
import game.Coordinate;
import piece.Owner;

import java.util.ArrayList;
import java.util.List;

public abstract class MoveCommand {
	protected Owner turn;
	protected BoardState originalBoard;
	protected boolean executed;

	public MoveCommand() {
		this.executed = false;
	}

	abstract public BoardState execute();

	abstract public boolean isValidMove();

	public Owner getTurn() {
		return turn;
	}

	public BoardState getOriginalBoard() {
		return this.originalBoard;
	}

	public boolean hasExecuted() {
		return this.executed;
	}

	protected void executed() {
		this.executed = true;
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
		return this.turn.equals(moveCommand.getTurn()) && this.originalBoard.equals(moveCommand.getOriginalBoard())
				&& this.executed == moveCommand.hasExecuted();
	}

	@Override
	public int hashCode() {
		return this.turn.hashCode() + this.originalBoard.hashCode();
	}

	// public boolean isValidMove();

	// public static boolean validMove(BoardState originalBoard, int row, int column) {
	// if (row >= 0 && row < 8 && column >= 0 && column < 8
	// && originalBoard.getBoardArray()[row][column] == ' ')
	// return true;
	// return false;
	// }

}
