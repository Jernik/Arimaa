package test_runner;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import ai.TestAi;
import ai.TestGenerateRandomMoveIsUniform;
import ai.TestGenerateRandomMoveIsValid;
import ai.TestGenerateRandomMoveOnlyIfEnoughMoves;
import ai.TestGenerateRandomMovePerformance;
import board.TestBoardState;
import board.TestCoordinate;
import game.TestEndGame;
import game.TestGame;
import game.TestGameEquality;
import game.TestGameHashCodeEquality;
import game.TestUndo;
import gui.TestLoad;
import gui.TestSave;
import gui.TestTimePanel;
import move_commands.TestMoveCommandEquality;
import move_commands.TestPullInvalid;
import move_commands.TestPullValid;
import move_commands.TestPushInvalid;
import move_commands.TestPushValid;
import move_commands.TestRegularInvalid;
import move_commands.TestRegularValid;
import piece.TestPiece;
import piece.TestPieceEquality;

@RunWith(Suite.class)
@Suite.SuiteClasses({ TestAi.class, TestGenerateRandomMoveIsUniform.class, TestGenerateRandomMoveIsValid.class,
		TestGenerateRandomMoveOnlyIfEnoughMoves.class, TestGenerateRandomMovePerformance.class, TestBoardState.class,
		TestCoordinate.class, TestEndGame.class, TestGame.class, TestGameEquality.class, TestGameHashCodeEquality.class,
		TestUndo.class, TestLoad.class, TestSave.class, TestTimePanel.class, TestMoveCommandEquality.class,
		TestPullInvalid.class, TestPullValid.class, TestPushValid.class, TestPushInvalid.class,
		TestRegularInvalid.class, TestRegularValid.class, TestPiece.class, TestPieceEquality.class })
public class AllTests {
	public static void main(String args[]) {
		org.junit.runner.JUnitCore.main("test_runner.AllTests");
	}
}
