package elastic;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import org.json.JSONObject;

@SuppressWarnings("deprecation")
public class RestApiResults {

	public static String execServer;
	public static String[] exeServer;	

		public static void main(String[] args)
		{
			long startTime = System.currentTimeMillis();
			System.out.println(getServer());
			long endTime = System.currentTimeMillis();
			
			System.out.println((endTime-startTime)/1000);
		}

















	/****
	 * Method used to get the execution server
	 * 
	 * @param URL
	 * @return
	 */


	public static String getServer()
	{
		List<String> servers=new ArrayList<String>(Arrays.asList("http://localhost:61000/grid/api/hub/status"));
		int temp=0;
		for(String server:servers) {

			if(temp<=serverCount(server))
			{
				temp=serverCount(server);
				execServer=server;
			}

		}
		exeServer=execServer.split("/grid");
		return exeServer[0];


	}

















	/****
	 * Method used to get the free slots from server
	 * 
	 * @param URL
	 * @return
	 */
	synchronized public static int serverCount(String URL) {
		int freeSlotsCount = 0;
		try {
			@SuppressWarnings({ "resource" })
			HttpClient client = new DefaultHttpClient();
			HttpGet request = new HttpGet(URL);
			HttpResponse response = client.execute(request);
			BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			String line = "";
			String s1 = "";
			while ((line = rd.readLine()) != null) {
				s1 = s1 + line;
			}
			JSONObject jsonObject=new JSONObject(s1);
			freeSlotsCount = jsonObject.getJSONObject("slotCounts").getInt("free");
		} catch (Exception e) {
			System.out.println(URL + " is not reachable");
			e.printStackTrace();
		}
		return freeSlotsCount;
	}

}
