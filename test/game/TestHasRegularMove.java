package game;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;

import org.junit.Test;

import ai.Ai;
import board.BoardState;
import board.Coordinate;
import move_commands.RegularMove;
import piece.AbstractPiece;
import piece.Dog;
import piece.Elephant;
import piece.Owner;
import piece.Rabbit;

public class TestHasRegularMove {
	public static final int ITERATION_SIZE = 1000;

	private void exhaustivelyCheckNoRegularMoves(Game game, Coordinate coor) {
		Owner owner = game.getPieceAt(coor).getOwner();
		assertFalse(game.hasRegularMove(coor));
		Ai ai = new Ai(owner, game);
		for (int i = 0; i < ITERATION_SIZE; i++) {
			try {
				assertFalse(ai.generateMove() instanceof RegularMove);
			} catch (StackOverflowError e) {
				// no moves
				break;
			}
		}
	}

	@Test
	public void testInvalidCoordinateCantMakeRegularMove() {
		assertFalse(new Game().hasRegularMove(new Coordinate(-1, 0)));
	}

	@Test
	public void testRabbitHasNoRegularMovesIfBlocked1() {
		HashMap<Coordinate, AbstractPiece> p = new HashMap<Coordinate, AbstractPiece>();
		p.put(new Coordinate(4, 4), new Rabbit(Owner.Player1));

		p.put(new Coordinate(3, 4), new Rabbit(Owner.Player2));
		p.put(new Coordinate(4, 5), new Rabbit(Owner.Player2));
		p.put(new Coordinate(5, 4), new Rabbit(Owner.Player2));
		Game game = new Game(new BoardState(p));

		exhaustivelyCheckNoRegularMoves(game, new Coordinate(4, 4));
		assertTrue(game.hasRegularMove(new Coordinate(3, 4)));
		assertTrue(game.hasRegularMove(new Coordinate(4, 5)));
		assertTrue(game.hasRegularMove(new Coordinate(5, 4)));
	}

	@Test
	public void testRabbitHasNoRegularMovesIfBlocked2() {
		HashMap<Coordinate, AbstractPiece> p = new HashMap<Coordinate, AbstractPiece>();
		p.put(new Coordinate(4, 4), new Rabbit(Owner.Player2));

		p.put(new Coordinate(4, 3), new Rabbit(Owner.Player1));
		p.put(new Coordinate(3, 4), new Rabbit(Owner.Player1));
		p.put(new Coordinate(5, 4), new Rabbit(Owner.Player1));
		Game game = new Game(new BoardState(p));

		exhaustivelyCheckNoRegularMoves(game, new Coordinate(4, 4));
		assertTrue(game.hasRegularMove(new Coordinate(4, 3)));
		assertTrue(game.hasRegularMove(new Coordinate(3, 4)));
		assertTrue(game.hasRegularMove(new Coordinate(5, 4)));
	}

	@Test
	public void testRabbitHasNoRegularMovesIfBlockedAtEdge1() {
		HashMap<Coordinate, AbstractPiece> p = new HashMap<Coordinate, AbstractPiece>();
		p.put(new Coordinate(0, 4), new Rabbit(Owner.Player1));

		p.put(new Coordinate(0, 5), new Rabbit(Owner.Player2));
		p.put(new Coordinate(1, 4), new Rabbit(Owner.Player2));
		Game game = new Game(new BoardState(p));

		exhaustivelyCheckNoRegularMoves(game, new Coordinate(0, 4));
		assertTrue(game.hasRegularMove(new Coordinate(0, 5)));
		assertTrue(game.hasRegularMove(new Coordinate(1, 4)));
	}

	@Test
	public void testRabbitHasNoRegularMovesIfBlockedAtEdge2() {
		HashMap<Coordinate, AbstractPiece> p = new HashMap<Coordinate, AbstractPiece>();
		p.put(new Coordinate(7, 4), new Rabbit(Owner.Player2));

		p.put(new Coordinate(7, 3), new Rabbit(Owner.Player1));
		p.put(new Coordinate(6, 4), new Rabbit(Owner.Player1));
		Game game = new Game(new BoardState(p));

		exhaustivelyCheckNoRegularMoves(game, new Coordinate(7, 4));
		assertTrue(game.hasRegularMove(new Coordinate(7, 3)));
		assertTrue(game.hasRegularMove(new Coordinate(6, 4)));
	}

	@Test
	public void testNonRabbitHasNoRegularMovesIfBlocked1() {
		HashMap<Coordinate, AbstractPiece> p = new HashMap<Coordinate, AbstractPiece>();
		p.put(new Coordinate(4, 4), new Dog(Owner.Player1));

		p.put(new Coordinate(4, 3), new Rabbit(Owner.Player2));
		p.put(new Coordinate(5, 4), new Rabbit(Owner.Player2));
		p.put(new Coordinate(4, 5), new Rabbit(Owner.Player2));
		p.put(new Coordinate(3, 4), new Rabbit(Owner.Player2));
		Game game = new Game(new BoardState(p));

		exhaustivelyCheckNoRegularMoves(game, new Coordinate(4, 4));
		assertTrue(game.hasRegularMove(new Coordinate(4, 3)));
		assertTrue(game.hasRegularMove(new Coordinate(5, 4)));
		assertTrue(game.hasRegularMove(new Coordinate(4, 5)));
		assertTrue(game.hasRegularMove(new Coordinate(3, 4)));
	}

	@Test
	public void testNonRabbitHasNoRegularMovesIfBlocked2() {
		HashMap<Coordinate, AbstractPiece> p = new HashMap<Coordinate, AbstractPiece>();
		p.put(new Coordinate(4, 4), new Elephant(Owner.Player2));

		p.put(new Coordinate(4, 3), new Rabbit(Owner.Player1));
		p.put(new Coordinate(5, 4), new Rabbit(Owner.Player1));
		p.put(new Coordinate(4, 5), new Rabbit(Owner.Player1));
		p.put(new Coordinate(3, 4), new Rabbit(Owner.Player1));
		Game game = new Game(new BoardState(p));

		exhaustivelyCheckNoRegularMoves(game, new Coordinate(4, 4));
		assertTrue(game.hasRegularMove(new Coordinate(4, 3)));
		assertTrue(game.hasRegularMove(new Coordinate(5, 4)));
		assertTrue(game.hasRegularMove(new Coordinate(4, 5)));
		assertTrue(game.hasRegularMove(new Coordinate(3, 4)));
	}
}
