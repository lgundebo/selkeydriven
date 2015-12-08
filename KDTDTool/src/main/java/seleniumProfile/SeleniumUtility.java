/**
 *
 */
package seleniumProfile;

import DRC.AutomationFramework.WebDriver.DriverEnumerators.Browser;
import DRC.AutomationFramework.WebDriver.DriverEnumerators.Platform;

/**
 * @author cjha
 *
 */
public class SeleniumUtility
{

	/**
	 * @param version
	 * @return
	 */
	public static String getPlatform(String version) {

		String os = version.split("::")[0];

		if (os.contains("WIN7"))
		{
			return Platform.WINDOWS7.toString();
		}

		else if (os.contains("VISTA"))
		{
			return Platform.WINDOWSXP.toString();
		} else if (os.contains("WIN8.1"))
		{
			return Platform.WINDOWS8_1.toString();
		} else if (os.contains("WIN8"))
		{
			return Platform.WINDOWS8.toString();
		} else if (os.contains("WIN"))
		{
			return Platform.WINDOWS.toString();
		}

		else if (os.contains("LI"))
		{
			return Platform.LINUX.toString();
		} else if (os.contains("MAC"))
		{
			return Platform.MAC.toString();
		}
		return null;

	}

	public static String getBrowserName(String version) {
		String browser = version.split("::")[1];

		if (browser.contains("Chm"))
		{
			return Browser.CHROMIUM.toString();

		}
		if (browser.contains("Chr"))
		{
			return Browser.CHROME.toString();

		} else if (browser.contains("IE"))
		{
			return Browser.IE.toString();

		} else if (browser.contains("FF"))
		{
			return Browser.FIREFOX.toString();
		} else if (browser.contains("Saf"))
		{
			return Browser.SAFARI.toString();
		}
		return null;

	}

}
