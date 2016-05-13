package move_commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Test;

import board.BoardState;
import board.Coordinate;
import game.Game;
import piece.AbstractPiece;
import piece.Camel;
import piece.Cat;
import piece.Dog;
import piece.Elephant;
import piece.Owner;
import piece.Rabbit;

public class TestRegularValid extends RegularSetup {
	@Test
	public void testCanGetAffectedCoordinates() {
		Coordinate c1 = new Coordinate(1, 1);
		Coordinate c2 = new Coordinate(1, 2);

		ArrayList<CoordinatePair> map = new ArrayList<CoordinatePair>();
		map.add(new CoordinatePair(c1, c2));

		MoveCommand move = new RegularMove(g, c1, c2, g.getPlayerTurn());

		assertEquals(map, move.getAffectedCoordinates());
	}

	@Test
	public void testGetAffectedCoordinatesIsEmptyIfInvalid() {
		MoveCommand move = new RegularMove(g, new Coordinate(-1, 6), new Coordinate(1, 5), g.getPlayerTurn());

		assertTrue(move.getAffectedCoordinates().isEmpty());
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
		Owner owner = g1.getPlayerTurn();
		MoveCommand move = new RegularMove(g1, start, end, owner);

		assertTrue(g1.move(move));
		assertEquals(new Cat(Owner.Player1), g1.getPieceAt(new Coordinate(6, 7)));
		assertFalse(g1.isPieceAt(new Coordinate(7, 7)));
	}

	@Test
	public void testRabbitCanMoveForwards1() {
		MoveCommand move = new RegularMove(g, new Coordinate(0, 1), new Coordinate(0, 2), g.getPlayerTurn());

		assertTrue(move.isValidMove());
		assertTrue(g.move(move));

		assertEquals(new Rabbit(Owner.Player1), g.getPieceAt(new Coordinate(0, 2)));
		assertFalse(g.isPieceAt(new Coordinate(0, 1)));
	}

	@Test
	public void testRabbitCanMoveForwards2() {
		g.incrementTurn();
		MoveCommand move = new RegularMove(g, new Coordinate(0, 6), new Coordinate(0, 5), g.getPlayerTurn());

		assertTrue(move.isValidMove());
		assertTrue(g.move(move));

		assertEquals(new Rabbit(Owner.Player2), g.getPieceAt(new Coordinate(0, 5)));
		assertFalse(g.isPieceAt(new Coordinate(0, 6)));
	}

	@Test
	public void testCanMoveIfFrozenByStrongerOpposingPieceButThawedByFriendlyPiece() {
		MoveCommand move = new RegularMove(freezingGame, new Coordinate(4, 3), new Coordinate(3, 3),
				freezingGame.getPlayerTurn());

		assertTrue(move.isValidMove());
		assertTrue(freezingGame.move(move));

		assertEquals(new Rabbit(Owner.Player1), freezingGame.getPieceAt(new Coordinate(3, 3)));
		assertFalse(freezingGame.isPieceAt(new Coordinate(4, 3)));
	}
}
