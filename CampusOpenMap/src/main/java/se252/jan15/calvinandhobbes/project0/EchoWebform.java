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
@Path("web")
public class EchoWebform {

    /**
     * Method handling HTTP GET requests. Display a simple HTML web form to accept message initially.
     * On form summit, call REST service and return formatted response from service as HTML. 
     *
     * @return String that will be returned as a text/html response.
     */
    @GET
    @Produces(MediaType.TEXT_HTML)
    public String getEchoForm(
	        @DefaultValue("") @QueryParam("inParam") String textValue) {
   	
    	if("".equals(textValue)) {
    		// Initial case when no input parameter is passed. Show web form.
    		return 
    				"<html><body><h2>Welcome to SE252:Jan15:Project 0</h2>"+
    				"<form action=\"web\" method=\"get\">Enter Message Text Value <input type=\"text\" name=\"inParam\"><br>" + 
    				"<input type=\"submit\" value=\"Submit\"></form>"+
    				"</body></html>";
    	} else { 
    		// Case when input param is passed. Call REST service.
    		// We act like REST Client accepting an EchoMessage as JSON response
            Client c = ClientBuilder.newClient();
            WebTarget target = c.target(EchoWebformLauncher.restBaseUri);
            EchoMessage responseMsg = target.path("echo").queryParam("msg",textValue).request().get(EchoMessage.class); // call the web service, and deserialize the JSON response into EchoMessage
            return "<html><body><h2>Welcome to SE252:Jan15:Project 0</h2>Remote server said <em>"+responseMsg.getMessage()+"</em></body></html>";
    	}
    }
}
