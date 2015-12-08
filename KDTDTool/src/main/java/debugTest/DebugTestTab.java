/**
 *
 */
package debugTest;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import mainWindow.MainWindow;
import mainWindow.ProjectDetails;

/**
 * @author cjha
 *
 */
public class DebugTestTab
{
	Tab						dTab;
	private DebugTestTable	table;

	public DebugTestTab()
	{
		dTab = new Tab();

		dTab.setText("Debug Test");
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.TOP_LEFT);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(0, 10, 0, 10));
		table = new DebugTestTable();
		dTab.setContent(addGrid());

	}

	/**
	 * @return
	 */
	private Node addGrid() {
		// TODO Auto-generated method stub
		GridPane grid = new GridPane();
		TestSelection tsBox = new TestSelection(table);
		grid.setAlignment(Pos.TOP_LEFT);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(0, 10, 0, 10));
		grid.setMinSize(1500, 800);
		grid.add(tsBox.getTestSelectionBox(), 0, 1, 4, 1);
		// grid.add(createTestExecutionPanel(), 0, 2, 4, 2);
		grid.add(createTable(), 0, 5, 4, 5);
		grid.add(LoggingPanel.getinstance().getLogArea(), 0, 10, 4, 10);
		return grid;
	}

	/**
	 * @return
	 */
	public Tab getTab() {
		// TODO Auto-generated method stub
		return dTab;
	}

	private VBox createTable() {

		final VBox vbox = new VBox();
		vbox.setSpacing(5);
		vbox.setPadding(new Insets(10, 0, 0, 10));
		vbox.getChildren().addAll(table.getTable());
		return vbox;
	}

	private HBox createTestSelectionPanel() {

		ChoiceBox tsCBox = new ChoiceBox();
		tsCBox.getItems().addAll(ProjectDetails.getInstance().getTestSuite());

		ChoiceBox tlCBox = new ChoiceBox();
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

		ChoiceBox localBrowserCBox = new ChoiceBox();
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
		ChoiceBox jenkinsBrowserCBox = new ChoiceBox();
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

		Button executeLocalBtn = new Button();
		executeLocalBtn.setAlignment(Pos.TOP_RIGHT);
		executeLocalBtn.setText("Execute ");

		executeLocalBtn.setOnAction(new EventHandler<ActionEvent>()
		{

			@Override
			public void handle(ActionEvent event) {

				executeLocalBtn.setDisable(true);
			}
		});

		Button executeJenkinsBtn = new Button();
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
}
