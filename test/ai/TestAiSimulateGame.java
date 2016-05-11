package ai;

import org.junit.BeforeClass;
import org.junit.Test;

import game.Game;
import piece.Owner;

public class TestAiSimulateGame extends AiSetup {
	@BeforeClass
	public static void setupStressSettings() {
		RANDOM_MARGIN = 0.05;
		ITERATION_SIZE = 500;
	}

	@Test
	public void testSimulateGamesWithNoErrors() {
		randomStressTest(() -> {
			Game game = new Game();
			Ai ai1 = new Ai(Owner.Player1, game);
			Ai ai2 = new Ai(Owner.Player2, game);
			while (game.getWinner() == Owner.Nobody) {
				while (game.getPlayerTurn() == Owner.Player1) {
					if (game.getWinner() != Owner.Nobody) {
						break;
					}
					game.move(ai1.generateMove());
				}

				while (game.getPlayerTurn() == Owner.Player2) {
					if (game.getWinner() != Owner.Nobody) {
						break;
					}
					game.move(ai2.generateMove());
				}
			}
			return game.getWinner();
		});
	}
}
