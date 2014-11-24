package ca.ualberta.cs.queueunderflow;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * The Class NetworkManager.
 */
public class NetworkManager {

	/** The instance. */
	private static NetworkManager instance = null;
	
	/** The network buffer. */
	private NetworkBuffer networkBuffer;
	
	/** The connect manager. */
	private ConnectivityManager connectManager;

	
	/**
	 * Instantiates a new network manager.
	 */
	private NetworkManager() {
		networkBuffer = new NetworkBuffer();
	}
	
	/**
	 * Gets the single instance of NetworkManager.
	 *
	 * @return single instance of NetworkManager
	 */
	public static NetworkManager getInstance() {
		if (instance == null) {
			instance = new NetworkManager();
		}
		return instance;
	}
	
	/**
	 * Gets the network buffer.
	 *
	 * @return the network buffer
	 */
	public NetworkBuffer getNetworkBuffer() {
		return networkBuffer;
	}
	
	// should we call this hasConnection instead of isOnline? - This returns true if we're connected and false otherwise - Note this only checks for 3g network and not wifi I think
	/**
	 * Checks if is online.
	 *
	 * @param context the context
	 * @return true, if is online
	 */
	public boolean isOnline(Context context) {
		connectManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connectManager.getActiveNetworkInfo();
		if (networkInfo != null && networkInfo.isConnected()) {
			return true;
		}
		return false;
	}
	
	/**
	 * Flush buffer.
	 */
	public void flushBuffer() {
		networkBuffer.flushAll();
	}

}


