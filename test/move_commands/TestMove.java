package move_commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

import game.BoardState;
import game.Coordinate;
import game.Game;
import piece.AbstractPiece;
import piece.Camel;
import piece.Cat;
import piece.Dog;
import piece.Elephant;
import piece.Owner;
import piece.Rabbit;

public class TestMove {
	private Game g;
	private Game freezingGame;

	@Before
	public void setup() {
		g = new Game();

		HashMap<Coordinate, AbstractPiece> fp = new HashMap<Coordinate, AbstractPiece>();
		fp.put(new Coordinate(6, 7), new Cat(Owner.Player1));
		fp.put(new Coordinate(7, 7), new Rabbit(Owner.Player1));
		fp.put(new Coordinate(3, 4), new Rabbit(Owner.Player1));
		fp.put(new Coordinate(4, 3), new Rabbit(Owner.Player1));
		fp.put(new Coordinate(5, 3), new Elephant(Owner.Player1));
		fp.put(new Coordinate(4, 4), new Camel(Owner.Player2));
		BoardState fb = new BoardState(fp);
		freezingGame = new Game(fb);
	}

	@Test
	public void testMoveLegal() {
		HashMap<Coordinate, AbstractPiece> p1 = new HashMap<Coordinate, AbstractPiece>();
		p1.put(new Coordinate(6, 7), new Cat(Owner.Player1));
		p1.put(new Coordinate(7, 7), new Cat(Owner.Player1));
		p1.put(new Coordinate(3, 6), new Dog(Owner.Player1));
		p1.put(new Coordinate(3, 4), new Camel(Owner.Player1));
		p1.put(new Coordinate(4, 4), new Elephant(Owner.Player1));
		BoardState b1 = new BoardState(p1);
		Game g1 = new Game(b1);

		assertEquals(new Cat(Owner.Player1), g1.getPieceAt(new Coordinate(7, 7)));

		Coordinate start = new Coordinate(7, 7);
		Coordinate end = new Coordinate(7, 6);
		Owner owner = g1.getOwner();
		MoveCommand move = new RegularMove(g1.getBoardState(), start, end, owner, g1.getNumMoves());

		assertTrue(g1.move(move));
		assertEquals(new Cat(Owner.Player1), g1.getPieceAt(new Coordinate(6, 7)));
		assertFalse(g1.checkCoor(7, 7));
	}

	@Test
	public void testMoveIllegalNotYourTurn() {
		Coordinate start = new Coordinate(7, 7);
		Coordinate end = new Coordinate(7, 6);
		Owner owner = g.getOwner();
		MoveCommand move = new RegularMove(g.getBoardState(), start, end, owner, g.getNumMoves());

		assertFalse(g.move(move));
	}

	@Test
	public void testMoveIllegalDown() {
		g.setPlayerTurn(2);

		Coordinate start = new Coordinate(7, 7);
		Coordinate end = start.down();
		Owner owner = g.getOwner();
		MoveCommand move = new RegularMove(g.getBoardState(), start, end, owner, g.getNumMoves());

		assertFalse(g.move(move));
	}

	@Test
	public void testMoveIllegalUp() {
		Coordinate start = new Coordinate(0, 0);
		Coordinate end = start.up();
		Owner owner = g.getOwner();
		MoveCommand move = new RegularMove(g.getBoardState(), start, end, owner, g.getNumMoves());

		assertFalse(g.move(move));

	}

	@Test
	public void testMoveIllegalRight() {
		g.setPlayerTurn(2);
		Coordinate start = new Coordinate(7, 7);
		Coordinate end = start.right();
		Owner owner = g.getOwner();
		assertFalse(g.move(new RegularMove(g.getBoardState(), start, end, owner, g.getNumMoves())));
	}

	@Test
	public void testMoveIllegalLeft() {
		Coordinate start = new Coordinate(0, 0);
		Coordinate end = start.left();
		Owner owner = g.getOwner();
		assertFalse(g.move(new RegularMove(g.getBoardState(), start, end, owner, g.getNumMoves())));
	}

	@Test
	public void testCannotMoveUpIntoOccupiedSpace() {
		assertEquals(new Dog(Owner.Player1), g.getPieceAt(new Coordinate(1, 0)));
		Coordinate start = new Coordinate(1, 0);
		Coordinate end = start.up();
		Owner owner = g.getOwner();
		MoveCommand move = new RegularMove(g.getBoardState(), start, end, owner, g.getNumMoves());
		assertFalse(g.move(move));
		assertEquals(new Dog(Owner.Player1), g.getPieceAt(new Coordinate(1, 0)));
	}

	@Test
	public void testCannotMoveRightIntoOccupiedSpace() {
		assertEquals(new Rabbit(Owner.Player1), g.getPieceAt(new Coordinate(0, 1)));
		Coordinate start = new Coordinate(0, 1);
		Coordinate end = start.right();
		Owner owner = g.getOwner();
		MoveCommand move = new RegularMove(g.getBoardState(), start, end, owner, g.getNumMoves());
		assertFalse(g.move(move));
		assertEquals(new Rabbit(Owner.Player1), g.getPieceAt(new Coordinate(0, 1)));
	}

	@Test
	public void testCannotMoveDownIntoOccupiedSpace() {
		assertEquals(new Cat(Owner.Player1), g.getPieceAt(new Coordinate(0, 0)));
		Coordinate start = new Coordinate(0, 0);
		Coordinate end = start.down();
		Owner owner = g.getOwner();
		MoveCommand move = new RegularMove(g.getBoardState(), start, end, owner, g.getNumMoves());
		assertFalse(g.move(move));
		assertEquals(new Cat(Owner.Player1), g.getPieceAt(new Coordinate(0, 0)));
	}

	@Test
	public void testCannotMoveLeftIntoOccupiedSpace() {
		assertEquals(new Dog(Owner.Player1), g.getPieceAt(new Coordinate(1, 0)));
		Coordinate start = new Coordinate(0, 1);
		Coordinate end = start.left();
		Owner owner = g.getOwner();
		MoveCommand move = new RegularMove(g.getBoardState(), start, end, owner, g.getNumMoves());
		assertFalse(g.move(move));
		assertEquals(new Dog(Owner.Player1), g.getPieceAt(new Coordinate(1, 0)));
	}

	@Test
	public void testCannotMoveIfFrozenByStrongerOpposingPiece() {
		Coordinate start = new Coordinate(3, 4);
		Coordinate end = start.left();
		Owner owner = freezingGame.getOwner();
		MoveCommand move = new RegularMove(freezingGame.getBoardState(), start, end, owner, freezingGame.getNumMoves());
		assertFalse(freezingGame.move(move));
	}

	@Test
	public void testCanMoveIfFrozenByStrongerOpposingPieceButThawedByFriendlyPiece() {
		Coordinate start = new Coordinate(4, 3);
		Coordinate end = start.left();
		Owner owner = freezingGame.getOwner();
		MoveCommand move = new RegularMove(freezingGame.getBoardState(), start, end, owner, freezingGame.getNumMoves());
		assertTrue(freezingGame.move(move));
	}

	@Test
	public void testNullMove() {
		Coordinate start = new Coordinate(3, 4);
		Coordinate end = start.up();
		Owner owner = freezingGame.getOwner();
		MoveCommand move = new RegularMove(freezingGame.getBoardState(), start, end, owner, freezingGame.getNumMoves());
		assertFalse(g.move(move));
	}
}
