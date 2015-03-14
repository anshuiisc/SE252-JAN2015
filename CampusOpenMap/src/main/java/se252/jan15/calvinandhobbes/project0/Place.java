package se252.jan15.calvinandhobbes.project0;

import org.json.JSONObject;

public class Place {

	private String name;
	private String types;
	private String vicinity;
	private double rating;
	private boolean open_now;
	
	public Place() {}
	
	public Place(JSONObject jsonObj) {
		this.setName(jsonObj.getString("name"));
		if(jsonObj.has("opening_hours"))
			this.setOpen_now(jsonObj.getJSONObject("opening_hours").getBoolean("open_now"));
		else 
			this.setOpen_now(false);
		this.setTypes(jsonObj.get("types").toString());
		this.setVicinity(jsonObj.getString("vicinity"));
		if(jsonObj.has("rating"))
			this.setRating(jsonObj.getDouble("rating"));
		else
			this.setRating(-1);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTypes() {
		return types;
	}

	public void setTypes(String types) {
		this.types = types;
	}

	public String getVicinity() {
		return vicinity;
	}

	public void setVicinity(String vicinity) {
		this.vicinity = vicinity;
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

	public boolean isOpen_now() {
		return open_now;
	}

	public void setOpen_now(boolean open_now) {
		this.open_now = open_now;
	}
	
}
