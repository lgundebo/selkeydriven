/**
 *
 */
package fxAction;

import javafx.beans.property.SimpleStringProperty;
import DRC.AutomationFramework.TestManagement.Action;

/**
 * @author cjha
 *
 */
public class FxAction
{

	private final SimpleStringProperty	actionName	= new SimpleStringProperty();
	private final SimpleStringProperty	fieldName	= new SimpleStringProperty();
	private final SimpleStringProperty	fieldValue	= new SimpleStringProperty();
	private final SimpleStringProperty	pageName	= new SimpleStringProperty();
	private final SimpleStringProperty	testStep	= new SimpleStringProperty();

	public FxAction(Action act)
	{
		if (act.actionName != null)
		{
			this.actionName.set(act.actionName);
		} else
		{
			this.actionName.set("");
		}
		if (act.fieldName != null)
		{
			this.fieldName.set(act.fieldName);
		} else
		{
			this.fieldName.set("");
		}
		if (act.fieldValue != null)
		{
			this.fieldValue.set(act.fieldValue);
		} else
		{
			this.fieldValue.set("");
		}
		if (act.pageName != null)
		{
			this.pageName.set(act.pageName);
		} else
		{
			this.pageName.set("");
		}

		this.testStep.set(Integer.toString(act.testStep));

	}

	public String getActionName() {
		return actionName.get();
	}

	public String getFieldName() {
		return fieldName.get();
	}

	public String getFieldValue() {
		return fieldValue.get();
	}

	public String getPageName() {
		return pageName.get();
	}

	public String getTestStep() {
		return testStep.get();
	}

}
