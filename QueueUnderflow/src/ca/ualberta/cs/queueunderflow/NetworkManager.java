package ca.ualberta.cs.queueunderflow;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkManager {

	private static NetworkManager instance = null;
	private NetworkBuffer networkBuffer;
	private ConnectivityManager connectManager;

	
	private NetworkManager() {
		networkBuffer = new NetworkBuffer();
	}
	
	public static NetworkManager getInstance() {
		if (instance == null) {
			instance = new NetworkManager();
		}
		return instance;
	}
	
	public NetworkBuffer getNetworkBuffer() {
		return networkBuffer;
	}
	
	// should we call this hasConnection instead of isOnline? - This returns true if we're connected and false otherwise - Note this only checks for 3g network and not wifi I think
	public boolean isOnline(Context context) {
		connectManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connectManager.getActiveNetworkInfo();
		if (networkInfo != null && networkInfo.isConnected()) {
			return true;
		}
		return false;
	}
	
	public void flushBuffer() {
		networkBuffer.flushAll();
	}

}


