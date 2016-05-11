package test_runner;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import ai.TestAiSuite;
import board.TestBoardSuite;
import game.TestGameSuite;
import gui.TestGuiSuite;
import move_commands.TestMoveCommandSuite;
import piece.TestPieceSuite;

@RunWith(Suite.class)
@Suite.SuiteClasses({ TestAiSuite.class, TestBoardSuite.class, TestGameSuite.class, TestGuiSuite.class,
		TestMoveCommandSuite.class, TestPieceSuite.class})
public class AllTests {
	public static void main(String args[]) {
		org.junit.runner.JUnitCore.main("test_runner.AllTests");
	}
}
