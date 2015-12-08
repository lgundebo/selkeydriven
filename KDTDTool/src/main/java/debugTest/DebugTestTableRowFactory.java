package debugTest;

import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.util.Callback;
import fxAction.FxAction;

/**
 *
 */

/**
 * @author cjha
 *
 */
public class DebugTestTableRowFactory implements
Callback<TableView<FxAction>, TableRow<FxAction>>
{

	protected DebugTestTable	table;

	/**
	 * @param table2
	 */
	public DebugTestTableRowFactory(DebugTestTable debugTestTable)
	{
		this.table = debugTestTable;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see javafx.util.Callback#call(java.lang.Object)
	 */
	@Override
	public TableRow<FxAction> call(TableView<FxAction> arg0) {
		final TableRow<FxAction> row = new TableRow<>();
		final ContextMenu rowMenu = new ContextMenu();
		MenuItem editItem = new MenuItem("Edit");
		MenuItem addItem = new MenuItem("Add");

		MenuItem setBreakPointItem = new MenuItem("Set Break Point");
		setBreakPointItem.setOnAction(new EventHandler<ActionEvent>()
				{
			@Override
			public void handle(ActionEvent event) {

				table.getCurrentTest().addDebugStep(row.getIndex());
				row.getStyleClass().add("selecteddRow");
				// table.getItems().remove(row.getItem());
			}
				});

		MenuItem removeBreakPointItem = new MenuItem("Remove Break Point");
		removeBreakPointItem.setOnAction(new EventHandler<ActionEvent>()
				{
			@Override
			public void handle(ActionEvent event) {

				table.getCurrentTest().removeDebugStep(row.getIndex());
				row.getStyleClass().remove("selecteddRow");

			}
		});

		// editItem.setOnAction(...);
		MenuItem removeItem = new MenuItem("Delete");
		removeItem.setOnAction(new EventHandler<ActionEvent>()
		{
			@Override
			public void handle(ActionEvent event) {
				// table.getItems().remove(row.getItem());
			}
		});
		rowMenu.getItems().addAll(setBreakPointItem, removeBreakPointItem,
				addItem, editItem, removeItem);

		// only display context menu for non-null items:
		row.contextMenuProperty().bind(
				Bindings.when(Bindings.isNotNull(row.itemProperty()))
						.then(rowMenu).otherwise((ContextMenu) null));
		return row;
	}

}
