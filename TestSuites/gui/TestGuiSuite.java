package gui;

import org.junit.runner.RunWith;
import org.junit.runners.model.InitializationError;

import test_runner.DynamicSuite;

@RunWith(TestGuiSuite.class)
public class TestGuiSuite extends DynamicSuite {
	public TestGuiSuite(Class<?> clazz) throws InitializationError {
		super(clazz, "gui");
	}
}
