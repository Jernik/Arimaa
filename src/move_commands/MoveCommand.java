package move_commands;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import game.BoardState;
import game.Coordinate;
import piece.Owner;

public abstract class MoveCommand implements Serializable {
	private static final long serialVersionUID = -1176514408247586630L;
	protected BoardState originalBoard;
	protected BoardState newBoard;

	protected Coordinate originalPosition;
	protected Coordinate newPosition;

	protected Owner turn;
	protected int movesLeft;

	protected MoveCommand(BoardState board, Coordinate originalPosition, Coordinate newPosition, Owner turn,
			int movesLeft) {
		this.originalBoard = new BoardState(board);
		this.newBoard = board;

		this.originalPosition = originalPosition;
		this.newPosition = newPosition;

		this.turn = turn;
		this.movesLeft = movesLeft;
	}

	abstract public BoardState execute();

	abstract public boolean isValidMove();
	abstract public int getNumberOfMoves();

	public BoardState getOriginalBoard() {
		return this.originalBoard;
	}

	public Coordinate getOriginalPosition() {
		return originalPosition;
	}

	public Coordinate getNewPosition() {
		return newPosition;
	}

	public Owner getTurn() {
		return turn;
	}
	

	public int getMovesLeft() {
		return this.movesLeft;
	}

	protected boolean isFrozen(Coordinate pieceToMove) {
		if (!isNextToStrongerPiece(pieceToMove, this.turn)
				&& isNextToStrongerPiece(pieceToMove, this.turn.getOtherOwner())) {
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
			if (this.originalBoard.isPieceAt(coor)) {
				if (coor.isValid() && this.originalBoard.getPieceAt(coor).getOwner() == player && this.originalBoard
						.getPieceAt(coor).isStrongerThan(this.originalBoard.getPieceAt(pieceToMove))) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof MoveCommand))
			return false;

		MoveCommand moveCommand = (MoveCommand) o;

		return this.eq(moveCommand) && moveCommand.eq(this);
	}

	protected boolean eq(MoveCommand moveCommand) {
		return this.originalPosition.equals(moveCommand.getOriginalPosition())
				&& this.newPosition.equals(moveCommand.getNewPosition()) && this.turn.equals(moveCommand.getTurn())
				&& this.getNumberOfMoves() == moveCommand.getNumberOfMoves() && this.movesLeft == moveCommand.getMovesLeft()
				&& this.originalBoard.equals(moveCommand.getOriginalBoard())
				&& this.newBoard.equals(moveCommand.newBoard);
	}

	@Override
	public int hashCode() {
		return this.originalBoard.hashCode() + this.newBoard.hashCode() + this.originalPosition.hashCode()
				+ this.newPosition.hashCode() + this.turn.hashCode()
				+ Integer.rotateLeft(this.getNumberOfMoves(), Integer.BYTES / 2)
				+ Integer.rotateLeft(this.movesLeft, Integer.BYTES / 2);
	}
}
