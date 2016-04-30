package game;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

import move_commands.MoveCommand;
import move_commands.RegularMove;
import piece.AbstractPiece;
import piece.Camel;
import piece.Cat;
import piece.Dog;
import piece.Elephant;
import piece.Owner;
import piece.Rabbit;

public class TestGame {
	private Game g;
	private Game g1;

	@Before
	public void setup() {
		g = new Game();

		HashMap<Coordinate, AbstractPiece> p1 = new HashMap<Coordinate, AbstractPiece>();
		p1.put(new Coordinate(6, 7), new Cat(Owner.Player1));
		p1.put(new Coordinate(7, 7), new Rabbit(Owner.Player1));
		p1.put(new Coordinate(3, 6), new Dog(Owner.Player1));
		p1.put(new Coordinate(3, 4), new Camel(Owner.Player1));
		p1.put(new Coordinate(4, 4), new Elephant(Owner.Player1));
		BoardState b1 = new BoardState(p1);
		g1 = new Game(b1);
	}

	@Test
	public void testInitializes() {
		Game g = new Game(null);
		assertNotNull(g);
	}

	@Test
	public void testInitializesWithBoardState() {
		assertEquals(new Camel(Owner.Player1), g1.getPieceAt(new Coordinate(3, 4)));
		assertEquals(new Elephant(Owner.Player1), g1.getPieceAt(new Coordinate(4, 4)));
		assertNull(g1.getPieceAt(new Coordinate(3, 7)));
		assertEquals(new Rabbit(Owner.Player1), g1.getPieceAt(new Coordinate(7, 7)));
	}

	@Test
	public void testTurnNumberIncrements() {
		g.incrementTurn();
		assertEquals(1, g.getTurnNumber());
		g.incrementTurn();
		g.incrementTurn();
		assertEquals(3, g.getTurnNumber());
	}

	@Test
	public void testSetTurnNumber() {
		g.setTurnNumber(5);
		assertEquals(5, g.getTurnNumber());
	}

	@Test
	public void testGetPieceExists() {
		assertTrue(g1.checkCoor(3, 4));
		assertEquals(new Camel(Owner.Player1), g1.getPieceAt(new Coordinate(3, 4)));
	}

	@Test
	public void testGetPieceNotExists() {
		assertFalse(g1.checkCoor(0, 0));
		assertNull(g1.getPieceAt(new Coordinate(0, 0)));
	}

	// Testing remove piece checks
	@Test
	public void testRemovePieceValid() {
		HashMap<Coordinate, AbstractPiece> removeP = new HashMap<Coordinate, AbstractPiece>();
		removeP.put(new Coordinate(5, 7), new Rabbit(Owner.Player2));
		removeP.put(new Coordinate(6, 7), new Cat(Owner.Player1));
		removeP.put(new Coordinate(7, 7), new Rabbit(Owner.Player1));
		removeP.put(new Coordinate(3, 6), new Dog(Owner.Player1));
		removeP.put(new Coordinate(4, 5), new Rabbit(Owner.Player2));
		removeP.put(new Coordinate(3, 4), new Camel(Owner.Player1));
		removeP.put(new Coordinate(4, 4), new Elephant(Owner.Player1));
		removeP.put(new Coordinate(5, 4), new Dog(Owner.Player2));
		removeP.put(new Coordinate(4, 3), new Cat(Owner.Player2));
		removeP.put(new Coordinate(2, 2), new Camel(Owner.Player1));

		Game game = new Game(new BoardState(removeP));
		// g.currentBoard.printBoard();
		Coordinate start = new Coordinate(3, 6);
		Coordinate end = start.up();
		Owner owner = game.getOwner();
		MoveCommand move = new RegularMove(game.getBoardState(), start, end, owner);
		assertTrue(game.move(move));
		// g.currentBoard.printBoard();
		assertFalse(game.checkCoor(2, 2));
	}

	@Test
	public void testRemovePiece2() {
		HashMap<Coordinate, AbstractPiece> removeP = new HashMap<Coordinate, AbstractPiece>();
		removeP.put(new Coordinate(5, 7), new Rabbit(Owner.Player2));
		removeP.put(new Coordinate(6, 7), new Cat(Owner.Player1));
		removeP.put(new Coordinate(7, 7), new Rabbit(Owner.Player1));
		removeP.put(new Coordinate(3, 6), new Dog(Owner.Player1));
		removeP.put(new Coordinate(4, 5), new Rabbit(Owner.Player2));
		removeP.put(new Coordinate(3, 4), new Camel(Owner.Player1));
		removeP.put(new Coordinate(4, 4), new Elephant(Owner.Player1));
		removeP.put(new Coordinate(5, 4), new Dog(Owner.Player2));
		removeP.put(new Coordinate(4, 3), new Cat(Owner.Player2));
		removeP.put(new Coordinate(2, 2), new Camel(Owner.Player1));
		removeP.put(new Coordinate(5, 2), new Camel(Owner.Player1));
		removeP.put(new Coordinate(5, 1), new Dog(Owner.Player1));
		Game game = new Game(new BoardState(removeP));
		Coordinate start = new Coordinate(2, 2);
		Coordinate end = start.up();
		Owner owner = game.getOwner();
		MoveCommand move = new RegularMove(game.getBoardState(), start, end, owner);
		game.move(move);
		assertEquals(game.getPieceAt(new Coordinate(2, 5)), game.getPieceAt(new Coordinate(2, 5)));
		start = new Coordinate(5, 1);
		end = start.up();
		owner = game.getOwner();
		move = new RegularMove(game.getBoardState(), start, end, owner);
		game.move(move);
		assertFalse(game.checkCoor(5, 1));
	}
}
