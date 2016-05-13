package move_commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.lang.reflect.Field;

import org.junit.Test;

import board.BoardState;
import board.Coordinate;
import game.Game;

public class TestRegularInvalid extends RegularSetup {
	public void testInvalidMove(Game game, MoveCommand move) {
		BoardState board = new BoardState(move.getOriginalBoard());
		assertFalse(move.isValidMove());
		assertFalse(game.move(move));
		assertEquals(board, game.getBoardState());
	}

	@Test
	public void testExecutingAnInvalidMoveReturnsTheOriginalBoard() {
		MoveCommand move = new RegularMove(g, new Coordinate(0, 0), new Coordinate(0, -1), g.getPlayerTurn());
		BoardState board = new BoardState(g.getBoardState());
		assertEquals(board, move.execute());
	}

	@Test
	public void testCantMoveWith0MovesLeft()
			throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		Field field = this.g.getClass().getDeclaredField("numMoves");
		field.setAccessible(true);
		field.setInt(g, 0);
		MoveCommand move = new RegularMove(this.g, new Coordinate(0, 1), new Coordinate(0, 2), this.g.getPlayerTurn());
		testInvalidMove(this.g, move);
	}

	@Test
	public void testDestinationOffBoard() {
		MoveCommand move = new RegularMove(g, new Coordinate(0, 0), new Coordinate(0, -1), g.getPlayerTurn());
		testInvalidMove(g, move);
	}

	@Test
	public void testOffBoardPiece() {
		MoveCommand move = new RegularMove(freezingGame, new Coordinate(0, -1), new Coordinate(0, 0),
				freezingGame.getPlayerTurn());
		testInvalidMove(freezingGame, move);
	}

	@Test
	public void testMustMoveToDifferentCoordinate() {
		MoveCommand move = new RegularMove(g, new Coordinate(0, 1), new Coordinate(0, 1), g.getPlayerTurn());
		testInvalidMove(g, move);
	}

	@Test
	public void testMustBeAdjacent() {
		MoveCommand move = new RegularMove(g, new Coordinate(0, 1), new Coordinate(0, 3), g.getPlayerTurn());
		testInvalidMove(g, move);
	}

	@Test
	public void testCantMoveNothing() {
		MoveCommand move = new RegularMove(g, new Coordinate(0, 2), new Coordinate(0, 3), g.getPlayerTurn());
		testInvalidMove(g, move);
	}

	@Test
	public void testCantMoveIntoOccupiedSpace() {
		MoveCommand move = new RegularMove(g, new Coordinate(1, 7), new Coordinate(1, 6), g.getPlayerTurn());
		testInvalidMove(g, move);
	}

	@Test
	public void testCantMoveEnemyPiece() {
		MoveCommand move = new RegularMove(g, new Coordinate(7, 6), new Coordinate(7, 5), g.getPlayerTurn());
		testInvalidMove(g, move);
	}

	@Test
	public void testRabbitCantMoveBackwards1() {
		g.getBoardState().movePiece(new Coordinate(0, 1), new Coordinate(0, 2));
		MoveCommand move = new RegularMove(g, new Coordinate(0, 2), new Coordinate(0, 1), g.getPlayerTurn());
		testInvalidMove(g, move);
	}

	@Test
	public void testRabbitCantMoveBackwards2() {
		g.incrementTurn();
		g.getBoardState().movePiece(new Coordinate(0, 6), new Coordinate(0, 5));
		MoveCommand move = new RegularMove(g, new Coordinate(0, 5), new Coordinate(0, 6), g.getPlayerTurn());
		testInvalidMove(g, move);
	}

	@Test
	public void testCannotMoveIfFrozenByStrongerOpposingPiece() {
		MoveCommand move = new RegularMove(freezingGame, new Coordinate(3, 4), new Coordinate(2, 4),
				freezingGame.getPlayerTurn());
		testInvalidMove(freezingGame, move);
	}
}
