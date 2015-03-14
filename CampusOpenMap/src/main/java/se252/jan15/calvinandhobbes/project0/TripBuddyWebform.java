package se252.jan15.calvinandhobbes.project0;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

/**
 * 
 * Web form exposed at "web" path relative to base URI
 * 
 * @author Yogesh Simmhan
 * @version 1.0
 * @see <a href="http://www.serc.iisc.ernet.in/~simmhan/SE252/">IISc SERC 'SE252:Intro to Cloud Computing' Course Webpage</a>
 * 
 * (c) Yogesh Simmhan, 2015
 * This work is licensed under a Attribution 4.0 International (CC BY 4.0).
 * http://creativecommons.org/licenses/by/4.0/
 */
@Path("tweb")
public class TripBuddyWebform {
	
	private static String web_init = "<!doctype html>\r\n" + 
			"<html>\r\n" + 
			"<head>\r\n" + 
			"	<title></title>\r\n" + 
			"</head>\r\n" + 
			"<body>\r\n" + 
			"<form action=\"tweb\" method=\"get\">\r\n" + 
			"<p style=\"text-align: center;\"><span style=\"font-size:22px;\"><strong><span style=\"color:#A52A2A;\"><span style=\"font-family:georgia,serif;\">Welcome to Tripbuddy.</span>&nbsp;<img alt=\"cool\" height=\"23\" src=\"http://www.quackit.com/html/online-html-editor/ckeditor/ckeditor_4.4.1_full/plugins/smiley/images/shades_smile.png\" title=\"cool\" width=\"23\" /></span></strong></span></p>\r\n" + 
			"\r\n" + 
			"<p>&nbsp;</p>\r\n" + 
			"\r\n" + 
			"<p>&nbsp;</p>\r\n" + 
			"\r\n" + 
			"<p>&nbsp;</p>\r\n" + 
			"\r\n" + 
			"<p style=\"margin-left: 40px;\"><span style=\"font-size:18px;\"><span style=\"color:#A52A2A;\"><span style=\"font-family:comic sans ms,cursive;\">Please fill following fields:&nbsp;</span></span></span></p>\r\n" + 
			"\r\n" + 
			"<p><span style=\"font-size:18px;\"><span style=\"color:#A52A2A;\"><span style=\"font-family:comic sans ms,cursive;\">Source: &nbsp; &nbsp; &nbsp; &nbsp;&nbsp;<input maxlength=\"25\" name=\"txt_src\" size=\"25\" type=\"text\" /></span></span></span></p>\r\n" + 
			"\r\n" + 
			"<p><span style=\"font-size:18px;\"><span style=\"color:#A52A2A;\"><span style=\"font-family:comic sans ms,cursive;\">Destination: &nbsp;<input maxlength=\"25\" name=\"txt_dest\" size=\"25\" type=\"text\" /></span></span></span></p>\r\n" + 
			"\r\n" + 
			"<p>&nbsp;</p>\r\n" + 
			"\r\n" + 
			"<p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp;&nbsp;&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;<input type=\"submit\" value=\"Submit\" /></p>\r\n" + 
			"</form>\r\n" + 
			"</body>\r\n" + 
			"</html>\r\n" + 
			""; 
	private static String web_err = "<!doctype html>\r\n" + 
			"<html>\r\n" + 
			"<head>\r\n" + 
			"	<title></title>\r\n" + 
			"</head>\r\n" + 
			"<body>\r\n" + 
			"<form action=\"tweb\" method=\"get\">\r\n" + 
			"<p style=\"text-align: center;\"><span style=\"font-size:22px;\"><strong><span style=\"color:#A52A2A;\"><span style=\"font-family:georgia,serif;\">Welcome to Tripbuddy.</span>&nbsp;<img alt=\"cool\" height=\"23\" src=\"http://www.quackit.com/html/online-html-editor/ckeditor/ckeditor_4.4.1_full/plugins/smiley/images/shades_smile.png\" title=\"cool\" width=\"23\" /></span></strong></span></p>\r\n" + 
			"\r\n" + 
			"<p>&nbsp;</p>\r\n" + 
			"\r\n" + 
			"<p>&nbsp;</p>\r\n" + 
			"\r\n" + 
			"<p>&nbsp;</p>\r\n" + 
			"\r\n" + 
			"<p style=\"margin-left: 40px;\"><span style=\"font-size:18px;\"><span style=\"color:#A52A2A;\"><span style=\"font-family:comic sans ms,cursive;\">Please fill following fields:&nbsp;</span></span></span></p>\r\n" + 
			"\r\n" + 
			"<p><span style=\"font-family:georgia,serif;\"><span style=\"font-size:18px;\"><span style=\"color:#FF0000;\">* Please enter both Source and Destination</span></span></span></p>\r\n" + 
			"\r\n" + 
			"<p><span style=\"font-size:18px;\"><span style=\"color:#A52A2A;\"><span style=\"font-family:comic sans ms,cursive;\">Source: &nbsp; &nbsp; &nbsp; &nbsp;&nbsp;<input maxlength=\"25\" name=\"txt_src\" size=\"25\" type=\"text\" /></span></span></span></p>\r\n" + 
			"\r\n" + 
			"<p><span style=\"font-size:18px;\"><span style=\"color:#A52A2A;\"><span style=\"font-family:comic sans ms,cursive;\">Destination: &nbsp;<input maxlength=\"25\" name=\"txt_dest\" size=\"25\" type=\"text\" /></span></span></span></p>\r\n" + 
			"\r\n" + 
			"<p>&nbsp;</p>\r\n" + 
			"\r\n" + 
			"<p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp;&nbsp;&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;<input type=\"submit\" value=\"Submit\" /></p>\r\n" + 
			"</form>\r\n" + 
			"</body>\r\n" + 
			"</html>\r\n" + 
			"";
	private static String web_resp = "<!doctype html>\r\n" + 
			"<html>\r\n" + 
			"<head>\r\n" + 
			"	<title></title>\r\n" + 
			"</head>\r\n" + 
			"<body>\r\n" + 
			"<p style=\"text-align: center;\"><span style=\"font-size:22px;\"><strong><span style=\"color:#A52A2A;\"><span style=\"font-family:georgia,serif;\">Thank you for using TripBuddy!</span>&nbsp;<img alt=\"laugh\" height=\"23\" src=\"http://www.quackit.com/html/online-html-editor/ckeditor/ckeditor_4.4.1_full/plugins/smiley/images/teeth_smile.png\" title=\"laugh\" width=\"23\" /></span></strong></span></p>\r\n" + 
			"\r\n" + 
			"<p style=\"text-align: center;\"><strong><span style=\"font-size:18px;\"><span style=\"color:#A52A2A;\"><span style=\"font-family:comic sans ms,cursive;\">Weather:</span></span></span></strong></p>\r\n" + 
			"\r\n" + 
			"<table align=\"center\" border=\"1\" cellpadding=\"1\" cellspacing=\"1\" style=\"width: 800px;\">\r\n" + 
			"	<thead>\r\n" + 
			"		<tr>\r\n" + 
			"			<th scope=\"col\">&nbsp;</th>\r\n"; 
	
    /**
     * Method handling HTTP GET requests. Display a simple HTML web form to accept message initially.
     * On form summit, call REST service and return formatted response from service as HTML. 
     *
     * @return String that will be returned as a text/html response.
     */
    @GET
    @Produces(MediaType.TEXT_HTML)
    public String getEchoForm(
	        @DefaultValue("") @QueryParam("txt_src") String srcVal, @DefaultValue("") @QueryParam("txt_dest") String destVal) {
   	
    	if("".equals(srcVal) && "".equals(destVal))
    		// Initial case when no input parameter is passed. Show web form.
    		return web_init;
    	else if("".equals(srcVal) || "".equals(destVal))
    		return web_err;
    	else { 
    		// Case when input param is passed. Call REST service.
    		// We act like REST Client accepting an EchoMessage as JSON response
            Client c = ClientBuilder.newClient();
            WebTarget target = c.target(EchoWebformLauncher.restBaseUri);
            TripBuddy response = target.path("trip").queryParam("src",srcVal).queryParam("dest",destVal).request().get(TripBuddy.class); // call the web service, and deserialize the JSON response into EchoMessage
            String respStr = web_resp;
            respStr += "			<th scope=\"col\"><strong><u><span style=\"color:#A52A2A;\">Source: " + response.getNameSrc() + "</span></u><span style=\"color:#A52A2A;\">&nbsp;</span></strong></th>\r\n" + 
        			"			<th scope=\"col\"><strong><u><span style=\"color:#A52A2A;\">Destination: " + response.getNameDest() + "</span></u><span style=\"color:#A52A2A;\">&nbsp;</span></strong></th>\r\n" + 
        			"		</tr>\r\n" + 
        			"	</thead>\r\n" + 
        			"	<tbody>\r\n" + 
        			"		<tr>\r\n" + 
        			"			<td><span style=\"color:#A52A2A;\">Weather</span></td>\r\n" + 
        			"			<td><span style=\"color:#A52A2A;\">" + response.getWeatherSrc() + "</span></td>\r\n" + 
        			"			<td><span style=\"color:#A52A2A;\">" + response.getWeatherDest() + "</span></td>\r\n" + 
        			"		</tr>\r\n" + 
        			"		<tr>\r\n" + 
        			"			<td><span style=\"color:#A52A2A;\">Temperature (C)</span></td>\r\n" + 
        			"			<td><span style=\"color:#A52A2A;\">" + response.getTemp_cSrc() + "</span></td>\r\n" + 
        			"			<td><span style=\"color:#A52A2A;\">" + response.getTemp_cDest() + "</span></td>\r\n" + 
        			"		</tr>\r\n" + 
        			"		<tr>\r\n" + 
        			"			<td><span style=\"color:#A52A2A;\">Humidity</span></td>\r\n" + 
        			"			<td><span style=\"color:#A52A2A;\">" + response.getRelative_humiditySrc() + "</span></td>\r\n" + 
        			"			<td><span style=\"color:#A52A2A;\">" + response.getRelative_humidityDest() + "</span></td>\r\n" + 
        			"		</tr>\r\n" + 
        			"	</tbody>\r\n" + 
        			"</table>\r\n" + 
        			"\r\n";
            int hrs, mins, diff;
            diff = response.getTimeDiff();
            boolean isAhead = diff > 0;
            String timeDiff = response.getNameSrc() + " and " + response.getNameDest() + " have same times.";
            if(diff != 0) {
            	if(!isAhead) {
            		diff *= (-1);
            		hrs = diff / 3600;
            		mins = (diff % 3600) / 60;
            		timeDiff = response.getNameDest() + " is behind " + response.getNameSrc() + 
            				" by " + hrs + " hours & " + mins + " mins.";
            	}
            	else {
            		hrs = diff / 3600;
            		mins = (diff % 3600) / 60;
            		timeDiff = response.getNameDest() + " is ahead " + response.getNameSrc() + 
            				" by " + hrs + " hours & " + mins + " mins.";
            	}
            }
            	
            respStr += "<p style=\"text-align: center;\"><strong style=\"text-align: center;\"><span style=\"font-size: 18px;\"><span style=\"color: rgb(165, 42, 42);\"><span style=\"font-family: 'comic sans ms', cursive;\">" + timeDiff + "</span></span></span></strong></p>\r\n" + 
        			"\r\n" + 
        			"<p style=\"text-align: center;\"><strong style=\"text-align: center;\"><span style=\"font-size: 18px;\"><span style=\"color: rgb(165, 42, 42);\"><span style=\"font-family: 'comic sans ms', cursive;\">Interesting places:</span></span></span></strong></p>\r\n" + 
        			"\r\n" + 
        			"<table align=\"center\" border=\"1\" cellpadding=\"1\" cellspacing=\"1\" style=\"width: 1300px;\">\r\n" + 
        			"	<thead>\r\n" + 
        			"		<tr>\r\n" + 
        			"			<th scope=\"col\"><span style=\"color:#A52A2A;\"><font face=\"lucida sans unicode, lucida grande, sans-serif\"><span style=\"font-size: 14px;\">Name</span></font></span></th>\r\n" + 
        			"			<th scope=\"col\"><span style=\"color:#A52A2A;\"><strong><span style=\"font-size:14px;\"><span style=\"font-family:lucida sans unicode,lucida grande,sans-serif;\">Types</span></span></strong></span></th>\r\n" + 
        			"			<th scope=\"col\"><span style=\"color:#A52A2A;\"><strong><span style=\"font-size:14px;\"><span style=\"font-family:lucida sans unicode,lucida grande,sans-serif;\">Address</span></span></strong></span></th>\r\n" + 
        			"			<th scope=\"col\"><span style=\"color:#A52A2A;\"><strong><span style=\"font-size:14px;\"><span style=\"font-family:lucida sans unicode,lucida grande,sans-serif;\">Rating</span></span></strong></span></th>\r\n" + 
        			"			<th scope=\"col\"><span style=\"color:#A52A2A;\"><strong><span style=\"font-size:14px;\"><span style=\"font-family:lucida sans unicode,lucida grande,sans-serif;\">Is it open now?</span></span></strong></span></th>\r\n" + 
        			"		</tr>\r\n" + 
        			"	</thead>\r\n" + 
        			"	<tbody>\r\n";
            Place[] places = response.getInterestingPlaces();
            for(int i = 0; i < places.length; i++) {
            	respStr += "		<tr>\r\n" + 
            			"			<td><span style=\"color:#A52A2A;\">" + places[i].getName() + "</span></td>\r\n" + 
            			"			<td><span style=\"color:#A52A2A;\">" + places[i].getTypes() + "</span></td>\r\n" + 
            			"			<td><span style=\"color:#A52A2A;\">" + places[i].getVicinity() + "</span></td>\r\n" + 
            			"			<td><span style=\"color:#A52A2A;\">" + (places[i].getRating() == -1 ? "-" : places[i].getRating()) + "</span></td>\r\n" +
            			"			<td><span style=\"color:#A52A2A;\">" + (places[i].isOpen_now() ? "Yes" : "No") + "</span></td>\r\n" +
            			"		</tr>\r\n";
            }
        	respStr += "	</tbody>\r\n" + 
        			"</table>\r\n" + 
        			"\r\n" + 
        			"<p>&nbsp;</p>\r\n" + 
        			"</body>\r\n" + 
        			"</html>\r\n" + 
        			"";
            return respStr;
    	}
    }
}
