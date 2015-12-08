/**
 *
 */
package mainWindow;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * @author cjha
 *
 */
public class NewProjectDlg
{

	private Stage		stage;
	private TextField	txtProjName;
	private TextField	txtConfig;
	private TextField	txtTCRepo;
	private TextField	txtFileUpload;

	public NewProjectDlg()
	{
		this.stage = new Stage();

		stage.centerOnScreen();
		stage.setWidth(600);
		stage.setHeight(400);
		stage.setResizable(false);
		addDetails();
		// setType(t);

		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setMaxWidth(Screen.getPrimary().getVisualBounds().getWidth() / 2);

	}

	public Stage getStage() {
		return stage;
	}

	/**
	 *
	 */
	private void addDetails() {

		GridPane grid = new GridPane();
		grid.setAlignment(Pos.TOP_LEFT);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(0, 10, 0, 10));

		final Label labelProjName = new Label("Set Project Name");
		labelProjName.setFont(new Font("Arial", 20));
		txtProjName = new TextField();
		final Label labelConfig = new Label("Set Configuration File");
		labelConfig.setFont(new Font("Arial", 20));
		txtConfig = new TextField();
		final Label labelTCRepo = new Label("Set TestCase Repository");
		labelTCRepo.setFont(new Font("Arial", 20));
		txtTCRepo = new TextField();
		final Label labelFileUpload = new Label("Set File Upload Repository");
		labelFileUpload.setFont(new Font("Arial", 20));
		txtFileUpload = new TextField();

		Button btn = new Button();
		btn.setAlignment(Pos.TOP_RIGHT);
		btn.setText("OK");
		btn.setOnAction(new EventHandler<ActionEvent>()
				{

			@Override
			public void handle(ActionEvent event) {
				createProject();
				stage.fireEvent(event);
				// stage.fireEvent(EventType.);
				stage.close();

			}

		});

		grid.add(labelProjName, 0, 0);
		grid.add(txtProjName, 1, 0);
		grid.add(labelConfig, 0, 1);
		grid.add(txtConfig, 1, 1, 2, 1);
		grid.add(labelTCRepo, 0, 3);
		grid.add(txtTCRepo, 1, 3, 2, 3);
		grid.add(labelFileUpload, 0, 5);
		grid.add(txtFileUpload, 1, 5, 2, 5);
		grid.add(btn, 2, 7);
		// TODO Auto-generated method stub
		stage.setScene(new Scene(grid, 600, 400));
	}

	private void createProject() {
		// TODO Auto-generated method stub

		String path = NewProjectDlg.class.getProtectionDomain().getCodeSource()
				.getLocation().getPath();
		path = path.substring(1);

		File dir = new File(path + "Projects/" + txtProjName.getText());
		dir.mkdir();
		File configFile = new File(path + "Projects/" + txtProjName.getText()
				+ "/config.properties");
		try
		{
			configFile.createNewFile();
			OutputStream out = new FileOutputStream(configFile);
			Properties props = new Properties();
			props.setProperty("configurationExcelFile", txtConfig.getText());
			props.setProperty("testCaseRepository", "" + txtTCRepo.getText());
			props.setProperty("fileLocationForFileUpload",
					"" + txtFileUpload.getText());
			props.setProperty("BrowserList", "" + txtFileUpload.getText());
			props.store(out, "Project Settings for " + txtProjName.getText()
					+ " Project");

		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 *
	 */
	public String getProjectNmae() {
		return txtProjName.getText();
	}
}
