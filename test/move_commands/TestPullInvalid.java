package move_commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import board.BoardState;
import board.Coordinate;
import move_commands.MoveCommand;
import move_commands.PullMove;
import move_commands.PullSetup;
import move_commands.RegularMove;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestPullInvalid extends PullSetup {
	public void testInvalidPull(MoveCommand move) {
		BoardState board = new BoardState(pullingGame.getBoardState());
		assertFalse(move.isValidMove());
		assertFalse(pullingGame.move(move));

		assertEquals(board, pullingGame.getBoardState());
	}

	@Test
	public void testExecutingAnInvalidMoveReturnsTheOriginalBoard() {
		MoveCommand move = new PullMove(pullingGame, new Coordinate(5, -1), new Coordinate(5, 0), new Coordinate(5, -2),
				pullingGame.getPlayerTurn());
		BoardState board = new BoardState(pullingGame.getBoardState());
		assertEquals(board, move.execute());
	}

	@Test
	public void testOffBoard1() {
		MoveCommand move = new PullMove(pullingGame, new Coordinate(5, -1), new Coordinate(5, 0), new Coordinate(5, -2),
				pullingGame.getPlayerTurn());
		testInvalidPull(move);
	}

	@Test
	public void testOffBoard2() {
		MoveCommand move = new PullMove(pullingGame, new Coordinate(7, 0), new Coordinate(7, -1), new Coordinate(7, 1),
				pullingGame.getPlayerTurn());
		testInvalidPull(move);
	}

	@Test
	public void testOffBoard3() {
		MoveCommand move = new PullMove(pullingGame, new Coordinate(3, 0), new Coordinate(4, 0), new Coordinate(3, -1),
				pullingGame.getPlayerTurn());
		testInvalidPull(move);
	}

	@Test
	public void testCantSwap() {
		MoveCommand move = new PullMove(pullingGame, new Coordinate(5, 5), new Coordinate(5, 4), new Coordinate(5, 4),
				pullingGame.getPlayerTurn());
		testInvalidPull(move);
	}

	@Test
	public void testMustMove() {
		MoveCommand move = new PullMove(pullingGame, new Coordinate(5, 5), new Coordinate(5, 5), new Coordinate(5, 6),
				pullingGame.getPlayerTurn());
		testInvalidPull(move);
	}

	@Test
	public void testCantPullSelf() {
		MoveCommand move = new PullMove(pullingGame, new Coordinate(5, 5), new Coordinate(5, 4), new Coordinate(5, 5),
				pullingGame.getPlayerTurn());
		testInvalidPull(move);
	}

	@Test
	public void testDestinationMustBeAdjacent() {
		MoveCommand move = new PullMove(pullingGame, new Coordinate(4, 4), new Coordinate(5, 2), new Coordinate(3, 4),
				pullingGame.getPlayerTurn());
		testInvalidPull(move);
	}

	@Test
	public void testPulledPieceMustBeAdjacent() {
		MoveCommand move = new PullMove(pullingGame, new Coordinate(4, 4), new Coordinate(5, 4), new Coordinate(3, 2),
				pullingGame.getPlayerTurn());
		testInvalidPull(move);
	}

	@Test
	public void testNullPiece() {
		MoveCommand move = new PullMove(pullingGame, new Coordinate(7, 5), new Coordinate(7, 6), new Coordinate(6, 5),
				pullingGame.getPlayerTurn());
		testInvalidPull(move);
	}

	@Test
	public void testCantPullNothing() {
		MoveCommand move = new PullMove(pullingGame, new Coordinate(5, 3), new Coordinate(5, 4), new Coordinate(4, 3),
				pullingGame.getPlayerTurn());
		testInvalidPull(move);
	}

	@Test
	public void testCantPullIntoOccupiedSpace() {
		MoveCommand move = new PullMove(pullingGame, new Coordinate(2, 2), new Coordinate(2, 1), new Coordinate(2, 3),
				pullingGame.getPlayerTurn());
		testInvalidPull(move);
	}

	@Test
	public void testCantPullOnEnemyTurn() {
		MoveCommand move = new PullMove(pullingGame, new Coordinate(1, 6), new Coordinate(2, 6), new Coordinate(0, 6),
				pullingGame.getPlayerTurn());
		testInvalidPull(move);
	}

	@Test
	public void testCantPullOwnPiece() {
		MoveCommand move = new PullMove(pullingGame, new Coordinate(1, 2), new Coordinate(1, 3), new Coordinate(2, 2),
				pullingGame.getPlayerTurn());
		testInvalidPull(move);
	}

	@Test
	public void testCantPullPieceOfGreaterStrength() {
		MoveCommand move = new PullMove(pullingGame, new Coordinate(3, 7), new Coordinate(2, 7), new Coordinate(4, 7),
				pullingGame.getPlayerTurn());
		testInvalidPull(move);
	}

	@Test
	public void testCantPullIfFrozen() {
		MoveCommand move = new PullMove(pullingGame, new Coordinate(1, 5), new Coordinate(1, 4), new Coordinate(2, 5),
				pullingGame.getPlayerTurn());
		testInvalidPull(move);
	}

	@Test
	public void testCantPullWith1Move() {
		assertTrue(pullingGame.move(
				new RegularMove(pullingGame, new Coordinate(6, 0), new Coordinate(5, 0), pullingGame.getPlayerTurn())));
		assertTrue(pullingGame.move(
				new RegularMove(pullingGame, new Coordinate(5, 0), new Coordinate(6, 0), pullingGame.getPlayerTurn())));
		assertTrue(pullingGame.move(
				new RegularMove(pullingGame, new Coordinate(6, 0), new Coordinate(5, 0), pullingGame.getPlayerTurn())));
		MoveCommand move = new PullMove(pullingGame, new Coordinate(5, 5), new Coordinate(5, 4), new Coordinate(5, 4),
				pullingGame.getPlayerTurn());
		testInvalidPull(move);
	}
}
