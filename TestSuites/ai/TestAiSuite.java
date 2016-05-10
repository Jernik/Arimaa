package ai;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ TestAi.class, TestGenerateRandomMoveIsUniform.class, TestGenerateRandomMoveIsValid.class,
		TestGenerateRandomMoveOnlyIfEnoughMoves.class, TestGenerateRandomMovePerformance.class })
public class TestAiSuite {

}
