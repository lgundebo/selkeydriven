/**
 *
 */
package mainWindow;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

/**
 * @author cjha
 *
 */
public class ProjectChangeListener implements ChangeListener<String>
{

	private ObservableValue	ov;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javafx.beans.value.ChangeListener#changed(javafx.beans.value.ObservableValue
	 * , java.lang.Object, java.lang.Object)
	 */
	@Override
	public void changed(ObservableValue ov, String cVal, String nVal) {
		this.ov = ov;
		if (cVal != null)
		{

		}

	}
}
