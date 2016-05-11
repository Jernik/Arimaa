package board;

import org.junit.runner.RunWith;
import org.junit.runners.model.InitializationError;

import test_runner.DynamicSuite;

@RunWith(TestBoardSuite.class)
public class TestBoardSuite extends DynamicSuite{
	public TestBoardSuite(Class<?> clazz) throws InitializationError {
		super(clazz, "board");
	}
}
