package move_commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import board.BoardState;
import board.Coordinate;

public class TestPushInvalid extends PushSetup {
	public void testInvalidPush(MoveCommand move) {
		BoardState board = new BoardState(pushingGame.getBoardState());

		assertFalse(move.isValidMove());
		assertFalse(pushingGame.move(move));

		assertEquals(board, pushingGame.getBoardState());
	}

	@Test
	public void testExecutingAnInvalidPushReturnsTheOriginalBoard() {
		MoveCommand move = new PushMove(g2, new Coordinate(1, 1), new Coordinate(1, 0), new Coordinate(1, -1),
				g2.getPlayerTurn());
		BoardState board = new BoardState(g2.getBoardState());
		assertEquals(board, move.execute());
	}

	@Test
	public void testOffBoard1() {
		MoveCommand move = new PushMove(g2, new Coordinate(-1, 6), new Coordinate(0, 6), new Coordinate(1, 6),
				g2.getPlayerTurn());
		testInvalidPush(move);
	}

	@Test
	public void testOffBoard2() {
		MoveCommand move = new PushMove(g2, new Coordinate(0, 7), new Coordinate(0, 8), new Coordinate(0, 9),
				g2.getPlayerTurn());
		testInvalidPush(move);
	}

	@Test
	public void testOffBoard3() {
		MoveCommand move = new PushMove(g2, new Coordinate(1, 1), new Coordinate(1, 0), new Coordinate(1, -1),
				g2.getPlayerTurn());
		testInvalidPush(move);
	}

	@Test
	public void testPushWithNullPiece() {
		MoveCommand move = new PushMove(pushingGame, new Coordinate(2, 7), new Coordinate(1, 7), new Coordinate(1, 6),
				pushingGame.getPlayerTurn());
		testInvalidPush(move);
	}

	@Test
	public void testCantPushToEmptySpace() {
		MoveCommand move = new PushMove(pushingGame, new Coordinate(4, 6), new Coordinate(5, 6), new Coordinate(6, 6),
				pushingGame.getPlayerTurn());
		testInvalidPush(move);
	}

	@Test
	public void testCantPushIntoOccupiedPiece() {
		MoveCommand move = new PushMove(pushingGame, new Coordinate(2, 6), new Coordinate(3, 6), new Coordinate(4, 6),
				pushingGame.getPlayerTurn());
		testInvalidPush(move);

	}

	@Test
	public void testCantSwap() {
		MoveCommand move = new PushMove(pushingGame, new Coordinate(5, 4), new Coordinate(6, 4), new Coordinate(5, 4),
				pushingGame.getPlayerTurn());
		testInvalidPush(move);
	}

	@Test
	public void testCantNotMove() {
		MoveCommand move = new PushMove(pushingGame, new Coordinate(2, 6), new Coordinate(2, 6), new Coordinate(4, 6),
				pushingGame.getPlayerTurn());
		testInvalidPush(move);
	}

	@Test
	public void testCantNotMoveOtherPiece() {
		MoveCommand move = new PushMove(pushingGame, new Coordinate(5, 4), new Coordinate(6, 4), new Coordinate(6, 4),
				pushingGame.getPlayerTurn());
		testInvalidPush(move);
	}

	@Test
	public void testMustBeToAdjacentCoordinate() {
		MoveCommand move = new PushMove(pushingGame, new Coordinate(5, 4), new Coordinate(0, 1), new Coordinate(0, 2),
				pushingGame.getPlayerTurn());
		testInvalidPush(move);
	}

	@Test
	public void testPieceMustBeAdjacent() {
		MoveCommand move = new PushMove(pushingGame, new Coordinate(5, 4), new Coordinate(4, 4), new Coordinate(2, 7),
				pushingGame.getPlayerTurn());
		testInvalidPush(move);
	}

	@Test
	public void testCantMoveEnemyPiece() {
		MoveCommand move = new PushMove(pushingGame, new Coordinate(7, 0), new Coordinate(6, 0), new Coordinate(5, 0),
				pushingGame.getPlayerTurn());
		testInvalidPush(move);
	}

	@Test
	public void testCantPushUpOwnPiece() {
		MoveCommand move = new PushMove(pushingGame, new Coordinate(2, 2), new Coordinate(2, 1), new Coordinate(2, 0),
				pushingGame.getPlayerTurn());
		testInvalidPush(move);
	}

	@Test
	public void testCantPushRightOwnPiece() {
		MoveCommand move = new PushMove(pushingGame, new Coordinate(2, 2), new Coordinate(3, 2), new Coordinate(4, 2),
				pushingGame.getPlayerTurn());
		testInvalidPush(move);
	}

	@Test
	public void testCantPushDownOwnPiece() {
		MoveCommand move = new PushMove(pushingGame, new Coordinate(2, 2), new Coordinate(2, 3), new Coordinate(2, 4),
				pushingGame.getPlayerTurn());
		testInvalidPush(move);
	}

	@Test
	public void testCantPushLeftOwnPiece() {
		MoveCommand move = new PushMove(pushingGame, new Coordinate(2, 2), new Coordinate(1, 2), new Coordinate(0, 2),
				pushingGame.getPlayerTurn());
		testInvalidPush(move);
	}

	@Test
	public void testThatPiecesMustBeStrongerToPush() {
		pushingGame.incrementTurn();
		MoveCommand move = new PushMove(pushingGame, new Coordinate(3, 6), new Coordinate(3, 5), new Coordinate(3, 4),
				pushingGame.getPlayerTurn());
		testInvalidPush(move);
	}

	@Test
	public void testCantPushIfFrozen() {
		pushingGame.incrementTurn();
		MoveCommand move = new PushMove(pushingGame, new Coordinate(5, 5), new Coordinate(6, 5), new Coordinate(7, 5),
				pushingGame.getPlayerTurn());
		testInvalidPush(move);
	}

	@Test
	public void testNotEnoughMoves() {
		assertTrue(pushingGame.move(
				new RegularMove(pushingGame, new Coordinate(7, 1), new Coordinate(7, 2), pushingGame.getPlayerTurn())));
		assertTrue(pushingGame.move(
				new RegularMove(pushingGame, new Coordinate(7, 2), new Coordinate(7, 3), pushingGame.getPlayerTurn())));
		assertTrue(pushingGame.move(
				new RegularMove(pushingGame, new Coordinate(7, 3), new Coordinate(7, 4), pushingGame.getPlayerTurn())));

		MoveCommand move = new PushMove(pushingGame, new Coordinate(5, 4), new Coordinate(5, 3), new Coordinate(5, 2),
				pushingGame.getPlayerTurn());
		testInvalidPush(move);
	}
}
