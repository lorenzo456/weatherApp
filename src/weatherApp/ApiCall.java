package weatherApp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.*;

public class ApiCall {
	
	private String key = "3c8b176087dec520c7cba73a24e42de7";
	private String name = "Rotterdam,NL";
	private String url = "http://api.openweathermap.org/data/2.5/weather?q=";

	private String currentTemperature; 
	private String currentCityName;
	private String currentDescription;
	
	public ApiCall() throws Exception 
	{
		//sendGet();
		currentCityName = name;
	}
	
	public void sendGet() throws Exception {
		
	    String apiUrl = url + currentCityName + "&units=metric" + "&appid=" + key;

		URL obj = new URL(apiUrl);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// optional default is GET
		con.setRequestMethod("GET");

		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'GET' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(new InputStreamReader( con.getInputStream() ));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) 
		{
			response.append(inputLine);
		}
		
		in.close();

		String str = response.toString();
		JSONObject jobj = new JSONObject(str);

		String cityName = jobj.getString("name");
		SetCurrentCityName(cityName);		
		System.out.println(jobj.get("name"));
		
		String temperature = "" + jobj.getJSONObject("main").getFloat("temp");
		SetCurrentTemperature(temperature);
		System.out.println(jobj.getJSONObject("main").getFloat("temp"));

		JSONArray weather = jobj.getJSONArray("weather");
		String description = weather.get(0).toString();
		JSONObject weatherObj = new JSONObject(description);
		String weatherDescription = weatherObj.getString("description");
		SetCurrentDescription(weatherDescription);

		System.out.println(weatherDescription);
		
		/*
		for (int i = 0; i < weather.length(); i++) {
		    String temp = weather.get(i).toString();
		    System.out.println(temp);
		}
		*/

		
		//print result
		//System.out.println(response.toString());
		//System.out.println(str);
		
	}

	public String GetCurrentTemperature() {
		return currentTemperature;
	}

	public void SetCurrentTemperature(String currentTemperature) {
		this.currentTemperature = currentTemperature;
	}

	public String GetCurrentCityName() {
		return currentCityName;
	}

	public void SetCurrentCityName(String currentCityName) {
		this.currentCityName = currentCityName;
	}

	public String GetCurrentDescription() {
		return currentDescription;
	}

	public void SetCurrentDescription(String currentDescription) {
		this.currentDescription = currentDescription;
	}
	
}
