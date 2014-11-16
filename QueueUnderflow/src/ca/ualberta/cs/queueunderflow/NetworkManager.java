package ca.ualberta.cs.queueunderflow;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.UUID;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import ca.ualberta.cs.queueunderflow.models.Answer;
import ca.ualberta.cs.queueunderflow.models.GenericResponse;
import ca.ualberta.cs.queueunderflow.models.Question;
import ca.ualberta.cs.queueunderflow.serializers.AnswerSerializer;
import ca.ualberta.cs.queueunderflow.serializers.QuestionSerializer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

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


