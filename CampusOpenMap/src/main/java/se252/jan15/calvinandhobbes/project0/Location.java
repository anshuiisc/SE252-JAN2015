package se252.jan15.calvinandhobbes.project0;

import org.json.JSONObject;

public class Location {
	private String city;
	private long local_epoch;
	private double latitude;
	private double longitude;
	private String weather;
	private int temp_c;
	private String relative_humidity;
	private double pressure_in;

	public Location() {}
	
	public Location(JSONObject jsonLocation) {
		JSONObject curObs = jsonLocation.getJSONObject("current_observation");
		JSONObject dispLoc = curObs.getJSONObject("display_location");
		this.setCity(dispLoc.getString("city"));
		this.setLatitude(dispLoc.getDouble("latitude"));
		this.setLongitude(dispLoc.getDouble("longitude"));
		this.setLocal_epoch(curObs.getLong("local_epoch"));
		this.setPressure_in(curObs.getDouble("pressure_in"));
		this.setRelative_humidity(curObs.getString("relative_humidity"));
		this.setTemp_c(curObs.getInt("temp_c"));
		this.setWeather(curObs.getString("weather"));
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public long getLocal_epoch() {
		return local_epoch;
	}

	public void setLocal_epoch(long local_epoch) {
		this.local_epoch = local_epoch;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public String getWeather() {
		return weather;
	}

	public void setWeather(String weather) {
		this.weather = weather;
	}

	public int getTemp_c() {
		return temp_c;
	}

	public void setTemp_c(int temp_c) {
		this.temp_c = temp_c;
	}

	public String getRelative_humidity() {
		return relative_humidity;
	}

	public void setRelative_humidity(String relative_humidity) {
		this.relative_humidity = relative_humidity;
	}

	public double getPressure_in() {
		return pressure_in;
	}

	public void setPressure_in(double pressure_in) {
		this.pressure_in = pressure_in;
	}
}
