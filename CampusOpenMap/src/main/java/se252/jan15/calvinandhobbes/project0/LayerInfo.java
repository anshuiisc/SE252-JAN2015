package se252.jan15.calvinandhobbes.project0;

import org.json.JSONObject;

public class LayerInfo {
	private String name;
	private String category;
	private String description;
	private float latitude;
	private float longitude;
	private String address;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
//		if(name.equals("JNC President's House Guest Rooms"))
//			name = "JNC President''s House Guest Rooms";
		this.name = name;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public float getLatitude() {
		return latitude;
	}
	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}
	public float getLongitude() {
		return longitude;
	}
	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}

	public LayerInfo() {}
	
	public LayerInfo(String name, String category, String description,
			float latitude, float longitude, String address) {
		super();
		this.name = name;
		this.category = category;
		this.description = description;
		this.latitude = latitude;
		this.longitude = longitude;
		this.address = address;
	}
	
	public LayerInfo(JSONObject jsonObj, String category) {
		this.setName(jsonObj.getString("name"));
		this.setLatitude((float)jsonObj.getJSONObject("geometry").getJSONObject("location").getDouble("lat"));
		this.setLongitude((float)jsonObj.getJSONObject("geometry").getJSONObject("location").getDouble("lng"));
		this.setAddress(jsonObj.getString("vicinity"));
		this.setCategory(category);
		this.setDescription("");
	}
}
