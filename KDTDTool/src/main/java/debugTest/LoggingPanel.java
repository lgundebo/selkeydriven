/**
 *
 */
package debugTest;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

import javafx.scene.control.TextArea;

/**
 * @author cjha
 *
 */
public class LoggingPanel
{

	private TextArea			consoleTextArea;
	private static LoggingPanel	logPanel	= null;	;

	private LoggingPanel()
	{
		consoleTextArea = new TextArea();
		Console console = new Console(consoleTextArea);
		PrintStream ps = new PrintStream(console, true);

		System.setOut(ps);
		// System.console().
		System.setErr(ps);
	}

	public static LoggingPanel getinstance() {
		if (logPanel == null)
		{
			logPanel = new LoggingPanel();
			return logPanel;
		} else
		{
			return logPanel;
		}
	}

	public TextArea getLogArea() {
		return this.consoleTextArea;
	}

	private class Console extends OutputStream
	{

		private TextArea	txtArea;

		public Console(TextArea txtArea)
		{
			this.txtArea = txtArea;
		}

		@Override
		public void write(int b) throws IOException {

			javafx.application.Platform.runLater(() ->
			{
				txtArea.appendText(String.valueOf((char) b));

			});

		}

	}
}
