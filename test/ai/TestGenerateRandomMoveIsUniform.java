package ai;

import org.junit.Test;

public class TestGenerateRandomMoveIsUniform extends AiSetup {
	@Test
	public void testNormalGeneratedMoveIsUniform() {
		randomStressTest(1 / 24.0, () -> this.normalAi.generateMove());
	}

	@Test
	public void testKatLoverGeneratedMoveIsUniform() {
		randomStressTest(1 / 24.0, () -> this.catLoverAi.generateMove());
	}

	@Test
	public void testNotManyMovesGeneratedMoveIsUniform() {
		randomStressTest(1 / 24.0, () -> this.notManyMovesAi.generateMove());
	}

	@Test
	public void testStartingGeneratedMoveIsUniform() {
		randomStressTest(1 / 24.0, () -> this.startingAi.generateMove());
	}
}
