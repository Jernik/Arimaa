package ai;

import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import game.BoardState;
import game.Coordinate;
import game.Game;
import move_commands.MoveCommand;
import piece.AbstractPiece;
import piece.Camel;
import piece.Cat;
import piece.Dog;
import piece.Elephant;
import piece.Horse;
import piece.Owner;
import piece.Rabbit;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestGenerateRandomMove {
	private static final double ITERATION_SIZE = 100_000;
	private static final double RANDOM_MARGIN = 0.005;
	private Ai normalAi;
	private Ai catLoverAi;
	private Ai notManyMovesAi;
	private Ai startingAi;

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

		HashMap<Coordinate, AbstractPiece> notManyMovesPieces = new HashMap<Coordinate, AbstractPiece>();
		notManyMovesPieces.put(new Coordinate(0, 0), new Cat(Owner.Player1));
		notManyMovesPieces.put(new Coordinate(1, 0), new Dog(Owner.Player1));
		notManyMovesPieces.put(new Coordinate(2, 0), new Horse(Owner.Player1));
		notManyMovesPieces.put(new Coordinate(3, 0), new Camel(Owner.Player1));
		notManyMovesPieces.put(new Coordinate(4, 0), new Elephant(Owner.Player1));
		notManyMovesPieces.put(new Coordinate(5, 0), new Horse(Owner.Player1));
		notManyMovesPieces.put(new Coordinate(6, 0), new Dog(Owner.Player1));
		notManyMovesPieces.put(new Coordinate(7, 0), new Cat(Owner.Player1));

		notManyMovesPieces.put(new Coordinate(0, 1), new Rabbit(Owner.Player1));
		notManyMovesPieces.put(new Coordinate(1, 1), new Rabbit(Owner.Player1));
		notManyMovesPieces.put(new Coordinate(2, 1), new Rabbit(Owner.Player1));
		notManyMovesPieces.put(new Coordinate(3, 1), new Rabbit(Owner.Player1));
		notManyMovesPieces.put(new Coordinate(4, 1), new Rabbit(Owner.Player1));
		notManyMovesPieces.put(new Coordinate(5, 1), new Rabbit(Owner.Player1));
		notManyMovesPieces.put(new Coordinate(6, 1), new Rabbit(Owner.Player1));
		notManyMovesPieces.put(new Coordinate(7, 1), new Rabbit(Owner.Player1));

		notManyMovesPieces.put(new Coordinate(0, 2), new Cat(Owner.Player2));
		notManyMovesPieces.put(new Coordinate(1, 3), new Dog(Owner.Player2));
		notManyMovesPieces.put(new Coordinate(2, 2), new Horse(Owner.Player2));
		notManyMovesPieces.put(new Coordinate(3, 3), new Camel(Owner.Player2));
		notManyMovesPieces.put(new Coordinate(4, 2), new Elephant(Owner.Player2));
		notManyMovesPieces.put(new Coordinate(5, 3), new Horse(Owner.Player2));
		notManyMovesPieces.put(new Coordinate(6, 2), new Dog(Owner.Player2));
		notManyMovesPieces.put(new Coordinate(7, 3), new Cat(Owner.Player2));
		this.notManyMovesAi = new Ai(Owner.Player2, new Game(new BoardState(notManyMovesPieces)));

		this.startingAi = new Ai(Owner.Player2, new Game());
	}

	public void randomStressTest(HashMap<Object, Double> expectedPercentages, Generater method) {
		HashMap<Object, Double> countMap = new HashMap<Object, Double>();
		for (int i = 0; i < ITERATION_SIZE; i++) {
			Object returnValue = method.generate();
			if (!countMap.containsKey(returnValue)) {
				countMap.put(returnValue, 0.0);
			}
			countMap.put(returnValue, countMap.get(returnValue) + 1);
		}

		// convert count to percentages
		for (Object obj : countMap.keySet()) {
			countMap.put(obj, round(countMap.get(obj) / ITERATION_SIZE, 2));
		}

		for (Object obj : countMap.keySet()) {
			double expectedPercent = expectedPercentages.get(obj);
			double expectedLow = Math.max(expectedPercent - RANDOM_MARGIN,
					Math.min(Double.MIN_NORMAL, expectedPercent));
			double expectedHigh = Math.min(expectedPercent + RANDOM_MARGIN,
					Math.max(100.0 - Double.MIN_NORMAL, expectedPercent));
			double percent = countMap.get(obj);
			String errorString = obj.toString() + " was outside of the expected range of " + (expectedPercent * 100)
					+ "% +- " + (RANDOM_MARGIN * 100) + "% with a percentage of " + (percent * 100) + "%";
			if (expectedLow > percent || percent > expectedHigh) {
				StackTraceElement methodName = Thread.currentThread().getStackTrace()[2];
				System.err.println("FAIL " + methodName);
				// System.err.println(countMap);
				System.out.println();
				System.err.println();
			}
			assertTrue(errorString, expectedLow <= percent && percent <= expectedHigh);
		}
	}

	public void randomStressTest(double expectedPercentages, Generater method) {
		HashMap<Object, Double> countMap = new HashMap<Object, Double>();
		for (int i = 0; i < ITERATION_SIZE; i++) {
			Object returnValue = method.generate();
			if (!countMap.containsKey(returnValue)) {
				countMap.put(returnValue, 0.0);
			}
			countMap.put(returnValue, countMap.get(returnValue) + 1);
		}

		// convert count to percentages
		for (Object obj : countMap.keySet()) {
			countMap.put(obj, round(countMap.get(obj) / ITERATION_SIZE, 2));
		}

		for (Object obj : countMap.keySet()) {
			double expectedPercent = expectedPercentages;
			double expectedLow = Math.max(expectedPercent - RANDOM_MARGIN,
					Math.min(Double.MIN_NORMAL, expectedPercent));
			double expectedHigh = Math.min(expectedPercent + RANDOM_MARGIN,
					Math.max(100.0 - Double.MIN_NORMAL, expectedPercent));
			double percent = countMap.get(obj);
			String errorString = obj.toString() + " was outside of the expected range of " + (expectedPercent * 100)
					+ "% +- " + (RANDOM_MARGIN * 100) + "% with a percentage of " + (percent * 100) + "%";
			if (expectedLow > percent || percent > expectedHigh) {
				StackTraceElement methodName = Thread.currentThread().getStackTrace()[2];
				System.err.println("FAIL " + methodName);
				// System.err.println(countMap);
				System.out.println();
				System.err.println();
			}
			assertTrue(errorString, expectedLow <= percent && percent <= expectedHigh);
		}
	}

	@Test
	public void testGenerateRandomDirectionMiddle() {
		Coordinate coor = new Coordinate(3, 6);
		randomStressTest(0.25, () -> this.normalAi.generateRandomDirection(coor));
	}

	@Test
	public void testGenerateRandomDirectionEdge() {
		Coordinate coor = new Coordinate(3, 0);
		randomStressTest(0.25, () -> this.normalAi.generateRandomDirection(coor));
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
		randomStressTest(1 / 6.0, () -> this.normalAi.getGame().getPieceAt(this.normalAi.generateRandomPieceCoor()));
	}

	@Test
	public void testNormalEveryGeneratedMoveIsValid() {
		for (int i = 0; i < ITERATION_SIZE; i++) {
			MoveCommand move = this.normalAi.generateMove();
			assertTrue(move.toString(), move.isValidMove());
		}
	}

	@Test
	public void testNormalGeneratedMoveIsUniform() {
		randomStressTest(1 / 24.0, () -> this.normalAi.generateMove());
	}

	@Test
	public void testNormalHardPerformanceLimit() {
		long maxTime = 0;
		for (int i = 0; i < ITERATION_SIZE; i++) {
			long start = System.nanoTime();
			this.normalAi.generateMove();
			long time = System.nanoTime() - start;
			if (time > maxTime) {
				maxTime = time;
			}
			String errorString = "this.normalAi.generateMove() exceeded the HARD_TIME_LIMIT of "
					+ getTimeUnits(Ai.HARD_TIME_LIMIT, time) + " by taking " + getTimeUnits(time, Ai.HARD_TIME_LIMIT);
			if (time > Ai.HARD_TIME_LIMIT) {
				System.err.println(
						"FAIL longest normal move generation time was " + getTimeUnits(time, Ai.HARD_TIME_LIMIT)
								+ ", it should be under " + getTimeUnits(Ai.HARD_TIME_LIMIT, time));
				System.out.println();
				System.err.println();
			}
			assertTrue(errorString, time <= Ai.HARD_TIME_LIMIT);
		}
		System.out.println("PASS longest normal move generation time was " + getTimeUnits(maxTime, Ai.HARD_TIME_LIMIT)
				+ ", it should be under " + getTimeUnits(Ai.HARD_TIME_LIMIT, maxTime));
		System.out.println();
		System.err.println();
	}

	@Test
	public void testNormalSoftPerformanceLimit() {
		long start = System.nanoTime();
		for (int i = 0; i < ITERATION_SIZE; i++) {
			this.normalAi.generateMove();
		}
		long time = System.nanoTime() - start;
		long avgTime = (long) (time / ITERATION_SIZE);
		String errorString = "this.normalAi.generateMove() exceeded the AVERAGE_TIME_LIMIT of "
				+ getTimeUnits(Ai.AVERAGE_TIME_LIMIT, avgTime) + " by taking "
				+ getTimeUnits(avgTime, Ai.AVERAGE_TIME_LIMIT);
		if (avgTime > Ai.AVERAGE_TIME_LIMIT) {
			System.err.println(
					"FAIL average normal move generation time was " + getTimeUnits(avgTime, Ai.AVERAGE_TIME_LIMIT)
							+ ", it should be under " + getTimeUnits(Ai.AVERAGE_TIME_LIMIT, avgTime));
			System.out.println();
			System.err.println();

		}
		assertTrue(errorString, avgTime <= Ai.AVERAGE_TIME_LIMIT);
		System.out.println("PASS average normal generation time was  " + getTimeUnits(avgTime, Ai.AVERAGE_TIME_LIMIT)
				+ ", it should be under " + getTimeUnits(Ai.AVERAGE_TIME_LIMIT, avgTime));
		System.out.println();
		System.err.println();
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
	public void testKatLoverEveryGeneratedMoveIsValid() {
		for (int i = 0; i < ITERATION_SIZE; i++) {
			MoveCommand move = this.catLoverAi.generateMove();
			assertTrue(move.toString(), move.isValidMove());
		}
	}

	@Test
	public void testKatLoverGeneratedMoveIsUniform() {
		randomStressTest(1 / 24.0, () -> this.catLoverAi.generateMove());
	}

	@Test
	public void testKatLoverHardPerformanceLimit() {
		long maxTime = 0;
		for (int i = 0; i < ITERATION_SIZE; i++) {
			long start = System.nanoTime();
			this.catLoverAi.generateMove();
			long time = System.nanoTime() - start;
			if (time > maxTime) {
				maxTime = time;
			}
			String errorString = "this.catLoverAi.generateMove() exceeded the HARD_TIME_LIMIT of "
					+ getTimeUnits(Ai.HARD_TIME_LIMIT, time) + " by taking " + getTimeUnits(time, Ai.HARD_TIME_LIMIT);
			if (time > Ai.HARD_TIME_LIMIT) {
				System.err.println(
						"FAIL longest catLover move generation time was " + getTimeUnits(time, Ai.HARD_TIME_LIMIT)
								+ ", it should be under " + getTimeUnits(Ai.HARD_TIME_LIMIT, time));
				System.out.println();
				System.err.println();
			}
			assertTrue(errorString, time <= Ai.HARD_TIME_LIMIT);
		}
		System.out.println("PASS longest catLover move generation time was " + getTimeUnits(maxTime, Ai.HARD_TIME_LIMIT)
				+ ", it should be under " + getTimeUnits(Ai.HARD_TIME_LIMIT, maxTime));
		System.out.println();
		System.err.println();
	}

	@Test
	public void testKatLoverSoftPerformanceLimit() {
		long start = System.nanoTime();
		for (int i = 0; i < ITERATION_SIZE; i++) {
			this.catLoverAi.generateMove();
		}
		long time = System.nanoTime() - start;
		long avgTime = (long) (time / ITERATION_SIZE);
		String errorString = "this.catLoverAi.generateMove() exceeded the AVERAGE_TIME_LIMIT of "
				+ getTimeUnits(Ai.AVERAGE_TIME_LIMIT, avgTime) + " by taking "
				+ getTimeUnits(avgTime, Ai.AVERAGE_TIME_LIMIT);
		if (avgTime > Ai.AVERAGE_TIME_LIMIT) {
			System.err.println(
					"FAIL average catLover move generation time was " + getTimeUnits(avgTime, Ai.AVERAGE_TIME_LIMIT)
							+ ", it should be under " + getTimeUnits(Ai.AVERAGE_TIME_LIMIT, avgTime));
			System.out.println();
			System.err.println();
		}
		assertTrue(errorString, avgTime <= Ai.AVERAGE_TIME_LIMIT);
		System.out.println("PASS average catLover generation time was  " + getTimeUnits(avgTime, Ai.AVERAGE_TIME_LIMIT)
				+ ", it should be under " + getTimeUnits(Ai.AVERAGE_TIME_LIMIT, avgTime));
		System.out.println();
		System.err.println();
	}

	@Test
	public void testNotManyMovesGenerateRandomPieceIsUniform() {
		randomStressTest(1 / 6.0,
				() -> this.notManyMovesAi.getGame().getPieceAt(this.notManyMovesAi.generateRandomPieceCoor()));
	}

	@Test
	public void testNotManyMovesEveryGeneratedMoveIsValid() {
		for (int i = 0; i < ITERATION_SIZE; i++) {
			MoveCommand move = this.notManyMovesAi.generateMove();
			assertTrue(move.toString(), move.isValidMove());
		}
	}

	@Test
	public void testNotManyMovesGeneratedMoveIsUniform() {
		randomStressTest(1 / 24.0, () -> this.notManyMovesAi.generateMove());
	}

	@Test
	public void testNotManyMovesHardPerformanceLimit() {
		long maxTime = 0;
		for (int i = 0; i < ITERATION_SIZE; i++) {
			long start = System.nanoTime();
			this.notManyMovesAi.generateMove();
			long time = System.nanoTime() - start;
			if (time > maxTime) {
				maxTime = time;
			}
			String errorString = "this.notManyMovesAi.generateMove() exceeded the HARD_TIME_LIMIT of "
					+ getTimeUnits(Ai.HARD_TIME_LIMIT, time) + " by taking " + getTimeUnits(time, Ai.HARD_TIME_LIMIT);
			if (time > Ai.HARD_TIME_LIMIT) {
				System.err.println(
						"FAIL longest notManyMoves move generation time was " + getTimeUnits(time, Ai.HARD_TIME_LIMIT)
								+ ", it should be under " + getTimeUnits(Ai.HARD_TIME_LIMIT, time));
				System.out.println();
				System.err.println();
			}
			assertTrue(errorString, time <= Ai.HARD_TIME_LIMIT);
		}
		System.out.println(
				"PASS longest notManyMoves move generation time was " + getTimeUnits(maxTime, Ai.HARD_TIME_LIMIT)
						+ ", it should be under " + getTimeUnits(Ai.HARD_TIME_LIMIT, maxTime));
		System.out.println();
		System.err.println();
	}

	@Test
	public void testNotManyMovesSoftPerformanceLimit() {
		long start = System.nanoTime();
		for (int i = 0; i < ITERATION_SIZE; i++) {
			this.notManyMovesAi.generateMove();
		}
		long time = System.nanoTime() - start;
		long avgTime = (long) (time / ITERATION_SIZE);
		String errorString = "this.notManyMovesAi.generateMove() exceeded the AVERAGE_TIME_LIMIT of "
				+ getTimeUnits(Ai.AVERAGE_TIME_LIMIT, avgTime) + " by taking "
				+ getTimeUnits(avgTime, Ai.AVERAGE_TIME_LIMIT);
		if (avgTime > Ai.AVERAGE_TIME_LIMIT) {
			System.err.println(
					"FAIL average notManyMoves move generation time was " + getTimeUnits(avgTime, Ai.AVERAGE_TIME_LIMIT)
							+ ", it should be under " + getTimeUnits(Ai.AVERAGE_TIME_LIMIT, avgTime));
			System.out.println();
			System.err.println();
		}
		assertTrue(errorString, avgTime <= Ai.AVERAGE_TIME_LIMIT);
		System.out.println(
				"PASS average notManyMoves generation time was  " + getTimeUnits(avgTime, Ai.AVERAGE_TIME_LIMIT)
						+ ", it should be under " + getTimeUnits(Ai.AVERAGE_TIME_LIMIT, avgTime));
		System.out.println();
		System.err.println();
	}

	@Test
	public void testStartingGenerateRandomPieceIsUniform() {
		randomStressTest(1 / 6.0,
				() -> this.startingAi.getGame().getPieceAt(this.startingAi.generateRandomPieceCoor()));
	}

	@Test
	public void testStartingEveryGeneratedMoveIsValid() {
		for (int i = 0; i < ITERATION_SIZE; i++) {
			MoveCommand move = this.startingAi.generateMove();
			assertTrue(move.toString(), move.isValidMove());
		}
	}

	@Test
	public void testStartingGeneratedMoveIsUniform() {
		randomStressTest(1 / 24.0, () -> this.startingAi.generateMove());
	}

	@Test
	public void testStartingHardPerformanceLimit() {
		long maxTime = 0;
		for (int i = 0; i < ITERATION_SIZE; i++) {
			long start = System.nanoTime();
			this.startingAi.generateMove();
			long time = System.nanoTime() - start;
			if (time > maxTime) {
				maxTime = time;
			}
			String errorString = "this.startingAi.generateMove() exceeded the HARD_TIME_LIMIT of "
					+ getTimeUnits(Ai.HARD_TIME_LIMIT, time) + " by taking " + getTimeUnits(time, Ai.HARD_TIME_LIMIT);
			if (time > Ai.HARD_TIME_LIMIT) {
				System.err.println(
						"FAIL longest starting move generation time was " + getTimeUnits(time, Ai.HARD_TIME_LIMIT)
								+ ", it should be under " + getTimeUnits(Ai.HARD_TIME_LIMIT, time));
				System.out.println();
				System.err.println();
			}
			assertTrue(errorString, time <= Ai.HARD_TIME_LIMIT);
		}
		System.out.println("PASS longest starting move generation time was " + getTimeUnits(maxTime, Ai.HARD_TIME_LIMIT)
				+ ", it should be under " + getTimeUnits(Ai.HARD_TIME_LIMIT, maxTime));
		System.out.println();
		System.err.println();
	}

	@Test
	public void testStartingSoftPerformanceLimit() {
		long start = System.nanoTime();
		for (int i = 0; i < ITERATION_SIZE; i++) {
			this.startingAi.generateMove();
		}
		long time = System.nanoTime() - start;
		long avgTime = (long) (time / ITERATION_SIZE);
		String errorString = "this.startingAi.generateMove() exceeded the AVERAGE_TIME_LIMIT of "
				+ getTimeUnits(Ai.AVERAGE_TIME_LIMIT, avgTime) + " by taking "
				+ getTimeUnits(avgTime, Ai.AVERAGE_TIME_LIMIT);
		if (avgTime > Ai.AVERAGE_TIME_LIMIT) {
			System.err.println(
					"FAIL average starting move generation time was " + getTimeUnits(avgTime, Ai.AVERAGE_TIME_LIMIT)
							+ ", it should be under " + getTimeUnits(Ai.AVERAGE_TIME_LIMIT, avgTime));
			System.out.println();
			System.err.println();
		}
		assertTrue(errorString, avgTime <= Ai.AVERAGE_TIME_LIMIT);
		System.out.println("PASS average starting generation time was  " + getTimeUnits(avgTime, Ai.AVERAGE_TIME_LIMIT)
				+ ", it should be under " + getTimeUnits(Ai.AVERAGE_TIME_LIMIT, avgTime));
		System.out.println();
		System.err.println();
	}

	/**
	 * returns a string that gives the given time difference in easily read time units. The second time param is another
	 * time which should have the same units
	 * 
	 * @param time
	 * @param otherTime
	 * @return
	 */
	public static String getTimeUnits(long time, long otherTime) {
		double newTime = time;
		double newOtherTime = otherTime;
		if (time < 1000 || newOtherTime < 1000) {
			return String.format("%d NanoSeconds", time);
		}
		newTime = time / 1000.0;
		newOtherTime = otherTime / 1000.0;
		if (newTime < 1000 || newOtherTime < 1000) {
			return String.format("%.3f MicroSeconds", newTime);
		}
		newTime /= 1000.0;
		newOtherTime = otherTime / 1000.0;
		if (newTime < 1000 || newOtherTime < 1000) {
			return String.format("%.3f MiliSeconds", newTime);
		}
		newTime /= 1000.0;
		newOtherTime = otherTime / 1000.0;
		if (newTime < 300 || newOtherTime < 300) {
			return String.format("%.3f Seconds", newTime);
		}
		newTime /= 60.0;
		newOtherTime = otherTime / 1000.0;
		if (newTime < 180 || newOtherTime < 180) {
			return String.format("%.3f Minutes", newTime);
		}
		return String.format("%.3f Hours", newTime / 60.0);
	}

	public static double round(double value, int places) {
		if (places < 0)
			throw new IllegalArgumentException();

		BigDecimal bd = new BigDecimal(value);
		bd = bd.setScale(places, RoundingMode.HALF_UP);
		return bd.doubleValue();
	}
}
