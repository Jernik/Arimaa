package ai;

import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestGenerateRandomMoveIsUniform extends AiSetup {
	@BeforeClass
	public static void setupStressSettings() {
		RANDOM_MARGIN = 0.005;
		ITERATION_SIZE = 100_000;
	}

	// sample size may be counted wrong
	@Test
	public void testNormalGeneratedMoveIsUniform() {
		randomStressTest(24, () -> this.normalAi.generateMove());
	}

	@Test
	public void testKatLoverGeneratedMoveIsUniform() {
		randomStressTest(28, () -> this.catLoverAi.generateMove());
	}

	@Test
	public void testNotManyMovesGeneratedMoveIsUniform() {
		randomStressTest(24, () -> this.notManyMovesAi.generateMove());
	}

	@Test
	public void testStartingGeneratedMoveIsUniform() {
		randomStressTest(24, () -> this.startingAi.generateMove());
	}
}
