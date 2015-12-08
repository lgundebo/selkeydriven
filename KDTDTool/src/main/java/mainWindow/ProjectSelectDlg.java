/**
 *
 */
package mainWindow;

import java.io.File;
import java.io.FilenameFilter;
import java.util.HashSet;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * @author cjha
 *
 */
public class ProjectSelectDlg extends Stage
{

	private Stage					stage;
	private static HashSet<String>	projList	= new HashSet<String>();

	public ProjectSelectDlg()
	{

		this.stage = new Stage();

		stage.centerOnScreen();
		stage.setWidth(600);
		stage.setHeight(400);
		stage.setResizable(false);
		createprojectList();
		addDetails();
		// setType(t);
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setMaxWidth(Screen.getPrimary().getVisualBounds().getWidth() / 2);
		// stage.show();

	}

	/**
	 *
	 */
	private void createprojectList() {
		// TODO Auto-generated method stub
		String path = NewProjectDlg.class.getProtectionDomain().getCodeSource()
				.getLocation().getPath();
		path = path.substring(1);

		File file = new File(path + "Projects");
		String[] directories = file.list(new FilenameFilter()
		{
			@Override
			public boolean accept(File current, String name) {
				return new File(current, name).isDirectory();
			}
		});

		for (String s : directories)
		{
			File f = new File(path + "Projects/" + s + "/config.properties");
			if (f.exists())
			{
				projList.add(s);
			}
		}
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

		final Label labelSelect = new Label("Select the project");
		labelSelect.setFont(new Font("Arial", 20));
		ChoiceBox projectCBox = new ChoiceBox();
		projectCBox.getItems().addAll(projList);
		projectCBox.getSelectionModel().selectedItemProperty()
		.addListener(new ProjectChangeListener());

		final Label labelCreate = new Label("Create new project");
		labelCreate.setFont(new Font("Arial", 20));

		Button btnNewProj = new Button();
		btnNewProj.setAlignment(Pos.TOP_RIGHT);
		btnNewProj.setText("New Project");
		btnNewProj.setOnAction(new EventHandler<ActionEvent>()
		{

			@Override
			public void handle(ActionEvent event) {

				NewProjectDlg npDlg = new NewProjectDlg();
				npDlg.getStage().show();

				npDlg.getStage().addEventHandler(ActionEvent.ACTION,
						new EventHandler<ActionEvent>()
						{

					@Override
					public void handle(ActionEvent event) {

						if (!projList.contains(npDlg.getProjectNmae()))
						{
							createprojectList();
							projectCBox.getItems().add(0,
									npDlg.getProjectNmae());
									projectCBox.setValue(npDlg.getProjectNmae());
						}

							}
						});
			}
		});

		Button btnLoad = new Button();
		btnLoad.setAlignment(Pos.TOP_RIGHT);
		btnLoad.setText("Load Project");
		btnLoad.setOnAction(new EventHandler<ActionEvent>()
		{

			@Override
			public void handle(ActionEvent event) {

				ProjectDetails
						.createInstance(projectCBox.getValue().toString());
				stage.fireEvent(event);
				stage.close();
				MainWindow.getPrimaryStage().show();

			}
		});

		grid.add(labelSelect, 0, 1);
		grid.add(projectCBox, 2, 1);
		grid.add(btnLoad, 3, 1);
		grid.add(labelCreate, 0, 2);
		grid.add(btnNewProj, 1, 2);
		// TODO Auto-generated method stub
		stage.setScene(new Scene(grid, 600, 400));
	}

	public void closeStage() {
		stage.close();
	}

	public Stage getStage() {
		return stage;
	}

}
