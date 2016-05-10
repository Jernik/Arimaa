package test_runner;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import ai.AiSetup;
import ai.TestAi;
import ai.TestGenerateRandomMoveIsUniform;
import ai.TestGenerateRandomMoveIsValid;
import ai.TestGenerateRandomMoveOnlyIfEnoughMoves;
import ai.TestGenerateRandomMovePerformance;
import game.TestBoardState;
import game.TestCoordinate;
import game.TestEndGame;
import game.TestGame;
import game.TestLoad;
import game.TestSave;
import game.TestTimePanel;
import game.TestUndo;
import move_commands.TestPullInvalid;
import move_commands.TestPullValid;
import move_commands.TestPushInvalid;
import move_commands.TestPushValid;
import move_commands.TestRegularInvalid;
import move_commands.TestRegularValid;
import piece.TestPiece;

@RunWith(Suite.class)
@Suite.SuiteClasses({ AiSetup.class, TestAi.class, TestGenerateRandomMoveIsUniform.class,
		TestGenerateRandomMoveIsValid.class, TestGenerateRandomMovePerformance.class,
		TestGenerateRandomMoveOnlyIfEnoughMoves.class, TestBoardState.class, TestBoardState.class, TestCoordinate.class,
		TestEndGame.class, TestGame.class, TestSave.class, TestLoad.class, TestTimePanel.class, TestUndo.class,
		TestRegularValid.class, TestRegularInvalid.class, TestPullInvalid.class, TestPullValid.class,
		TestPushValid.class, TestPushInvalid.class, TestPiece.class })
public class AllTests {
	public static void main(String args[]) {
		org.junit.runner.JUnitCore.main("test_runner.AllTests");
	}
}
