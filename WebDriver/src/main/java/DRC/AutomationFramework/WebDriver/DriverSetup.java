package DRC.AutomationFramework.WebDriver;

import DRC.AutomationFramework.WebDriver.DriverEnumerators.Browser;
import DRC.AutomationFramework.WebDriver.DriverEnumerators.BrowserTargetBits;
import DRC.AutomationFramework.WebDriver.DriverEnumerators.Platform;

public class DriverSetup
{

	public DriverSetup()
	{
	}

	public DriverSetup(String targetPlatform, String targetBrowser,
			String targetVersion, String browserProfile, String targetHubURL,
			String TargetBits)
	{
		platform = Platform.valueOf(targetPlatform.toUpperCase());
		targetBits = BrowserTargetBits.valueOf(TargetBits.toUpperCase());
		browser = Browser.valueOf(targetBrowser.toUpperCase());
		hubURL = targetHubURL;
		version = targetVersion;
		profile = browserProfile;

	}

	public Platform				platform;
	public BrowserTargetBits	targetBits;
	public Browser				browser;
	// public HubURL hubURL;
	public String				hubURL;
	// public Version version;
	public String				version;
	public String				profile;
}
