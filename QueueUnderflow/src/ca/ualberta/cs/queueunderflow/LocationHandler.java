package ca.ualberta.cs.queueunderflow;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Locale;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;


public class LocationHandler implements LocationListener{

	/* Use the LocationManager class to obtain GPS locations */
	//LocationManager locManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
	//LocationListener locListener = new MyLocationListener();
	
	LocationManager locManager;
	Context ctx;
	
	//Static variables.
	public static double longitude;
	public static double latitude;
	public static boolean listeningGPS = false;
	
	final long minGPSUpdateTime = 0; //This is how often GPS will check for new position in milliseconds.
										  //Lower values drains battery apparently
	
	public LocationHandler(Context context)
	{
		ctx = context;
		locManager = (LocationManager) ctx.getSystemService(Context.LOCATION_SERVICE);
	}
	
	/**
	 * Checks if GPS is enabled. Returns true if it is, false if it isn't.
	 * @return Boolean that states if GPS is enabled.
	 */
	public boolean isGPSEnabled()
	{
		if(!locManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
			//GPS is not enabled.
			return false;
		}
		return true;
	}
	
	/**
	 * Return a string indicating the location from GPS coordinates
	 * @param latitude
	 * @param longitude
	 * @return Returns in format "City|Country". Returns null if unable to find one.
	 */
	public String getLocationFromCoordinates(double latitude, double longitude)
	{
		//Only use this on another thread...
		//Requires an internet connection, etc.
		
		//Some of this is taken from http://stackoverflow.com/questions/13196234/simple-parse-json-from-url-on-android for inspiration
		//author: Asok
		
		String sLatitude = Double.toString(latitude);
		String sLongitude = Double.toString(longitude);
		
		String sCountry;
		String sCity;
		
		String urlString = "http://nominatim.openstreetmap.org/reverse?format=json&lat="+latitude+"&lon="+longitude+"&zoom=18&addressdetails=1";
		String jsonString = null;
		
		HttpClient httpClient = new DefaultHttpClient();
		
		HttpGet httpget = new HttpGet(urlString);
		try {
			HttpResponse httpResponse = httpClient.execute(httpget);
			HttpEntity he = httpResponse.getEntity();
			InputStream is = he.getContent();
			
			//Convert this to a string.
			BufferedReader reader = new BufferedReader(new InputStreamReader(is));
			StringBuilder sBuilder = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null) {
				sBuilder.append(line);
			}
			
			jsonString = sBuilder.toString();
			
		} catch (Exception e) {
			return "Unknown|Unknown";
		}
		
		JSONObject jsonObject = null;
		try {
			jsonObject = new JSONObject(jsonString);
		} catch (JSONException e) {
			//Failed to convert to jsonObject. Return unknown
			return "Unknown|Unknown";
		}
		
		JSONObject address = null;
		try {
			address = jsonObject.getJSONObject("address");
		} catch (JSONException e) {
			// No address value returned... return Unknown
			return "Unknown|Unknown";
		}
		
		Log.d("lolol", address.toString());
		
		if(address.has("country")) {
			sCountry = address.optString("country");
		}else{
			//No country, return unknown
			return "Unknown|Unknown";
		}
		
		//Depending on where the user is, city may be named something else...
		//Try a bunch
		sCity = address.optString("city");
		if(sCity.equals("")) sCity = address.optString("town");
		if(sCity.equals("")) sCity = address.optString("hamlet");
		
		//No city at all? set to unknown
		if(sCity.equals("")) sCity = "Unknown";
		
		//http://nominatim.openstreetmap.org/reverse?format=json&lat=53.526797&lon=-113.5273&zoom=18&addressdetails=1
		
		
		return sCity + "|" + sCountry;
	}
	
	/*
	public String getLocationFromCoordinates(double latitude, double longitude)
	{
		//This returns a string formated like "CITY, COUNTRY"
		//eg. "Edmonton,Canada"
		
		//This REQUIRES an Internet connection to run.
		
		
		//Lots of this is taken from http://developer.android.com/training/location/display-address.html
		Geocoder geocoder = new Geocoder(ctx, Locale.getDefault());
		List<Address> addresses = null;
		
		try {
			addresses = geocoder.getFromLocation(latitude, longitude, 1);
		} catch (IOException e1) {
			// IOException. No idea when this will run.
			return null;
		} catch (IllegalArgumentException e2) {
			//Illegal arguments! Something wrong with the coordinates passed.
			//Most likely longitude and latitude is unset with no values available.
			//For now, let's just say the user is at "Unknown"
			return null;
		}
		
		if (addresses != null && addresses.size() > 0) {
			
			//It returned an address.
			
			String addressString;
			
			Address address = addresses.get(0);
			addressString = String.format("%s|%s", address.getLocality(), address.getCountryName());
			return addressString;
			
		} else {
			
			//If getFromLocation returns no addresses at all.......
			//This happens if the user isn't in a country
			//example: adding questions while in the middle of the Atlantic ocean.
			
			//In this situation what are we supposed to do?
			//For now, return unknown. (maybe return coordinates later)?
			
			return null;
			
		}
		
	}*/
	
	/**
	 * Tell Location to stop listening for GPS update events.
	 */
	public void GPSUnlisten()
	{
		locManager.removeUpdates(this);
	}
	
	/**
	 * Tell Location to listen for GPS update events. New data is stored in latitude/longitude.
	 */
	public void onLocationChanged(Location location)
	{
		Double latitude = location.getLatitude();
		Double longitude = location.getLongitude();
		
		LocationHandler.latitude = latitude;
		LocationHandler.longitude = longitude;
		LocationHandler.listeningGPS = false;
		GPSUnlisten();
	}
	
	/**
	 * Listen for GPS updates (only updates ONCE)
	 */
	public void GetGPSLocation()
	{
		//ONLY USE THIS IN ANOTHER THREAD
		LocationHandler.listeningGPS = true;
		locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, minGPSUpdateTime, 0, this);
	}
	
	//Useless functions we don't need.
	public void onProviderDisabled(String arg) {}
	public void onProviderEnabled(String arg) {}
	public void onStatusChanged(String arg, int arg2, Bundle arg3) {}
	
}
