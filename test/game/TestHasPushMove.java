package game;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;

import org.junit.Test;

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
	
	@Test
	public void testHasPushMoveReturnsTrueIfPossiblePush() {
		HashMap<Coordinate, AbstractPiece> p = new HashMap<Coordinate, AbstractPiece>();
		p.put(new Coordinate(4, 4), new Cat(Owner.Player1));
		p.put(new Coordinate(4, 3), new Rabbit(Owner.Player2));

		Game game = new Game(new BoardState(p));
		
		assertTrue(game.hasPushMove(new Coordinate(4, 4)));
	}

	@Test
	public void testInvalidCoordinateCantMakePushMove() {
		assertFalse(new Game().hasPushMove(new Coordinate(-1, 0)));
	}

	@Test
	public void testLonePieceHasNoPushMoves() {
		HashMap<Coordinate, AbstractPiece> p = new HashMap<Coordinate, AbstractPiece>();
		p.put(new Coordinate(4, 4), new Cat(Owner.Player1));

		Game game = new Game(new BoardState(p));

		exhaustivelyCheckNoPushMoves(game, new Coordinate(4, 4));
	}

	@Test
	public void testRabbitHasNoPushMoves() {
		HashMap<Coordinate, AbstractPiece> p = new HashMap<Coordinate, AbstractPiece>();
		p.put(new Coordinate(4, 4), new Rabbit(Owner.Player1));

		p.put(new Coordinate(4, 3), new Rabbit(Owner.Player2));

		Game game = new Game(new BoardState(p));
		exhaustivelyCheckNoPushMoves(game, new Coordinate(4, 4));
		exhaustivelyCheckNoPushMoves(game, new Coordinate(4, 3));
	}

	@Test
	public void testNoPushMovesIfWeaker() {
		HashMap<Coordinate, AbstractPiece> p = new HashMap<Coordinate, AbstractPiece>();
		p.put(new Coordinate(4, 4), new Dog(Owner.Player1));

		p.put(new Coordinate(4, 3), new Camel(Owner.Player2));

		Game game = new Game(new BoardState(p));
		exhaustivelyCheckNoPushMoves(game, new Coordinate(4, 4));
	}

	@Test
	public void testNoPushMovesIfEqualStrength() {
		HashMap<Coordinate, AbstractPiece> p = new HashMap<Coordinate, AbstractPiece>();
		p.put(new Coordinate(4, 4), new Dog(Owner.Player1));

		p.put(new Coordinate(4, 3), new Dog(Owner.Player2));

		Game game = new Game(new BoardState(p));
		exhaustivelyCheckNoPushMoves(game, new Coordinate(4, 4));
	}

	@Test
	public void testNoPushMovesIfSuroundedByOwnPieces() {
		HashMap<Coordinate, AbstractPiece> p = new HashMap<Coordinate, AbstractPiece>();
		p.put(new Coordinate(4, 4), new Dog(Owner.Player1));

		p.put(new Coordinate(4, 3), new Rabbit(Owner.Player1));
		p.put(new Coordinate(5, 4), new Rabbit(Owner.Player1));
		p.put(new Coordinate(4, 5), new Rabbit(Owner.Player1));
		p.put(new Coordinate(3, 4), new Rabbit(Owner.Player1));

		Game game = new Game(new BoardState(p));
		exhaustivelyCheckNoPushMoves(game, new Coordinate(4, 4));
	}

}
