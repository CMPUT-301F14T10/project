package ca.ualberta.cs.queueunderflow.singletons;

import ca.ualberta.cs.queueunderflow.NetworkBuffer;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * The Class NetworkManager.
 * Singleton.
 * @author group 10
 * @version 1.0
 */
public class NetworkManager {

	/** The instance. */
	private static NetworkManager instance = null;
	
	/** The connect manager. */
	private ConnectivityManager connectManager;
	
	/** The network buffer. */
	private NetworkBuffer networkBuffer;
	
	/** The online status. */
	private String onlineStatus = null;
	
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
	 * Checks if is online.
	 *
	 * @param context the context
	 * @return true, if is online. false otherwise
	 */
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
	
	/**
	 * Gets the network buffer.
	 *
	 * @return the network buffer
	 */
	public NetworkBuffer getNetworkBuffer() {
		return networkBuffer;
	}
	
	/**
	 * Flush buffer.
	 */
	public void flushBuffer() {
		networkBuffer.flushAll();
	}
	
	// For changing network status in tests
	/**
	 * Sets the online.
	 *
	 * @param onlineStatus the new online
	 */
	public void setOnline(boolean onlineStatus) {
		this.onlineStatus = Boolean.toString(onlineStatus);
	}

	//Probably used to set the network buffer to that of shared preferences
	/**
	 * Sets the network buffer.
	 *
	 * @param networkBuffer the new network buffer
	 */
	public void setNetworkBuffer(NetworkBuffer networkBuffer) {
		this.networkBuffer=networkBuffer;
	}
}


