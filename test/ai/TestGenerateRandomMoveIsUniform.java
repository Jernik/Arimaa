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

	@Test
	public void testNormalGeneratedMoveIsUniform() {
		this.normalAi.generateMove();
		randomStressTest(() -> this.normalAi.generateMove());
	}

	@Test
	public void testKatLoverGeneratedMoveIsUniform() {
		randomStressTest(() -> this.catLoverAi.generateMove());
	}

	@Test
	public void testNotManyMovesGeneratedMoveIsUniform() {
		randomStressTest(() -> this.notManyMovesAi.generateMove());
	}

	@Test
	public void testStartingGeneratedMoveIsUniform() {
		randomStressTest(() -> this.startingAi.generateMove());
	}
}
