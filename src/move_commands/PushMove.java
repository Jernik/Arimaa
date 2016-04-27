package move_commands;

import game.BoardState;

/**
 * Created by millerlj on 4/27/2016.
 */
public class PushMove extends MoveCommand {
    @Override
    public BoardState execute() {
        return null;
    }

    @Override
    public BoardState getOriginalBoard() {
        return null;
    }

    @Override
    public boolean isValidMove() {
        return false;
    }
}
