package ca.ualberta.cs.queueunderflow.singletons;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.UUID;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;
import ca.ualberta.cs.queueunderflow.NetworkBuffer;
import ca.ualberta.cs.queueunderflow.models.QuestionList;
import ca.ualberta.cs.queueunderflow.serializers.QuestionListDeserializer;
import ca.ualberta.cs.queueunderflow.serializers.QuestionListSerializer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;


/**
 * The Class LoadSave.
 * Singleton. Handles loading & saving.
 * @author group 10
 * @version 0.5
 */
public class LoadSave {
	
	/** The instance. */
	private static LoadSave instance = null;
	
	/** The context. */
	public static Context context;
	
	/**  Indicates unsaved changes. */
	public static boolean unsavedChanges = false;
	
	/** The save file. */
	final String saveFile = "queueUnderflowData";
	
	/** The favs key. */
	final String favsKey = "favlist";
	
	/** The my q key. */
	final String myQKey = "myquestions";
	
	/** The username key. */
	final String usernameKey = "username";
	
	/**  The city key. */
	final String cityKey = "city";
	
	/**  The country key. */
	final String countryKey = "country";
	
	/**  The use location key. */
	final String useLocationKey = "useLocation";
	
	/** The reading list key. */
	final String readingListKey = "readinglist";
	
	/** The network buffer key. */
	final String networkBufferKey= "networkBuffer";
	
	/** The use location check box. */
	final String useLocationCheckBox= "useLocationCheckBox";
	
	/** The upvoted questions key. */
	final String upvotedQuestionsKey="upvotedQuestions";
	
	/** The upvoted answers key. */
	final String upvotedAnswersKey="upvotedAnswers";
	
	/** The loaded. */
	public static boolean loaded = false;
	
	//Constructor
	/**
	 * Instantiates a new load save.
	 */
	protected LoadSave() {
		
	}
	
	/**
	 * Gets the single instance of LoadSave.
	 *
	 * @return single instance of LoadSave
	 */
	public static LoadSave getInstance() {
		if (instance == null) {
			instance = new LoadSave();
		}
		return instance;
	}
	
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
     * Save reading list.
     */
    public void SaveReadingList ()
    {
            GsonBuilder gsonbuild = new GsonBuilder();
            gsonbuild.registerTypeAdapter(QuestionList.class, new QuestionListSerializer());
            Gson gson = gsonbuild.create();
            String gsonString = gson.toJson(ListHandler.getReadingList());
            
            Log.d("test", "printing string now...");
            Log.d("test", gsonString);
            this.saveData(readingListKey, gsonString);
    }
	
	/**
	 * Load reading list.
	 */
	public void LoadReadingList()
	{
		String gsonString = loadData(readingListKey);
        GsonBuilder gsonbuild = new GsonBuilder();
        gsonbuild.registerTypeAdapter(QuestionList.class, new QuestionListDeserializer());
        Gson gson = gsonbuild.create();
        
        Type qlType = new TypeToken<QuestionList>() {}.getType();
        QuestionList qList = gson.fromJson(gsonString, qlType);
        
        if(qList != null)
        {
            for(int i=0; i<qList.size(); i++)
            {
                ListHandler.getReadingList().add(qList.get(i));
               
            }
        }
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
            
        QuestionList qList = gson.fromJson(gsonString, QuestionList.class);
    
            if(qList != null)
            {
                for(int i=0; i<qList.size(); i++)
                {
                    ListHandler.getMyQsList().add(qList.get(i));
                   
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
                
                this.saveData(myQKey, gsonString);
	}
	
	/**
	 * Load favorites.
	 */
	public void loadFavorites()
	{
		String gsonString = loadData(favsKey);
	
		GsonBuilder gsonbuild = new GsonBuilder();
		gsonbuild.registerTypeAdapter(QuestionList.class, new QuestionListDeserializer());
		Gson gson = gsonbuild.create();
		
		QuestionList qList = gson.fromJson(gsonString, QuestionList.class);

		if(qList != null)
		{
			for(int i=0; i<qList.size(); i++)
			{
				ListHandler.getFavsList().add(qList.get(i));
			}
			ListHandler.getFavsList().sortBy("most recent");
		}
			
		
	
	}

	/**
	 * Save city.
	 *
	 * @param city the city
	 */
	public void saveCity(String city)
	{
		saveData(cityKey, city);
	}
	
	/**
	 * Save country.
	 *
	 * @param country the country
	 */
	public void saveCountry(String country)
	{
		saveData(countryKey, country);
	}
	
	/**
	 * Save Use Location boolean.
	 *
	 * @param useLocation the use location
	 */
	public void saveUseLocation(boolean useLocation)
	{
		if(useLocation) {
			saveData(useLocationKey, "1");
		}else{
			saveData(useLocationKey, "0");
		}
	}
	
	/**
	 * Load country.
	 */
	public void loadCountry() {
		String loaded = loadData(countryKey);
		if(loaded.length() != 0)
		{
			User.setCountry(loaded);
		}
	}
	
	/**
	 * Load city.
	 */
	public void loadCity() {
		String loaded = loadData(cityKey);
		if(loaded.length() != 0)
		{
			User.setCity(loaded);
		}
	}
	
	/**
	 * Load use location.
	 */
	public void loadUseLocation() {
		String loadedB = loadData(useLocationKey);
		if(loadedB.equals("1"))
		{
			User.setUseLocation(true);
		}else if(loadedB.equals("0")){
			User.setUseLocation(false);
		}
	}
	
	/**
	 * Save username.
	 *
	 * @param value the value
	 */
	public void saveUseLocationCheckBox(boolean value) {
		if (value) {
			saveData(useLocationCheckBox,"1");
		}
		else {
			saveData(useLocationCheckBox,"0");
		}
	}
	
	/**
	 * Load use location check box.
	 */
	public void loadUseLocationCheckBox() {
		String checkBox= loadData(useLocationCheckBox);
		if (checkBox.equals("1")) {
			User.setCheckbox(true);
			Log.d("test", "checkbox is checked");

		}
		else {
			User.setCheckbox(false);
			Log.d("test", "checkbox isn't checked");

		}
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
        		
        		return false;
        	}
        }
        return true;
	}
	
	/**
	 * Save upvoted questions.
	 */
	public void saveUpvotedQuestions() {
		ArrayList <UUID> upvotedQuestions= User.upvotedQuestions;
		Gson gson= new Gson();
		String gsonString= gson.toJson(upvotedQuestions);
		this.saveData(upvotedQuestionsKey, gsonString);
	}
	
	/**
	 * Load upvoted questions.
	 */
	public void loadUpvotedQuestions() {
		String gsonString= loadData(upvotedQuestionsKey);
		if (gsonString=="") {
			ArrayList <UUID> upvotedQuestions= new ArrayList <UUID> ();
			User.upvotedQuestions=upvotedQuestions;
		}
		else {
			Gson gson= new Gson();
			Type listOfTestObject = new TypeToken<ArrayList<UUID>>(){}.getType();
			//ArrayList <Question> questionList2= gson2.fromJson(json2,listOfTestObject);
			ArrayList <UUID> upvotedQuestions= gson.fromJson(gsonString,listOfTestObject);
			User.upvotedQuestions=upvotedQuestions;
		}
	}
	
	/**
	 * Save upvoted answers.
	 */
	public void saveUpvotedAnswers() {
		ArrayList <UUID> upvotedAnswers= User.upvotedAnswers;
		Gson gson= new Gson();
		String gsonString= gson.toJson(upvotedAnswers);
		this.saveData(upvotedAnswersKey, gsonString);
	}
	
	/**
	 * Load upvoted answers.
	 */
	public void loadUpvotedAnswers() {
		String gsonString= loadData(upvotedAnswersKey);
		if (gsonString=="") {
			ArrayList <UUID> upvotedAnswers= new ArrayList <UUID> ();
			User.upvotedAnswers=upvotedAnswers;
		}
		else {
			Gson gson= new Gson();
			Type listOfTestObject = new TypeToken<ArrayList<UUID>>(){}.getType();
			//ArrayList <Question> questionList2= gson2.fromJson(json2,listOfTestObject);
			ArrayList <UUID> upvotedAnswers= gson.fromJson(gsonString,listOfTestObject);
			User.upvotedAnswers=upvotedAnswers;
		}
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
	
	/**
	 * Load network buffer.
	 */
	public void loadNetworkBuffer()
	{
		//load and set the network buffer
		String gsonString = loadData(networkBufferKey);
		if (gsonString=="") {
			NetworkManager networkManager= NetworkManager.getInstance();
			NetworkBuffer networkBuffer= new NetworkBuffer();
			networkManager.setNetworkBuffer(networkBuffer);
		}
		else {
			Gson gson = new Gson();
			NetworkBuffer networkBuffer = gson.fromJson(gsonString, NetworkBuffer.class);
			NetworkManager networkManager= NetworkManager.getInstance();
			networkManager.setNetworkBuffer(networkBuffer);
		}

	}
	
	
	public void saveAll()
	{
	    this.SaveFavorites();
	    this.saveMyQuestions();
	    this.SaveReadingList();
	           
	    this.saveUseLocation(User.getUseLocation());
	    this.saveUseLocationCheckBox(User.displayCheckbox());
	    this.saveCity(User.getCity());
	    this.saveCountry(User.getCountry());
	           
	    this.SaveNetworkBuffer();
	    this.saveUpvotedQuestions();
	    this.saveUpvotedAnswers();
	}
	
	/**
	 * Load all data required.
	 */
	public void loadAll()
	{
	    if(!LoadSave.loaded) //Make sure this only loads once!
	    {
	        LoadSave lSave = LoadSave.getInstance();
                lSave.loadUsername();
                Log.d("test", "loading favorites...");
                lSave.loadMyQuestions();
                lSave.loadFavorites();
                lSave.LoadReadingList();
                lSave.loadUseLocation();
                lSave.loadCity();
                Log.d("loading city test", User.getCity());
                lSave.loadCountry();
                lSave.loadNetworkBuffer();

                lSave.loadUpvotedQuestions();
                lSave.loadUpvotedAnswers();
                lSave.loadUseLocationCheckBox();
                LoadSave.loaded = true;
	    }
	}
	
	/**
	 * Save network buffer.
	 */
	public void SaveNetworkBuffer() {
	
		//save network buffer to sharedpreferences
		Gson gson= new Gson();
		NetworkBuffer networkBuffer= NetworkManager.getInstance().getNetworkBuffer();
		String json= gson.toJson(networkBuffer);
		this.saveData(networkBufferKey,json);
	}
}
