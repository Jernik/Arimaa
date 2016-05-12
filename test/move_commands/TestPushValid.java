package move_commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Test;

import board.Coordinate;
import piece.AbstractPiece;
import piece.Camel;
import piece.Elephant;
import piece.Owner;

public class TestPushValid extends PushSetup {
	@Test
	public void testPushCanGetPushPiece() {
		Coordinate coor = new Coordinate(4, 2);
		PushMove move = new PushMove(g2, new Coordinate(4, 4), new Coordinate(4, 3), coor);
		assertEquals(coor, move.getPushPiecePlace());
	}

	@Test
	public void testCanGetAffectedCoordinates() {
		Coordinate c1 = new Coordinate(4, 4);
		Coordinate c2 = new Coordinate(4, 3);
		Coordinate c3 = new Coordinate(4, 2);

		ArrayList<CoordinatePair> map = new ArrayList<CoordinatePair>();
		map.add(new CoordinatePair(c2, c3));
		map.add(new CoordinatePair(c1, c2));

		PushMove move = new PushMove(g2, c1, c2, c3);

		assertEquals(map, move.getAffectedCoordinates());
	}

	@Test
	public void testGetAffectedPiecesIsEmptyIfInvalid() {
		PushMove move = new PushMove(g2, new Coordinate(-1, 4), new Coordinate(4, 3), new Coordinate(4, 2));

		assertTrue(move.getAffectedCoordinates().isEmpty());
	}

	@Test
	public void testBasicPushUp() {
		AbstractPiece p1 = g2.getPieceAt(new Coordinate(4, 4));
		AbstractPiece p2 = g2.getPieceAt(new Coordinate(4, 3));

		MoveCommand move = new PushMove(g2, new Coordinate(4, 4), new Coordinate(4, 3), new Coordinate(4, 2));
		assertTrue(move.isValidMove());
		assertTrue(g2.move(move));

		assertEquals(p1, g2.getPieceAt(new Coordinate(4, 3)));
		assertEquals(p2, g2.getPieceAt(new Coordinate(4, 2)));
		assertFalse(g2.isPieceAt(new Coordinate(4, 4)));
	}

	@Test
	public void testBasicPushDown() {
		AbstractPiece p1 = g2.getPieceAt(new Coordinate(4, 4));
		AbstractPiece p2 = g2.getPieceAt(new Coordinate(4, 5));

		MoveCommand move = new PushMove(g2, new Coordinate(4, 4), new Coordinate(4, 5), new Coordinate(4, 6));
		assertTrue(move.isValidMove());
		assertTrue(g2.move(move));

		assertEquals(p1, g2.getPieceAt(new Coordinate(4, 5)));
		assertEquals(p2, g2.getPieceAt(new Coordinate(4, 6)));
		assertFalse(g2.isPieceAt(new Coordinate(4, 4)));
	}

	@Test
	public void testBasicPushRight() {
		AbstractPiece p1 = g2.getPieceAt(new Coordinate(4, 4));
		AbstractPiece p2 = g2.getPieceAt(new Coordinate(5, 4));

		MoveCommand move = new PushMove(g2, new Coordinate(4, 4), new Coordinate(5, 4), new Coordinate(6, 4));
		assertTrue(move.isValidMove());
		assertTrue(g2.move(move));

		assertEquals(p1, g2.getPieceAt(new Coordinate(5, 4)));
		assertEquals(p2, g2.getPieceAt(new Coordinate(6, 4)));
		assertFalse(g2.isPieceAt(new Coordinate(4, 4)));
	}

	@Test
	public void testBasicPushLeft() {
		AbstractPiece p1 = g2.getPieceAt(new Coordinate(6, 7));
		AbstractPiece p2 = g2.getPieceAt(new Coordinate(5, 7));

		MoveCommand move = new PushMove(g2, new Coordinate(6, 7), new Coordinate(5, 7), new Coordinate(4, 7));
		assertTrue(move.isValidMove());
		assertTrue(g2.move(move));

		assertEquals(p1, g2.getPieceAt(new Coordinate(5, 7)));
		assertEquals(p2, g2.getPieceAt(new Coordinate(4, 7)));
		assertFalse(g2.isPieceAt(new Coordinate(6, 7)));
	}

	@Test
	public void testPushWithDifferentDirections() {
		MoveCommand move = new PushMove(pushingGame, new Coordinate(5, 4), new Coordinate(6, 4), new Coordinate(6, 3));
		assertTrue(move.isValidMove());
		assertTrue(pushingGame.move(move));

		assertEquals(new Elephant(Owner.Player1), pushingGame.getPieceAt(new Coordinate(6, 4)));
		assertEquals(new Camel(Owner.Player2), pushingGame.getPieceAt(new Coordinate(6, 3)));
		assertFalse(pushingGame.isPieceAt(new Coordinate(5, 4)));
	}

	@Test
	public void testCanPushIfFrozenButThawed() {
		pushingGame.incrementTurn();
		AbstractPiece p1 = pushingGame.getPieceAt(new Coordinate(5, 5));
		AbstractPiece p2 = pushingGame.getPieceAt(new Coordinate(6, 5));
		pushingGame.getBoardState().movePiece(new Coordinate(4, 4), new Coordinate(4, 5));

		MoveCommand move = new PushMove(pushingGame, new Coordinate(5, 5), new Coordinate(6, 5), new Coordinate(7, 5));

		assertTrue(move.isValidMove());
		assertTrue(pushingGame.move(move));

		assertEquals(p1, pushingGame.getPieceAt(new Coordinate(6, 5)));
		assertEquals(p2, pushingGame.getPieceAt(new Coordinate(7, 5)));
		assertFalse(pushingGame.isPieceAt(new Coordinate(5, 5)));
	}
}
