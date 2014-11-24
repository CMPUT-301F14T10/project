package ca.ualberta.cs.queueunderflow.views;

import java.util.ArrayList;

import android.app.Activity;
import android.app.Fragment;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import ca.ualberta.cs.queueunderflow.ESManager;
import ca.ualberta.cs.queueunderflow.ListHandler;
import ca.ualberta.cs.queueunderflow.LoadSave;
import ca.ualberta.cs.queueunderflow.NetworkController;
import ca.ualberta.cs.queueunderflow.NetworkManager;
import ca.ualberta.cs.queueunderflow.R;
import ca.ualberta.cs.queueunderflow.models.Question;
import ca.ualberta.cs.queueunderflow.models.QuestionList;


/**
 * The Class MainActivity.
 * Handles clicks & swipes relating to switching between fragments via the navigation drawer.
 * Also handles calling the load/save functions.
 * @author group 10
 * @version 0.5
 */
public class MainActivity extends Activity {

	/** The Constant HOME_SCREEN_FRAGMENT. */
	public static final int HOME_SCREEN_FRAGMENT = 1;
	
	/** The Constant FAVORITES_FRAGMENT. */
	public static final int FAVORITES_FRAGMENT = 2;
	
	/** The Constant MY_QUESTIONS_FRAGMENT. */
	public static final int MY_QUESTIONS_FRAGMENT = 3;
	
	/** The Constant READING_LIST_FRAGMENT. */
	public static final int READING_LIST_FRAGMENT = 4;
	
	/** The Constant SET_USERNAME_FRAGMENT. */
	public static final int SET_USERNAME_FRAGMENT = 5;
	
	/** The Constant SET_LOCATION_FRAGMENT. */
	public static final int SET_LOCATION_FRAGMENT = 6;
	
	/** The drawer layout. */
	private DrawerLayout drawerLayout;
	
	/** The drawer toggle. */
	protected ActionBarDrawerToggle drawerToggle;
	
	/** The drawer title. */
	private CharSequence drawerTitle;
	
	/** The title. */
	private CharSequence title;
	
	/** The drawer list. */
	private ListView drawerList;
	
	/** The nav drawer titles. */
	private String[] navDrawerTitles;
	
	/** The fragment position. */
	private int fragmentPosition;	// For sorting
	
	/** The question list. */
	private QuestionList questionList; 	// For sorting
	
    /* (non-Javadoc)
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        drawerTitle = "QueueUnderflow";
        title = getTitle();

        //Set variable required for Load/Save
        LoadSave.context = this;
        
        // Lets us use an icon in the menu bar to access the nav drawer too
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
        
        // Initializes nav drawer items
        navDrawerTitles = new String[] {"Home", "My Favorites", "My Questions", "Reading List", "Set Username", "Set Location"};
        drawerList = (ListView) findViewById(R.id.left_drawer);
        drawerList.setAdapter(new ArrayAdapter<String>(getApplicationContext(), R.layout.list_item_drawer, navDrawerTitles));
        drawerList.setOnItemClickListener(new DrawerItemClickListener());
        
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
       
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.drawable.ic_drawer, R.string.drawerOpen, R.string.drawerClosed) {
        	
        	public void onDrawerOpened(View view) {
        		super.onDrawerOpened(view);
        		getActionBar().setTitle(drawerTitle);
        		invalidateOptionsMenu();
        	}
        	
        	public void onDrawerClosed(View view) {
        		super.onDrawerClosed(view);
        		getActionBar().setTitle(title);
        		invalidateOptionsMenu();
        	}
        };
        
        drawerLayout.setDrawerListener(drawerToggle);
        
        int returnFragment = getIntent().getIntExtra("returnFragment", -1);
        if (returnFragment != -1) {
        	fragmentPosition = returnFragment - 1;
        	selectItem(returnFragment-1);
        }
        
        // This automatically shows the HomeScreen after the app is first launched and a user hasn't selected a tab yet.
        else if (savedInstanceState == null) {
        	fragmentPosition = 0;
        	selectItem(0);
        }
        
        
        
        //----- Load data from phone memory (move this into a controller somewhere, maybe)

        loadOnce();

        //----- Done loading data from phone memory
        
        //This part here is to load questionlist from server into home screen/section-- test only
        //Kind of works (app doesn't crash at least) Can add to server but not pull from it yet
/*        NetworkManager networkManager= NetworkManager.getInstance();
        boolean online= networkManager.isOnline(getApplicationContext());
        if (online) {
        	ESManager esManager= new ESManager();
        	QuestionList questionList= esManager.getQuestionList();
        	ListHandler.setMasterQList(questionList);
        }*/
        
        NetworkManager networkManager= NetworkManager.getInstance();
        boolean online= networkManager.isOnline(getApplicationContext());
        if (online) {
        	ESManager esManager= new ESManager();
        	ArrayList<String> questionIDS= esManager.getQuestionIDS();
        	QuestionList allQuestions= new QuestionList();
        	for (int i=0; i<questionIDS.size();i++) {
        		Question question= esManager.getQuestion(questionIDS.get(i));
        		allQuestions.add(question);
        	}
        	ListHandler.setMasterQList(allQuestions);
        }
        
        
        
    }


	private void loadOnce()
	{

		if(!LoadSave.loaded) //Make sure this only loads once!
        {
        LoadSave lSave = LoadSave.getInstance();
        lSave.loadUsername();
        Log.d("test", "loading favorites...");
        lSave.loadMyQuestions();
        lSave.loadFavorites();
        lSave.loadUseLocation();
        lSave.loadCity();
        lSave.loadCountry();
        LoadSave.loaded = true;
        }
	}


    /* (non-Javadoc)
     * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
     */

    @Override
    public void onResume() {
    	//This executes whenever the main activity resumes.
    	//Examples: after adding a question, returning to the main screen after favoriting, etc
    	
    	//I used this instead of onPause() because I think it works better for knowing when to save.
    	//With onPause it wouldn't save after adding a question unless specifically clicking on another question
    	super.onResume();
    	Log.d("Test", "On resume executed!"); //Debugging purposes.

    	//I have implemented LoadSave.unsavedChanges as a static boolean to be set as true whenever there are
    	//changes in favorites, myquestions, or reading list that need to be saved to the phone's memory. Constantly
    	//saving data to the phone whenever you return to the main activity may lose us marks in the future?
    	
    	//However, since we don't currently have unique ID's for questions or unique ID's for users, it's very
    	//difficult to determine if new replies/answers to a question belong to one the user wants saved.
    	//I'm going to assume there WILL be unique ID's in the future, so I implemented the basics in various controllers
    	//that will inform LoadSave that there are changes that need to be saved.
    	
    	//This code does not yet work though, so for now the application will save whenever you return to the main activity.
    	
    	LoadSave ls = LoadSave.getInstance();
    	ls.SaveFavorites();
    	ls.saveMyQuestions();
    	ls.SaveReadingList();
    	
    	/*
    	if(LoadSave.unsavedChanges)
    	{
    		LoadSave ls = new LoadSave();
    		ls.SaveFavorites();
    		ls.saveMyQuestions();
    		LoadSave.unsavedChanges = false;
    	}
    	*/
    	
    	// Below deals with pushing Questions, Answers & Replies that weren't posted online while the device was offline
    	NetworkManager networkManager = NetworkManager.getInstance();
    	if (networkManager.isOnline(getApplicationContext())) {
    		networkManager.flushBuffer();
    		
        	//Add the master questionlist to the server, doesn't work yet
        	//NetworkController networkController = new NetworkController();
        	//networkController.addQuestionList(ListHandler.getMasterQList());
    		
    		//Push the questionlist to the server
           	//ESManager esManager= new ESManager();
        	//QuestionList questionList= ListHandler.getMasterQList();
        	//esManager.addQuestionList(questionList);
    	}
    }
        
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    /* (non-Javadoc)
     * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        
        if (drawerToggle.onOptionsItemSelected(item)) {
        	return true;
        }
		
        switch(id) {
        case R.id.mostRecentMenu:
        	questionList.sortBy("most recent");
        	selectItem(fragmentPosition);
        	return true;
        case R.id.leastRecentMenu:
        	questionList.sortBy("least recent");
        	selectItem(fragmentPosition);
        	return true;
        case R.id.mostUpvotesMenu:
        	questionList.sortBy("most upvotes");
        	selectItem(fragmentPosition);
        	return true;
        case R.id.hasPictureMenu:
        	questionList.sortBy("has pictures");
        	selectItem(fragmentPosition);
        	return true;
        case R.id.noPictureMenu:
        	questionList.sortBy("no pictures");
        	selectItem(fragmentPosition);
        	return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    // This is called when we call invalidateOptionsMenu()
    /* (non-Javadoc)
     * @see android.app.Activity#onPrepareOptionsMenu(android.view.Menu)
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
    	
    	// Finds if the nav drawer is currently open or closed
    	boolean isDrawerOpen = drawerLayout.isDrawerOpen(drawerList);
    	
    	// Hides menu bar items when nav drawer is open & displays menu bar items when nav drawer is closed
    	menu.findItem(R.id.searchMenu).setVisible(!isDrawerOpen);
    	menu.findItem(R.id.sortByMenu).setVisible(!isDrawerOpen);
    	
    	return super.onPrepareOptionsMenu(menu);
    }
    
    /* (non-Javadoc)
     * @see android.app.Activity#onPostCreate(android.os.Bundle)
     */
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
    	super.onPostCreate(savedInstanceState);
    	drawerToggle.syncState();
    }
    
    /* (non-Javadoc)
     * @see android.app.Activity#onConfigurationChanged(android.content.res.Configuration)
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
    	super.onConfigurationChanged(newConfig);
    	drawerToggle.onConfigurationChanged(newConfig);
    }
    
    /**
     * The listener interface for receiving drawerItemClick events.
     * The class that is interested in processing a drawerItemClick
     * event implements this interface, and the object created
     * with that class is registered with a component using the
     * component's <code>addDrawerItemClickListener<code> method. When
     * the drawerItemClick event occurs, that object's appropriate
     * method is invoked.
     *
     * @see DrawerItemClickEvent
     */
    private class DrawerItemClickListener implements ListView.OnItemClickListener {

		/* (non-Javadoc)
		 * @see android.widget.AdapterView.OnItemClickListener#onItemClick(android.widget.AdapterView, android.view.View, int, long)
		 */
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			fragmentPosition = position;
			selectItem(position);
		}
    }
    
    // Sets the view to the fragment of the user selection from the nav drawer
    /**
     * Switches to the specified fragment & inflates it
     *
     * @param position the position of the fragment to switch to
     */
    private void selectItem(int position) {
    	Fragment fragment;
    	
    	switch(position) {
    	case 0:
    		fragment = new SuperFragment(HOME_SCREEN_FRAGMENT);
    		questionList = ListHandler.getMasterQList();
    		break;
    	case 1:
    		fragment = new SuperFragment(FAVORITES_FRAGMENT);
    		questionList = ListHandler.getFavsList();
    		break;
    	case 2:
    		fragment = new SuperFragment(MY_QUESTIONS_FRAGMENT);
    		questionList = ListHandler.getMyQsList();
    		break;
    	case 3:
    		fragment = new SuperFragment(READING_LIST_FRAGMENT);
    		questionList = ListHandler.getReadingList();
    		break;
    	case 4:
    		fragment = new SetUsernameFragment();
    		break;
    	case 5:
    	        fragment = new SetLocationFragment();
    	        break;
    	default:
    		fragment = new SuperFragment(HOME_SCREEN_FRAGMENT);
    		questionList = ListHandler.getMasterQList();
    		break;
    	}
    	
    	getFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).commit();
    	drawerList.setItemChecked(position, true);
    	setTitle(navDrawerTitles[position]);
    	drawerLayout.closeDrawer(drawerList);
    	
    	System.out.println(navDrawerTitles[position]);
    }
    
    /* (non-Javadoc)
     * @see android.app.Activity#setTitle(java.lang.CharSequence)
     */
    @Override
    public void setTitle(CharSequence newTitle) {
    	title = newTitle;
    	getActionBar().setTitle(title);
    }
    
    
}
