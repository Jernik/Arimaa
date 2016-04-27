package ai;

import static org.junit.Assert.assertTrue;

import java.text.DecimalFormat;
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
import piece.Horse;
import piece.Owner;
import piece.Rabbit;

public class TestGenerateRandomMove {
	private static final double ITERATION_SIZE = 50_000;
	private static final double RANDOM_MARGIN = 0.01;
	private Ai normalAi;
	private Ai catLoverAi;

	@Before
	public void setup() {
		HashMap<Coordinate, AbstractPiece> pieces = new HashMap<Coordinate, AbstractPiece>();
		pieces.put(new Coordinate(0, 0), new Rabbit(Owner.Player1));
		pieces.put(new Coordinate(0, 1), new Dog(Owner.Player1));
		pieces.put(new Coordinate(1, 0), new Rabbit(Owner.Player1));
		pieces.put(new Coordinate(1, 1), new Dog(Owner.Player1));

		pieces.put(new Coordinate(2, 2), new Cat(Owner.Player2));
		pieces.put(new Coordinate(3, 3), new Rabbit(Owner.Player2));
		pieces.put(new Coordinate(4, 4), new Dog(Owner.Player2));
		pieces.put(new Coordinate(5, 5), new Camel(Owner.Player2));
		pieces.put(new Coordinate(6, 6), new Elephant(Owner.Player2));
		pieces.put(new Coordinate(7, 7), new Horse(Owner.Player2));
		this.normalAi = new Ai(Owner.Player2, new Game(new BoardState(pieces)));

		HashMap<Coordinate, AbstractPiece> catLoverPieces = new HashMap<Coordinate, AbstractPiece>();
		catLoverPieces.put(new Coordinate(0, 0), new Rabbit(Owner.Player1));
		catLoverPieces.put(new Coordinate(0, 1), new Dog(Owner.Player1));
		catLoverPieces.put(new Coordinate(1, 0), new Rabbit(Owner.Player1));
		catLoverPieces.put(new Coordinate(1, 1), new Dog(Owner.Player1));

		catLoverPieces.put(new Coordinate(6, 7), new Cat(Owner.Player2));
		catLoverPieces.put(new Coordinate(2, 2), new Cat(Owner.Player2));
		catLoverPieces.put(new Coordinate(2, 3), new Cat(Owner.Player2));
		catLoverPieces.put(new Coordinate(3, 2), new Cat(Owner.Player2));
		catLoverPieces.put(new Coordinate(3, 3), new Cat(Owner.Player2));

		catLoverPieces.put(new Coordinate(7, 7), new Rabbit(Owner.Player2));
		catLoverPieces.put(new Coordinate(3, 6), new Rabbit(Owner.Player2));
		catLoverPieces.put(new Coordinate(3, 4), new Camel(Owner.Player2));
		catLoverPieces.put(new Coordinate(4, 4), new Elephant(Owner.Player2));
		catLoverPieces.put(new Coordinate(0, 0), new Horse(Owner.Player2));
		this.catLoverAi = new Ai(Owner.Player2, new Game(new BoardState(catLoverPieces)));
	}

	public void runGenerateRandomPiece(Ai ai, HashMap<Class<? extends AbstractPiece>, Double> expectedPercentages) {
		HashMap<Class<? extends AbstractPiece>, Integer> pieceCount = new HashMap<Class<? extends AbstractPiece>, Integer>();
		pieceCount.put(Cat.class, 0);
		pieceCount.put(Camel.class, 0);
		pieceCount.put(Dog.class, 0);
		pieceCount.put(Elephant.class, 0);
		pieceCount.put(Horse.class, 0);
		pieceCount.put(Rabbit.class, 0);

		for (int i = 0; i < ITERATION_SIZE; i++) {
			AbstractPiece piece = ai.getGame().getPieceAt(ai.generateRandomPieceCoor());
			pieceCount.put(piece.getClass(), pieceCount.get(piece.getClass()) + 1);
		}

		DecimalFormat df = new DecimalFormat("#.00");
		for (Class<? extends AbstractPiece> clazz : pieceCount.keySet()) {
			double expectedPercent = expectedPercentages.get(clazz);
			double expectedLow = expectedPercent - RANDOM_MARGIN;
			double expectedHigh = expectedPercent + RANDOM_MARGIN;
			int count = pieceCount.get(clazz);
			double percent = count / ITERATION_SIZE;
			String errorString = clazz.toString() + " was outside of the expected range of "
					+ df.format(expectedPercent * 100) + "% +- " + df.format(RANDOM_MARGIN * 100)
					+ "% with a percentage of " + df.format(percent * 100) + "%";
			assertTrue(errorString, expectedLow <= percent && percent <= expectedHigh);
		}
	}

	@Test
	public void testGenerateRandomPieceUniform() {
		HashMap<Class<? extends AbstractPiece>, Double> expectedPercentages = new HashMap<Class<? extends AbstractPiece>, Double>();
		expectedPercentages.put(Cat.class, 1 / 6.0);
		expectedPercentages.put(Camel.class, 1 / 6.0);
		expectedPercentages.put(Dog.class, 1 / 6.0);
		expectedPercentages.put(Elephant.class, 1 / 6.0);
		expectedPercentages.put(Horse.class, 1 / 6.0);
		expectedPercentages.put(Rabbit.class, 1 / 6.0);
		runGenerateRandomPiece(this.normalAi, expectedPercentages);
	}

	@Test
	public void testGenerateRandomPieceCatLover() {
		HashMap<Class<? extends AbstractPiece>, Double> expectedPercentages = new HashMap<Class<? extends AbstractPiece>, Double>();
		expectedPercentages.put(Cat.class, 1 / 2.0);
		expectedPercentages.put(Camel.class, 1 / 10.0);
		expectedPercentages.put(Dog.class, 0.0);
		expectedPercentages.put(Elephant.class, 1 / 10.0);
		expectedPercentages.put(Horse.class, 1 / 10.0);
		expectedPercentages.put(Rabbit.class, 1 / 5.0);
		runGenerateRandomPiece(this.catLoverAi, expectedPercentages);
	}

	@Test
	public void lambdas() {
		testRandom(() -> normalAi.generateRandomPieceCoor());
		testRandom(() -> normalAi.getGame().getPieceAt(normalAi.generateRandomPieceCoor()));
	}

	// public <T, V> void testRandom(Function<T, V> function) {
	//
	// }
	private void testRandom(Generater method) {
		System.out.println(method.generate());
		System.out.println(method.generate());
		System.out.println(method.generate());

	}

}
