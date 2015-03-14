package se252.jan15.calvinandhobbes.project0;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;


import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.ProxySelector;
import java.net.URI;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

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
//import org.json.simple.JSONObject;
import org.json.JSONObject;
/**
 * tripInfo web service exposed at "trip" path relative to base URI
 * 
 * @author Arnab sen
 * @version 1.0
 * @see <a href="http://www.serc.iisc.ernet.in/~simmhan/SE252/">IISc SERC 'SE252:Intro to Cloud Computing' Course Webpage</a>
 * 
 * (c) Arnab sen, 2015
 * This work is licensed under a Attribution 4.0 International (CC BY 4.0).
 * http://creativecommons.org/licenses/by/4.0/
 */
@Path("map")
public class mapService {
  
    /**
     * Method handling HTTP GET requests. The request expects a "msg" input parameter with value of type string. 
     * The returned object will be sent to the client as JSON media type.
     *
     * @return JSON form of EchoMessage will be returned as response.
     * @throws IOException 
     */
    // 	
    //@Produces(MediaType.TEXT_PLAIN)
  //  @Produces(MediaType.APPLICATION_JSON)
    @GET
    @Produces(MediaType.TEXT_HTML)
    public String tripinfo() throws IOException {
    	String proxyHost="";
    	int proxyPort=-1;
    	String proxyType="";
    	//System.out.println( "Map program");
    	 try {
             
             System.setProperty("java.net.useSystemProxies","true");
             List l = ProxySelector.getDefault().select(
                         new URI("http://www.google.com/"));
             
             for (Iterator iter = l.iterator(); iter.hasNext(); ) {
                 
                 Proxy proxy = (Proxy) iter.next();
                 proxyType=proxy.type().toString();
                // System.out.println("proxy type : " + proxy.type());
                 
                 InetSocketAddress addr = (InetSocketAddress)
                     proxy.address();
                 
                 if(addr == null) {
                     
                 //    System.out.println("No Proxy");
                     
                 } else {
                     
                /*  System.out.println("proxy hostname : " + 
                             addr.getHostName());
                */     proxyHost=addr.getHostName();
                     
              //       System.out.println("proxy port : " + 
                //             addr.getPort());
                     proxyPort=addr.getPort();
                 }
             }
         } catch (Exception e) {
             e.printStackTrace();
         }
    	
	
    	Proxy proxy1 = null;

    	if(!"".equals(proxyHost)){

    		proxy1 = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyHost, proxyPort));
    	}
    	
    	Scanner map = new Scanner(new File("C:\\Users\\Arnab\\Desktop\\SE252JAN2015PROJ-0$Arnab sen\\se252-jan15\\project-0\\src\\main\\java\\se252\\jan15\\calvinandhobbes\\project0\\map.html"));
    	
    	String mapstr = map.useDelimiter("\\Z").next();
    	//System.out.println("3");
    	map.close();
    	return mapstr;
    } 


	
   
    	
    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
          sb.append((char) cp);
        }
        return sb.toString();
      }    
}