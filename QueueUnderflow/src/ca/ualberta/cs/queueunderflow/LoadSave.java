package ca.ualberta.cs.queueunderflow;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import ca.ualberta.cs.queueunderflow.models.QuestionList;
import ca.ualberta.cs.queueunderflow.serializers.QuestionListSerializer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class LoadSave {

	Context context;
	final String favsKey = "favList";
	
	public void SaveData(String key, String value)
	{
		SharedPreferences settings = context.getSharedPreferences(favsKey, Context.MODE_PRIVATE);
		Editor editor = settings.edit();
		editor.putString(favsKey, value);
		editor.commit();
	}
	
	public void SaveFavorites()
	{
		GsonBuilder gsonbuild = new GsonBuilder();
		gsonbuild.registerTypeAdapter(QuestionList.class, new QuestionListSerializer());
		Gson gson = gsonbuild.create();
		String gsonString = gson.toJson(ListHandler.getFavsList());
		
		System.out.print(gsonString);
		
		return;
		
		//this.SaveData(favsKey, gsonString);
	}
	
	public void LoadFavorites()
	{
		
	}
}
