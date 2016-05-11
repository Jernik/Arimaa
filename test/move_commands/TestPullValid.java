package move_commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import board.Coordinate;
import piece.AbstractPiece;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestPullValid extends PullSetup {
	@Test
	public void testCanGetPullPiece() {
		Coordinate coor = new Coordinate(5, 6);
		PullMove move = new PullMove(pullingGame.getBoardState(), new Coordinate(5, 5), new Coordinate(5, 4), coor,
				pullingGame.getPlayerTurn(), pullingGame.getNumMoves());
		assertEquals(coor, move.getPullPiecePlace());
	}

	@Test
	public void testCanGetAffectedPieces() {
		Coordinate c1 = new Coordinate(5, 5);
		Coordinate c2 = new Coordinate(5, 4);
		Coordinate c3 = new Coordinate(5, 6);

		HashSet<Coordinate> set = new HashSet<Coordinate>();
		set.add(c1);
		set.add(c2);
		set.add(c3);

		PullMove move = new PullMove(pullingGame.getBoardState(), c1, c2, c3, pullingGame.getPlayerTurn(),
				pullingGame.getNumMoves());

		assertEquals(set, move.getAffectedCoordinates());
	}

	@Test
	public void testGetAffectedPiecesIsEmptyIfInvalid() {
		PullMove move = new PullMove(pullingGame.getBoardState(), new Coordinate(1, 5), new Coordinate(5, 4), new Coordinate(5, 6), pullingGame.getPlayerTurn(),
				pullingGame.getNumMoves());

		assertTrue(move.getAffectedCoordinates().isEmpty());
	}

	@Test
	public void testBasicPullUp() {
		AbstractPiece p1 = pullingGame.getPieceAt(new Coordinate(5, 5));
		AbstractPiece p2 = pullingGame.getPieceAt(new Coordinate(5, 6));

		MoveCommand move = new PullMove(pullingGame.getBoardState(), new Coordinate(5, 5), new Coordinate(5, 4),
				new Coordinate(5, 6), pullingGame.getPlayerTurn(), pullingGame.getNumMoves());
		assertTrue(move.isValidMove());
		assertTrue(pullingGame.move(move));

		assertEquals(p1, pullingGame.getPieceAt(new Coordinate(5, 4)));
		assertEquals(p2, pullingGame.getPieceAt(new Coordinate(5, 5)));
		assertFalse(pullingGame.isPieceAt(new Coordinate(5, 6)));
	}

	@Test
	public void testBasicPullRight() {
		AbstractPiece p1 = pullingGame.getPieceAt(new Coordinate(4, 4));
		AbstractPiece p2 = pullingGame.getPieceAt(new Coordinate(3, 4));

		MoveCommand move = new PullMove(pullingGame.getBoardState(), new Coordinate(4, 4), new Coordinate(5, 4),
				new Coordinate(3, 4), pullingGame.getPlayerTurn(), pullingGame.getNumMoves());
		assertTrue(move.isValidMove());
		assertTrue(pullingGame.move(move));

		assertEquals(p1, pullingGame.getPieceAt(new Coordinate(5, 4)));
		assertEquals(p2, pullingGame.getPieceAt(new Coordinate(4, 4)));
		assertFalse(pullingGame.isPieceAt(new Coordinate(3, 4)));
	}

	@Test
	public void testBasicPullDown() {
		AbstractPiece p1 = pullingGame.getPieceAt(new Coordinate(5, 3));
		AbstractPiece p2 = pullingGame.getPieceAt(new Coordinate(5, 2));

		MoveCommand move = new PullMove(pullingGame.getBoardState(), new Coordinate(5, 3), new Coordinate(5, 4),
				new Coordinate(5, 2), pullingGame.getPlayerTurn(), pullingGame.getNumMoves());
		assertTrue(move.isValidMove());
		assertTrue(pullingGame.move(move));

		assertEquals(p1, pullingGame.getPieceAt(new Coordinate(5, 4)));
		assertEquals(p2, pullingGame.getPieceAt(new Coordinate(5, 3)));
		assertFalse(pullingGame.isPieceAt(new Coordinate(5, 2)));
	}

	@Test
	public void testBasicPullLeft() {
		AbstractPiece p1 = pullingGame.getPieceAt(new Coordinate(6, 4));
		AbstractPiece p2 = pullingGame.getPieceAt(new Coordinate(7, 4));

		MoveCommand move = new PullMove(pullingGame.getBoardState(), new Coordinate(6, 4), new Coordinate(5, 4),
				new Coordinate(7, 4), pullingGame.getPlayerTurn(), pullingGame.getNumMoves());
		assertTrue(move.isValidMove());
		assertTrue(pullingGame.move(move));

		assertEquals(p1, pullingGame.getPieceAt(new Coordinate(5, 4)));
		assertEquals(p2, pullingGame.getPieceAt(new Coordinate(6, 4)));
		assertFalse(pullingGame.isPieceAt(new Coordinate(7, 4)));
	}

	@Test
	public void testBidirectionalPullUpRight() {
		AbstractPiece p1 = pullingGame.getPieceAt(new Coordinate(4, 4));
		AbstractPiece p2 = pullingGame.getPieceAt(new Coordinate(3, 4));

		MoveCommand move = new PullMove(pullingGame.getBoardState(), new Coordinate(4, 4), new Coordinate(4, 3),
				new Coordinate(3, 4), pullingGame.getPlayerTurn(), pullingGame.getNumMoves());
		assertTrue(move.isValidMove());
		assertTrue(pullingGame.move(move));

		assertEquals(p1, pullingGame.getPieceAt(new Coordinate(4, 3)));
		assertEquals(p2, pullingGame.getPieceAt(new Coordinate(4, 4)));
		assertFalse(pullingGame.isPieceAt(new Coordinate(3, 4)));
	}

	@Test
	public void testBidirectionalPullRightDown() {
		AbstractPiece p1 = pullingGame.getPieceAt(new Coordinate(5, 3));
		AbstractPiece p2 = pullingGame.getPieceAt(new Coordinate(5, 2));

		MoveCommand move = new PullMove(pullingGame.getBoardState(), new Coordinate(5, 3), new Coordinate(6, 3),
				new Coordinate(5, 2), pullingGame.getPlayerTurn(), pullingGame.getNumMoves());
		assertTrue(move.isValidMove());
		assertTrue(pullingGame.move(move));

		assertEquals(p1, pullingGame.getPieceAt(new Coordinate(6, 3)));
		assertEquals(p2, pullingGame.getPieceAt(new Coordinate(5, 3)));
		assertFalse(pullingGame.isPieceAt(new Coordinate(5, 2)));
	}

	@Test
	public void testBidirectionalPullDownLeft() {
		AbstractPiece p1 = pullingGame.getPieceAt(new Coordinate(6, 4));
		AbstractPiece p2 = pullingGame.getPieceAt(new Coordinate(7, 4));

		MoveCommand move = new PullMove(pullingGame.getBoardState(), new Coordinate(6, 4), new Coordinate(6, 3),
				new Coordinate(7, 4), pullingGame.getPlayerTurn(), pullingGame.getNumMoves());
		assertTrue(move.isValidMove());
		assertTrue(pullingGame.move(move));

		assertEquals(p1, pullingGame.getPieceAt(new Coordinate(6, 3)));
		assertEquals(p2, pullingGame.getPieceAt(new Coordinate(6, 4)));
		assertFalse(pullingGame.isPieceAt(new Coordinate(7, 4)));
	}

	@Test
	public void testBidirectionalPullLeftUp() {
		AbstractPiece p1 = pullingGame.getPieceAt(new Coordinate(5, 5));
		AbstractPiece p2 = pullingGame.getPieceAt(new Coordinate(5, 6));

		MoveCommand move = new PullMove(pullingGame.getBoardState(), new Coordinate(5, 5), new Coordinate(4, 5),
				new Coordinate(5, 6), pullingGame.getPlayerTurn(), pullingGame.getNumMoves());
		assertTrue(move.isValidMove());
		assertTrue(pullingGame.move(move));

		assertEquals(p1, pullingGame.getPieceAt(new Coordinate(4, 5)));
		assertEquals(p2, pullingGame.getPieceAt(new Coordinate(5, 5)));
		assertFalse(pullingGame.isPieceAt(new Coordinate(5, 6)));
	}

	@Test
	public void testCanPullIfFrozenButThawed() {
		pullingGame.getBoardState().movePiece(new Coordinate(0, 7), new Coordinate(0, 5));
		AbstractPiece p1 = pullingGame.getPieceAt(new Coordinate(1, 5));
		AbstractPiece p2 = pullingGame.getPieceAt(new Coordinate(2, 5));

		MoveCommand move = new PullMove(pullingGame.getBoardState(), new Coordinate(1, 5), new Coordinate(1, 4),
				new Coordinate(2, 5), pullingGame.getPlayerTurn(), pullingGame.getNumMoves());

		assertTrue(move.isValidMove());
		assertTrue(pullingGame.move(move));

		assertEquals(p1, pullingGame.getPieceAt(new Coordinate(1, 4)));
		assertEquals(p2, pullingGame.getPieceAt(new Coordinate(1, 5)));
		assertFalse(pullingGame.isPieceAt(new Coordinate(2, 5)));
	}
}
