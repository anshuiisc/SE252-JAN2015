package se252.jan15.calvinandhobbes.project0;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class IIScCampusMapProperties {
	
	private static Properties prop = null;
	private final static String propFileName = "se252\\jan15\\calvinandhobbes\\project0\\resources\\config.properties";
	private InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
	
	public String getProperty(String key) {
		if(prop == null) {
			prop = new Properties();

			try {
				if (inputStream != null) {
					prop.load(inputStream);
				} else {
					throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
				}
			}
			catch(IOException e) {
				System.out.println(e);
			}
		}
		return prop.getProperty(key);
	}
}
