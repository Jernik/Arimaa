package ai;

import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import move_commands.MoveCommand;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestGenerateRandomMoveIsValid extends AiSetup {
	@BeforeClass
	public static void setupStressSettings() {
		ITERATION_SIZE = 10_000;
	}

	private void testGeneratedMovesAreValid(Ai ai) {
		for (int i = 0; i < ITERATION_SIZE; i++) {
			MoveCommand move = ai.generateMove();
			assertTrue(move.toString(), move.isValidMove());
		}
	}

	@Test
	public void testNormalEveryGeneratedMoveIsValid() {
		testGeneratedMovesAreValid(this.normalAi);
	}

	@Test
	public void testKatLoverEveryGeneratedMoveIsValid() {
		testGeneratedMovesAreValid(this.catLoverAi);
	}

	@Test
	public void testNotManyMovesEveryGeneratedMoveIsValid() {
		testGeneratedMovesAreValid(this.notManyMovesAi);
	}

	@Test
	public void testStartingEveryGeneratedMoveIsValid() {
		testGeneratedMovesAreValid(this.startingAi);
	}
}
