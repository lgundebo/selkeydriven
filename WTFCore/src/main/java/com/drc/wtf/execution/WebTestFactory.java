package com.drc.wtf.execution;

import java.util.List;

import org.testng.annotations.Factory;

import DRC.AutomationFramework.Excel.WorkBook;
import DRC.AutomationFramework.WebDriver.DriverEnumerators.Browser;
import DRC.AutomationFramework.WebDriver.DriverEnumerators.BrowserTargetBits;
import DRC.AutomationFramework.WebDriver.DriverEnumerators.HubURL;
import DRC.AutomationFramework.WebDriver.DriverEnumerators.Platform;
import DRC.AutomationFramework.WebDriver.DriverEnumerators.Version;
import DRC.AutomationFramework.WebDriver.DriverSetup;

public class WebTestFactory
{

	@Factory
	public Object[] createInstances() throws Exception {
		Object[] result = new Object[0];
		WorkBook configWorkBook = null;
		try
		{

			String filePath = "C:\\DRCWebTestingFramework\\IAT\\TestConfiguration\\IATAutomationConfiguration.xls";
			String filePath2 = "C:\\DRCWebTestingFramework\\IAT\\TestConfiguration\\IATAutomationConfiguration2.xls";

			configWorkBook = new WorkBook(filePath, filePath2);

			configWorkBook.SetActiveSheet("OSConfig");

			// configWorkBook.activeSheet.moveToNextRow();
			List<String> row;// = configWorkBook.activeSheet.getCurrentRow();

			int count = 0;
			while (configWorkBook.activeSheet.moveToNextRow()
					&& configWorkBook.activeSheet.getCurrentRow().size() > 0)
			{
				System.out.println(configWorkBook.activeSheet.getRowIndex());
				row = configWorkBook.activeSheet.getCurrentRow();
				if (!row.get(1).equals("NONE"))
				{

					DriverSetup a = new DriverSetup();
					a.browser = Browser.valueOf(row.get(2).toUpperCase());
					a.hubURL = HubURL.LOCALHOST.toString();
					a.platform = Platform.valueOf(row.get(1).toUpperCase());
					a.targetBits = BrowserTargetBits.BIT_32;
					a.version = Version.NOTSPECIFIED.toString();

					result = ResizeArray(result);
					result[count] = new TestCaseExecution(a);
					count++;
				}

			}

		} finally
		{
			configWorkBook.close();
		}
		return result;
	}

	public Object[] ResizeArray(Object[] source) {
		int size = source.length;
		Object[] destination = new Object[size + 1];
		System.arraycopy(source, 0, destination, 0, size);

		return destination;
	}

}
