package ca.ualberta.cs.queueunderflow;

import java.lang.reflect.Type;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;
import ca.ualberta.cs.queueunderflow.models.QuestionList;
import ca.ualberta.cs.queueunderflow.serializers.QuestionListDeserializer;
import ca.ualberta.cs.queueunderflow.serializers.QuestionListSerializer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

// TODO: Auto-generated Javadoc
/**
 * The Class LoadSave.
 */
public class LoadSave {
	
	/** The context. */
	public static Context context;
	
	/** The save file. */
	final String saveFile = "queueUnderflowData";
	
	/** The favs key. */
	final String favsKey = "favlist";
	
	/** The my q key. */
	final String myQKey = "myquestions";
	
	/** The username key. */
	final String usernameKey = "username";
	
	/** The loaded. */
	public static boolean loaded = false;
	
	/**
	 * Save data.
	 *
	 * @param key the key
	 * @param value the value
	 */
	private void saveData(String key, String value)
	{
		SharedPreferences settings = LoadSave.context.getSharedPreferences(saveFile, Context.MODE_PRIVATE);
		Editor editor = settings.edit();
		editor.putString(key, value);
		editor.commit();
	}
	
	/**
	 * Load data.
	 *
	 * @param key the key
	 * @return the string
	 */
	private String loadData(String key)
	{
		SharedPreferences settings = LoadSave.context.getSharedPreferences(saveFile, Context.MODE_PRIVATE);
		String lData = settings.getString(key, "");
		return lData;
	}
	
	/**
	 * Load my questions.
	 */
	public void loadMyQuestions()
	{
	    String gsonString = loadData(myQKey);
            GsonBuilder gsonbuild = new GsonBuilder();
            gsonbuild.registerTypeAdapter(QuestionList.class, new QuestionListDeserializer());
            Gson gson = gsonbuild.create();
            
            Type qlType = new TypeToken<QuestionList>() {}.getType();
            QuestionList qList = gson.fromJson(gsonString, qlType);
            
            if(qList != null)
            {
                for(int i=0; i<qList.size(); i++)
                {
                    ListHandler.getMyQsList().add(qList.get(i));
                    ListHandler.getMasterQList().add(qList.get(i));
                }
            }
	}
	
        /**
         * Save my questions.
         */
        public void saveMyQuestions ()
        {
                GsonBuilder gsonbuild = new GsonBuilder();
                gsonbuild.registerTypeAdapter(QuestionList.class, new QuestionListSerializer());
                Gson gson = gsonbuild.create();
                String gsonString = gson.toJson(ListHandler.getMyQsList());
                
                //Log.d("test", "printing string now...");
                //Log.d("test", gsonString);
                this.saveData(myQKey, gsonString);
        }
	
	/**
	 * Load favorites.
	 */
	public void loadFavorites()
	{
		String gsonString = loadData(favsKey);
		//Turn this into a questionList.
		GsonBuilder gsonbuild = new GsonBuilder();
		gsonbuild.registerTypeAdapter(QuestionList.class, new QuestionListDeserializer());
		Gson gson = gsonbuild.create();
		
		Type qlType = new TypeToken<QuestionList>() {}.getType();
		QuestionList qList = gson.fromJson(gsonString, qlType);
		
		if(qList != null)
		{
			for(int i=0; i<qList.size(); i++)
			{
				ListHandler.getFavsList().add(qList.get(i));
			}
			ListHandler.getFavsList().sortBy("most recent");
		}
			
		
		//ListHandler.setFavsList(qList);
	}
	
	/**
	 * Save username.
	 *
	 * @param username the username
	 */
	public void saveUsername(String username)
	{
		saveData(usernameKey, username);
	}
	
	/**
	 * Load username.
	 *
	 * @return true, if successful
	 */
	public boolean loadUsername()
	{
		String loadedUsername = loadData(usernameKey);
        if(loadedUsername.length() != 0)
        {
        	User user= ListHandler.getUser();
        	try
        	{
        		user.setUserName(loadedUsername);
        	} catch (IllegalArgumentException e){
        		//This should never happen. Any username saved will be able to pass through without triggering this.
        		return false;
        	}
        }
        return true;
	}
	
	/**
	 * Save favorites.
	 */
	public void SaveFavorites()
	{
		GsonBuilder gsonbuild = new GsonBuilder();
		gsonbuild.registerTypeAdapter(QuestionList.class, new QuestionListSerializer());
		Gson gson = gsonbuild.create();
		String gsonString = gson.toJson(ListHandler.getFavsList());
		
		Log.d("test", "printing string now...");
		Log.d("test", gsonString);
		this.saveData(favsKey, gsonString);
	}
}
