package se252.jan15.calvinandhobbes.project0;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

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
@Path("echo")
public class EchoService {

    /**
     * Method handling HTTP GET requests. The request expects a "msg" input parameter with value of type string. 
     * The returned object will be sent to the client as JSON media type.
     *
     * @return JSON form of EchoMessage will be returned as response.
     */
    @GET
    //@Produces(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public EchoMessage echoMessage(
	        @DefaultValue("") @QueryParam("msg") String message) {
    		return new EchoMessage("I heard you say '"+message+"'");
    }
}
