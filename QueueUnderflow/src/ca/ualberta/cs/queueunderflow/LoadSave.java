package ca.ualberta.cs.queueunderflow;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;
import ca.ualberta.cs.queueunderflow.models.QuestionList;
import ca.ualberta.cs.queueunderflow.serializers.QuestionListSerializer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class LoadSave {

	Context context;
	final String favsfile = "favList";
	final String favsKey = "favlist";
	
	private void saveData(String key, String value)
	{
		SharedPreferences settings = context.getSharedPreferences(favsfile, Context.MODE_PRIVATE);
		Editor editor = settings.edit();
		editor.putString(favsKey, value);
		editor.commit();
	}
	
	private String loadData(String key)
	{
		SharedPreferences settings = context.getSharedPreferences(favsfile, Context.MODE_PRIVATE);
		String lData = settings.getString(favsKey, "");
		return lData;
	}
	
	public void LoadFavorites()
	{
		String gsonString = loadData(favsKey);
		//Use deserializer here...
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
