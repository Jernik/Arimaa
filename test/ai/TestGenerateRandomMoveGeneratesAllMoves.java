package ai;

import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestGenerateRandomMoveGeneratesAllMoves extends AiSetup {
	@BeforeClass
	public static void setupStressSettings() {
		RANDOM_MARGIN = 1;
		ITERATION_SIZE = 1_000;
	}

	// sample sizes may be wrong
	@Test
	public void testNormalGeneratedMoveGeneratesAllMoves() {
		randomStressTest(24, () -> this.normalAi.generateMove());
	}

	@Test
	public void testKatLoverGeneratedMoveGeneratesAllMoves() {
		randomStressTest(28, () -> this.catLoverAi.generateMove());
	}

	@Test
	public void testNotManyMovesGeneratedMoveGeneratesAllMoves() {
		randomStressTest(24, () -> this.notManyMovesAi.generateMove());
	}

	@Test
	public void testStartingGeneratedMoveGeneratesAllMoves() {
		randomStressTest(8, () -> this.startingAi.generateMove());
	}

}
