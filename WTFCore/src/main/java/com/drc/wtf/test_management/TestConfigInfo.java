package com.drc.wtf.test_management;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import DRC.AutomationFramework.WebDriver.DriverSetup;

public class TestConfigInfo {
	public String testResultsFolderPath;
	public String testLogFile;
	public String screenShotDirectroy;
	public String configurationFile;
	public String testCaseRepository;
	public String browserGeneral;
	public String url;
	public String fileLocationFileUpload;
	public static String runFolder = setBaseLogFolder();

	public TestConfigInfo(DriverSetup setup) {
		Properties configFile = initalize();


		
		configurationFile = configFile.getProperty("configurationExcelFile");
		testCaseRepository = configFile.getProperty("testCaseRepository");

		screenShotDirectroy = null;
		testLogFile = null;

		String pathTail = runFolder + "\\"+setup.platform.getValue() + "\\"
				+ setup.browser.getValue()+ "\\";
		testResultsFolderPath = configFile.getProperty("testResultsFolderPath")
				+ pathTail;

		screenShotDirectroy = null;
		testLogFile = null;
		
		fileLocationFileUpload = configFile.getProperty("fileLocationForFileUpload");

	}

	public TestConfigInfo(DriverSetup setup, TestCase testCase) {

		Properties configFile = initalize();

		configurationFile = configFile.getProperty("configurationExcelFile");
		testCaseRepository = configFile.getProperty("testCaseRepository");

		String pathTail = runFolder + "\\"+ setup.platform.getValue() + "\\"
				+ setup.browser.getValue() + "\\" + testCase.testCaseName
				+ "\\";

		testResultsFolderPath = configFile.getProperty("testResultsFolderPath")
				+ pathTail;

		screenShotDirectroy = testResultsFolderPath + "ScreenShot\\";
		testLogFile = testResultsFolderPath + generateLogFileName(testCase);

		fileLocationFileUpload = configFile.getProperty("fileLocationForFileUpload");
		
		ReadConfiguration(configurationFile);
		
	}

	private Properties initalize() {
		Properties configFile = new Properties();
		try {
			configFile.load(TestConfigInfo.class.getClassLoader()
					.getResourceAsStream("config.properties"));

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return configFile;
	}

	private String generateLogFileName(TestCase testCase) {
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
		Date date = new Date();
		String fileName = testCase.testCaseName + dateFormat.format(date)
				+ ".txt";

		return fileName;
	}

	private static String setBaseLogFolder()
	{
		String baseFolder = System.getProperty("user.name").toString();
		
		DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy_HHmmss");
		Date date = new Date();
		baseFolder += "-"+dateFormat.format(date);
		
		return baseFolder;
	}
	
	//Refactor this
	private void ReadConfiguration(String inputFile) {

		File inputWorkbook = new File(inputFile);
		Workbook w;
		try {
			w = Workbook.getWorkbook(inputWorkbook);
			// Get the first sheet
			Sheet sheet = w.getSheet(0);

			Cell cell = sheet.getCell(1, 1);
			browserGeneral = cell.getContents().toString().trim();
			cell = sheet.getCell(1, 2);
			url = cell.getContents().toString().trim();
		} catch (Exception e) {
			e.printStackTrace();
			System.out
					.println("Error Reading Configuration file: " + inputFile);
		}
	}
	
}
