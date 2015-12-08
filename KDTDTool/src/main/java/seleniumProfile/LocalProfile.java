/**
 *
 */
package seleniumProfile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import com.DRC.BrowserOS.RemoteHub;
import com.DRC.BrowserOS.RemoteNode;

/**
 * @author cjha
 *
 */
public class LocalProfile
{
	private List				localBrowsers	= new ArrayList();
	private String				localHubPort;
	private String				localNodePort;

	private static LocalProfile	localProf		= null;

	private LocalProfile()
	{
		RemoteHub.getInstance().startHub(
				new String[] { "-role", "hub", "-port", "4443" });

		RemoteNode.getInstance().startNode(
				new String[] { "-role", "node", "-hub",
						"http://localhost:4443/wd/hub", "-port", "5554" });

		localBrowsers
		.addAll(getVersionList("http://localhost:4443/grid/console"));

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
						// if (vList.add(str))
						// {
						// verNodeMap.put(str, hostIP);
						// }
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
