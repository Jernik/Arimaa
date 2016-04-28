package ai;

import static org.junit.Assert.assertTrue;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestGenerateRandomMovePerformance extends AiSetup {
	@BeforeClass
	public static void setupStressSettings() {
		ITERATION_SIZE = 100_000;
	}

	@AfterClass
	public static void print() {
		System.out.println();
	}

	private void testHardPerformance(Ai ai) {
		long maxTime = 0;
		String aiVarString = getAiVarString(ai);
		for (int i = 0; i < ITERATION_SIZE; i++) {
			long start = System.nanoTime();
			ai.generateMove();
			long time = System.nanoTime() - start;
			if (time > maxTime) {
				maxTime = time;
			}
			String errorString = "this." + aiVarString + ".generateMove() exceeded the HARD_TIME_LIMIT of "
					+ getTimeUnits(Ai.HARD_TIME_LIMIT) + " by taking " + getTimeUnits(time);
			if (time > Ai.HARD_TIME_LIMIT) {
				System.err.println("FAIL longest " + aiVarString + " move generation time was " + getTimeUnits(time)
						+ ", it should be under " + getTimeUnits(Ai.HARD_TIME_LIMIT));
			}
			assertTrue(errorString, time <= Ai.HARD_TIME_LIMIT);
		}
		System.out.println("PASS longest " + aiVarString + " move generation time was " + getTimeUnits(maxTime)
				+ ", it should be under " + getTimeUnits(Ai.HARD_TIME_LIMIT));
	}

	private void testSoftPerformance(Ai ai) {
		long start = System.nanoTime();
		String aiVarString = getAiVarString(ai);
		for (int i = 0; i < ITERATION_SIZE; i++) {
			ai.generateMove();
		}
		long time = System.nanoTime() - start;
		long avgTime = (long) (time / ITERATION_SIZE);
		String errorString = "this." + aiVarString + ".generateMove() exceeded the AVERAGE_TIME_LIMIT of "
				+ getTimeUnits(Ai.AVERAGE_TIME_LIMIT) + " by taking " + getTimeUnits(avgTime);
		if (avgTime > Ai.AVERAGE_TIME_LIMIT) {
			System.err.println("FAIL average " + aiVarString + " move generation time was " + getTimeUnits(avgTime)
					+ ", it should be under " + getTimeUnits(Ai.AVERAGE_TIME_LIMIT));
		}
		assertTrue(errorString, avgTime <= Ai.AVERAGE_TIME_LIMIT);
		System.out.println("PASS average " + aiVarString + " move generation time was " + getTimeUnits(avgTime)
				+ ", it should be under " + getTimeUnits(Ai.AVERAGE_TIME_LIMIT));
	}

	@Test
	public void testNormalHardPerformanceLimit() {
		testHardPerformance(this.normalAi);
	}

	@Test
	public void testNormalSoftPerformanceLimit() {
		this.testSoftPerformance(this.normalAi);
	}

	@Test
	public void testKatLoverHardPerformanceLimit() {
		this.testHardPerformance(this.catLoverAi);
	}

	@Test
	public void testKatLoverSoftPerformanceLimit() {
		this.testSoftPerformance(this.catLoverAi);
	}

	@Test
	public void testNotManyMovesHardPerformanceLimit() {
		this.testHardPerformance(this.notManyMovesAi);
	}

	@Test
	public void testNotManyMovesSoftPerformanceLimit() {
		this.testSoftPerformance(this.notManyMovesAi);
	}

	@Test
	public void testStartingHardPerformanceLimit() {
		this.testHardPerformance(this.startingAi);
	}

	@Test
	public void testStartingSoftPerformanceLimit() {
		testSoftPerformance(this.startingAi);
	}
}
