package move_commands;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ TestMoveCommandEquality.class, TestPullInvalid.class, TestPullValid.class, TestPushInvalid.class,
		TestPushValid.class, TestRegularInvalid.class, TestRegularValid.class })
public class TestMoveCommandSuite {

}
