package move_commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import game.BoardState;
import game.Coordinate;

public class TestPushInvalid extends PushSetup {
	public void testPushInvalid(MoveCommand move) {
		BoardState board = new BoardState(pushingGame.getBoardState());

		assertFalse(move.isValidMove());
		assertFalse(pushingGame.move(move));

		assertEquals(board, pushingGame.getBoardState());
	}

	@Test
	public void testPushCantSwap() {
		MoveCommand move = new PushMove(pushingGame.getBoardState(), new Coordinate(5, 4), new Coordinate(6, 4),
				new Coordinate(5, 4), pushingGame.getOwner(), pushingGame.getNumMoves());
		testPushInvalid(move);
	}

	@Test
	public void testPushCantNotMove() {
		MoveCommand move = new PushMove(pushingGame.getBoardState(), new Coordinate(2, 6), new Coordinate(2, 6),
				new Coordinate(4, 6), pushingGame.getOwner(), pushingGame.getNumMoves());
		testPushInvalid(move);
	}

	@Test
	public void testPushCantNotMoveOtherPiece() {
		MoveCommand move = new PushMove(pushingGame.getBoardState(), new Coordinate(5, 4), new Coordinate(6, 4),
				new Coordinate(6, 4), pushingGame.getOwner(), pushingGame.getNumMoves());
		testPushInvalid(move);
	}

	@Test
	public void testPushOffBoard1() {
		MoveCommand move = new PushMove(g2.getBoardState(), new Coordinate(1, 1), new Coordinate(1, 0),
				new Coordinate(1, -1), g2.getOwner(), g2.getNumMoves());
		testPushInvalid(move);
	}

	@Test
	public void testPushOffBoard2() {
		MoveCommand move = new PushMove(g2.getBoardState(), new Coordinate(0, 7), new Coordinate(0, 8),
				new Coordinate(0, 9), g2.getOwner(), g2.getNumMoves());
		testPushInvalid(move);
	}

	@Test
	public void testPushOffBoard3() {
		MoveCommand move = new PushMove(g2.getBoardState(), new Coordinate(-1, 6), new Coordinate(0, 6),
				new Coordinate(1, 6), g2.getOwner(), g2.getNumMoves());
		testPushInvalid(move);
	}

	@Test
	public void testPushInvalid1() {
		MoveCommand move = new PushMove(g2.getBoardState(), new Coordinate(3, 3), new Coordinate(3, 2),
				new Coordinate(3, 1), g2.getOwner(), g2.getNumMoves());
		testPushInvalid(move);
		// assertFalse(g2.push(3, 3, 0, 0));
	}

	@Test
	public void testPushInvalid2() {
		MoveCommand move = new PushMove(g2.getBoardState(), new Coordinate(3, 4), new Coordinate(4, 4),
				new Coordinate(5, 4), g2.getOwner(), g2.getNumMoves());
		testPushInvalid(move);
		// assertFalse(g2.push(4, 3, 1, 1));
	}

	@Test
	public void testPushInvalid3() {
		MoveCommand move = new PushMove(g2.getBoardState(), new Coordinate(3, 4), new Coordinate(4, 4),
				new Coordinate(4, 3), g2.getOwner(), g2.getNumMoves());
		testPushInvalid(move);
		// assertFalse(g2.push(4, 3, 1, 0));
	}

	@Test
	public void testPushUpWithSamePlayersPieces() {
		MoveCommand move = new PushMove(pushingGame.getBoardState(), new Coordinate(2, 2), new Coordinate(2, 1),
				new Coordinate(2, 0), pushingGame.getOwner(), pushingGame.getNumMoves());
		testPushInvalid(move);

		// assertFalse(pushingGame.push(2, 2, 0, 0));
		// assertEquals(new Elephant(Owner.Player1), pushingGame.getPieceAt(new Coordinate(2, 2)));
		// assertEquals(new Rabbit(Owner.Player1), pushingGame.getPieceAt(new Coordinate(1, 2)));
	}

	@Test
	public void testPushRightWithSamePlayersPieces() {
		MoveCommand move = new PushMove(pushingGame.getBoardState(), new Coordinate(2, 2), new Coordinate(3, 2),
				new Coordinate(4, 2), pushingGame.getOwner(), pushingGame.getNumMoves());
		testPushInvalid(move);

		// assertFalse(pushingGame.push(2, 2, 1, 1));
		// assertEquals(new Elephant(Owner.Player1), pushingGame.getPieceAt(new Coordinate(2, 2)));
		// assertEquals(new Rabbit(Owner.Player1), pushingGame.getPieceAt(new Coordinate(2, 3)));
	}

	@Test
	public void testPushDownWithSamePlayersPieces() {
		MoveCommand move = new PushMove(pushingGame.getBoardState(), new Coordinate(2, 2), new Coordinate(2, 3),
				new Coordinate(2, 4), pushingGame.getOwner(), pushingGame.getNumMoves());
		testPushInvalid(move);

		// assertFalse(pushingGame.push(2, 2, 2, 2));
		// assertEquals(new Elephant(Owner.Player1), pushingGame.getPieceAt(new Coordinate(2, 2)));
		// assertEquals(new Rabbit(Owner.Player1), pushingGame.getPieceAt(new Coordinate(3, 2)));
	}

	@Test
	public void testPushLeftWithSamePlayersPieces() {
		MoveCommand move = new PushMove(pushingGame.getBoardState(), new Coordinate(2, 2), new Coordinate(1, 2),
				new Coordinate(0, 2), pushingGame.getOwner(), pushingGame.getNumMoves());
		testPushInvalid(move);

		// assertFalse(pushingGame.push(2, 2, 3, 3));
		// assertEquals(new Elephant(Owner.Player1), pushingGame.getPieceAt(new Coordinate(2, 2)));
		// assertEquals(new Rabbit(Owner.Player1), pushingGame.getPieceAt(new Coordinate(2, 1)));
	}

	@Test
	public void testThatPiecesMustBeStrongerToPush() {
		pushingGame.setPlayerTurn(2);
		MoveCommand move = new PushMove(pushingGame.getBoardState(), new Coordinate(3, 6), new Coordinate(3, 5),
				new Coordinate(3, 4), pushingGame.getOwner(), pushingGame.getNumMoves());
		testPushInvalid(move);

		// assertFalse(pushingGame.push(6, 3, 0, 0));
		// assertEquals(new Rabbit(Owner.Player2), pushingGame.getPieceAt(new Coordinate(6, 3)));
		// assertEquals(new Camel(Owner.Player1), pushingGame.getPieceAt(new Coordinate(5, 3)));
	}

	@Test
	public void testPushNotEnoughMoves() {
		assertTrue(pushingGame.move(new RegularMove(pushingGame.getBoardState(), new Coordinate(7, 1),
				new Coordinate(7, 2), pushingGame.getOwner(), pushingGame.getNumMoves())));
		assertTrue(pushingGame.move(new RegularMove(pushingGame.getBoardState(), new Coordinate(7, 2),
				new Coordinate(7, 3), pushingGame.getOwner(), pushingGame.getNumMoves())));
		assertTrue(pushingGame.move(new RegularMove(pushingGame.getBoardState(), new Coordinate(7, 3),
				new Coordinate(7, 4), pushingGame.getOwner(), pushingGame.getNumMoves())));
		// assertTrue(pushingGame.move(1, 7, 2));
		// assertTrue(pushingGame.move(2, 7, 2));
		// assertTrue(pushingGame.move(3, 7, 2));
		MoveCommand move = new PushMove(pushingGame.getBoardState(), new Coordinate(5, 4), new Coordinate(5, 3),
				new Coordinate(5, 2), pushingGame.getOwner(), pushingGame.getNumMoves());
		testPushInvalid(move);
		// assertFalse(pushingGame.push(4, 5, 0, 0));
	}
}
