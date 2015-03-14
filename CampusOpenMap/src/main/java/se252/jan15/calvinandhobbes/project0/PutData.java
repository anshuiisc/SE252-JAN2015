package se252.jan15.calvinandhobbes.project0;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Properties;

import org.json.JSONArray;
import org.json.JSONObject;

public class PutData {
	private static String readAll(Reader rd) throws IOException {
		StringBuilder sb = new StringBuilder();
		int cp;
		while ((cp = rd.read()) != -1) {
			sb.append((char) cp);
		}
		return sb.toString();
	}

	private static JSONObject getJSON(String url) {
		IIScCampusMapProperties prop = new IIScCampusMapProperties();
		Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(prop.getProperty("proxy"), Integer.valueOf(prop.getProperty("port"))));
		InputStream is;
		try {
			is = new URL(url).openConnection(proxy).getInputStream();
			BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
			String jsonText = readAll(rd);
			JSONObject json = new JSONObject(jsonText);
			is.close();
			return json;
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args) {
		String category = "16";
		String tempUrl = "";
		
		JSONObject jsonObj = getJSON(tempUrl);
		JSONArray jsonPlaces = jsonObj.getJSONArray("results");
		int len = jsonPlaces.length();
		for(int j = 0; j < len; j++) {
			JSONObject temp = jsonPlaces.getJSONObject(j);
			LayerInfo layer = new LayerInfo(temp, category);
			if(DBConn.insertData(layer))
				System.out.println("Done");
		}
	}
}
