package ai;

import org.junit.runner.RunWith;
import org.junit.runners.model.InitializationError;

import test_runner.DynamicSuite;

@RunWith(TestAiSuite.class)
public class TestAiSuite extends DynamicSuite {
	public TestAiSuite(Class<?> clazz) throws InitializationError {
		super(clazz, "ai");
	}
}
