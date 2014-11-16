package ca.ualberta.cs.queueunderflow;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import ca.ualberta.cs.queueunderflow.models.GenericResponse;
import ca.ualberta.cs.queueunderflow.models.Question;
import ca.ualberta.cs.queueunderflow.serializers.QuestionSerializer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

public class NetworkManager {

	private static NetworkManager instance = null;
	private NetworkBuffer networkBuffer;
	private ConnectivityManager connectManager;
	
	private static final String SEARCH_URL = "http://cmput301.softwareprocess.es:8080/cmput301f14t10/questions/_search";
	private static final String RESOURCE_URL = "http://cmput301.softwareprocess.es:8080/cmput301f14t10/questions/";

	private Gson gson;
	
	private NetworkManager() {
		networkBuffer = new NetworkBuffer();
		gson = new Gson();
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
	
	// should we call this hasConnection instead of isOnline?
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

	public void addQuestion(Question newQuestion) {
		System.out.print("INSIDE NETWORK MANAGER - ADDQUESTION METHOD");
		HttpClient httpClient = new DefaultHttpClient();
		
		try {
			HttpPost addRequest = new HttpPost(RESOURCE_URL + newQuestion.getID().toString());
			//gson.toJson(jsonElement)
			
			final GsonBuilder gsonBuilder2 = new GsonBuilder();
			Type generictype = new TypeToken<GenericResponse>() { }.getType();
		    gsonBuilder2.registerTypeAdapter(generictype, new QuestionSerializer());
		    final Gson gson2 = gsonBuilder2.create();
		    final JsonElement json2 = gson2.toJsonTree(newQuestion);
			
			StringEntity stringEntity = new StringEntity(json2.toString());
			addRequest.setEntity(stringEntity);
			addRequest.setHeader("Accept", "application/json");
			
			HttpResponse response = httpClient.execute(addRequest);
			String status = response.getStatusLine().toString();
			//Log.i("Adding question", status);
			System.out.println("ADDQUESTION - HTTP STATUS ----- " + status);
			
		} catch (Exception e) {
			System.out.println("ADD QUESTION FAILED");
			e.printStackTrace();
		}
	}

	
	
//	public List<Question> searchQuestions(String searchString, String field) {
//		List<Question> result = new ArrayList<Question>();
//
//		// TODO: Implement search Questions using ElasticSearch
//		if (searchString == null || "".equals(searchString)) {
//			searchString = "*"; // wildcard - search for everything
//		}
//		
//		HttpClient httpClient = new DefaultHttpClient();
//		
//		try {
//			HttpPost searchRequest = createSearchRequest(searchString, field);
//			HttpResponse response = httpClient.execute(searchRequest);
//			
//			String status = response.getStatusLine().toString();
//			Log.i("post", status);
//			
//			SearchResponse<Question> esResponse = parseSearchResponse(response);
//			Hits<Question> hits = esResponse.getHits();
//			
//			if (hits != null) {
//				if(hits.getHits() != null) {
//					for (SearchHit<Question> sesr : hits.getHits()) {
//						result.add(sesr.getSource());
//					}
//				}
//			}
//			
//		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//		} catch (ClientProtocolException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		
//		
//		
//		
//		return result;
//	}
//	
//	/**
//	 * Creates a search request from a search string and a field
//	 */
//	private HttpPost createSearchRequest(String searchString, String field)	throws UnsupportedEncodingException {
//		
//		HttpPost searchRequest = new HttpPost(SEARCH_URL);
//
//		String[] fields = null;
//		if (field != null) {
//			fields = new String[1];
//			fields[0] = field;
//		}
//		
//		SimpleSearchCommand command = new SimpleSearchCommand(searchString,	fields);
//		
//		String query = command.getJsonCommand();
//		Log.i("post", "Json command: " + query);
//
//		StringEntity stringEntity;
//		stringEntity = new StringEntity(query);
//
//		searchRequest.setHeader("Accept", "application/json");
//		searchRequest.setEntity(stringEntity);
//
//		return searchRequest;
//	}
//
//	
//	/**
//	 * Parses the response of a search
//	 */
//	private SearchResponse<Question> parseSearchResponse(HttpResponse response) throws IOException {
//		String json;
//		json = getEntityContent(response);
//
//		Type searchResponseType = new TypeToken<SearchResponse<Question>>() {
//		}.getType();
//		SearchResponse<Question> esResponse = gson.fromJson(json, searchResponseType);
//
//		return esResponse;
//	}
//	
//	/**
//	 * Gets content from an HTTP response
//	 */
//	public String getEntityContent(HttpResponse response) throws IOException {
//		BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
//
//		StringBuffer result = new StringBuffer();
//		String line = "";
//		while ((line = rd.readLine()) != null) {
//			result.append(line);
//		}
//
//		return result.toString();
//	}
}


