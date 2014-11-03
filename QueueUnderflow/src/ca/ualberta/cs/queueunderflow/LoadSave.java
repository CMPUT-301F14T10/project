package ca.ualberta.cs.queueunderflow;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import ca.ualberta.cs.queueunderflow.models.QuestionList;
import ca.ualberta.cs.queueunderflow.serializers.QuestionListSerializer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class LoadSave {
	
	public static Context context;
	final String saveFile = "queueUnderflowData";
	final String favsKey = "favlist";
	final String usernameKey = "username";
	
	private void saveData(String key, String value)
	{
		SharedPreferences settings = LoadSave.context.getSharedPreferences(saveFile, Context.MODE_PRIVATE);
		Editor editor = settings.edit();
		editor.putString(key, value);
		editor.commit();
	}
	
	private String loadData(String key)
	{
		SharedPreferences settings = LoadSave.context.getSharedPreferences(saveFile, Context.MODE_PRIVATE);
		String lData = settings.getString(key, "");
		return lData;
	}
	
	public void loadFavorites()
	{
		String gsonString = loadData(favsKey);
		//Use deserializer here...
	}
	
	public void saveUsername(String username)
	{
		saveData(usernameKey, username);
	}
	
	public String loadUsername()
	{
		return loadData(usernameKey);
	}
	
	public void SaveFavorites()
	{
		GsonBuilder gsonbuild = new GsonBuilder();
		gsonbuild.registerTypeAdapter(QuestionList.class, new QuestionListSerializer());
		Gson gson = gsonbuild.create();
		String gsonString = gson.toJson(ListHandler.getFavsList());
		
		//Log.d("test", "printing string now...");
		//Log.d("test", gsonString);
		this.saveData(favsKey, gsonString);
	}
}
