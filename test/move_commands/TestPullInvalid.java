package move_commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import game.BoardState;
import game.Coordinate;

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
		MoveCommand move = new PullMove(pullingGame.getBoardState(), new Coordinate(5, -1), new Coordinate(5, 0),
				new Coordinate(5, -2), pullingGame.getOwner(), pullingGame.getNumMoves());
		BoardState board = new BoardState(pullingGame.getBoardState());
		assertEquals(board, move.execute());
	}

	@Test
	public void testOffBoard1() {
		MoveCommand move = new PullMove(pullingGame.getBoardState(), new Coordinate(5, -1), new Coordinate(5, 0),
				new Coordinate(5, -2), pullingGame.getOwner(), pullingGame.getNumMoves());
		testInvalidPull(move);
	}

	@Test
	public void testOffBoard2() {
		MoveCommand move = new PullMove(pullingGame.getBoardState(), new Coordinate(7, 0), new Coordinate(7, -1),
				new Coordinate(7, 1), pullingGame.getOwner(), pullingGame.getNumMoves());
		testInvalidPull(move);
		// assertFalse(pullingGame.pull(0, 7, 1, 7, 0));
	}

	@Test
	public void testOffBoard3() {
		MoveCommand move = new PullMove(pullingGame.getBoardState(), new Coordinate(3, 0), new Coordinate(4, 0),
				new Coordinate(3, -1), pullingGame.getOwner(), pullingGame.getNumMoves());
		testInvalidPull(move);
	}

	@Test
	public void testCantSwap() {
		MoveCommand move = new PullMove(pullingGame.getBoardState(), new Coordinate(5, 5), new Coordinate(5, 4),
				new Coordinate(5, 4), pullingGame.getOwner(), pullingGame.getNumMoves());
		testInvalidPull(move);
	}

	@Test
	public void testMustMove() {
		MoveCommand move = new PullMove(pullingGame.getBoardState(), new Coordinate(5, 5), new Coordinate(5, 5),
				new Coordinate(5, 6), pullingGame.getOwner(), pullingGame.getNumMoves());
		testInvalidPull(move);
	}

	@Test
	public void testCantPullSelf() {
		MoveCommand move = new PullMove(pullingGame.getBoardState(), new Coordinate(5, 5), new Coordinate(5, 4),
				new Coordinate(5, 5), pullingGame.getOwner(), pullingGame.getNumMoves());
		testInvalidPull(move);
	}

	@Test
	public void testDestinationMustBeAdjacent() {
		MoveCommand move = new PullMove(pullingGame.getBoardState(), new Coordinate(4, 4), new Coordinate(5, 2),
				new Coordinate(3, 4), pullingGame.getOwner(), pullingGame.getNumMoves());
		testInvalidPull(move);
	}

	@Test
	public void testPulledPieceMustBeAdjacent() {
		MoveCommand move = new PullMove(pullingGame.getBoardState(), new Coordinate(4, 4), new Coordinate(5, 4),
				new Coordinate(3, 2), pullingGame.getOwner(), pullingGame.getNumMoves());
		testInvalidPull(move);
	}

	@Test
	public void testNullPiece() {
		MoveCommand move = new PullMove(pullingGame.getBoardState(), new Coordinate(7, 5), new Coordinate(7, 6),
				new Coordinate(6, 5), pullingGame.getOwner(), pullingGame.getNumMoves());
		testInvalidPull(move);
		// assertFalse(pullingGame.pull(0, 4, 0, 5, 3));
	}

	@Test
	public void testCantPullNothing() {
		MoveCommand move = new PullMove(pullingGame.getBoardState(), new Coordinate(5, 3), new Coordinate(5, 4),
				new Coordinate(4, 3), pullingGame.getOwner(), pullingGame.getNumMoves());
		testInvalidPull(move);
	}

	@Test
	public void testCantPullIntoOccupiedSpace() {
		MoveCommand move = new PullMove(pullingGame.getBoardState(), new Coordinate(2, 2), new Coordinate(2, 1),
				new Coordinate(2, 3), pullingGame.getOwner(), pullingGame.getNumMoves());
		testInvalidPull(move);
		// assertFalse(pullingGame.pull(2, 2, 3, 2, 0));
	}

	@Test
	public void testCantPullOnEnemyTurn() {
		MoveCommand move = new PullMove(pullingGame.getBoardState(), new Coordinate(1, 6), new Coordinate(2, 6),
				new Coordinate(0, 6), pullingGame.getOwner(), pullingGame.getNumMoves());
		testInvalidPull(move);
		// assertFalse(pullingGame.pull(6, 1, 6, 0, 1));
		// assertFalse(pullingGame.checkCoor(6, 2));
	}

	@Test
	public void testCantPullOwnPiece() {
		MoveCommand move = new PullMove(pullingGame.getBoardState(), new Coordinate(1, 2), new Coordinate(1, 3),
				new Coordinate(2, 2), pullingGame.getOwner(), pullingGame.getNumMoves());
		testInvalidPull(move);
		// assertFalse(pullingGame.pull(6, 1, 7, 1, 0));
		// assertFalse(pullingGame.checkCoor(5, 1));
	}

	@Test
	public void testCantPullPieceOfGreaterStrength() {
		MoveCommand move = new PullMove(pullingGame.getBoardState(), new Coordinate(3, 7), new Coordinate(6, 3),
				new Coordinate(4, 7), pullingGame.getOwner(), pullingGame.getNumMoves());
		testInvalidPull(move);
		// assertFalse(pullingGame.pull(7, 3, 7, 4, 3));
	}

	@Test
	public void testCantPullWith1Move() {
		assertTrue(pullingGame.move(new RegularMove(pullingGame.getBoardState(), new Coordinate(6, 0),
				new Coordinate(5, 0), pullingGame.getOwner(), pullingGame.getNumMoves())));
		assertTrue(pullingGame.move(new RegularMove(pullingGame.getBoardState(), new Coordinate(5, 0),
				new Coordinate(6, 0), pullingGame.getOwner(), pullingGame.getNumMoves())));
		assertTrue(pullingGame.move(new RegularMove(pullingGame.getBoardState(), new Coordinate(6, 0),
				new Coordinate(5, 0), pullingGame.getOwner(), pullingGame.getNumMoves())));
		MoveCommand move = new PullMove(pullingGame.getBoardState(), new Coordinate(5, 5), new Coordinate(5, 4),
				new Coordinate(5, 6), pullingGame.getOwner(), pullingGame.getNumMoves());
		testInvalidPull(move);
	}
}
