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
import move_commands.RegularMove;
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
		if (owner != game.getPlayerTurn()) {
			game.incrementTurn();
		}
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
		if (owner != game.getPlayerTurn()) {
			game.incrementTurn();
		}
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

		exhaustivelyCheckPushMoves(game, new Coordinate(4, 4));
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
	public void testHasPushMoveReturnsTrueIfThereAre3MovesLeft() {
		HashMap<Coordinate, AbstractPiece> p = new HashMap<Coordinate, AbstractPiece>();
		p.put(new Coordinate(5, 4), new Cat(Owner.Player1));
		p.put(new Coordinate(4, 3), new Rabbit(Owner.Player2));

		Game game = new Game(new BoardState(p));
		game.move(new RegularMove(game, new Coordinate(5, 4), new Coordinate(4, 4)));

		exhaustivelyCheckPushMoves(game, new Coordinate(4, 4));
	}

	@Test
	public void testHasPushMoveReturnsTrueIfThereAre2MovesLeft() {
		HashMap<Coordinate, AbstractPiece> p = new HashMap<Coordinate, AbstractPiece>();
		p.put(new Coordinate(4, 4), new Cat(Owner.Player1));
		p.put(new Coordinate(4, 3), new Rabbit(Owner.Player2));

		Game game = new Game(new BoardState(p));
		game.move(new RegularMove(game, new Coordinate(4, 4), new Coordinate(5, 4)));
		game.move(new RegularMove(game, new Coordinate(5, 4), new Coordinate(4, 4)));

		exhaustivelyCheckPushMoves(game, new Coordinate(4, 4));
	}

	@Test
	public void testHasPushMoveReturnsTrueIfThereIs1MoveLeft() {
		HashMap<Coordinate, AbstractPiece> p = new HashMap<Coordinate, AbstractPiece>();
		p.put(new Coordinate(5, 4), new Cat(Owner.Player1));
		p.put(new Coordinate(4, 3), new Rabbit(Owner.Player2));

		Game game = new Game(new BoardState(p));
		game.move(new RegularMove(game, new Coordinate(5, 4), new Coordinate(4, 4)));
		game.move(new RegularMove(game, new Coordinate(4, 4), new Coordinate(5, 4)));
		game.move(new RegularMove(game, new Coordinate(5, 4), new Coordinate(4, 4)));

		exhaustivelyCheckNoPushMoves(game, new Coordinate(4, 4));
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
	public void testHasPushMoveReturnsFalseForLonePieceByEdge1() {
		HashMap<Coordinate, AbstractPiece> p = new HashMap<Coordinate, AbstractPiece>();
		p.put(new Coordinate(0, 0), new Cat(Owner.Player1));

		Game game = new Game(new BoardState(p));

		exhaustivelyCheckNoPushMoves(game, new Coordinate(0, 0));
	}

	@Test
	public void testHasPushMoveReturnsFalseForLonePieceByEdge2() {
		HashMap<Coordinate, AbstractPiece> p = new HashMap<Coordinate, AbstractPiece>();
		p.put(new Coordinate(7, 7), new Cat(Owner.Player2));

		Game game = new Game(new BoardState(p));

		exhaustivelyCheckNoPushMoves(game, new Coordinate(7, 7));
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
	public void testHasPushMoveReturnsFalseForRabbitByEdge1() {
		HashMap<Coordinate, AbstractPiece> p = new HashMap<Coordinate, AbstractPiece>();
		p.put(new Coordinate(0, 6), new Rabbit(Owner.Player1));

		p.put(new Coordinate(0, 7), new Rabbit(Owner.Player2));

		Game game = new Game(new BoardState(p));
		exhaustivelyCheckNoPushMoves(game, new Coordinate(0, 6));
		exhaustivelyCheckNoPushMoves(game, new Coordinate(0, 7));
	}

	@Test
	public void testHasPushMoveReturnsFalseForRabbitByEdge2() {
		HashMap<Coordinate, AbstractPiece> p = new HashMap<Coordinate, AbstractPiece>();
		p.put(new Coordinate(6, 0), new Rabbit(Owner.Player2));

		p.put(new Coordinate(7, 0), new Rabbit(Owner.Player1));

		Game game = new Game(new BoardState(p));
		exhaustivelyCheckNoPushMoves(game, new Coordinate(6, 0));
		exhaustivelyCheckNoPushMoves(game, new Coordinate(7, 0));
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
	public void testHasPushMoveReturnsFalseIfWeakerByEdge1() {
		HashMap<Coordinate, AbstractPiece> p = new HashMap<Coordinate, AbstractPiece>();
		p.put(new Coordinate(0, 1), new Dog(Owner.Player1));

		p.put(new Coordinate(0, 0), new Camel(Owner.Player2));

		Game game = new Game(new BoardState(p));
		exhaustivelyCheckNoPushMoves(game, new Coordinate(0, 1));
	}

	@Test
	public void testHasPushMoveReturnsFalseIfWeakerByEdge2() {
		HashMap<Coordinate, AbstractPiece> p = new HashMap<Coordinate, AbstractPiece>();
		p.put(new Coordinate(6, 7), new Dog(Owner.Player2));

		p.put(new Coordinate(7, 7), new Camel(Owner.Player1));

		Game game = new Game(new BoardState(p));
		exhaustivelyCheckNoPushMoves(game, new Coordinate(6, 7));
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
	public void testHasPushMoveReturnsFalseIfEqualStrengthByEdge1() {
		HashMap<Coordinate, AbstractPiece> p = new HashMap<Coordinate, AbstractPiece>();
		p.put(new Coordinate(0, 1), new Dog(Owner.Player1));

		p.put(new Coordinate(0, 0), new Dog(Owner.Player2));

		Game game = new Game(new BoardState(p));
		exhaustivelyCheckNoPushMoves(game, new Coordinate(0, 1));
		exhaustivelyCheckNoPushMoves(game, new Coordinate(0, 0));
	}

	@Test
	public void testHasPushMoveReturnsFalseIfEqualStrengthByEdge2() {
		HashMap<Coordinate, AbstractPiece> p = new HashMap<Coordinate, AbstractPiece>();
		p.put(new Coordinate(6, 7), new Dog(Owner.Player1));

		p.put(new Coordinate(7, 7), new Dog(Owner.Player2));

		Game game = new Game(new BoardState(p));
		exhaustivelyCheckNoPushMoves(game, new Coordinate(6, 7));
		exhaustivelyCheckNoPushMoves(game, new Coordinate(7, 7));
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
	public void testHasPushMoveReturnsFalseIfSuroundedByOwnPiecesByEdge1() {
		HashMap<Coordinate, AbstractPiece> p = new HashMap<Coordinate, AbstractPiece>();
		p.put(new Coordinate(0, 0), new Dog(Owner.Player1));

		p.put(new Coordinate(0, 1), new Rabbit(Owner.Player1));
		p.put(new Coordinate(1, 0), new Rabbit(Owner.Player1));

		Game game = new Game(new BoardState(p));
		exhaustivelyCheckNoPushMoves(game, new Coordinate(0, 0));
	}

	@Test
	public void testHasPushMoveReturnsFalseIfSuroundedByOwnPiecesByEdge2() {
		HashMap<Coordinate, AbstractPiece> p = new HashMap<Coordinate, AbstractPiece>();
		p.put(new Coordinate(7, 7), new Dog(Owner.Player2));

		p.put(new Coordinate(7, 6), new Rabbit(Owner.Player2));
		p.put(new Coordinate(6, 7), new Rabbit(Owner.Player2));

		Game game = new Game(new BoardState(p));
		exhaustivelyCheckNoPushMoves(game, new Coordinate(7, 7));
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
	public void testHasPushMoveReturnsFalseIfEnemyPieceAreSuroundedByEdge1() {
		HashMap<Coordinate, AbstractPiece> p = new HashMap<Coordinate, AbstractPiece>();
		p.put(new Coordinate(0, 0), new Dog(Owner.Player1));

		p.put(new Coordinate(1, 0), new Rabbit(Owner.Player2));
		p.put(new Coordinate(2, 0), new Rabbit(Owner.Player2));
		p.put(new Coordinate(1, 1), new Rabbit(Owner.Player2));

		p.put(new Coordinate(0, 1), new Rabbit(Owner.Player2));
		p.put(new Coordinate(0, 2), new Rabbit(Owner.Player2));

		Game game = new Game(new BoardState(p));
		exhaustivelyCheckNoPushMoves(game, new Coordinate(0, 0));
	}

	@Test
	public void testHasPushMoveReturnsFalseIfEnemyPieceAreSuroundedByEdge2() {
		HashMap<Coordinate, AbstractPiece> p = new HashMap<Coordinate, AbstractPiece>();
		p.put(new Coordinate(7, 7), new Dog(Owner.Player2));

		p.put(new Coordinate(6, 7), new Rabbit(Owner.Player1));
		p.put(new Coordinate(5, 7), new Rabbit(Owner.Player1));
		p.put(new Coordinate(6, 6), new Rabbit(Owner.Player1));

		p.put(new Coordinate(7, 6), new Rabbit(Owner.Player1));
		p.put(new Coordinate(7, 5), new Rabbit(Owner.Player1));

		Game game = new Game(new BoardState(p));
		exhaustivelyCheckNoPushMoves(game, new Coordinate(7, 7));
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

	@Test
	public void testHasPushMoveReturnsTrueIfEnemyPieceAreAlmostSuroundedByEdge1() {
		HashMap<Coordinate, AbstractPiece> p = new HashMap<Coordinate, AbstractPiece>();
		p.put(new Coordinate(0, 0), new Dog(Owner.Player1));

		p.put(new Coordinate(1, 0), new Rabbit(Owner.Player2));
		p.put(new Coordinate(2, 0), new Rabbit(Owner.Player2));

		p.put(new Coordinate(0, 1), new Rabbit(Owner.Player2));
		p.put(new Coordinate(0, 2), new Rabbit(Owner.Player2));

		Game game = new Game(new BoardState(p));
		exhaustivelyCheckPushMoves(game, new Coordinate(0, 0));
	}

	@Test
	public void testHasPushMoveReturnsTrueIfEnemyPieceAreAlmostSuroundedByEdge2() {
		HashMap<Coordinate, AbstractPiece> p = new HashMap<Coordinate, AbstractPiece>();
		p.put(new Coordinate(7, 7), new Dog(Owner.Player2));

		p.put(new Coordinate(6, 7), new Rabbit(Owner.Player1));
		p.put(new Coordinate(5, 7), new Rabbit(Owner.Player1));
		p.put(new Coordinate(6, 6), new Rabbit(Owner.Player1));

		p.put(new Coordinate(7, 6), new Rabbit(Owner.Player1));

		Game game = new Game(new BoardState(p));
		exhaustivelyCheckPushMoves(game, new Coordinate(7, 7));
	}
}
