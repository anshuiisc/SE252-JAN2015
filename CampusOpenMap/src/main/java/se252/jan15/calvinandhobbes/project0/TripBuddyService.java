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
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import org.json.JSONArray;
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
@Path("trip")
public class TripBuddyService {
	private static String weatherLink = "http://api.wunderground.com/api/8c6cd1113f0f40d9/conditions/q/CA/";
	private static String timezoneLink = "https://maps.googleapis.com/maps/api/timezone/json?";
	private static String placeLink = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?";
	private static String apiGoogle = "AIzaSyCIthNbg4lwx-LWHxXMyw1wHBYssjmAuDA";
	private static String[] types = {"food", "amusement_park", "aquarium", "art_gallery", "casino", "museum", "spa", "stadium", "zoo"};
	private static int[] radius = {2000, 20000, 20000, 10000, 10000, 10000, 10000, 20000, 20000};
	
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
	
	private static JSONObject getTimezone(String location, long timestamp) {
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
	}
	
	private static JSONObject getPlaces(String location, int radius, String type) {
		Client client = ClientBuilder.newClient();
    	WebTarget target = client.target(placeLink);
    	System.out.println("Trying places");
    	String timezone = target.path("json")
    			.queryParam("location", location)
    			.queryParam("radius", String.valueOf(radius))
    			.queryParam("types", type)
    			.queryParam("key", apiGoogle)
    			.request().get(String.class);
    	System.out.println("places done!");
    	return new JSONObject(timezone);
	}

    @GET
//    @Produces(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public TripBuddy TripBuddyGet(
	        @DefaultValue("") @QueryParam("src") String source, 
	        @DefaultValue("") @QueryParam("dest") String destination) {
    	String wunderSrc = weatherLink + source + ".json";
    	String wunderDest = weatherLink + destination + ".json";
    	
    	JSONObject weatherSrc = getJSON(wunderSrc);
    	JSONObject weatherDest = getJSON(wunderDest);

    	Location srcLoc = new Location(weatherSrc);
    	Location destLoc = new Location(weatherDest);
    	
    	String locSrc = srcLoc.getLatitude()+","+srcLoc.getLongitude();
    	String locDest = destLoc.getLatitude()+","+destLoc.getLongitude();
    	
    	String timeStrSrc = timezoneLink + "location=" + locSrc + "&timestamp=" + srcLoc.getLocal_epoch() + "&key=" + apiGoogle;
    	String timeStrDest = timezoneLink + "location=" + locDest + "&timestamp=" + destLoc.getLocal_epoch() + "&key=" + apiGoogle;

//    	JSONObject timezoneSrc = getTimezone(locSrc, srcLoc.getLocal_epoch());
//    	JSONObject timezoneDest = getTimezone(locDest, destLoc.getLocal_epoch());
    	JSONObject timezoneSrc = getJSON(timeStrSrc);
    	JSONObject timezoneDest = getJSON(timeStrDest);
    	
    	TimeZone srcTime = new TimeZone(timezoneSrc);
    	TimeZone destTime = new TimeZone(timezoneDest);
    	
    	ArrayList<Place> places = new ArrayList<Place>();
    	for(int i = 0; i < types.length; i++) {
    		String tempUrl = placeLink + "location=" + locSrc + "&radius=" + radius[i] + "&types=" + types[i] + "&key=" + apiGoogle;
//    		JSONObject jsonObj = getPlaces(locDest, radius[i], types[i]);
    		JSONObject jsonObj = getJSON(tempUrl);
    		JSONArray jsonPlaces = jsonObj.getJSONArray("results");
    		int len = jsonPlaces.length();
    		for(int j = 0; j < len; j++) {
    			
    			JSONObject temp = jsonPlaces.getJSONObject(j);
    			Place place = new Place(temp);
    			places.add(place);
    		}
    	}
    	
    	return new TripBuddy(srcLoc, destLoc, srcTime, destTime, places);
    }
}
