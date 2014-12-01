package ca.ualberta.cs.queueunderflow.singletons;

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


/**
 * The Class LocationHandler.
 * @author group 10
 * @version 1.0
 */
public class LocationHandler implements LocationListener{

	/* Use the LocationManager class to obtain GPS locations */
	
	/** The loc manager. */
	LocationManager locManager;
	
	/** The ctx. */
	Context ctx;
	
	/** The longitude. */
	public static double longitude;
	
	/** The latitude. */
	public static double latitude;
	
	/** The listening gps. */
	public static boolean listeningGPS = false;
	
	/** The min gps update time. */
	final long minGPSUpdateTime = 0; //This is how often GPS will check for new position in milliseconds.
										  //Lower values drains battery apparently
	
	/**
  	* Instantiates a new location handler.
  	*
  	* @param context the context
  	*/
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
		return locManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
	}
	
	/**
	 * Checks if you can get your location from your network. Returns true if it is, false if it isn't
	 * @return Boolean that states if network location is valid.
	 */
	public boolean isNetworkEnabled()
	{
		return locManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
	}
	
	/**
	 * Return a string indicating the location from GPS coordinates.
	 *
	 * @param latitude the latitude
	 * @param longitude the longitude
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
		sCity = address.optString("city");
		if(sCity.equals("")) sCity = address.optString("town");
		if(sCity.equals("")) sCity = address.optString("hamlet");
		
		//No city at all? set to unknown
		if(sCity.equals("")) sCity = "Unknown";
		
		//http://nominatim.openstreetmap.org/reverse?format=json&lat=53.526797&lon=-113.5273&zoom=18&addressdetails=1
		
		
		return sCity + "|" + sCountry;
	}
	

	/**
	 * Tell Location to stop listening for GPS update events.
	 */
	public void GPSUnlisten()
	{
		locManager.removeUpdates(this);
	}
	
	/**
	 * Tell Location to listen for GPS update events. New data is stored in latitude/longitude.
	 *
	 * @param location the location
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
	 * Listen for GPS updates (only updates ONCE).
	 */
	public void GetGPSLocation()
	{
		LocationHandler.listeningGPS = true;
		if(this.isGPSEnabled()) locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, minGPSUpdateTime, 0, this);
		if(this.isNetworkEnabled()) locManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, minGPSUpdateTime, 0, this);
	}
	
	//Useless functions we don't need.
	/* (non-Javadoc)
	 * @see android.location.LocationListener#onProviderDisabled(java.lang.String)
	 */
	public void onProviderDisabled(String arg) {}
	
	/* (non-Javadoc)
	 * @see android.location.LocationListener#onProviderEnabled(java.lang.String)
	 */
	public void onProviderEnabled(String arg) {}
	
	/* (non-Javadoc)
	 * @see android.location.LocationListener#onStatusChanged(java.lang.String, int, android.os.Bundle)
	 */
	public void onStatusChanged(String arg, int arg2, Bundle arg3) {}
	
}
