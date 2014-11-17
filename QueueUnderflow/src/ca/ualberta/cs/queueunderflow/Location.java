package ca.ualberta.cs.queueunderflow;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;


public class Location{

	/* Use the LocationManager class to obtain GPS locations */
	//LocationManager locManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
	//LocationListener locListener = new MyLocationListener();
	
	LocationManager locManager;
	LocationListener listener;
	Context ctx;
	
	//Static variables.
	public static double longitude;
	public static double latitude;
	
	final long minGPSUpdateTime = 60000; //This is how often GPS will check for new position in milliseconds. Lower values drains battery apparently
	
	public Location(Context context)
	{
		ctx = context;
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
	
	public void GPSUnlisten()
	{
		locManager.removeUpdates(listener);
	}
	
	public void GPSListen()
	{
		locManager = (LocationManager) ctx.getSystemService(Context.LOCATION_SERVICE);
		listener = new LocationListener() {

			@Override
			public void onLocationChanged(android.location.Location location) {
				// TODO Auto-generated method stub
				Double latitude = location.getLatitude();
				Double longitude = location.getLongitude();
				
				Location.latitude = latitude;
				Location.longitude = longitude;
				
			}

			@Override
			public void onStatusChanged(String provider, int status,
					Bundle extras) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onProviderEnabled(String provider) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onProviderDisabled(String provider) {
				// TODO Auto-generated method stub
				
			}
			
		};
		
		locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, minGPSUpdateTime, 0, listener);
	}
	
}
