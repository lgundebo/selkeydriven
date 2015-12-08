package DRC.AutomationFramework.WebDriver;

import java.net.MalformedURLException;
import java.net.URL;

public class DriverEnumerators
{

	public enum Platform
	{
		LINUX("LINUX"), WINDOWS("WINDOWS"), MAC("MAC"), LOCAL("LOCAL"), WINDOWSXP(
				"WINDOWSXP"), WINDOWS7("VISTA"), WINDOWS8("WIN8"), WINDOWS8_1(
				"WIN8_1");

		private String	value;

		private Platform(String value)
		{
			this.value = value;
		}

		public String getValue() {
			return this.value;

		}

	}

	// Only used for local execution
	public enum BrowserTargetBits
	{
		BIT_32("32BIT"), BIT_64("64BIT"), NOTAPPLICABLE("NA");

		private String	value;

		private BrowserTargetBits(String value)
		{
			this.value = value;
		}

		public String getValue() {
			return this.value;

		}

	}

	public enum Browser
	{
		CHROME("chrome"), FIREFOX("firefox"), IE("internet explorer"), SAFARI(
				"safari");

		private String	value;

		private Browser(String value)
		{

			this.value = value;

		}

		public String getValue() {
			return this.value;

		}

	}

	public enum HubURL
	{
		TESTCONTROL02("http://testcontrol02:4444/wd/hub"), LOCALHOST(
				"http://localhost:4444/wd/hub"), MGWS10423(
				"http://mgws10423:4444/wd/hub"), MGLT11740(
				"http://mglt11740:4444/wd/hub"), WINHUB01(
				"http://WinHub01:4444/wd/hub"), LINUXHUB01(
				"http://LinuxHub01:4444/wd/hub"), MACHUB01(
				"http://MacHub01:4444/wd/hub"), NONE(null);

		private URL	value;

		private HubURL(String value)
		{
			try
			{
				this.value = new URL(value);
			} catch (MalformedURLException e)
			{
				if (this.value != null)
				{
					e.printStackTrace();
				}
			}
		}

		public URL getValue() {
			return this.value;

		}

	}

	// Typically used to target a specific version of a browser
	// WBTE uses this to target a specific version of an OS
	public enum Version
	{
		// Style used for granualar control for WBTE
		MAC_10_6_32_BIT("10.6-32-bit"), LINUX_12_04_32_BIT("12.04-32-bit"), WINDOWS_2003_32_BIT(
				"2003-32-bit"), MAC_10_9_64_BIT("10.9-64-bit"), MAC_10_8_64_BIT(
				"10.8-64-bit"), LATEST("Latest"),
		//
		NOTSPECIFIED("NotSpecified");

		private String	value;

		private Version(String value)
		{
			this.value = value;
		}

		public String getValue() {
			return this.value;

		}

	}

}
