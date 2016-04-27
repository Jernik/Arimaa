package move_commands;

import game.BoardState;
import game.Coordinate;
import piece.Owner;

public abstract class MoveCommand {


    Owner turn;

    protected BoardState originalBoard;

    abstract public BoardState execute();

    abstract public BoardState getOriginalBoard();

    abstract public boolean isValidMove();

    protected boolean isFrozen(Coordinate pieceToMove) {
        if (!isNextToStrongerPiece(pieceToMove, this.turn)
                && isNextToStrongerPiece(pieceToMove, this.getOtherOwner())) {
            return true;
        }
        return false;
    }

    private boolean isNextToStrongerPiece(Coordinate pieceToMove, Owner player) {
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                Coordinate coor = new Coordinate(i + pieceToMove.getX(), j + pieceToMove.getY());
                if (this.originalBoard.getPieceAt(coor) != null) {
                    if (coor.isValid() && !coor.equals(pieceToMove)
                            && this.originalBoard.getPieceAt(coor).getOwner() == player
                            && this.originalBoard.getPieceAt(coor).isStrongerThan(this.originalBoard.getPieceAt(pieceToMove))) {
                        return true;
                    }
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

//	public boolean isValidMove();

//	public static boolean validMove(BoardState originalBoard, int row, int column) {
//		if (row >= 0 && row < 8 && column >= 0 && column < 8
//				&& originalBoard.getBoardArray()[row][column] == ' ')
//			return true;
//		return false;
//	}

}
