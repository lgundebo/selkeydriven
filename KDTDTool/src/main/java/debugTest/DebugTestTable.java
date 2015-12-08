/**
 *
 */
package debugTest;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import DRC.AutomationFramework.TestManagement.Action;
import fxAction.FxAction;

/**
 * @author cjha
 *
 */
public class DebugTestTable
{

	private TableView				table;
	final ObservableList<FxAction>	data	= FXCollections
													.observableArrayList();
	private CurrentTestCase			currentTC;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public DebugTestTable()
	{
		table = new TableView();
		table.setMaxWidth(1000);

		TableColumn actionNameCol = new TableColumn("Action Name");
		actionNameCol.setMinWidth(150);
		actionNameCol.setSortable(false);

		TableColumn feildNameCol = new TableColumn("Field Name");
		feildNameCol.setMinWidth(250);
		feildNameCol.setSortable(false);

		TableColumn valCol = new TableColumn("Value");
		valCol.setMinWidth(250);
		valCol.setSortable(false);

		TableColumn pgCol = new TableColumn("Page Name");
		pgCol.setMinWidth(150);
		pgCol.setSortable(false);

		TableColumn stepCol = new TableColumn("Step");
		stepCol.setMinWidth(30);
		stepCol.setSortable(false);

		actionNameCol
		.setCellValueFactory(new PropertyValueFactory<FxAction, String>(
				"actionName"));
		feildNameCol
		.setCellValueFactory(new PropertyValueFactory<FxAction, String>(
				"fieldName"));
		valCol.setCellValueFactory(new PropertyValueFactory<FxAction, String>(
				"fieldValue"));
		pgCol.setCellValueFactory(new PropertyValueFactory<FxAction, String>(
				"pageName"));

		stepCol.setCellValueFactory(new PropertyValueFactory<FxAction, String>(
				"testStep"));

		table.setEditable(true);
		table.getColumns().addAll(stepCol, actionNameCol, feildNameCol, valCol,
				pgCol);
		table.getStylesheets().add(
				DebugTestTable.class.getResource("app.css").toExternalForm());
		table.setRowFactory(new DebugTestTableRowFactory(this));
	}

	public TableView getTable() {
		return table;
	}

	public CurrentTestCase getCurrentTest() {
		return currentTC;
	}

	public void populateTable(String testSuiteName, String testCaseName) {
		if (!data.isEmpty())
		{
			data.clear();
		}
		currentTC = new CurrentTestCase(testSuiteName, testCaseName);

		List<Action> steps = currentTC.getTestSteps();
		Iterator itr = steps.iterator();
		while (itr.hasNext())
		{
			FxAction act = new FxAction((Action) itr.next());

			data.add(act);

			// stepCol.setCellValueFactory(new
			// PropertyValueFactory<Action,
			// int>(act.testStep));

			table.setItems(data);

		}
	}
}
