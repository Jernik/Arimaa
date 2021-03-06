package game;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.HashMap;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import ai.Ai;
import board.BoardState;
import board.Coordinate;
import move_commands.RegularMove;
import piece.AbstractPiece;
import piece.Camel;
import piece.Dog;
import piece.Owner;
import piece.Rabbit;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestHasNoMoves {
	public static final int ITERATION_SIZE = 1000;
	private Game freezingGame;

	private void exhaustivelyCheckHasNoMoves(Game game, Coordinate coor) {
		Owner owner = game.getPieceAt(coor).getOwner();
		assertFalse(game.hasMove(coor));
		Ai ai = new Ai(owner, game);
		for (int i = 0; i < ITERATION_SIZE; i++) {
			try {
				assertNotEquals(coor, ai.generateMove().getOriginalPosition());
			} catch (StackOverflowError e) {
				// no moves
				break;
			}
		}
	}

	private void exhaustivelyCheckHasMoves(Game game, Coordinate coor) {
		Owner owner = game.getPieceAt(coor).getOwner();
		assertTrue(game.hasMove(coor));
		Ai ai = new Ai(owner, game);
		for (int i = 0; i < ITERATION_SIZE; i++) {
			try {
				if (ai.generateMove().getOriginalPosition().equals(coor)) {
					// found a for this coor
					break;
				}
			} catch (StackOverflowError e) {
				// no moves
				fail("couldn't find a move");
			}
		}
	}

	@Before
	public void setup() {
		HashMap<Coordinate, AbstractPiece> fp = new HashMap<Coordinate, AbstractPiece>();
		fp.put(new Coordinate(0, 0), new Rabbit(Owner.Player1));

		fp.put(new Coordinate(1, 1), new Rabbit(Owner.Player1));
		fp.put(new Coordinate(2, 1), new Rabbit(Owner.Player2));

		fp.put(new Coordinate(3, 3), new Rabbit(Owner.Player1));
		fp.put(new Coordinate(2, 3), new Dog(Owner.Player2));

		fp.put(new Coordinate(5, 5), new Rabbit(Owner.Player1));
		fp.put(new Coordinate(6, 5), new Rabbit(Owner.Player1));
		fp.put(new Coordinate(4, 5), new Dog(Owner.Player2));
		fp.put(new Coordinate(3, 5), new Camel(Owner.Player1));

		freezingGame = new Game(new BoardState(fp));
	}

	@Test
	public void testHasNoMovesReturnsFalseIfNotFrozen1() {
		exhaustivelyCheckHasMoves(freezingGame, new Coordinate(0, 0));
	}

	@Test
	public void testHasNoMovesReturnsFalseIfNotFrozen2() {
		exhaustivelyCheckHasMoves(freezingGame, new Coordinate(1, 1));
		exhaustivelyCheckHasMoves(freezingGame, new Coordinate(2, 1));
	}

	@Test
	public void testHasNoMovesReturnsFalseIfNotFrozen3() {
		exhaustivelyCheckHasMoves(freezingGame, new Coordinate(2, 3));
	}

	@Test
	public void testHasNoMovesReturnsFalseIfNotFrozen4() {
		exhaustivelyCheckHasMoves(freezingGame, new Coordinate(3, 5));
	}

	@Test
	public void testHasNoMovesReturnsFalseIfFrozenButThawed() {
		exhaustivelyCheckHasMoves(freezingGame, new Coordinate(5, 5));
	}

	@Test
	public void testHasNoMovesReturnsTrueIfFrozen1() {
		exhaustivelyCheckHasNoMoves(freezingGame, new Coordinate(3, 3));
	}

	@Test
	public void testHasNoMovesReturnsTrueIfFrozen2() {
		exhaustivelyCheckHasNoMoves(freezingGame, new Coordinate(4, 5));
	}

	@Test
	public void testHasNoMovesReturnsFalseIfOnlyPushAvaliable() {
		HashMap<Coordinate, AbstractPiece> p = new HashMap<Coordinate, AbstractPiece>();
		p.put(new Coordinate(4, 4), new Dog(Owner.Player1));

		p.put(new Coordinate(4, 3), new Rabbit(Owner.Player1));
		p.put(new Coordinate(4, 2), new Rabbit(Owner.Player2));
		p.put(new Coordinate(5, 3), new Camel(Owner.Player2));
		p.put(new Coordinate(3, 3), new Rabbit(Owner.Player2));

		p.put(new Coordinate(5, 4), new Rabbit(Owner.Player2));
		p.put(new Coordinate(6, 4), new Rabbit(Owner.Player2));

		p.put(new Coordinate(4, 5), new Rabbit(Owner.Player2));
		p.put(new Coordinate(5, 5), new Rabbit(Owner.Player2));
		p.put(new Coordinate(4, 6), new Rabbit(Owner.Player2));
		p.put(new Coordinate(3, 5), new Rabbit(Owner.Player2));

		p.put(new Coordinate(3, 4), new Rabbit(Owner.Player2));

		Game game = new Game(new BoardState(p));

		exhaustivelyCheckHasMoves(game, new Coordinate(4, 4));
		assertFalse(game.hasNoMoves(Owner.Player1));
	}

	@Test
	public void testHasNoMovesReturnsTrueIfOnlyPushAvaliableButWeaker() {
		HashMap<Coordinate, AbstractPiece> p = new HashMap<Coordinate, AbstractPiece>();
		p.put(new Coordinate(4, 4), new Dog(Owner.Player1));

		p.put(new Coordinate(4, 3), new Rabbit(Owner.Player1));
		p.put(new Coordinate(4, 2), new Rabbit(Owner.Player2));
		p.put(new Coordinate(5, 3), new Camel(Owner.Player2));
		p.put(new Coordinate(3, 3), new Rabbit(Owner.Player2));

		p.put(new Coordinate(5, 4), new Rabbit(Owner.Player2));
		p.put(new Coordinate(6, 4), new Rabbit(Owner.Player2));

		p.put(new Coordinate(4, 5), new Rabbit(Owner.Player2));
		p.put(new Coordinate(5, 5), new Rabbit(Owner.Player2));
		p.put(new Coordinate(4, 6), new Rabbit(Owner.Player2));
		p.put(new Coordinate(3, 5), new Rabbit(Owner.Player2));

		p.put(new Coordinate(3, 4), new Camel(Owner.Player2));

		Game game = new Game(new BoardState(p));

		exhaustivelyCheckHasNoMoves(game, new Coordinate(4, 4));
		exhaustivelyCheckHasNoMoves(game, new Coordinate(4, 3));
		assertTrue(game.hasNoMoves(Owner.Player1));
	}

	@Test
	public void testHasNoMovesReturnsTrueIfOnlyPushAvaliableButEqualStrength() {
		HashMap<Coordinate, AbstractPiece> p = new HashMap<Coordinate, AbstractPiece>();
		p.put(new Coordinate(4, 4), new Dog(Owner.Player1));

		p.put(new Coordinate(4, 3), new Rabbit(Owner.Player1));
		p.put(new Coordinate(4, 2), new Rabbit(Owner.Player2));
		p.put(new Coordinate(5, 3), new Camel(Owner.Player2));
		p.put(new Coordinate(3, 3), new Rabbit(Owner.Player2));

		p.put(new Coordinate(5, 4), new Rabbit(Owner.Player2));
		p.put(new Coordinate(6, 4), new Rabbit(Owner.Player2));

		p.put(new Coordinate(4, 5), new Rabbit(Owner.Player2));
		p.put(new Coordinate(5, 5), new Rabbit(Owner.Player2));
		p.put(new Coordinate(4, 6), new Rabbit(Owner.Player2));
		p.put(new Coordinate(3, 5), new Rabbit(Owner.Player2));

		p.put(new Coordinate(3, 4), new Dog(Owner.Player2));

		Game game = new Game(new BoardState(p));

		exhaustivelyCheckHasNoMoves(game, new Coordinate(4, 4));
		exhaustivelyCheckHasNoMoves(game, new Coordinate(4, 3));
		assertTrue(game.hasNoMoves(Owner.Player1));
	}

	@Test
	public void testHasNoMovesReturnsTrueIfOnlyPushAvaliableAndNotEnoughMovesLeft() {
		HashMap<Coordinate, AbstractPiece> p = new HashMap<Coordinate, AbstractPiece>();
		p.put(new Coordinate(4, 4), new Dog(Owner.Player1));
		p.put(new Coordinate(4, 0), new Dog(Owner.Player1));

		p.put(new Coordinate(4, 3), new Rabbit(Owner.Player2));
		p.put(new Coordinate(4, 2), new Rabbit(Owner.Player2));
		p.put(new Coordinate(5, 3), new Camel(Owner.Player2));
		p.put(new Coordinate(3, 3), new Rabbit(Owner.Player2));

		p.put(new Coordinate(5, 4), new Rabbit(Owner.Player2));
		p.put(new Coordinate(6, 4), new Rabbit(Owner.Player2));

		p.put(new Coordinate(4, 5), new Rabbit(Owner.Player2));
		p.put(new Coordinate(5, 5), new Rabbit(Owner.Player2));
		p.put(new Coordinate(4, 6), new Rabbit(Owner.Player2));
		p.put(new Coordinate(3, 5), new Rabbit(Owner.Player2));

		p.put(new Coordinate(3, 4), new Rabbit(Owner.Player2));

		Game game = new Game(new BoardState(p));
		game.move(new RegularMove(game, new Coordinate(4, 0), new Coordinate(5, 0), game.getPlayerTurn()));
		game.move(new RegularMove(game, new Coordinate(5, 0), new Coordinate(5, 1), game.getPlayerTurn()));
		game.move(new RegularMove(game, new Coordinate(5, 1), new Coordinate(5, 2), game.getPlayerTurn()));

		exhaustivelyCheckHasNoMoves(game, new Coordinate(4, 4));
		assertTrue(game.hasNoMoves(Owner.Player1));
	}
}
