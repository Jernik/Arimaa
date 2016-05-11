package piece;

import org.junit.runner.RunWith;
import org.junit.runners.model.InitializationError;

import test_runner.DynamicSuite;

@RunWith(TestPieceSuite.class)
public class TestPieceSuite extends DynamicSuite{
	public TestPieceSuite(Class<?> clazz) throws InitializationError {
		super(clazz, "piece");
	}
}
