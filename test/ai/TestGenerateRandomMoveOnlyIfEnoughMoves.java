package ai;

import static org.junit.Assert.assertFalse;

import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import board.Coordinate;
import game.Game;
import move_commands.MoveCommand;
import move_commands.PullMove;
import move_commands.PushMove;
import move_commands.RegularMove;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestGenerateRandomMoveOnlyIfEnoughMoves extends AiSetup {
	@BeforeClass
	public static void setupStressSettings() {
		ITERATION_SIZE = 10_000;
	}

	private void testOnlyRegularMoves(Ai ai) {
		for (int i = 0; i < ITERATION_SIZE; i++) {
			MoveCommand move = ai.generateMove();
			assertFalse("Generated a " + move.getClass().getSimpleName() + " move: " + move,
					(move instanceof PushMove) || (move instanceof PullMove));
		}
	}

	@Test
	public void testNormalNoPushOrPullIf1MoveLeft() {
		Game game = this.normalAi.getGame();
		game.move(new RegularMove(game, new Coordinate(1, 1), new Coordinate(1, 2), game.getPlayerTurn()));
		game.move(new RegularMove(game, new Coordinate(1, 2), new Coordinate(1, 1), game.getPlayerTurn()));
		game.move(new RegularMove(game, new Coordinate(1, 1), new Coordinate(1, 2), game.getPlayerTurn()));
		testOnlyRegularMoves(this.normalAi);
	}

	@Test
	public void testKatLoverNoPushOrPullIf1MoveLeft() {
		Game game = this.catLoverAi.getGame();
		game.move(new RegularMove(game, new Coordinate(0, 1), new Coordinate(0, 0), game.getPlayerTurn()));
		game.move(new RegularMove(game, new Coordinate(0, 0), new Coordinate(0, 1), game.getPlayerTurn()));
		game.move(new RegularMove(game, new Coordinate(0, 1), new Coordinate(0, 0), game.getPlayerTurn()));
		testOnlyRegularMoves(this.catLoverAi);
	}

	@Test
	public void testNotManyMovesNoPushOrPullIf1MoveLeft() {
		Game game = this.notManyMovesAi.getGame();
		game.move(new RegularMove(game, new Coordinate(1, 1), new Coordinate(1, 2), game.getPlayerTurn()));
		game.getBoardState().movePiece(new Coordinate(1, 2), new Coordinate(1, 1));
		game.move(new RegularMove(game, new Coordinate(1, 1), new Coordinate(1, 2), game.getPlayerTurn()));
		game.getBoardState().movePiece(new Coordinate(1, 2), new Coordinate(1, 1));
		game.move(new RegularMove(game, new Coordinate(1, 1), new Coordinate(1, 2), game.getPlayerTurn()));
		game.getBoardState().movePiece(new Coordinate(1, 2), new Coordinate(1, 1));
		testOnlyRegularMoves(this.notManyMovesAi);
	}

	@Test
	public void testStartingNoPushOrPullIf1MoveLeft() {
		Game game = this.startingAi.getGame();
		game.move(new RegularMove(game, new Coordinate(1, 1), new Coordinate(1, 2), game.getPlayerTurn()));
		game.move(new RegularMove(game, new Coordinate(1, 2), new Coordinate(1, 3), game.getPlayerTurn()));
		game.move(new RegularMove(game, new Coordinate(1, 3), new Coordinate(1, 4), game.getPlayerTurn()));
		game.getBoardState().movePiece(new Coordinate(1, 4), new Coordinate(1, 1));
		testOnlyRegularMoves(this.startingAi);
	}
}
