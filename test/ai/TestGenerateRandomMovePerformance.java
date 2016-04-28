package ai;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class TestGenerateRandomMovePerformance extends AiSetup {
	private void testHardPerformance(Ai ai) {
		long maxTime = 0;
		for (int i = 0; i < ITERATION_SIZE; i++) {
			long start = System.nanoTime();
			ai.generateMove();
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

	private void testSoftPerformance(Ai ai) {
		long start = System.nanoTime();
		for (int i = 0; i < ITERATION_SIZE; i++) {
			ai.generateMove();
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
