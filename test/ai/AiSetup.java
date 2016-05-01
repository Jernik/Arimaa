package ai;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.HashMap;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

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

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AiSetup {
	public static double ITERATION_SIZE;
	public static double RANDOM_MARGIN;
	protected Ai normalAi;
	protected Ai catLoverAi;
	protected Ai notManyMovesAi;
	protected Ai startingAi;

	@BeforeClass
	public static void setStressSettings() {
		ITERATION_SIZE = 100_000;
		RANDOM_MARGIN = 0.005;
	}

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
		catLoverPieces.put(new Coordinate(4, 2), new Rabbit(Owner.Player1));
		catLoverPieces.put(new Coordinate(0, 1), new Dog(Owner.Player1));
		catLoverPieces.put(new Coordinate(2, 4), new Rabbit(Owner.Player1));
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
		catLoverPieces.put(new Coordinate(1, 2), new Horse(Owner.Player2));
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
		notManyMovesPieces.put(new Coordinate(3, 2), new Rabbit(Owner.Player1));
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

		notManyMovesPieces.put(new Coordinate(1, 4), new Rabbit(Owner.Player2));
		notManyMovesPieces.put(new Coordinate(3, 4), new Rabbit(Owner.Player2));
		notManyMovesPieces.put(new Coordinate(5, 4), new Rabbit(Owner.Player2));
		notManyMovesPieces.put(new Coordinate(7, 4), new Rabbit(Owner.Player2));
		this.notManyMovesAi = new Ai(Owner.Player2, new Game(new BoardState(notManyMovesPieces)));

		this.startingAi = new Ai(Owner.Player2, new Game());
	}

	public void randomStressTest(HashMap<Object, Double> expectedPercentages, Generater method) {
		randomStressTest(expectedPercentages, method, (HashMap<Object, Double> map) -> {
		});
	}

	public void randomStressTest(HashMap<Object, Double> expectedPercentages, Generater method, Asserter asserts) {
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
			countMap.put(obj, round(countMap.get(obj) / ITERATION_SIZE, 4));
		}
		asserts.execute(countMap);

		DecimalFormat df = new DecimalFormat("0.00");
		for (Object obj : countMap.keySet()) {
			double expectedPercent = expectedPercentages.get(obj);
			double expectedLow = Math.max(expectedPercent - RANDOM_MARGIN,
					Math.min(Double.MIN_NORMAL, expectedPercent));
			double expectedHigh = Math.min(expectedPercent + RANDOM_MARGIN,
					Math.max(100.0 - Double.MIN_NORMAL, expectedPercent));
			double percent = countMap.get(obj);
			String errorString = obj.toString() + " was outside of the expected range of "
					+ df.format(expectedPercent * 100) + "% +- " + (RANDOM_MARGIN * 100) + "% with a percentage of "
					+ (percent * 100) + "%";
			if (expectedLow > percent || percent > expectedHigh) {
				StackTraceElement methodName = Thread.currentThread().getStackTrace()[2];
				System.err.println("FAIL " + methodName);
				System.err.println(countMap);
				System.out.println();
				System.err.println();
			}
			assertTrue(errorString, expectedLow <= percent && percent <= expectedHigh);
		}
	}

	// have asserts statements
	public void randomStressTest(int sampleSize, Generater method) {
		randomStressTest(sampleSize, method, (HashMap<Object, Double> map) -> {
		});
	}

	public void randomStressTest(int sampleSize, Generater method, Asserter asserts) {
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
			countMap.put(obj, round(countMap.get(obj) / ITERATION_SIZE, 4));
		}

		asserts.execute(countMap);

		DecimalFormat df = new DecimalFormat("0.00");
		for (Object obj : countMap.keySet()) {
			// double expectedPercent = expectedPercentages;
			double expectedPercent = 1 / (double) countMap.size();
			double expectedLow = Math.max(expectedPercent - RANDOM_MARGIN,
					Math.min(Double.MIN_NORMAL, expectedPercent));
			double expectedHigh = Math.min(expectedPercent + RANDOM_MARGIN,
					Math.max(100.0 - Double.MIN_NORMAL, expectedPercent));
			double percent = countMap.get(obj);
			String errorString = obj.toString() + " was outside of the expected range of "
					+ df.format(expectedPercent * 100) + "% +- " + (RANDOM_MARGIN * 100) + "% with a percentage of "
					+ (percent * 100) + "%";
			if (expectedLow > percent || percent > expectedHigh) {
				StackTraceElement methodName = Thread.currentThread().getStackTrace()[2];
				System.err.println("FAIL " + methodName);
				System.err.println(countMap);
				System.out.println();
				System.err.println();
			}
			assertTrue(errorString, expectedLow <= percent && percent <= expectedHigh);
		}
		assertEquals("expected a sample size of " + sampleSize + ", but was " + countMap.size(), sampleSize,
				countMap.size());
	}

	public String getAiVarString(Ai ai) {
		if (ai == this.normalAi) {
			return "normalAi      ";
		} else if (ai == this.catLoverAi) {
			return "catLoverAi    ";
		} else if (ai == this.notManyMovesAi) {
			return "notManyMovesAi";
		} else if (ai == this.startingAi) {
			return "startingAi    ";
		}
		return "I don't know this AI, add it to the Ai Var string function";
	}

	/**
	 * returns a string that gives the given time difference in miliseconds.
	 * 
	 * @param time
	 * @return
	 */
	public static String getTimeUnits(long time) {
		double newTime = time;
		newTime = time / 1_000_000.0;
		return String.format("%.3f MiliSeconds", newTime);
	}

	public static double round(double value, int places) {
		if (places < 0)
			throw new IllegalArgumentException();

		BigDecimal bd = new BigDecimal(value);
		bd = bd.setScale(places, RoundingMode.HALF_UP);
		return bd.doubleValue();
	}
}
