package ca.ualberta.cs.queueunderflow;

import android.content.Context;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import ca.ualberta.cs.queueunderflow.views.MainActivity;


//needs work but need to know if we need to use google play services or not and what extent of location services we need.
public class Location{

	/* Use the LocationManager class to obtain GPS locations */
	//LocationManager locManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
	//LocationListener locListener = new MyLocationListener();
	
	public void GPSListener(Context ctx)
	{
		LocationManager locManager = (LocationManager) ctx.getSystemService(Context.LOCATION_SERVICE);
		LocationListener listener = new LocationListener() {

			@Override
			public void onLocationChanged(android.location.Location location) {
				// TODO Auto-generated method stub
				Double latitude = location.getLatitude();
				Double longitude = location.getLongitude();
				
				User.latitude = latitude;
				User.longitude = longitude;
				
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
		
		locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 60000, 0, listener); //Only update once every minute. Too short??
	}
	
}
