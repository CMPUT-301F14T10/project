package ca.ualberta.cs.queueunderflow;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkManager {

	private static NetworkManager instance = null;
	private ConnectivityManager connectManager;
	private NetworkBuffer networkBuffer;
	
	private String onlineStatus = null;
	
	private NetworkManager() {
		networkBuffer = new NetworkBuffer();
	}
	
	public static NetworkManager getInstance() {
		if (instance == null) {
			instance = new NetworkManager();
		}
		return instance;
	}
	
	// should we call this hasConnection instead of isOnline? - This returns true if we're connected and false otherwise - Note this only checks for 3g network and not wifi I think
	public boolean isOnline(Context context) {
		if (onlineStatus != null) {
			return Boolean.getBoolean(onlineStatus);
		}
		
		connectManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connectManager.getActiveNetworkInfo();
		if (networkInfo != null && networkInfo.isConnected()) {
			return true;
		}
		return false;
	}
	
	public NetworkBuffer getNetworkBuffer() {
		return networkBuffer;
	}
	
	public void flushBuffer() {
		networkBuffer.flushAll();
	}
	
	// For changing network status in tests
	public void setOnline(boolean onlineStatus) {
		this.onlineStatus = Boolean.toString(onlineStatus);
	}
}


