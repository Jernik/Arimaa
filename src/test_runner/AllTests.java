package test_runner;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import ai.TestAi;
import ai.TestAiSuite;
import ai.TestGenerateRandomMoveIsUniform;
import ai.TestGenerateRandomMoveIsValid;
import ai.TestGenerateRandomMoveOnlyIfEnoughMoves;
import ai.TestGenerateRandomMovePerformance;
import board.TestBoardState;
import board.TestBoardSuite;
import board.TestCoordinate;
import game.TestEndGame;
import game.TestGame;
import game.TestGameEquality;
import game.TestGameHashCodeEquality;
import game.TestGameSuite;
import game.TestUndo;
import gui.TestGuiSuite;
import gui.TestLoad;
import gui.TestSave;
import gui.TestTimePanel;
import move_commands.TestMoveCommandEquality;
import move_commands.TestMoveCommandSuite;
import move_commands.TestPullInvalid;
import move_commands.TestPullValid;
import move_commands.TestPushInvalid;
import move_commands.TestPushValid;
import move_commands.TestRegularInvalid;
import move_commands.TestRegularValid;
import piece.TestPiece;
import piece.TestPieceEquality;
import piece.TestPieceSuite;

@RunWith(Suite.class)
@Suite.SuiteClasses({ TestAiSuite.class, TestBoardSuite.class, TestGameSuite.class, TestGuiSuite.class,
		TestMoveCommandSuite.class, TestPieceSuite.class})
public class AllTests {
	public static void main(String args[]) {
		org.junit.runner.JUnitCore.main("test_runner.AllTests");
	}
}
