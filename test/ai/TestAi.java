package ai;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import java.util.HashMap;

import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import board.BoardState;
import board.Coordinate;
import game.Game;
import piece.AbstractPiece;
import piece.Camel;
import piece.Cat;
import piece.Dog;
import piece.Elephant;
import piece.Horse;
import piece.Owner;
import piece.Rabbit;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestAi extends AiSetup {
	@BeforeClass
	public static void setupStressSettings() {
		RANDOM_MARGIN = 0.01;
		ITERATION_SIZE = 25_000;
	}

	@Test
	public void testConstructor() {
		Ai ai = new Ai(Owner.Player2, new Game());
		assertNotNull(ai);
	}

	@Test
	public void testGetOwner() {
		Owner owner = Owner.Player1;
		Ai ai = new Ai(owner, new Game());
		assertEquals(owner, ai.getOwner());

		owner = Owner.Player2;
		ai = new Ai(owner, new Game());
		assertEquals(owner, ai.getOwner());
	}

	@Test
	public void testGetGame() {
		Game game = new Game();
		Ai ai = new Ai(Owner.Player1, game);
		assertEquals(game, ai.getGame());

		HashMap<Coordinate, AbstractPiece> pieces = new HashMap<Coordinate, AbstractPiece>();
		pieces.put(new Coordinate(3, 5), new Rabbit(Owner.Player1));
		pieces.put(new Coordinate(1, 2), new Camel(Owner.Player1));
		pieces.put(new Coordinate(6, 3), new Elephant(Owner.Player2));

		game = new Game();
		ai = new Ai(Owner.Player1, game);
		assertEquals(game, ai.getGame());
	}

	@Test
	public void testAiEqualsReturnsFalseIfNull() {
		Game game = new Game();
		Ai ai = new Ai(Owner.Player1, game);

		assertNotEquals(ai, null);
	}

	@Test
	public void testAiEqualsReturnsTrueForReflexive() {
		Game game = new Game();
		Ai ai = new Ai(Owner.Player1, game);

		assertEquals(ai, ai);
		assertEquals(ai.hashCode(), ai.hashCode());
	}

	@Test
	public void testAiEqualsReturnsTrueForSymmetric() {
		Game game = new Game();
		Ai ai1 = new Ai(Owner.Player1, game);
		Ai ai2 = new Ai(Owner.Player1, game);

		assertEquals(ai1, ai2);
		assertEquals(ai2, ai1);

		assertEquals(ai1.hashCode(), ai2.hashCode());
	}

	@Test
	public void testAiEqualsReturnsFalseIfDifferentGame() {
		Ai ai1 = new Ai(Owner.Player1, new Game());
		Game game = new Game();
		game.incrementTurn();
		Ai ai2 = new Ai(Owner.Player1, game);

		assertNotEquals(ai1, ai2);
		assertNotEquals(ai1.hashCode(), ai2.hashCode());
	}

	@Test
	public void testAiEqualsReturnsFalseIfDifferentOwner() {
		Game game = new Game();
		Ai ai1 = new Ai(Owner.Player1, game);
		Ai ai2 = new Ai(Owner.Player2, game);

		assertNotEquals(ai1, ai2);
		assertNotEquals(ai1.hashCode(), ai2.hashCode());
	}

	@Test
	public void testGenerateRandomDirectionMiddle() {
		Coordinate coor = new Coordinate(3, 6);
		randomStressTest(4, () -> this.normalAi.generateRandomDirection(coor));
	}

	@Test
	public void testGenerateRandomDirectionEdge() {
		Coordinate coor = new Coordinate(3, 0);
		randomStressTest(4, () -> this.normalAi.generateRandomDirection(coor));
	}

	@Test
	public void testGenerateRandomDirectionCorner() {
		Coordinate coor = new Coordinate(0, 0);
		HashMap<Object, Double> expectedPercentages = new HashMap<Object, Double>();
		expectedPercentages.put(coor.down(), 0.25);
		expectedPercentages.put(coor.right(), 0.25);
		expectedPercentages.put(coor.up(), 0.5); // invalid coordinates are equal
		expectedPercentages.put(coor.left(), 0.5); // invalid coordinates are equal

		randomStressTest(expectedPercentages, () -> this.normalAi.generateRandomDirection(coor));
	}

	@Test
	public void testNormalGenerateRandomPieceIsUniform() {
		randomStressTest(6, () -> this.normalAi.getGame().getPieceAt(this.normalAi.generateRandomPieceCoor()));
	}

	@Test
	public void testKatLoverGenerateRandomPieceIsUniform() {
		HashMap<Object, Double> expectedPercentages = new HashMap<Object, Double>();
		expectedPercentages.put(new Cat(this.catLoverAi.getOwner()), 1 / 2.0);
		expectedPercentages.put(new Camel(this.catLoverAi.getOwner()), 1 / 10.0);
		expectedPercentages.put(new Dog(this.catLoverAi.getOwner()), 0.0);
		expectedPercentages.put(new Elephant(this.catLoverAi.getOwner()), 1 / 10.0);
		expectedPercentages.put(new Horse(this.catLoverAi.getOwner()), 1 / 10.0);
		expectedPercentages.put(new Rabbit(this.catLoverAi.getOwner()), 1 / 5.0);
		randomStressTest(expectedPercentages,
				() -> this.catLoverAi.getGame().getPieceAt(this.catLoverAi.generateRandomPieceCoor()));
	}

	@Test
	public void testNotManyMovesGenerateRandomPieceIsUniform() {
		HashMap<Object, Double> expectedPercentages = new HashMap<Object, Double>();
		expectedPercentages.put(new Cat(this.notManyMovesAi.getOwner()), 1 / 6.0);
		expectedPercentages.put(new Camel(this.notManyMovesAi.getOwner()), 1 / 12.0);
		expectedPercentages.put(new Dog(this.notManyMovesAi.getOwner()), 1 / 6.0);
		expectedPercentages.put(new Elephant(this.notManyMovesAi.getOwner()), 1 / 12.0);
		expectedPercentages.put(new Horse(this.notManyMovesAi.getOwner()), 1 / 6.0);
		expectedPercentages.put(new Rabbit(this.notManyMovesAi.getOwner()), 1 / 3.0);

		randomStressTest(expectedPercentages,
				() -> this.notManyMovesAi.getGame().getPieceAt(this.notManyMovesAi.generateRandomPieceCoor()));
	}

	@Test
	public void testStartingGenerateRandomPieceIsUniform() {
		HashMap<Object, Double> expectedPercentages = new HashMap<Object, Double>();
		expectedPercentages.put(new Cat(this.startingAi.getOwner()), 1 / 8.0);
		expectedPercentages.put(new Camel(this.startingAi.getOwner()), 1 / 16.0);
		expectedPercentages.put(new Dog(this.startingAi.getOwner()), 1 / 8.0);
		expectedPercentages.put(new Elephant(this.startingAi.getOwner()), 1 / 16.0);
		expectedPercentages.put(new Horse(this.startingAi.getOwner()), 1 / 8.0);
		expectedPercentages.put(new Rabbit(this.startingAi.getOwner()), 1 / 2.0);

		randomStressTest(expectedPercentages,
				() -> this.startingAi.getGame().getPieceAt(this.startingAi.generateRandomPieceCoor()));
	}

	@Test
	public void testGenerateMoveDoesNotMutateBoardState() {
		for (int i = 0; i < ITERATION_SIZE; i++) {
			BoardState board = new BoardState(this.normalAi.getGame().getBoardState());
			this.normalAi.generateMove();
			assertEquals(board, this.normalAi.getGame().getBoardState());
		}
	}
}
