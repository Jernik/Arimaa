package game;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;

import org.junit.Test;

import piece.AbstractPiece;
import piece.Camel;
import piece.Cat;
import piece.Dog;
import piece.Elephant;
import piece.Horse;
import piece.Owner;
import piece.Rabbit;

/**
 * @author shellajt
 *
 */
public class TestBoardState {
	@Test
	public void testInitializes() {
		BoardState b = new BoardState();
		assertNotNull(b);
		int[] ys = new int[] { 0, 1, 6, 7 };
		for (int y : ys) {
			for (int x = 0; x < BoardState.MAX_BOARD_SIZE; x++) {
				assertTrue(b.pieceAt(new Coordinate(x, y)));
			}
		}

		assertEquals(new Rabbit(Owner.Player2), b.getPieceAt(new Coordinate(4, 6)));
		assertEquals(new Camel(Owner.Player2), b.getPieceAt(new Coordinate(3, 7)));
		assertEquals(new Elephant(Owner.Player2), b.getPieceAt(new Coordinate(4, 7)));

		assertEquals(new Rabbit(Owner.Player1), b.getPieceAt(new Coordinate(4, 1)));
		assertEquals(new Camel(Owner.Player1), b.getPieceAt(new Coordinate(3, 0)));
		assertEquals(new Elephant(Owner.Player1), b.getPieceAt(new Coordinate(4, 0)));
	}
	
	@Test
	public void testCopyConstructor() {
		BoardState b = new BoardState();
		BoardState bCopy = new BoardState(b);
		assertFalse(b == bCopy);
		assertFalse(b.getPieces() == bCopy.getPieces());
		assertEquals(b, bCopy);
		
		
		b.movePiece(new Coordinate(0,1), new Coordinate(0, 2));
		assertNotEquals(b, bCopy);
	}

	@Test
	public void testPieceAt() {
		HashMap<Coordinate, AbstractPiece> pieces = new HashMap<Coordinate, AbstractPiece>();
		pieces.put(new Coordinate(0, 0), new Cat(Owner.Player1));
		pieces.put(new Coordinate(1, 1), new Cat(Owner.Player1));
		pieces.put(new Coordinate(2, 2), new Cat(Owner.Player1));
		pieces.put(new Coordinate(3, 3), new Cat(Owner.Player2));

		BoardState b = new BoardState(pieces);

		assertTrue(b.pieceAt(new Coordinate(0, 0)));
		assertTrue(b.pieceAt(new Coordinate(1, 1)));
		assertTrue(b.pieceAt(new Coordinate(2, 2)));
		assertTrue(b.pieceAt(new Coordinate(3, 3)));

		assertFalse(b.pieceAt(new Coordinate(0, 1)));
		assertFalse(b.pieceAt(new Coordinate(4, 2)));
		assertFalse(b.pieceAt(new Coordinate(7, 3)));
		assertFalse(b.pieceAt(new Coordinate(10, 1)));
		assertFalse(b.pieceAt(new Coordinate(-2, -2)));
	}

	@Test
	public void testGetPieceAt() {
		HashMap<Coordinate, AbstractPiece> pieces = new HashMap<Coordinate, AbstractPiece>();
		AbstractPiece p1 = new Cat(Owner.Player1);
		AbstractPiece p2 = new Camel(Owner.Player1);
		AbstractPiece p3 = new Horse(Owner.Player2);
		AbstractPiece p4 = new Dog(Owner.Player2);

		pieces.put(new Coordinate(0, 0), p1);
		pieces.put(new Coordinate(1, 1), p2);
		pieces.put(new Coordinate(2, 2), p3);
		pieces.put(new Coordinate(3, 3), p4);

		BoardState b = new BoardState(pieces);
		assertEquals(p1, b.getPieceAt(new Coordinate(0, 0)));
		assertEquals(p2, b.getPieceAt(new Coordinate(1, 1)));
		assertEquals(p3, b.getPieceAt(new Coordinate(2, 2)));
		assertEquals(p4, b.getPieceAt(new Coordinate(3, 3)));

		assertNull(b.getPieceAt(new Coordinate(1, 0)));
	}

	@Test
	public void testValidMove() {
		HashMap<Coordinate, AbstractPiece> pieces = new HashMap<Coordinate, AbstractPiece>();
		AbstractPiece p1 = new Cat(Owner.Player1);
		AbstractPiece p2 = new Camel(Owner.Player1);
		AbstractPiece p3 = new Horse(Owner.Player2);
		AbstractPiece p4 = new Dog(Owner.Player2);

		pieces.put(new Coordinate(0, 0), p1);
		pieces.put(new Coordinate(1, 1), p2);
		pieces.put(new Coordinate(2, 2), p3);
		pieces.put(new Coordinate(3, 3), p4);

		BoardState b = new BoardState(pieces);
		assertTrue(b.movePiece(new Coordinate(0, 0), new Coordinate(1, 0)));

		assertFalse(b.pieceAt(new Coordinate(0, 0)));
		assertEquals(p1, b.getPieceAt(new Coordinate(1, 0)));

		assertEquals(p2, b.getPieceAt(new Coordinate(1, 1)));
		assertEquals(p3, b.getPieceAt(new Coordinate(2, 2)));
		assertEquals(p4, b.getPieceAt(new Coordinate(3, 3)));
	}

	@Test
	public void testInValidMoveNoPiece() {
		HashMap<Coordinate, AbstractPiece> pieces = new HashMap<Coordinate, AbstractPiece>();
		AbstractPiece p1 = new Cat(Owner.Player1);
		AbstractPiece p2 = new Camel(Owner.Player1);
		AbstractPiece p3 = new Horse(Owner.Player2);
		AbstractPiece p4 = new Dog(Owner.Player2);

		pieces.put(new Coordinate(0, 0), p1);
		pieces.put(new Coordinate(1, 1), p2);
		pieces.put(new Coordinate(2, 2), p3);
		pieces.put(new Coordinate(3, 3), p4);

		BoardState b = new BoardState(pieces);
		assertFalse(b.pieceAt(new Coordinate(1, 0)));

		assertFalse(b.movePiece(new Coordinate(1, 0), new Coordinate(2, 0)));

		assertTrue(b.pieceAt(new Coordinate(0, 0)));
		assertFalse(b.pieceAt(new Coordinate(2, 0)));

		assertEquals(p1, b.getPieceAt(new Coordinate(0, 0)));
		assertEquals(p2, b.getPieceAt(new Coordinate(1, 1)));
		assertEquals(p3, b.getPieceAt(new Coordinate(2, 2)));
		assertEquals(p4, b.getPieceAt(new Coordinate(3, 3)));
	}

	@Test
	public void testInValidMovePieceAlreadyThere() {
		HashMap<Coordinate, AbstractPiece> pieces = new HashMap<Coordinate, AbstractPiece>();
		AbstractPiece p1 = new Cat(Owner.Player1);
		AbstractPiece p2 = new Camel(Owner.Player1);
		AbstractPiece p3 = new Horse(Owner.Player2);
		AbstractPiece p4 = new Dog(Owner.Player2);

		pieces.put(new Coordinate(0, 0), p1);
		pieces.put(new Coordinate(1, 1), p2);
		pieces.put(new Coordinate(2, 2), p3);
		pieces.put(new Coordinate(3, 3), p4);

		BoardState b = new BoardState(pieces);

		assertFalse(b.movePiece(new Coordinate(0, 0), new Coordinate(1, 1)));

		assertEquals(p1, b.getPieceAt(new Coordinate(0, 0)));
		assertEquals(p2, b.getPieceAt(new Coordinate(1, 1)));
		assertEquals(p3, b.getPieceAt(new Coordinate(2, 2)));
		assertEquals(p4, b.getPieceAt(new Coordinate(3, 3)));
	}

	@Test
	public void testValidRemove() {
		HashMap<Coordinate, AbstractPiece> pieces = new HashMap<Coordinate, AbstractPiece>();
		AbstractPiece p1 = new Cat(Owner.Player1);
		AbstractPiece p2 = new Camel(Owner.Player1);
		AbstractPiece p3 = new Horse(Owner.Player2);
		AbstractPiece p4 = new Dog(Owner.Player2);

		pieces.put(new Coordinate(0, 0), p1);
		pieces.put(new Coordinate(1, 1), p2);
		pieces.put(new Coordinate(2, 2), p3);
		pieces.put(new Coordinate(3, 3), p4);

		BoardState b = new BoardState(pieces);

		assertTrue(b.removePiece(new Coordinate(0, 0)));
		assertFalse(b.pieceAt(new Coordinate(0, 0)));
	}

	@Test
	public void testInValidRemove() {
		HashMap<Coordinate, AbstractPiece> pieces = new HashMap<Coordinate, AbstractPiece>();
		AbstractPiece p1 = new Cat(Owner.Player1);
		AbstractPiece p2 = new Camel(Owner.Player1);
		AbstractPiece p3 = new Horse(Owner.Player2);
		AbstractPiece p4 = new Dog(Owner.Player2);

		pieces.put(new Coordinate(0, 0), p1);
		pieces.put(new Coordinate(1, 1), p2);
		pieces.put(new Coordinate(2, 2), p3);
		pieces.put(new Coordinate(3, 3), p4);

		BoardState b = new BoardState(pieces);

		assertFalse(b.removePiece(new Coordinate(1, 0)));
		assertFalse(b.pieceAt(new Coordinate(1, 0)));
	}

	@Test
	public void testClone() {
		HashMap<Coordinate, AbstractPiece> pieces = new HashMap<Coordinate, AbstractPiece>();
		AbstractPiece p1 = new Cat(Owner.Player1);
		AbstractPiece p2 = new Camel(Owner.Player1);
		AbstractPiece p3 = new Horse(Owner.Player2);
		AbstractPiece p4 = new Dog(Owner.Player2);

		pieces.put(new Coordinate(0, 0), p1);
		pieces.put(new Coordinate(1, 1), p2);
		pieces.put(new Coordinate(2, 2), p3);
		pieces.put(new Coordinate(3, 3), p4);

		BoardState b = new BoardState(pieces);
		BoardState cloned = b.clone();
		assertEquals(b, cloned);
		b.movePiece(new Coordinate(0, 0), new Coordinate(1, 0));

		assertEquals(p1, cloned.getPieceAt(new Coordinate(0, 0)));
		assertFalse(b.pieceAt(new Coordinate(0, 0)));
		assertEquals(p1, b.getPieceAt(new Coordinate(1, 0)));
	}

	@Test
	public void testEquals() {
		HashMap<Coordinate, AbstractPiece> pieces1 = new HashMap<Coordinate, AbstractPiece>();
		AbstractPiece p11 = new Cat(Owner.Player1);
		AbstractPiece p12 = new Camel(Owner.Player1);
		AbstractPiece p13 = new Horse(Owner.Player2);
		AbstractPiece p14 = new Dog(Owner.Player2);

		pieces1.put(new Coordinate(0, 0), p11);
		pieces1.put(new Coordinate(1, 1), p12);
		pieces1.put(new Coordinate(2, 2), p13);
		pieces1.put(new Coordinate(3, 3), p14);

		BoardState b1 = new BoardState(pieces1);

		HashMap<Coordinate, AbstractPiece> pieces2 = new HashMap<Coordinate, AbstractPiece>();
		AbstractPiece p21 = new Cat(Owner.Player1);
		AbstractPiece p22 = new Camel(Owner.Player1);
		AbstractPiece p23 = new Horse(Owner.Player2);
		AbstractPiece p24 = new Dog(Owner.Player2);

		pieces2.put(new Coordinate(0, 0), p21);
		pieces2.put(new Coordinate(1, 1), p22);
		pieces2.put(new Coordinate(2, 2), p23);
		pieces2.put(new Coordinate(3, 3), p24);

		BoardState b2 = new BoardState(pieces2);

		HashMap<Coordinate, AbstractPiece> pieces3 = new HashMap<Coordinate, AbstractPiece>();
		AbstractPiece p31 = new Cat(Owner.Player1);
		AbstractPiece p32 = new Camel(Owner.Player1);
		AbstractPiece p33 = new Horse(Owner.Player2);
		AbstractPiece p34 = new Dog(Owner.Player2);

		pieces3.put(new Coordinate(0, 0), p31);
		pieces3.put(new Coordinate(1, 1), p32);
		pieces3.put(new Coordinate(2, 2), p34);
		pieces3.put(new Coordinate(3, 3), p33);

		BoardState b3 = new BoardState(pieces3);

		assertNotEquals(b1, null); // null test
		assertEquals(b1, b1); // reflexive

		assertEquals(b1, b2); // symmetric
		assertEquals(b2, b1);

		assertEquals(new BoardState(), new BoardState());

		assertNotEquals(b1, b3);
		assertNotEquals(b3, b1);

		assertNotEquals(b1, new BoardState());
		assertNotEquals(new BoardState(), b1);
	}

	@Test
	public void testHashCode() {
		HashMap<Coordinate, AbstractPiece> pieces1 = new HashMap<Coordinate, AbstractPiece>();
		AbstractPiece p11 = new Cat(Owner.Player1);
		AbstractPiece p12 = new Camel(Owner.Player1);
		AbstractPiece p13 = new Horse(Owner.Player2);
		AbstractPiece p14 = new Dog(Owner.Player2);

		pieces1.put(new Coordinate(0, 0), p11);
		pieces1.put(new Coordinate(1, 1), p12);
		pieces1.put(new Coordinate(2, 2), p13);
		pieces1.put(new Coordinate(3, 3), p14);

		BoardState b1 = new BoardState(pieces1);

		HashMap<Coordinate, AbstractPiece> pieces2 = new HashMap<Coordinate, AbstractPiece>();
		AbstractPiece p21 = new Cat(Owner.Player1);
		AbstractPiece p22 = new Camel(Owner.Player1);
		AbstractPiece p23 = new Horse(Owner.Player2);
		AbstractPiece p24 = new Dog(Owner.Player2);

		pieces2.put(new Coordinate(0, 0), p21);
		pieces2.put(new Coordinate(1, 1), p22);
		pieces2.put(new Coordinate(2, 2), p23);
		pieces2.put(new Coordinate(3, 3), p24);

		BoardState b2 = new BoardState(pieces2);

		HashMap<Coordinate, AbstractPiece> pieces3 = new HashMap<Coordinate, AbstractPiece>();
		AbstractPiece p31 = new Cat(Owner.Player1);
		AbstractPiece p32 = new Camel(Owner.Player1);
		AbstractPiece p33 = new Horse(Owner.Player2);
		AbstractPiece p34 = new Dog(Owner.Player2);

		pieces3.put(new Coordinate(0, 0), p31);
		pieces3.put(new Coordinate(1, 1), p32);
		pieces3.put(new Coordinate(2, 2), p34);
		pieces3.put(new Coordinate(3, 3), p33);

		BoardState b3 = new BoardState(pieces3);

		assertEquals(b1.hashCode(), b1.hashCode());
		assertEquals(b1.hashCode(), b2.hashCode());

		assertNotEquals(b1.hashCode(), b3.hashCode());
		assertNotEquals(b1.hashCode(), new BoardState().hashCode());
	}
}
