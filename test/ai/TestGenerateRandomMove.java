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
	private static final double RANDOM_MARGIN = 0.005;
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

	public void randomStressTest(HashMap<Object, Double> expectedPercentages, Generater method) {
		HashMap<Object, Integer> countMap = new HashMap<Object, Integer>();
		for (int i = 0; i < ITERATION_SIZE; i++) {
			Object returnValue = method.generate();
			if (!countMap.containsKey(returnValue)) {
				countMap.put(returnValue, 0);
			}
			countMap.put(returnValue, countMap.get(returnValue) + 1);
		}

		DecimalFormat df = new DecimalFormat("#.00");
		for (Object obj : countMap.keySet()) {
			double expectedPercent = expectedPercentages.get(obj);
			double expectedLow = expectedPercent - RANDOM_MARGIN;
			double expectedHigh = expectedPercent + RANDOM_MARGIN;
			int count = countMap.get(obj);
			double percent = count / ITERATION_SIZE;
			String errorString = obj.toString() + " was outside of the expected range of "
					+ df.format(expectedPercent * 100) + "% +- " + df.format(RANDOM_MARGIN * 100)
					+ "% with a percentage of " + df.format(percent * 100) + "%";
			assertTrue(errorString, expectedLow <= percent && percent <= expectedHigh);
		}
	}

	@Test
	public void testGenerateRandomPieceUniform() {
		HashMap<Object, Double> expectedPercentages = new HashMap<Object, Double>();
		expectedPercentages.put(new Cat(this.normalAi.getOwner()), 1 / 6.0);
		expectedPercentages.put(new Camel(this.normalAi.getOwner()), 1 / 6.0);
		expectedPercentages.put(new Dog(this.normalAi.getOwner()), 1 / 6.0);
		expectedPercentages.put(new Elephant(this.normalAi.getOwner()), 1 / 6.0);
		expectedPercentages.put(new Horse(this.normalAi.getOwner()), 1 / 6.0);
		expectedPercentages.put(new Rabbit(this.normalAi.getOwner()), 1 / 6.0);
		randomStressTest(expectedPercentages,
				() -> this.normalAi.getGame().getPieceAt(this.normalAi.generateRandomPieceCoor()));
	}

	@Test
	public void testGenerateRandomPieceCatLover() {
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
}
