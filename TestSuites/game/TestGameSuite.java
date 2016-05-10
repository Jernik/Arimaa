package game;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ TestEndGame.class, TestGame.class, TestGameEquality.class, TestGameHashCodeEquality.class,
		TestHasPushMove.class, TestHasRegularMove.class, TestUndo.class })
public class TestGameSuite {

}
