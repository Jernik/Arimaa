package game;

import org.junit.runner.RunWith;
import org.junit.runners.model.InitializationError;

import test_runner.DynamicSuite;

@RunWith(TestGameSuite.class)
public class TestGameSuite extends DynamicSuite {
	public TestGameSuite(Class<?> clazz) throws InitializationError {
		super(clazz, "game");
	}
}
