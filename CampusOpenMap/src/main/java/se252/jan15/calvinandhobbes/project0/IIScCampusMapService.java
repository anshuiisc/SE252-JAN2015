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
import java.util.ArrayList;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONObject;

/**
 * Echo web service exposed at "echo" path relative to base URI
 * 
 * @author Yogesh Simmhan
 * @version 1.0
 * @see <a href="http://www.serc.iisc.ernet.in/~simmhan/SE252/">IISc SERC 'SE252:Intro to Cloud Computing' Course Webpage</a>
 * 
 * (c) Yogesh Simmhan, 2015
 * This work is licensed under a Attribution 4.0 International (CC BY 4.0).
 * http://creativecommons.org/licenses/by/4.0/
 */
@Path("map")
public class IIScCampusMapService {
    /**
     * Method handling HTTP GET requests. The request expects a "msg" input parameter with value of type string. 
     * The returned object will be sent to the client as JSON media type.
     *
     * @return JSON form of EchoMessage will be returned as response.
     */

	private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }
	
	private static JSONObject getJSON(String url) {
		Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("proxy.iisc.ernet.in", 3128));
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
	
	/*private static JSONObject getTimezone(String location, long timestamp) {
		Client client = ClientBuilder.newClient();
    	WebTarget target = client.target(timezoneLink);
    	System.out.println("Trying timezone");
    	String timezone = target.path("json")
    			.queryParam("location", location)
    			.queryParam("timestamp", String.valueOf(timestamp))
    			.queryParam("key", apiGoogle)
    			.request().get(String.class);
    	System.out.println("timezone done!");
    	return new JSONObject(timezone);
	}*/
	
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response IIScCampusMapGet(@DefaultValue("") @QueryParam("category") String category) {
    	System.out.println("called " + category);
    	LayerInfo[] layerArray = null;
    	if(category.equals(""))
    		layerArray = DBConn.getCategories();
    	else
    		layerArray = DBConn.getCategoryInfo(category);
    	return Response.ok(layerArray, MediaType.APPLICATION_JSON).header("Access-Control-Allow-Origin", "*").build();
    }
}
