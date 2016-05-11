package move_commands;

import org.junit.runner.RunWith;
import org.junit.runners.model.InitializationError;

import test_runner.DynamicSuite;

@RunWith(TestMoveCommandSuite.class)
public class TestMoveCommandSuite extends DynamicSuite{
	public TestMoveCommandSuite(Class<?> clazz) throws InitializationError {
		super(clazz, "move_commands");
	}
}
