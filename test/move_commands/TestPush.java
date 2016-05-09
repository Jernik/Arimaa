package move_commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import game.Coordinate;
import piece.AbstractPiece;
import piece.Camel;
import piece.Elephant;
import piece.Owner;

public class TestPush extends PushSetup{	
	@Test
	public void testBasicPushUp() {
		AbstractPiece p1 = g2.getPieceAt(new Coordinate(4, 4));
		AbstractPiece p2 = g2.getPieceAt(new Coordinate(4, 3));

		MoveCommand move = new PushMove(g2.getBoardState(), new Coordinate(4, 4), new Coordinate(4, 3),
				new Coordinate(4, 2), g2.getOwner(), g2.getNumMoves());
		assertTrue(move.isValidMove());
		assertTrue(g2.move(move));

		assertEquals(p1, g2.getPieceAt(new Coordinate(4, 3)));
		assertEquals(p2, g2.getPieceAt(new Coordinate(4, 2)));
		assertFalse(g2.isPieceAt(new Coordinate(4, 4)));

//		assertTrue(g2.push(4, 4, 0, 0));
//		assertTrue(g2.push(3, 4, 0, 0));
//		g2.setPlayerTurn(1);
//		assertTrue(g2.push(2, 4, 0, 0));
//		assertFalse(g2.push(1, 4, 0, 0));
	}

	@Test
	public void testBasicPushDown() {
		AbstractPiece p1 = g2.getPieceAt(new Coordinate(4, 4));
		AbstractPiece p2 = g2.getPieceAt(new Coordinate(4, 5));

		MoveCommand move = new PushMove(g2.getBoardState(), new Coordinate(4, 4), new Coordinate(4, 5),
				new Coordinate(4, 6), g2.getOwner(), g2.getNumMoves());
		assertTrue(move.isValidMove());
		assertTrue(g2.move(move));

		assertEquals(p1, g2.getPieceAt(new Coordinate(4, 5)));
		assertEquals(p2, g2.getPieceAt(new Coordinate(4, 6)));
		assertFalse(g2.isPieceAt(new Coordinate(4, 4)));

//		assertTrue(g2.push(4, 4, 2, 2));
//		assertTrue(g2.push(5, 4, 2, 2));
//		assertFalse(g2.push(6, 4, 2, 2));
//		assertFalse(g2.push(7, 4, 2, 2));
	}

	@Test
	public void testBasicPushRight() {
		AbstractPiece p1 = g2.getPieceAt(new Coordinate(4, 4));
		AbstractPiece p2 = g2.getPieceAt(new Coordinate(5, 4));

		MoveCommand move = new PushMove(g2.getBoardState(), new Coordinate(4, 4), new Coordinate(5, 4),
				new Coordinate(6, 4), g2.getOwner(), g2.getNumMoves());
		assertTrue(move.isValidMove());
		assertTrue(g2.move(move));

		assertEquals(p1, g2.getPieceAt(new Coordinate(5, 4)));
		assertEquals(p2, g2.getPieceAt(new Coordinate(6, 4)));
		assertFalse(g2.isPieceAt(new Coordinate(4, 4)));
		
//		assertTrue(g2.push(4, 4, 1, 1));
//		assertTrue(g2.push(4, 5, 1, 1));
//		assertFalse(g2.push(4, 6, 1, 1));
//		assertFalse(g2.push(4, 7, 1, 1));
	}

	@Test
	public void testBasicPushLeft() {
		AbstractPiece p1 = g2.getPieceAt(new Coordinate(6, 7));
		AbstractPiece p2 = g2.getPieceAt(new Coordinate(6, 6));

		MoveCommand move = new PushMove(g2.getBoardState(), new Coordinate(6, 7), new Coordinate(5, 7),
				new Coordinate(4, 7), g2.getOwner(), g2.getNumMoves());
		assertTrue(move.isValidMove());
		assertTrue(g2.move(move));

		assertEquals(p1, g2.getPieceAt(new Coordinate(5, 7)));
		assertEquals(p2, g2.getPieceAt(new Coordinate(4, 7)));
		assertFalse(g2.isPieceAt(new Coordinate(6, 7)));
		

//		assertTrue(g2.push(7, 6, 3, 3));
//		assertTrue(g2.push(7, 5, 3, 3));
//		g2.setPlayerTurn(1);
//		assertTrue(g2.push(7, 4, 3, 3));
//		assertTrue(g2.push(7, 3, 3, 3));
//		g2.setPlayerTurn(1);
//		assertTrue(g2.push(7, 2, 3, 3));
//		assertFalse(g2.push(7, 1, 3, 3));
	}

	@Test
	public void testPushWithDifferentDirections() {
		MoveCommand move = new PushMove(pushingGame.getBoardState(), new Coordinate(5, 4), new Coordinate(6, 4),
				new Coordinate(6, 3), pushingGame.getOwner(), pushingGame.getNumMoves());
		assertTrue(move.isValidMove());
		assertTrue(pushingGame.move(move));
//		assertTrue(pushingGame.push(4, 5, 1, 0));

		assertEquals(new Elephant(Owner.Player1), pushingGame.getPieceAt(new Coordinate(6, 4)));
		assertEquals(new Camel(Owner.Player2), pushingGame.getPieceAt(new Coordinate(6, 3)));
		assertFalse(pushingGame.isPieceAt(new Coordinate(5, 4)));
	}
}
