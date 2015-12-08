/*
 *
 */
package com.drc.wtf.test_management;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.io.FileUtils;
import org.testng.TestListenerAdapter;
import org.testng.TestNG;
import org.testng.collections.Lists;
import org.testng.xml.Parser;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlDefine;
import org.testng.xml.XmlDependencies;
import org.testng.xml.XmlGroups;
import org.testng.xml.XmlInclude;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;
import org.xml.sax.SAXException;

import DRC.AutomationFramework.WebDriver.DriverEnumerators.Browser;
import DRC.AutomationFramework.WebDriver.DriverEnumerators.BrowserTargetBits;
import DRC.AutomationFramework.WebDriver.DriverEnumerators.HubURL;
import DRC.AutomationFramework.WebDriver.DriverEnumerators.Platform;
import DRC.AutomationFramework.WebDriver.DriverEnumerators.Version;
import DRC.AutomationFramework.WebDriver.DriverSetup;

import com.sun.codemodel.JBlock;
import com.sun.codemodel.JClass;
import com.sun.codemodel.JClassAlreadyExistsException;
import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JDefinedClass;
import com.sun.codemodel.JExpr;
import com.sun.codemodel.JFieldVar;
import com.sun.codemodel.JMethod;
import com.sun.codemodel.JMod;
import com.sun.codemodel.JTryBlock;

// TODO: Auto-generated Javadoc
/*
 * This class builds the testsuite from the XL based configuration file.
 */

/**
 * The Class BuildTestSuite.
 */
public class BuildTestSuite
{
	// Single public instance of suite and testlist
	/** The test suite. */
	public static TestSuite				testSuite	= null;

	/** The test case list. */
	public static ArrayList<TestCase>	testCaseListAll;
	public static ArrayList<TestCase>	testCaseList;
	public static ArrayList<TestCase>	testCaseListSingleThread;
	private static Properties			testConfigFile;

	private static Properties			suiteConfigFile;

	private static String 				testSuiteNameParallel;
	private static String 				testSuiteNameSingle;
	
	/**
	 * The main method.
	 *
	 * @param arg
	 *            the arguments
	 */
	public static void main(String[] arg) {
		// Values from config.properties
		testConfigFile = new Properties();
		suiteConfigFile = new Properties();
		// System.out.print(System.getProperties().toString());

		try
		{
			testConfigFile.load(BuildTestSuite.class.getClassLoader()
					.getResourceAsStream("config.properties"));
			suiteConfigFile.load(BuildTestSuite.class.getClassLoader()
					.getResourceAsStream("suite.properties"));
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		Properties configFile = new Properties();
		try
		{
			configFile.load(BuildTestSuite.class.getClassLoader()
					.getResourceAsStream("suite.properties"));

		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		testSuite = new TestSuite("TestExecutionSheet", "ObjectReporsitory",
				getDriverObject());

		testCaseListAll = new ArrayList<TestCase>();
		testCaseList = new ArrayList<TestCase>();
		testCaseListSingleThread = new ArrayList<TestCase>();
		TestCase testCase;

		Map<String, ArrayList<String>> testSuiteMapAll = new HashMap();
		Map<String, ArrayList<String>> testSuiteMap = new HashMap();
		Map<String, ArrayList<String>> testSuiteMapSingleThread = new HashMap();
		if (arg.length == 0)
		{
			while ((testCase = testSuite.GetNextTestCaseToRun()) != null)
			{
				System.out.print(testCase.testCaseName);
				
				testCaseListAll.add(testCase);
				
				if(testCase.runParallel)
				{
				testCaseList.add(testCase);
				}
				else
				{
					testCaseListSingleThread.add(testCase);
				}
			}
			testSuiteMapAll = createTestSuite(testCaseListAll);
			testSuiteMap = createTestSuite(testCaseList);
			testSuiteMapSingleThread = createTestSuite(testCaseListSingleThread);
		} else
		{


			testCaseListAll = testSuite.GetAllTestCase(false);
			// while ((testCase = testSuite.GetAllTestCase()) != null)
			// {
			// System.out.print(testCase.testCaseName);
			// testCaseList.add(testCase);
			// }
			List<TestCase> testListAll = new ArrayList();
			List<TestCase> testList = new ArrayList();
			List<TestCase> testListSingleThread = new ArrayList();
			List argTest = new ArrayList();
			//
			for (String str : arg[0].toString().split(","))
			{
				argTest.add(str);

			}
			Iterator<TestCase> itr = testCaseListAll.iterator();
			while (itr.hasNext())
			{
				TestCase tc = itr.next();
				String str = tc.testCaseName;

				if (argTest.contains(str))
				{
					tc.initalizeTestCase(testSuite.getObjectRepository(), getDriverObject()); 
					
					testListAll.add(tc);
					
					if(tc.runParallel)
					{
					testList.add(tc);
					}
					else 
					{
						testListSingleThread.add(tc);
					}
				}

			}

			//
			// testSuiteMap.put("TestSuite", testList);
			testSuiteMapAll = createTestSuite(testListAll);
			testSuiteMap = createTestSuite(testList);
			testSuiteMapSingleThread = createTestSuite(testListSingleThread);
		}
		
		//Create Java Classes for each test case
		createTestClass(testSuiteMapAll);
		//createTestClass(testSuiteMapSingleThread);
		
		
		Boolean distributeTests = suiteConfigFile.getProperty("DistributeTests").equals("true");
		Boolean parallelTests = suiteConfigFile.getProperty("Parallel").equals("true");
		String projectName = suiteConfigFile.getProperty("Project");
		
		
		testSuiteNameParallel = projectName + "-Parallel";
		testSuiteNameSingle = projectName + "-Single";
		
		
		//List<XmlSuite> suites = buildSuiteXml(testSuiteMap.keySet(), distributeTests);
		List<XmlSuite> suites = new ArrayList<XmlSuite>();
		
		//Set threadCount for top level at 250 so all OS run in parallel regardless of 
		suites.add(buildVersionSuiteXml(testSuiteMap, parallelTests, distributeTests));
		
		//these test will be run one at a time
		suites.add(buildVersionSuiteXml2(testSuiteMapSingleThread, distributeTests));
		
		
		try {
			String testXML = suites.get(0).toXml();
			PrintWriter out = new PrintWriter("./target/SuiteXML.XML");
			out.print(testXML);
			out.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	
		
		
		TestListenerAdapter tla = new TestListenerAdapter();
		
		TestNG tng = new TestNG();
		tng.setSuiteThreadPoolSize(1);
		tng.addListener(tla);

		tng.setOutputDirectory("./target/tempReport");
		tng.setXmlSuites(suites);
	
	    tng.run();

		System.out.print("Check for failed TestCases");
		
		
		//Move the files for publishing

		File source = new File("./target/tempReport");
		File destination = new File("./target/report");
		
		try {
			FileUtils.copyDirectory(source, destination);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		File emailReport = new File("./target/report/emailable-report.html");
		File initalEmailReport = new File("./target/report/emailable-report-InitialRun.html");
		try {
			FileUtils.copyFile(emailReport, initalEmailReport);
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		
		if (suiteConfigFile.getProperty("RunFailedTests").equals("true")
				&& tng.hasFailure())
		{
			
		
			//tng.setOutputDirectory("./target/report/ReTest");
			System.out.print("Failed testcases found");
			
			//tng.setXmlSuites(null);
			List<XmlSuite> suitesFailedParallel = new ArrayList<XmlSuite>();
			List<XmlSuite> suitesFailedSingle = new ArrayList<XmlSuite>();
			try {
				Parser failedSuiteParser = new Parser("./target/report/" + testSuiteNameParallel + "/testng-failed.xml");
				suitesFailedParallel =failedSuiteParser.parseToList();
			
				failedSuiteParser = new Parser("./target/report/" + testSuiteNameSingle + "/testng-failed.xml");
				suitesFailedSingle = failedSuiteParser.parseToList();
				
				suitesFailedParallel.add(suitesFailedSingle.get(0));
				
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParserConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
						
			tng.setXmlSuites(suitesFailedParallel);
				
			tng.run();

			File tempTestResults = new File("./target/tempReport/testng-results.xml");
			File finalTestResults = new File("./target/report/testng-results.xml");
			try {
				FileUtils.copyFile(tempTestResults, finalTestResults);
			} catch (IOException e) {
				
				e.printStackTrace();
			}
			
			File tempEmailReport = new File("./target/tempReport/emailable-report.html");
			File finalEmailReport = new File("./target/report/emailable-report.html");
			try {
				FileUtils.copyFile(tempEmailReport, finalEmailReport);
			} catch (IOException e) {
				
				e.printStackTrace();
			}
			
		}
		else
		{
			//Delete the pass only file so there are not duplicates if 
			//failed tests are not executed
			
			File passFile = new File("./target/report/testng-passed-tests.xml");
			FileUtils.deleteQuietly(passFile);
		}
		
	}

	// Creates the testclass with testmethods. Uses CodeModel API
	/**
	 * Creates the test class.
	 *
	 * @param testSuite
	 *            the test suite
	 */
	private static void createTestClass(Map<String, ArrayList<String>> testSuite) {
		Iterator itr = testSuite.keySet().iterator();
		
		while (itr.hasNext())
		{
			String className = itr.next().toString();
			ArrayList<String> method = testSuite.get(className);

			JCodeModel cm = new JCodeModel();
			JDefinedClass dc = null;



			try
			{
				dc = cm._class("Test." + className);
				dc._extends(BaseTestCase.class);

				dc.annotate(org.testng.annotations.Test.class);
			} catch (JClassAlreadyExistsException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			for (String meth : method)
			{
				
				/*
				 * Add a lock for the method
				 * this stops the same test case from executing at the same time
				 * while still allowing parallel execution through TestNG
				 * 
				 */
				
				
				JFieldVar lockFiled =dc.field( JMod.PRIVATE | JMod.FINAL | JMod.STATIC, ReentrantLock.class, meth.toLowerCase()+"Lock");
				lockFiled.init(JExpr.direct("new ReentrantLock()"));
				
				JMethod m = dc.method(0, void.class, meth);
				
				m.body().directStatement(meth.toLowerCase()+"Lock.lock();");
				
				JTryBlock tryBlock = m.body()._try();
				tryBlock.body().directStatement("executeTest(\"" + meth + "\");");
				
				JBlock finallyBody = tryBlock._finally();
				finallyBody.block().directStatement(meth.toLowerCase()+"Lock.unlock();");
				
				//add test annotation
				m.annotate(cm.ref("org.testng.annotations.Test"));
			}

			File file = new File("./target/classes");
			file.mkdirs();

			try
			{
				cm.build(file);
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
			String fileToCompile = "./target/classes/Test/" + className
					+ ".java";
			int compilationResult = compiler.run(null, null, null,
					fileToCompile);
			// TODO check for result and putthe error message
		}
	}

	/**
	 * Creates the test suite.
	 *
	 * @param testCaseList
	 *            the test case list
	 * @return the map
	 */
	private static Map<String, ArrayList<String>> createTestSuite(
			List<TestCase> testCaseList) {
		Iterator<TestCase> itr = testCaseList.iterator();

		Map<String, ArrayList<String>> testSuite = new HashMap<String, ArrayList<String>>();

		
		
		while (itr.hasNext())
		{
			String ts = itr.next().testSuiteName;
			if (!testSuite.containsKey(ts))
			{
				testSuite.put(ts, getTestList(ts, testCaseList));
			}
		}

		return testSuite;
	}

	/**
	 * Gets the test list.
	 *
	 * @param testSuiteName
	 *            the test suite name
	 * @param testCaseList
	 *            the test case list
	 * @return the test list
	 */
	private static ArrayList<String> getTestList(String testSuiteName,
			List<TestCase> testCaseList) {
		List<String> testList = new ArrayList<String>();
		Iterator<TestCase> itr = testCaseList.iterator();

		while (itr.hasNext())
		{
			TestCase tCase = itr.next();

			if (tCase.testSuiteName.equals(testSuiteName))
			{
				testList.add(tCase.testCaseName.toString());
			}
		}

		return (ArrayList<String>) testList;
	}

	// Creates the suite xml used by TestNG
	/**
	 * Builds the suite xml.
	 *
	 * @param suiteClasss
	 *            the suite classs
	 * @return the list
	 */
	public static List<XmlSuite> buildSuiteXml(Set suiteClasss, Boolean distributeTests) {

		
			return buildVersionSuiteXml(suiteClasss, distributeTests);
	
	}

	/**
	 * @param suiteClasss
	 * @return
	 */
	private static List<XmlSuite> buildVersionSuiteXml(Set suiteClasss, Boolean distributeTests) {

		// TODO Auto-generated method stub
		XmlSuite suite = new XmlSuite();
		suite.setName("IATSuite");
		suite.addListener("com.drc.wf.testNGReporters.XMLPassedTestReporter");
		//Iterator itr;// = suiteClasss.iterator();
	
		//if (suiteConfigFile.getProperty("Parallel").equals("true"))
	//	{

			suite.setParallel("tests");
			suite.setPreserveOrder("false");
			suite.setThreadCount(250);
			suite.setVerbose(-1);

	//	}

		String[] versionList = suiteConfigFile.getProperty("Versions").split(
				",");

		int versionIndex =0;
		for (String ver : versionList)
		{
			String hub = ver.split("_")[1];
			String version = ver.split("_")[0];
			String platform = getPlatform(version);
			String browsername = getBrowserName(version);
			//String browserBit = getBrowserBit(version);

			XmlTest test = new XmlTest(suite);

			if (suiteConfigFile.getProperty("Parallel").equals("true")) //&& !ver.contains("Saf"))
			{
			test.setParallel("classes");
		    test.setThreadCount(3);
		    test.setPreserveOrder("false");
			}
			else{
				test.setParallel("classes");
			    test.setThreadCount(1);
			    test.setPreserveOrder("false");
				}
			List<XmlClass> classes = new ArrayList<XmlClass>();
			//

			Iterator itr = suiteClasss.iterator();
			
			int iteratorCount =0;
			while (itr.hasNext())
			{
				Object testClass = itr.next();
				
				if(distributeTests)
				{
					int modValue = iteratorCount % versionList.length;
					
					
					
					if(modValue == versionIndex)
					{
						classes.add(new XmlClass("Test." + (String) testClass));
					}
					
				}
				else
				{
					classes.add(new XmlClass("Test." + (String) testClass));
				}
				iteratorCount++;
			}

			test.setName("Test_" + version.replaceAll(":", "_"));
			test.setXmlClasses(classes);

			Map<String, String> parameter = new HashMap<String, String>();
			parameter.put("browser", browsername);
			parameter.put("platform", platform);
			parameter.put("version", version);
			parameter.put("profile", "default");
			parameter.put("hubURL", hub);
			//parameter.put("bit", browserBit);
			test.setParameters(parameter);

		
		versionIndex++;
		}
		List<XmlSuite> suites = new ArrayList<XmlSuite>();
		suites.add(suite);

		return suites;

	}

	
	
	
	
	private static XmlSuite buildVersionSuiteXml(Map<String, ArrayList<String>> testSuite, Boolean parallelTests, Boolean distributeTests) {

		
		
		// TODO Auto-generated method stub
		XmlSuite suite = new XmlSuite();
		suite.setName(testSuiteNameParallel);
		suite.addListener("com.drc.wf.testNGReporters.XMLPassedTestReporter");
		//Iterator itr;// = suiteClasss.iterator();
	
		//if (suiteConfigFile.getProperty("Parallel").equals("true"))
	//	{

			suite.setParallel("tests");
			suite.setPreserveOrder("false");
			suite.setThreadCount(250);
			suite.setVerbose(-1);

	//	}

		String[] versionList = suiteConfigFile.getProperty("Versions").split(
				",");

		int versionIndex =0;
		
		
		
		for (String ver : versionList)
		{
			
			Iterator itr = testSuite.keySet().iterator();
			
			String hub = ver.split("_")[1];
			String version = ver.split("_")[0];
			String platform = getPlatform(version);
			String browsername = getBrowserName(version);
			//String browserBit = getBrowserBit(version);

			XmlTest test = new XmlTest(suite);
			test.addXmlDependencyGroup("Parallel", "");
			if (parallelTests) //&& !ver.contains("Saf"))
			{
			test.setParallel("classes");
		    test.setThreadCount(3);
		    test.setPreserveOrder("false");
			}
			else{
				test.setParallel("classes");
			    test.setThreadCount(1);
			    test.setPreserveOrder("false");
				}
			List<XmlClass> classes = new ArrayList<XmlClass>();
			//

			int iteratorCount =0;
			while (itr.hasNext())
			{
				String className = itr.next().toString();
				ArrayList<String> method = testSuite.get(className);
			
			//Iterator itr = suiteClasss.iterator();
			
			//int iteratorCount =0;
				Boolean testAdded = false;
				XmlClass versionClass = new XmlClass("Test." + (String) className);
				List<XmlInclude> includedMethods = new ArrayList<XmlInclude>();
			for (String meth : method)
			{
				//Object testClass = itr.next();
				
				if(distributeTests)
				{
					int modValue = iteratorCount % versionList.length;
					
					
					if(modValue == versionIndex)
					{
						
						includedMethods.add(new XmlInclude(meth));
						testAdded = true;
					}
					
				}
				else
				{
					includedMethods.add(new XmlInclude(meth));
					testAdded = true;
				}
				iteratorCount++;
			}
			
			if(testAdded)
			{
				versionClass.setIncludedMethods(includedMethods);
				classes.add(versionClass);
			}
			
			}
			test.setName("Test_" + version.replaceAll(":", "_"));
			test.setXmlClasses(classes);

			Map<String, String> parameter = new HashMap<String, String>();
			parameter.put("browser", browsername);
			parameter.put("platform", platform);
			parameter.put("version", version);
			parameter.put("profile", "default");
			parameter.put("hubURL", hub);
			//parameter.put("bit", browserBit);
			test.setParameters(parameter);

		
		versionIndex++;
		}
		
		return suite;
		
	}
	
	
	
private static XmlSuite buildVersionSuiteXml2(Map<String, ArrayList<String>> testSuite, Boolean distributeTests) {

		
	// TODO Auto-generated method stub
	XmlSuite suite = new XmlSuite();
	suite.setName(testSuiteNameSingle);
	suite.addListener("com.drc.wf.testNGReporters.XMLPassedTestReporter");
	
		suite.setParallel("false");
		suite.setPreserveOrder("false");
		
		suite.setVerbose(-1);

//	}

	
		String[] versionList = suiteConfigFile.getProperty("Versions").split(
				",");

		int versionIndex =0;
		
		
		
		for (String ver : versionList)
		{
			
			Iterator itr = testSuite.keySet().iterator();
			
			String hub = ver.split("_")[1];
			String version = ver.split("_")[0];
			String platform = getPlatform(version);
			String browsername = getBrowserName(version);
			//String browserBit = getBrowserBit(version);

			XmlTest test = new XmlTest(suite);

			
			test.setParallel("false");
			test.setThreadCount(1);
			test.setParallel("tests");
		  
			List<XmlClass> classes = new ArrayList<XmlClass>();
			//

			int iteratorCount =0;
			while (itr.hasNext())
			{
				String className = itr.next().toString();
				ArrayList<String> method = testSuite.get(className);
			
			
				Boolean testAdded = false;
				XmlClass versionClass = new XmlClass("Test." + (String) className);
				List<XmlInclude> includedMethods = new ArrayList<XmlInclude>();
			for (String meth : method)
			{
				
				if(distributeTests)
				{
					int modValue = iteratorCount % versionList.length;
					
					
					if(modValue == versionIndex)
					{
						
						includedMethods.add(new XmlInclude(meth));
						testAdded = true;
					}
					
				}
				else
				{
					includedMethods.add(new XmlInclude(meth));
					testAdded = true;
				}
				iteratorCount++;
			}
			
			if(testAdded)
			{
				versionClass.setIncludedMethods(includedMethods);
				classes.add(versionClass);
			}
			
			}
			test.setName("Test_" + version.replaceAll(":", "_")+"SingleThread");
			test.setXmlClasses(classes);

			Map<String, String> parameter = new HashMap<String, String>();
			parameter.put("browser", browsername);
			parameter.put("platform", platform);
			parameter.put("version", version);
			parameter.put("profile", "default");
			parameter.put("hubURL", hub);
			//parameter.put("bit", browserBit);
			test.setParameters(parameter);

		
		versionIndex++;
		}
		
		return suite;
		
	}
	
	
	
	/**
	 * @param version
	 * @return
	 */
	private static String getPlatform(String version) {

		String os = version.split("::")[0];

		if (os.contains("WIN7"))
		{
			return Platform.WINDOWS7.toString();
		}

		else if (os.contains("VISTA"))
		{
			return Platform.WINDOWS7.toString();
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

	private static String getBrowserName(String version) {
		String browser = version.split("::")[1];

		if (browser.contains("Ch"))
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

	private static String getBrowserBit(String version) {
		String browser = version.split("::")[1];

		if (browser.split(":")[2].equalsIgnoreCase("64Bit"))
		{
			return BrowserTargetBits.BIT_64.getValue();
		} else
		{
			return BrowserTargetBits.BIT_32.getValue();
		}

	}

	/**
	 * Gets the driver object.
	 *
	 * @return the driver object
	 */
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
}
