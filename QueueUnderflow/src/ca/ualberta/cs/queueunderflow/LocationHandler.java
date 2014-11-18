package ca.ualberta.cs.queueunderflow;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;


public class LocationHandler implements LocationListener{

	/* Use the LocationManager class to obtain GPS locations */
	//LocationManager locManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
	//LocationListener locListener = new MyLocationListener();
	
	LocationManager locManager;
	Context ctx;
	
	//Static variables.
	public static double longitude;
	public static double latitude;
	
	final long minGPSUpdateTime = 60000; //This is how often GPS will check for new position in milliseconds. Lower values drains battery apparently
	
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
	 * Return a string indicating the location from GPS coordinates.
	 * @param latitude
	 * @param longitude
	 * @return Address string. Returns 'unknown' if unable to find one.
	 */
	public String getLocationFromCoordinates(double latitude, double longitude)
	{
		//This returns a string formated like "CITY, COUNTRY"
		//eg. "Edmonton, Canada"
		
		//This REQUIRES an Internet connection to run.
		
		//Apparently geocoder is REALLY REALLY slow and is intended to run in the background.
		//So if we use this it should be called in an AsyncTask background while the rest of the application runs.
		//Mentioned in the link below.
		
		//Lots of this is taken from http://developer.android.com/training/location/display-address.html
		Geocoder geocoder = new Geocoder(ctx, Locale.getDefault());
		List<Address> addresses = null;
		
		try {
			addresses = geocoder.getFromLocation(latitude, longitude, 1);
		} catch (IOException e1) {
			// IOException. No idea when this will run.
			e1.printStackTrace();
		} catch (IllegalArgumentException e2) {
			//Illegal arguments! Something wrong with the coordinates passed.
			//Most likely longitude and latitude is unset with no values available.
			//For now, let's just say the user is at "Unknown"
			return "Unknown";
		}
		
		if (addresses != null && addresses.size() > 0) {
			
			//It returned an address.
			
			String addressString;
			
			Address address = addresses.get(0);
			addressString = String.format("%s, %s", address.getLocality(), address.getCountryName());
			return addressString;
			
		} else {
			
			//If getFromLocation returns no addresses at all.......
			//This happens if the user isn't in a country
			//example: adding questions while in the middle of the Atlantic ocean.
			
			//In this situation what are we supposed to do?
			//For now, return unknown. (maybe return coordinates later)?
			
			return "Unknown";
			
		}
		
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
	 */
	public void onLocationChanged(Location location)
	{
		Double latitude = location.getLatitude();
		Double longitude = location.getLongitude();
		
		LocationHandler.latitude = latitude;
		LocationHandler.longitude = longitude;
		GPSUnlisten();
	};
	
	/**
	 * Listen for GPS updates (only updates ONCE)
	 */
	public void GPSListen()
	{
		locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, minGPSUpdateTime, 0, this);
	}
	
	//Useless functions we don't need.
	public void onProviderDisabled(String arg) {}
	public void onProviderEnabled(String arg) {}
	public void onStatusChanged(String arg, int arg2, Bundle arg3) {}
	
}
