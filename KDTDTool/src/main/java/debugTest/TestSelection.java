/**
 *
 */
package debugTest;

import java.util.NoSuchElementException;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import mainWindow.MainWindow;
import mainWindow.ProjectDetails;

import org.testng.Assert;

import seleniumProfile.SeleniumUtility;
import DRC.AutomationFramework.Exceptions.ObjectRepositoryException;
import DRC.AutomationFramework.Execution.StepExecution;
import DRC.AutomationFramework.TestManagement.TestCase;
import DRC.AutomationFramework.WebDriver.DriverSetup;

/**
 * @author cjha
 *
 */
public class TestSelection
{

	private ChoiceBox		tsCBox;
	private ChoiceBox		tlCBox;
	private ChoiceBox		localBrowserCBox;
	private ChoiceBox		jenkinsBrowserCBox;
	private Button			executeLocalBtn;
	private Button			executeJenkinsBtn;
	private VBox			testSelectionBox;
	private DebugTestTable	table;
	private Thread			monitor;

	public TestSelection(DebugTestTable table)
	{
		testSelectionBox = new VBox();
		testSelectionBox.setSpacing(5);
		testSelectionBox.setPadding(new Insets(10, 0, 0, 10));
		this.table = table;
		testSelectionBox.getChildren().addAll(createTestSelectionPanel(),
				createTestExecutionPanel());
	}

	public VBox getTestSelectionBox() {
		return this.testSelectionBox;
	}

	private HBox createTestSelectionPanel() {

		tsCBox = new ChoiceBox();
		tsCBox.getItems().addAll(ProjectDetails.getInstance().getTestSuite());

		tlCBox = new ChoiceBox();
		tsCBox.getSelectionModel().selectedItemProperty()
		.addListener(new ChangeListener<String>()
				{

			private ObservableValue	ov;

			@Override
			public void changed(ObservableValue ov, String cVal,
					String nVal) {
				this.ov = ov;
				if (cVal != null && !cVal.isEmpty())
				{
					tlCBox.getItems().removeAll(
									ProjectDetails.getInstance().getTestSet(
											cVal));
				}
				tlCBox.getItems().addAll(
								ProjectDetails.getInstance().getTestSet(nVal));

			}

				});
		tlCBox.getSelectionModel().selectedItemProperty()
		.addListener(new ChangeListener<String>()
				{

			private ObservableValue	ov;

			@Override
			public void changed(ObservableValue ov, String cVal,
					String nVal) {
				this.ov = ov;
				if (nVal != null)
				{
							table.populateTable(tsCBox.getValue().toString(),
									nVal);
				}
				if (cVal != null)
				{

					// data.clear();
				}
						// currentTC = getTestCase(nVal);
						// if (currentTC != null)
						// {
						// List<Action> steps = currentTC.testSteps;
						// Iterator itr = steps.iterator();
						// while (itr.hasNext())
						// {
						// FxAction act = new FxAction((Action) itr.next());
						//
						// data.add(act);
						//
						// // stepCol.setCellValueFactory(new
						// // PropertyValueFactory<Action,
						// // int>(act.testStep));
						//
						// table.setItems(data);
						//
				// }
					}
			// }

				});
		// final Label label = new Label("TestCase");
		// label.setFont(new Font("Arial", 20));
		final Label labelTS = new Label("Select TestSuite");
		labelTS.setFont(new Font("Arial", 20));
		final Label labelTC = new Label("Select TestCase");
		labelTC.setFont(new Font("Arial", 20));
		// //final Label labelExecute = new Label("Click to Execute TestCase");
		// label.setFont(new Font("Arial", 20));
		// final Label labelVer = new
		// Label("Select the Browser OS combination");
		// label.setFont(new Font("Arial", 20));
		final HBox hbox = new HBox();
		hbox.setSpacing(5);
		hbox.setPadding(new Insets(10, 0, 0, 10));

		hbox.getChildren().addAll(labelTS, tsCBox, labelTC, tlCBox);
		return hbox;

		// return null;

	}

	private VBox createTestExecutionPanel() {

		localBrowserCBox = new ChoiceBox();
		localBrowserCBox.getItems().addAll(MainWindow.localBrowsers);
		localBrowserCBox.getSelectionModel().selectedItemProperty()
				.addListener(new ChangeListener<String>()
				{

					private ObservableValue	ov;

					@Override
					public void changed(ObservableValue ov, String cVal,
							String nVal) {
						this.ov = ov;
						if (cVal != null)
						{

					// currentVer = null;
						}
				// currentVer = nVal;

					}

				});
		jenkinsBrowserCBox = new ChoiceBox();
		jenkinsBrowserCBox.getItems().addAll(MainWindow.jenkinsBrowsers);
		jenkinsBrowserCBox.getSelectionModel().selectedItemProperty()
				.addListener(new ChangeListener<String>()
				{

					private ObservableValue	ov;

					@Override
					public void changed(ObservableValue ov, String cVal,
							String nVal) {
						this.ov = ov;
						if (cVal != null)
						{

					// currentVer = null;
						}
				// currentVer = nVal;

					}

				});
		final Label labelLocBrow = new Label("Select the Local Browser ");
		labelLocBrow.setFont(new Font("Arial", 20));
		final Label labelJenKinsBrow = new Label("Select the Jenkins Browser ");
		labelJenKinsBrow.setFont(new Font("Arial", 20));

		final Label labelExecute = new Label("Click to Execute TestCase");
		labelExecute.setFont(new Font("Arial", 20));

		executeLocalBtn = new Button();
		executeLocalBtn.setAlignment(Pos.TOP_RIGHT);
		executeLocalBtn.setText("Execute ");

		executeLocalBtn.setOnAction(new EventHandler<ActionEvent>()
				{

			@Override
			public void handle(ActionEvent event) {

				disableAll();
				executeLocalTest();
			}
				});

		executeJenkinsBtn = new Button();
		executeJenkinsBtn.setAlignment(Pos.TOP_RIGHT);
		executeJenkinsBtn.setText("Execute ");

		final HBox hbox = new HBox();
		hbox.setSpacing(5);
		hbox.setPadding(new Insets(10, 0, 0, 10));
		hbox.getChildren().addAll(labelLocBrow, localBrowserCBox,
				executeLocalBtn);
		final HBox hbox1 = new HBox();
		hbox1.setSpacing(5);
		hbox1.setPadding(new Insets(10, 0, 0, 10));
		hbox1.getChildren().addAll(labelJenKinsBrow, jenkinsBrowserCBox,
				executeJenkinsBtn);
		final VBox vbox = new VBox();
		vbox.setSpacing(5);
		vbox.setPadding(new Insets(10, 0, 0, 10));

		vbox.getChildren().addAll(hbox, hbox1);
		return vbox;

	}

	/**
	 *
	 */
	protected void executeLocalTest() {

		String currentVer = localBrowserCBox.getValue().toString();
		// TODO Auto-generated method stub
		DriverSetup driverSetup = new DriverSetup(
				SeleniumUtility.getPlatform(currentVer),
				SeleniumUtility.getBrowserName(currentVer), currentVer,
				"deafult", "localhost:4442", "NOTAPPLICABLE");

		monitor = new Thread()
		{
			@Override
			public void run() {

				try
				{
					executeTest(driverSetup);

					enableFxAll();
				} catch (Exception e)
				{
					enableFxAll();
				}

			}
		};

		monitor.start();
	}

	// @Parameters({"browser"})
	public void executeTest(DriverSetup driverSetup) {

		// String localhost = null;
		// String hostIP = (String) verNodeMap.get(currentVer);
		// System.out.print("Startting node at :" + hostIP);
		// try
		// {
		// localhost = InetAddress.getLocalHost().getHostAddress();
		// String address =
		// "http://jenkins-sqa:8080/SeleniumHubNode/startNodeURL?host="
		// + hostIP + "&node=5556&hub=" + localhost + "&hPort=4443";
		// url = new URL(address);
		// BufferedReader in = new BufferedReader(new InputStreamReader(
		// url.openStream()));
		// Thread.sleep(80000);
		//
		// } catch (IOException | InterruptedException e1)
		// {
		// // TODO Auto-generated catch block
		// e1.printStackTrace();
		// }

		// try
		// {
		//
		// String path = Main.class.getProtectionDomain().getCodeSource()
		// .getLocation().getPath();
		// path = path.substring(1);
		//
		// Process p = Runtime.getRuntime().exec(
		// "java -jar " + path + "tightvnc-jviewer.jar -host="
		// + hostIP + " -password=sqatest -viewonly=yes");
		// } catch (IOException e2)
		// {
		// // TODO Auto-generated catch block
		// System.out.print("Failed to start VNC session");
		// e2.printStackTrace();
		// }
		// int index = 0;
		//
		// for (int i = 0; i < testCaseList.size(); i++)
		// {
		// if (testCaseList.get(i).testCaseName.equals(testname))
		// {
		// index = i;
		// break;
		// }
		// }
		//
		TestCase testCase = null;

		try
		{
			// testCase = (TestCase)
			// (BuildTestSuite.testCaseList.get(index)).clone();
			testCase = new TestCase(table.getCurrentTest().getTest(),
					driverSetup);
		} catch (Exception e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		testCase.currentStep = 0;
		boolean bRunningScriptwithVBSdriver = false;
		boolean bMamkeTestFailforNotCallingMethodFromCommon = false;
		// Core sExecuteTest = new Core();
		StringBuffer failedSteps = new StringBuffer("The Failed Steps are ");
		bMamkeTestFailforNotCallingMethodFromCommon = false;

		try
		{

			testCase.logging().writeLogAndConsole(
					"Start executing Test Case : " + testCase.testCaseName
							+ " from Test Suite : " + testCase.testSuiteName);

			testCase.start();
			try
			{
				int fStepCnt = 0;
				for (int i = 0; i < testCase.testSteps.size(); i++)
				{
					final int currStep = testCase.currentStep;

					if (table.getCurrentTest().getDebugSteps()
							.contains(currStep))
					{

					} else
					{

						testCase.moveToNextStep();
					}

					if (table.getCurrentTest().getDebugSteps()
							.contains(currStep))
					{
						monitor.wait();
					}
					// currStep=testCase.currentStep;
					javafx.application.Platform.runLater(new Runnable()
					{
						@Override
						public void run() {
							table.getTable().getSelectionModel()
									.select(currStep);
							if (currStep > 5)
							{
								table.getTable().scrollTo(currStep - 3);
							}
							try
							{
								Thread.sleep(500);
							} catch (InterruptedException e)
							{
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							// highlightRows.setAll(table.getSelectionModel()
							// .getSelectedIndices());

						}
					});

					StepExecution.ExecuteStep(testCase);
					Thread.sleep(1000);
					if (testCase.testPass == false)
					{
						javafx.application.Platform.runLater(new Runnable()
						{
							@Override
							public void run() {

								// highlightFailedRows.addAll(table
								// .getSelectionModel()
								// .getSelectedIndices());

							}
						});

						fStepCnt++;
						failedSteps.append(testCase.currentStep + ", " + '\n');
						if (fStepCnt > 3)
						{
							failedSteps
									.insert(0,
											"More Than 3 steps failed. Aborting the test ");
							break;
						}
					}

					System.out.print("THREAD" + Thread.currentThread().getId()
							+ testCase.driverSetup.browser.getValue()
							+ testCase.driverSetup.platform.getValue()
							+ "TESTSTATUS::" + testCase.testPass + '\n');

				}
			} catch (NoSuchElementException e)
			{
				testCase.testPass = false;
				testCase.logging().stepFailed();
				e.printStackTrace();
				return;
			} catch (RuntimeException e)
			{
				testCase.testPass = false;
				testCase.logging().stepFailed();
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ObjectRepositoryException e)
			{
				testCase.testPass = false;
				testCase.logging().stepFailed();
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally
			{
				if (testCase.testPass == false)
				{
					enableFxAll();
					Assert.fail("TestCase execution failed." + failedSteps
							+ testCase.failedMessage);
				}

			}

			// Log End of Test case
			testCase.logging().writeLogAndConsole(
					"End executing Test Case : " + testCase.testCaseName
							+ " from Test Suite : " + testCase.testSuiteName);

			bMamkeTestFailforNotCallingMethodFromCommon = true;
		} catch (Exception e)
		{
			testCase.testPass = false;
			e.printStackTrace();

		} finally
		{

			// if (testCase.skipException != null)
			// {
			//
			// throw testCase.skipException;
			// } else
			if (testCase.testPass == false)
			{
				// BuildTestSuite.testSuite.updateTestResults("Failed",
				// testCase);
			} else
			{
				// BuildTestSuite.testSuite.updateTestResults("Passed",
				// testCase);
			}

			if (bMamkeTestFailforNotCallingMethodFromCommon == false)
			{

				testCase.logging()
						.writeLogAndConsole(
								"Script was unable to call any method from the common file");
			}

			if (testCase.testPass == false)
			{
				javafx.application.Platform.runLater(new Runnable()
				{
					@Override
					public void run() {

						// highlightFailedRows.addAll(table.getSelectionModel()
						// .getSelectedIndices());

					}
				});

				// testInProgressFlag = false;
				testCase.browser.driver().quit();
				enableFxAll();
				Assert.fail("TestCase execution failed." + failedSteps
						+ testCase.failedMessage);
				testCase.browser.driver().quit();
			}

		}

	}

	/**
	 *
	 */
	protected void disableAll() {
		// TODO Auto-generated method stub
		this.executeJenkinsBtn.setDisable(true);
		this.executeLocalBtn.setDisable(true);
		this.tsCBox.setDisable(true);
		this.tlCBox.setDisable(true);
	}

	protected void enableFxAll() {

		javafx.application.Platform.runLater(new Runnable()
		{
			@Override
			public void run() {
				enableAll();

			}
		});
	}

	/**
	 *
	 */
	protected void enableAll() {
		// TODO Auto-generated method stub
		this.executeJenkinsBtn.setDisable(false);
		this.executeLocalBtn.setDisable(false);
		this.tsCBox.setDisable(false);
		this.tlCBox.setDisable(false);
	}

}
