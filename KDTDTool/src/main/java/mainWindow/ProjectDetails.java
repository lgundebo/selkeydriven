/**
 *
 */
package mainWindow;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import DRC.AutomationFramework.Excel.WorkBook;
import DRC.AutomationFramework.FileUtilities.FileUtilities;
import DRC.AutomationFramework.ObjectRepository.ObjectRepository;

/**
 * @author cjha
 *
 */
public class ProjectDetails
{

	private String						fileLocationFileUpload;
	private String						configurationFile;
	private String						testCaseRepository;
	private String						path;
	private ObjectRepository			repository;
	private WorkBook					workBook;
	HashMap<String, HashSet<String>>	testSuiteCaseMap	= new HashMap<String, HashSet<String>>();
	private Properties					projectConfig;
	private static ProjectDetails		projDetails			= null;										;

	private ProjectDetails(String projName)
	{
		projectConfig = initalize(projName);

		configurationFile = projectConfig.getProperty("configurationExcelFile");
		testCaseRepository = projectConfig.getProperty("testCaseRepository");

		fileLocationFileUpload = projectConfig
				.getProperty("fileLocationForFileUpload");
		String workingFile = FileUtilities.copyFile(configurationFile, path);
		// FileUtilities.copyFile(testCaseRepository, path);
		repository = new ObjectRepository(workingFile, "ObjectReporsitory");
		workBook = new WorkBook(workingFile);
		CreateSuiteCaseMap();

	}

	public static ProjectDetails createInstance(String projName) {
		if (projDetails == null)
		{
			projDetails = new ProjectDetails(projName);
			return projDetails;
		} else
		{
			return projDetails;
		}
	}

	public static ProjectDetails getInstance() {
		return projDetails;

	}

	/**
	 * @return
	 */
	private Properties initalize(String projName) {

		Properties configFile = new Properties();
		path = NewProjectDlg.class.getProtectionDomain().getCodeSource()
				.getLocation().getPath();
		path = path.substring(1);
		path = path + "Projects/" + projName + "/";

		try
		{
			File f = new File(path + "/config.properties");
			FileInputStream is = new FileInputStream(f);

			configFile.load(is);

		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return configFile;
	}

	public void CreateSuiteCaseMap() {
		if (!workBook.SetActiveSheet("TestExecutionSheet"))
		{
			return;// Throw error
		}

		// Loop while there are still test case rows to iterate and the current
		// test should not be
		// run
		while (workBook.activeSheet.moveToNextRow())
		{

			List<String> testCaseRow = workBook.activeSheet.getCurrentRow();

			if (testCaseRow.size() > 3)
			{

				String testCaseName = testCaseRow.get(1).trim();

				String testSuiteName = testCaseRow.get(0).trim();

				if (testSuiteCaseMap.containsKey(testSuiteName))
				{
					HashSet<String> testSet = testSuiteCaseMap
							.get(testSuiteName);
					testSet.add(testCaseName);
					testSuiteCaseMap.put(testSuiteName, testSet);
				} else
				{
					HashSet<String> testSet = new HashSet();
					testSet.add(testCaseName);
					testSuiteCaseMap.put(testSuiteName, testSet);
				}

			}
			//
			// if (testCase != null && testCase.runTest)
			// {
			//
			// break;
			// }

		}

		// If there are no more test cases to run return null

	}

	public Set<String> getTestSuite() {
		return testSuiteCaseMap.keySet();
	}

	public Set<String> getTestSet(String suiteName) {
		return testSuiteCaseMap.get(suiteName);
	}

	public Properties getProjectConfig() {
		return this.projectConfig;
	}

	public ObjectRepository getObjectRepository() {
		return this.repository;
	}

}
