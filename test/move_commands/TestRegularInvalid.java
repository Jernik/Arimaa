package move_commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import game.BoardState;
import game.Coordinate;
import game.Game;

public class TestRegularInvalid extends RegularSetup {
	public void testInvalidMove(Game game, MoveCommand move) {
		BoardState board = new BoardState(move.getOriginalBoard());
		assertFalse(move.isValidMove());
		assertFalse(game.move(move));
		assertEquals(board, game.getBoardState());
	}

	@Test
	public void testMoveExecutingAnInvalidMoveReturnsTheOriginalBoard() {
		MoveCommand move = new RegularMove(g.getBoardState(), new Coordinate(0, 0), new Coordinate(0, -1), g.getOwner(),
				g.getNumMoves());
		assertEquals(g.getBoardState(), move.execute());
	}

	@Test
	public void testMoveOffBoard() {
		MoveCommand move = new RegularMove(g.getBoardState(), new Coordinate(0, 0), new Coordinate(0, -1), g.getOwner(),
				g.getNumMoves());
		testInvalidMove(g, move);
	}

	@Test
	public void testMoveOffBoardPiece() {
		MoveCommand move = new RegularMove(freezingGame.getBoardState(), new Coordinate(0, -1), new Coordinate(0, 0),
				freezingGame.getOwner(), freezingGame.getNumMoves());
		testInvalidMove(freezingGame, move);
	}

	@Test
	public void testMoveMustMoveToDifferentCoordinate() {
		MoveCommand move = new RegularMove(g.getBoardState(), new Coordinate(0, 1), new Coordinate(0, 1), g.getOwner(),
				g.getNumMoves());
		testInvalidMove(g, move);
	}

	@Test
	public void testMoveMustBeAdjacent() {
		MoveCommand move = new RegularMove(g.getBoardState(), new Coordinate(0, 1), new Coordinate(0, 3), g.getOwner(),
				g.getNumMoves());
		testInvalidMove(g, move);
	}

	@Test
	public void testMoveCantMoveNothing() {
		MoveCommand move = new RegularMove(g.getBoardState(), new Coordinate(0, 2), new Coordinate(0, 3), g.getOwner(),
				g.getNumMoves());
		testInvalidMove(g, move);
	}

	@Test
	public void testCannotMoveIntoOccupiedSpace() {
		MoveCommand move = new RegularMove(g.getBoardState(), new Coordinate(1, 7), new Coordinate(1, 6), g.getOwner(),
				g.getNumMoves());
		testInvalidMove(g, move);
	}

	@Test
	public void testMoveCantMoveEnemyPiece() {
		MoveCommand move = new RegularMove(g.getBoardState(), new Coordinate(7, 6), new Coordinate(7, 5), g.getOwner(),
				g.getNumMoves());
		testInvalidMove(g, move);
	}

	@Test
	public void testRabbitCantMoveBackwards1() {
		g.getBoardState().movePiece(new Coordinate(0, 1), new Coordinate(0, 2));
		MoveCommand move = new RegularMove(g.getBoardState(), new Coordinate(0, 2), new Coordinate(0, 1), g.getOwner(),
				g.getNumMoves());
		testInvalidMove(g, move);
	}

	@Test
	public void testRabbitCantMoveBackwards2() {
		g.setPlayerTurn(2);
		g.getBoardState().movePiece(new Coordinate(0, 6), new Coordinate(0, 5));
		MoveCommand move = new RegularMove(g.getBoardState(), new Coordinate(0, 5), new Coordinate(0, 6), g.getOwner(),
				g.getNumMoves());
		testInvalidMove(g, move);
	}

	@Test
	public void testCannotMoveIfFrozenByStrongerOpposingPiece() {
		MoveCommand move = new RegularMove(freezingGame.getBoardState(), new Coordinate(3, 4), new Coordinate(2, 4),
				freezingGame.getOwner(), freezingGame.getNumMoves());
		testInvalidMove(freezingGame, move);
	}

	@Test
	public void testCanMoveIfFrozenByStrongerOpposingPieceButThawedByFriendlyPiece() {
		MoveCommand move = new RegularMove(freezingGame.getBoardState(), new Coordinate(4, 3), new Coordinate(3, 3),
				freezingGame.getOwner(), freezingGame.getNumMoves());
		assertTrue(freezingGame.move(move));
	}
}
