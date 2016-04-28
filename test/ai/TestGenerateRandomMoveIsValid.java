package ai;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import move_commands.MoveCommand;

public class TestGenerateRandomMoveIsValid extends AiSetup {
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
