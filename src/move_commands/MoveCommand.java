package move_commands;

import game.BoardState;
import game.Coordinate;
import piece.Owner;

import java.util.ArrayList;
import java.util.List;

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
        List<Coordinate> checkList = new ArrayList<>();
        checkList.add(pieceToMove.up());
        checkList.add(pieceToMove.down());
        checkList.add(pieceToMove.left());
        checkList.add(pieceToMove.right());
        for(Coordinate coor:checkList){
            if (this.originalBoard.getPieceAt(coor) != null) {
                if (coor.isValid() && !coor.equals(pieceToMove)
                        && this.originalBoard.getPieceAt(coor).getOwner() == player
                        && this.originalBoard.getPieceAt(coor).isStrongerThan(this.originalBoard.getPieceAt(pieceToMove))) {
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

//	public boolean isValidMove();

//	public static boolean validMove(BoardState originalBoard, int row, int column) {
//		if (row >= 0 && row < 8 && column >= 0 && column < 8
//				&& originalBoard.getBoardArray()[row][column] == ' ')
//			return true;
//		return false;
//	}

}
