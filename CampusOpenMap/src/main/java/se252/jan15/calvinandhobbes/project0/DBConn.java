package se252.jan15.calvinandhobbes.project0;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

//import com.microsoft.sqlserver.jdbc.*;

public class DBConn {
	private static Connection conn = null;
	
	private static Connection getConn() {
		if(conn == null) {
			IIScCampusMapProperties prop = new IIScCampusMapProperties();
			String forName, host, username, password, database;
			forName = prop.getProperty("forName");
			host = prop.getProperty("host");
			username = prop.getProperty("username");
			password = prop.getProperty("password");
			database = prop.getProperty("database");
			try {
				Class.forName(forName);
				String url = "jdbc:sqlserver://"+host+";user="+username+";password="+password+";database="+database;
				conn = DriverManager.getConnection(url);
			}
			catch(Exception e) {
				System.out.println(e);
			}
		}
		return conn;
	}
	
	public static LayerInfo[] getCategoryInfo(String category) {
		LayerInfo[] layerArray = null;
		ArrayList<LayerInfo> layers = new ArrayList<LayerInfo>();
		Connection sqlConn = getConn();
		String queryString = "select * from PointOfInterest where CatId = (select catId from Category where CatName = '"+category+"')";
		try {
			Statement stmt = sqlConn.createStatement();
			ResultSet rs = stmt.executeQuery("select * from Category where CatName = '"+category+"'");
			if(!rs.next()) {
				System.out.println("Invalide category "+ category +" asked");
				stmt.close();
				return null;
			}
			rs = stmt.executeQuery(queryString);
			while(rs.next()) {
				LayerInfo lf = new LayerInfo();
				lf.setName(rs.getString(1));
				lf.setCategory(category);
				lf.setLatitude(rs.getFloat(3));
				lf.setLongitude(rs.getFloat(4));
				lf.setDescription(rs.getString(5));
				layers.add(lf);
			}
			stmt.close();
		}
		catch(Exception e) {
			System.out.println(e);
		}
		layerArray = new LayerInfo[layers.size()];
		layers.toArray(layerArray);
		return layerArray;
	}
	
	public static LayerInfo[] getCategories() {
		LayerInfo[] layerArray = null;
		ArrayList<LayerInfo> layers = new ArrayList<LayerInfo>();
		Connection sqlConn = getConn();
		String queryString = "select CatName from Category order by CatId";
		try {
			Statement stmt = sqlConn.createStatement();
			ResultSet rs = stmt.executeQuery(queryString);
			while(rs.next()) {
				LayerInfo lf = new LayerInfo();
				lf.setName(rs.getString(1));
				lf.setCategory("");
				lf.setLatitude(0);
				lf.setLongitude(0);
				lf.setDescription("");
				layers.add(lf);
			}
			stmt.close();
		}
		catch(Exception e) {
			System.out.println(e);
		}
		layerArray = new LayerInfo[layers.size()];
		layers.toArray(layerArray);
		return layerArray;
	}
	
	public static boolean insertData(LayerInfo layer) {
		boolean ret = true;
		String queryStr = "insert into PointOfInterest values ('";
		queryStr += layer.getName() + "', ";
		queryStr += layer.getCategory() + ", ";
		queryStr += layer.getLatitude() + ", ";
		queryStr += layer.getLongitude() + ", '";
		queryStr += layer.getAddress() + "', ' ')";
		
		Connection sqlConn = getConn();
		try {
			Statement stmt = sqlConn.createStatement();
			stmt.execute(queryStr);
			stmt.close();
		}
		catch(Exception e) {
			System.out.println(queryStr);
			ret = false;
			System.out.println(e);
		}
		
		return ret;
	}
}