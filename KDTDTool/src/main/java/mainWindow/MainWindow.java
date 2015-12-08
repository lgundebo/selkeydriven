/**
 *
 */
package mainWindow;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import newTest.NewTestTab;
import settings.SettingsTab;

import com.DRC.BrowserOS.RemoteHub;
import com.DRC.BrowserOS.RemoteNode;

import debugTest.DebugTestTab;

/**
 * @author cjha
 *
 */
public class MainWindow extends Application
{
	private static Stage	primaryStage;

	public static boolean	dlgFlag			= true;
	public static List		localBrowsers	= new ArrayList();
	public static List		jenkinsBrowsers	= new ArrayList();
	private static HashMap	verNodeMap		= new HashMap();

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		// Thread monitor = new Thread()
		// {
		// @Override
		// public void run() {

		RemoteHub.getInstance().startHub(
				new String[] { "-role", "hub", "-port", "4442" });

		RemoteNode.getInstance().startNode(
				new String[] { "-role", "node", "-hub",
						"http://localhost:4442/wd/hub", "-port", "5554" });

		try
		{
			Thread.sleep(3000);

		} catch (Throwable t)
		{
			System.out.println(t.getMessage());
		}

		localBrowsers
				.addAll(getVersionList("http://localhost:4442/grid/console"));

		// RemoteHub.getInstance().startHub(
		// new String[] { "-role", "hub", "-port", "4443" });
		//
		// localBrowsers = RemoteNode.getInstance().getBrowserList();
		HashSet<String> hubList = getHubList();
		//
		for (String s : hubList)
		{
			jenkinsBrowsers.addAll(getVersionList(s));
		}

		// }
		// };
		//
		// monitor.start();
		javafx.application.Platform.runLater(new Runnable()
		{

			@Override
			public void run() {

				final ProjectSelectDlg pDlg = new ProjectSelectDlg();
				// pDlg.getStage().show();

				pDlg.addEventHandler(ActionEvent.ACTION,
						new EventHandler<ActionEvent>()
						{

					@Override
					public void handle(ActionEvent event) {

						dlgFlag = false;

					}
						});
				pDlg.getStage().show();
			}

		});
		// while (dlgFlag)
		// {
		//
		// }
		// javafx.application.Platform.exit();

		try
		{
			Thread.sleep(1000);
			launch(args);
		} catch (Throwable t)
		{
			System.out.println(t.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see javafx.application.Application#start(javafx.stage.Stage)
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {

		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("KDTDTool");

		primaryStage.setOnShown(new EventHandler<WindowEvent>()
		{

			@Override
			public void handle(WindowEvent event) {

				Group root = new Group();
				Scene scene = new Scene(root, 1200, 800, Color.WHITE);
				TabPane tabPane = new TabPane();
				DebugTestTab debugTab = new DebugTestTab();
				tabPane.getTabs().add(debugTab.getTab());
				NewTestTab testTab = new NewTestTab();
				tabPane.getTabs().add(testTab);
				SettingsTab setTab = new SettingsTab();
				tabPane.getTabs().add(setTab);
				root.getChildren().add(tabPane);
				primaryStage.setScene(scene);

			}
		});
		// primaryStage.show();
	}

	/**
	 * Returns the main stage.
	 *
	 * @return
	 */
	public static Stage getPrimaryStage() {
		return primaryStage;
	}

	/**
	 * Gets the version list.
	 *
	 * @param address
	 *            the address
	 * @return the version list
	 */
	private static HashSet<String> getVersionList(String address) {

		URL url;
		BufferedReader in = null;
		HashSet<String> vList = new HashSet<String>();
		try
		{
			url = new URL(address);
			in = new BufferedReader(new InputStreamReader(url.openStream()));
		} catch (MalformedURLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String inputLine;
		String hostIP = null;
		try
		{
			while ((inputLine = in.readLine()) != null)
			{
				System.out.println(inputLine);

				if (inputLine.contains("DefaultRemoteProxy (version"))
				{
					String str = inputLine.replaceAll(
							"^.*?class='proxyid'>id :", "").split(",")[0];
					hostIP = str.split(":")[1].replace("//", "");
				}
				if (!inputLine.contains("/div><div"))
				{
					String str = Arrays.toString(inputLine.replaceAll(
							"^.*?version=", "").split(",.*?(version=|$)"));
					str = str.replaceAll("[<>\\[\\],-]", "");
					if (str.contains("}"))
					{
						str = str.substring(0, str.indexOf("}"));
					}
					if (str.contains("::") && str.endsWith("Bit"))
					{
						if (vList.add(str))
						{
							verNodeMap.put(str, hostIP);
						}
					}
				}
			}
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return vList;
	}

	/**
	 * @return
	 */
	private static HashSet getHubList() {

		String address = "http://jenkins-sqa:8080/SeleniumHubNode/configurations";

		URL url;
		BufferedReader in = null;
		HashSet<String> vList = new HashSet<String>();
		try
		{
			url = new URL(address);
			in = new BufferedReader(new InputStreamReader(url.openStream()));
		} catch (MalformedURLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String inputLine;

		try
		{
			while ((inputLine = in.readLine()) != null)
			{
				System.out.println(inputLine);
				if (inputLine.contains("grid/console"))
				{
					String str = null;
					String strArr[] = inputLine.split("href=");
					for (String s : strArr)
					{
						if (s.contains("grid/console"))
						{
							str = s.split(">")[0];
						}
					}

					// String str = Arrays.toString(inputLine.replaceAll(
					// "^.*?href=", "").split(",.*?(hre=|$)"));
					str = str.replaceAll("\"", "");
					// if (str.contains("}"))
					// {
					// str = str.substring(0, str.indexOf("}"));
					// }
					// if (str.contains("::") && str.endsWith("Bit"))
					{
						vList.add(str);
					}
				}
			}
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return vList;

	}

}
