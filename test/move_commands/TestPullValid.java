package move_commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import game.Coordinate;
import piece.AbstractPiece;
import piece.Elephant;
import piece.Owner;
import piece.Rabbit;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestPullValid extends PullSetup {
	@Test
	public void testBasicPullUp() {
		MoveCommand move = new PullMove(pullingGame.getBoardState(), new Coordinate(5, 5), new Coordinate(5, 4),
				new Coordinate(5, 6), pullingGame.getOwner(), pullingGame.getNumMoves());
		assertTrue(pullingGame.move(move));
		// assertTrue(pullingGame.pull(5, 5, 6, 5, 0));
		assertEquals(new Rabbit(Owner.Player2), pullingGame.getPieceAt(new Coordinate(5, 5)));
		assertEquals(new Elephant(Owner.Player1), pullingGame.getPieceAt(new Coordinate(4, 5)));
		assertFalse(pullingGame.isPieceAt(new Coordinate(5, 6)));
	}

	@Test
	public void testBasicPullRight() {
		MoveCommand move = new PullMove(pullingGame.getBoardState(), new Coordinate(4, 4), new Coordinate(5, 4),
				new Coordinate(3, 4), pullingGame.getOwner(), pullingGame.getNumMoves());
		assertTrue(pullingGame.move(move));
		// assertTrue(pullingGame.pull(4, 4, 4, 3, 1));
		assertEquals(new Rabbit(Owner.Player2), pullingGame.getPieceAt(new Coordinate(4, 4)));
		assertEquals(new Elephant(Owner.Player1), pullingGame.getPieceAt(new Coordinate(4, 5)));
		assertFalse(pullingGame.isPieceAt(new Coordinate(3, 4)));
	}

	@Test
	public void testBasicPullDown() {
		MoveCommand move = new PullMove(pullingGame.getBoardState(), new Coordinate(5, 3), new Coordinate(5, 4),
				new Coordinate(5, 2), pullingGame.getOwner(), pullingGame.getNumMoves());
		assertTrue(pullingGame.move(move));
		// assertTrue(pullingGame.pull(3, 5, 2, 5, 2));
		assertEquals(new Rabbit(Owner.Player2), pullingGame.getPieceAt(new Coordinate(3, 5)));
		assertEquals(new Elephant(Owner.Player1), pullingGame.getPieceAt(new Coordinate(4, 5)));
		assertFalse(pullingGame.isPieceAt(new Coordinate(5, 2)));
	}

	@Test
	public void testBasicPullLeft() {
		MoveCommand move = new PullMove(pullingGame.getBoardState(), new Coordinate(6, 4), new Coordinate(5, 4),
				new Coordinate(7, 4), pullingGame.getOwner(), pullingGame.getNumMoves());
		assertTrue(pullingGame.move(move));
		// assertTrue(pullingGame.pull(4, 6, 4, 7, 3));
		assertEquals(new Rabbit(Owner.Player2), pullingGame.getPieceAt(new Coordinate(4, 6)));
		assertEquals(new Elephant(Owner.Player1), pullingGame.getPieceAt(new Coordinate(4, 5)));
		assertFalse(pullingGame.isPieceAt(new Coordinate(7, 4)));
	}

	@Test
	public void testBidirectionalPullUpRight() {
		AbstractPiece p1 = pullingGame.getPieceAt(new Coordinate(4, 4));
		AbstractPiece p2 = pullingGame.getPieceAt(new Coordinate(3, 4));

		MoveCommand move = new PullMove(pullingGame.getBoardState(), new Coordinate(4, 4), new Coordinate(4, 3),
				new Coordinate(3, 4), pullingGame.getOwner(), pullingGame.getNumMoves());
		assertTrue(move.isValidMove());
		assertTrue(pullingGame.move(move));
		// assertTrue(pullingGame.pull(4, 4, 4, 3, 0));

		assertEquals(p1, pullingGame.getPieceAt(new Coordinate(4, 3)));
		assertEquals(p2, pullingGame.getPieceAt(new Coordinate(4, 4)));
		assertFalse(pullingGame.isPieceAt(new Coordinate(3, 4)));
	}

	@Test
	public void testBidirectionalPullRightDown() {
		AbstractPiece p1 = pullingGame.getPieceAt(new Coordinate(5, 3));
		AbstractPiece p2 = pullingGame.getPieceAt(new Coordinate(5, 2));

		MoveCommand move = new PullMove(pullingGame.getBoardState(), new Coordinate(5, 3), new Coordinate(6, 3),
				new Coordinate(5, 2), pullingGame.getOwner(), pullingGame.getNumMoves());
		assertTrue(move.isValidMove());
		assertTrue(pullingGame.move(move));
		// assertTrue(pullingGame.pull(3, 5, 2, 5, 1));

		assertEquals(p1, pullingGame.getPieceAt(new Coordinate(6, 3)));
		assertEquals(p2, pullingGame.getPieceAt(new Coordinate(5, 3)));
		assertFalse(pullingGame.isPieceAt(new Coordinate(5, 2)));
	}

	@Test
	public void testBidirectionalPullDownLeft() {
		AbstractPiece p1 = pullingGame.getPieceAt(new Coordinate(4, 6));
		AbstractPiece p2 = pullingGame.getPieceAt(new Coordinate(4, 7));

		MoveCommand move = new PullMove(pullingGame.getBoardState(), new Coordinate(6, 4), new Coordinate(6, 3),
				new Coordinate(7, 4), pullingGame.getOwner(), pullingGame.getNumMoves());
		assertTrue(move.isValidMove());
		assertTrue(pullingGame.move(move));
		// assertTrue(pullingGame.pull(4, 6, 4, 7, 0));

		assertEquals(p1, pullingGame.getPieceAt(new Coordinate(3, 6)));
		assertEquals(p2, pullingGame.getPieceAt(new Coordinate(4, 6)));
	}

	@Test
	public void testBidirectionalPullLeftUp() {
		AbstractPiece p1 = pullingGame.getPieceAt(new Coordinate(5, 5));
		AbstractPiece p2 = pullingGame.getPieceAt(new Coordinate(6, 5));

		MoveCommand move = new PullMove(pullingGame.getBoardState(), new Coordinate(5, 5), new Coordinate(4, 5),
				new Coordinate(5, 6), pullingGame.getOwner(), pullingGame.getNumMoves());
		assertTrue(move.isValidMove());
		assertTrue(pullingGame.move(move));
		// assertTrue(pullingGame.pull(5, 5, 6, 5, 3));

		assertEquals(p1, pullingGame.getPieceAt(new Coordinate(5, 4)));
		assertEquals(p2, pullingGame.getPieceAt(new Coordinate(5, 5)));
		assertFalse(pullingGame.isPieceAt(new Coordinate(5, 6)));
	}
}
