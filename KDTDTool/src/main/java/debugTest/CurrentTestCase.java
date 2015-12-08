/**
 *
 */
package debugTest;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import mainWindow.ProjectDetails;
import DRC.AutomationFramework.Excel.WorkBook;
import DRC.AutomationFramework.TestManagement.Action;
import DRC.AutomationFramework.TestManagement.TestCase;
import DRC.AutomationFramework.WebDriver.DriverEnumerators.Browser;
import DRC.AutomationFramework.WebDriver.DriverEnumerators.BrowserTargetBits;
import DRC.AutomationFramework.WebDriver.DriverEnumerators.HubURL;
import DRC.AutomationFramework.WebDriver.DriverEnumerators.Platform;
import DRC.AutomationFramework.WebDriver.DriverEnumerators.Version;
import DRC.AutomationFramework.WebDriver.DriverSetup;

/**
 * @author cjha
 *
 */
public class CurrentTestCase
{
	private WorkBook	testCaseWB;
	private TestCase	currTest;
	DriverSetup			setup;
	Set					debugSteps	= new HashSet();

	public CurrentTestCase(String suitename, String testname)
	{
		List<String> l = new ArrayList<String>();
		l.add(suitename);
		l.add(testname);
		l.add("");
		setup = getDriverObject();
		currTest = new TestCase(l, 0, ProjectDetails.getInstance()
				.getObjectRepository(), setup);

	}

	/**
	 * @return
	 * @return
	 */
	public List<Action> getTestSteps() {
		// TODO Auto-generated method stub
		return currTest.testSteps;
	}

	private static DriverSetup getDriverObject() {
		DriverSetup a = new DriverSetup();
		a.browser = Browser.CHROME;
		// a.hubURL = HubURL.LOCALHOST
		a.hubURL = HubURL.LOCALHOST.toString();
		a.platform = Platform.WINDOWS7;
		// a.version = Version.NOTSPECIFIED
		a.targetBits = BrowserTargetBits.BIT_32;

		a.version = Version.NOTSPECIFIED.toString();

		return a;
	}

	public TestCase getTest() {
		return this.currTest;
	}

	public void addDebugStep(int step) {
		debugSteps.add(step);
	}

	public void removeDebugStep(int step) {
		debugSteps.remove(step);
	}

	public Set getDebugSteps() {
		return debugSteps;
	}
}
