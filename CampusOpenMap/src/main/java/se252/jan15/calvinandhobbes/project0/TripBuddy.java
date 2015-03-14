package se252.jan15.calvinandhobbes.project0;

import java.util.ArrayList;

public class TripBuddy {
	private String nameSrc;
	private String nameDest;
	private String weatherSrc;
	private int temp_cSrc;
	private String relative_humiditySrc;
	private String weatherDest;
	private int temp_cDest;
	private String relative_humidityDest;
	private int timeDiff;
	private Place[] interestingPlaces;
	
	public TripBuddy() {}
	
	public TripBuddy(Location src, Location dest, 
			TimeZone srcTime, TimeZone destTime, 
			ArrayList<Place> places) {
		this.setNameSrc(src.getCity());
		this.setWeatherSrc(src.getWeather());
		this.setTemp_cSrc(src.getTemp_c());
		this.setRelative_humiditySrc(src.getRelative_humidity());
		this.setNameDest(dest.getCity());
		this.setWeatherDest(dest.getWeather());
		this.setTemp_cDest(dest.getTemp_c());
		this.setRelative_humidityDest(dest.getRelative_humidity());
		this.setTimeDiff(destTime.getRawOffset() - srcTime.getRawOffset());
		Place[] allPlaces = new Place[places.size()];
		places.toArray(allPlaces);
		this.setInterestingPlaces(allPlaces);
	}

	public String getNameSrc() {
		return nameSrc;
	}

	public void setNameSrc(String nameSrc) {
		this.nameSrc = nameSrc;
	}

	public String getNameDest() {
		return nameDest;
	}

	public void setNameDest(String nameDest) {
		this.nameDest = nameDest;
	}

	public String getWeatherSrc() {
		return weatherSrc;
	}

	public void setWeatherSrc(String weatherSrc) {
		this.weatherSrc = weatherSrc;
	}

	public int getTemp_cSrc() {
		return temp_cSrc;
	}

	public void setTemp_cSrc(int temp_cSrc) {
		this.temp_cSrc = temp_cSrc;
	}

	public String getRelative_humiditySrc() {
		return relative_humiditySrc;
	}

	public void setRelative_humiditySrc(String relative_humiditySrc) {
		this.relative_humiditySrc = relative_humiditySrc;
	}

	public String getWeatherDest() {
		return weatherDest;
	}

	public void setWeatherDest(String weatherDest) {
		this.weatherDest = weatherDest;
	}

	public int getTemp_cDest() {
		return temp_cDest;
	}

	public void setTemp_cDest(int temp_cDest) {
		this.temp_cDest = temp_cDest;
	}

	public String getRelative_humidityDest() {
		return relative_humidityDest;
	}

	public void setRelative_humidityDest(String relative_humidityDest) {
		this.relative_humidityDest = relative_humidityDest;
	}

	public int getTimeDiff() {
		return timeDiff;
	}

	public void setTimeDiff(int timeDiff) {
		this.timeDiff = timeDiff;
	}

	public Place[] getInterestingPlaces() {
		return interestingPlaces;
	}

	public void setInterestingPlaces(Place[] interestingPlaces) {
		this.interestingPlaces = interestingPlaces;
	}
}
