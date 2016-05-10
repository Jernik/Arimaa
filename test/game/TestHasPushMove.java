package game;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.HashMap;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import ai.Ai;
import board.BoardState;
import board.Coordinate;
import move_commands.PushMove;
import piece.AbstractPiece;
import piece.Camel;
import piece.Cat;
import piece.Dog;
import piece.Owner;
import piece.Rabbit;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestHasPushMove {
	public static final int ITERATION_SIZE = 1000;

	private void exhaustivelyCheckNoPushMoves(Game game, Coordinate coor) {
		Owner owner = game.getPieceAt(coor).getOwner();
		assertFalse(game.hasPushMove(coor));
		Ai ai = new Ai(owner, game);
		for (int i = 0; i < ITERATION_SIZE; i++) {
			try {
				assertFalse(ai.generateMove() instanceof PushMove);
			} catch (StackOverflowError e) {
				// no moves
				break;
			}
		}
	}

	private void exhaustivelyCheckPushMoves(Game game, Coordinate coor) {
		Owner owner = game.getPieceAt(coor).getOwner();
		assertTrue(game.hasPushMove(coor));
		Ai ai = new Ai(owner, game);
		for (int i = 0; i < ITERATION_SIZE; i++) {
			try {
				if (ai.generateMove() instanceof PushMove) {
					// found a push
					break;
				}
			} catch (StackOverflowError e) {
				// no moves
				fail("couldn't find a push move");
			}
		}
	}

	@Test
	public void testHasPushMoveReturnsTrueIfPossiblePush() {
		HashMap<Coordinate, AbstractPiece> p = new HashMap<Coordinate, AbstractPiece>();
		p.put(new Coordinate(4, 4), new Cat(Owner.Player1));
		p.put(new Coordinate(4, 3), new Rabbit(Owner.Player2));

		Game game = new Game(new BoardState(p));

		exhaustivelyCheckPushMoves(game, new Coordinate(0, 4));
	}

	@Test
	public void testHasPushMoveReturnsTrueIfPossiblePushNearEdge1() {
		HashMap<Coordinate, AbstractPiece> p = new HashMap<Coordinate, AbstractPiece>();
		p.put(new Coordinate(0, 4), new Cat(Owner.Player1));
		p.put(new Coordinate(0, 3), new Rabbit(Owner.Player2));

		Game game = new Game(new BoardState(p));

		exhaustivelyCheckPushMoves(game, new Coordinate(0, 4));
	}

	@Test
	public void testHasPushMoveReturnsTrueIfPossiblePushNearEdge2() {
		HashMap<Coordinate, AbstractPiece> p = new HashMap<Coordinate, AbstractPiece>();
		p.put(new Coordinate(7, 1), new Cat(Owner.Player2));
		p.put(new Coordinate(7, 0), new Rabbit(Owner.Player1));

		Game game = new Game(new BoardState(p));

		exhaustivelyCheckPushMoves(game, new Coordinate(7, 1));
	}

	@Test
	public void testHasPushMoveReturnsFalseForInvalidCoordinate() {
		assertFalse(new Game().hasPushMove(new Coordinate(-1, 0)));
	}

	@Test
	public void testHasPushMoveReturnsFalseForLonePiece() {
		HashMap<Coordinate, AbstractPiece> p = new HashMap<Coordinate, AbstractPiece>();
		p.put(new Coordinate(4, 4), new Cat(Owner.Player1));

		Game game = new Game(new BoardState(p));

		exhaustivelyCheckNoPushMoves(game, new Coordinate(4, 4));
	}

	@Test
	public void testHasPushMoveReturnsFalseForRabbit() {
		HashMap<Coordinate, AbstractPiece> p = new HashMap<Coordinate, AbstractPiece>();
		p.put(new Coordinate(4, 4), new Rabbit(Owner.Player1));

		p.put(new Coordinate(4, 3), new Rabbit(Owner.Player2));

		Game game = new Game(new BoardState(p));
		exhaustivelyCheckNoPushMoves(game, new Coordinate(4, 4));
		exhaustivelyCheckNoPushMoves(game, new Coordinate(4, 3));
	}

	@Test
	public void testHasPushMoveReturnsFalseIfWeaker() {
		HashMap<Coordinate, AbstractPiece> p = new HashMap<Coordinate, AbstractPiece>();
		p.put(new Coordinate(4, 4), new Dog(Owner.Player1));

		p.put(new Coordinate(4, 3), new Camel(Owner.Player2));

		Game game = new Game(new BoardState(p));
		exhaustivelyCheckNoPushMoves(game, new Coordinate(4, 4));
	}

	@Test
	public void testHasPushMoveReturnsFalseIfEqualStrength() {
		HashMap<Coordinate, AbstractPiece> p = new HashMap<Coordinate, AbstractPiece>();
		p.put(new Coordinate(4, 4), new Dog(Owner.Player1));

		p.put(new Coordinate(4, 3), new Dog(Owner.Player2));

		Game game = new Game(new BoardState(p));
		exhaustivelyCheckNoPushMoves(game, new Coordinate(4, 4));
	}

	@Test
	public void testHasPushMoveReturnsFalseIfSuroundedByOwnPieces() {
		HashMap<Coordinate, AbstractPiece> p = new HashMap<Coordinate, AbstractPiece>();
		p.put(new Coordinate(4, 4), new Dog(Owner.Player1));

		p.put(new Coordinate(4, 3), new Rabbit(Owner.Player1));
		p.put(new Coordinate(5, 4), new Rabbit(Owner.Player1));
		p.put(new Coordinate(4, 5), new Rabbit(Owner.Player1));
		p.put(new Coordinate(3, 4), new Rabbit(Owner.Player1));

		Game game = new Game(new BoardState(p));
		exhaustivelyCheckNoPushMoves(game, new Coordinate(4, 4));
	}

	@Test
	public void testHasPushMoveReturnsFalseIfEnemyPieceAreSurounded() {
		HashMap<Coordinate, AbstractPiece> p = new HashMap<Coordinate, AbstractPiece>();
		p.put(new Coordinate(4, 4), new Dog(Owner.Player1));

		p.put(new Coordinate(4, 3), new Rabbit(Owner.Player2));
		p.put(new Coordinate(4, 2), new Rabbit(Owner.Player2));
		p.put(new Coordinate(5, 3), new Rabbit(Owner.Player2));
		p.put(new Coordinate(3, 3), new Rabbit(Owner.Player2));

		p.put(new Coordinate(5, 4), new Rabbit(Owner.Player2));
		p.put(new Coordinate(6, 4), new Rabbit(Owner.Player2));

		p.put(new Coordinate(4, 5), new Rabbit(Owner.Player2));
		p.put(new Coordinate(5, 5), new Rabbit(Owner.Player2));
		p.put(new Coordinate(4, 6), new Rabbit(Owner.Player2));
		p.put(new Coordinate(3, 5), new Rabbit(Owner.Player2));

		p.put(new Coordinate(3, 4), new Rabbit(Owner.Player2));
		p.put(new Coordinate(2, 4), new Rabbit(Owner.Player2));

		Game game = new Game(new BoardState(p));
		exhaustivelyCheckNoPushMoves(game, new Coordinate(4, 4));
	}

	@Test
	public void testHasPushMoveReturnsTrueIfEnemyPieceAreAlmostSurounded() {
		HashMap<Coordinate, AbstractPiece> p = new HashMap<Coordinate, AbstractPiece>();
		p.put(new Coordinate(4, 4), new Dog(Owner.Player1));

		p.put(new Coordinate(4, 3), new Rabbit(Owner.Player2));
		p.put(new Coordinate(4, 2), new Rabbit(Owner.Player2));
		p.put(new Coordinate(5, 3), new Rabbit(Owner.Player2));
		p.put(new Coordinate(3, 3), new Rabbit(Owner.Player2));

		p.put(new Coordinate(5, 4), new Rabbit(Owner.Player2));
		p.put(new Coordinate(6, 4), new Rabbit(Owner.Player2));

		p.put(new Coordinate(4, 5), new Rabbit(Owner.Player2));
		p.put(new Coordinate(5, 5), new Rabbit(Owner.Player2));
		p.put(new Coordinate(4, 6), new Rabbit(Owner.Player2));
		p.put(new Coordinate(3, 5), new Rabbit(Owner.Player2));

		p.put(new Coordinate(3, 4), new Rabbit(Owner.Player2));

		Game game = new Game(new BoardState(p));
		exhaustivelyCheckPushMoves(game, new Coordinate(4, 4));
	}

}
