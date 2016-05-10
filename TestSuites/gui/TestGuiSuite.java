package gui;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ TestLoad.class, TestSave.class, TestTimePanel.class })
public class TestGuiSuite {

}
