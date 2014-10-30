package ca.ualberta.cs.queueunderflow.views;

import ca.ualberta.cs.queueunderflow.ListHandler;
import ca.ualberta.cs.queueunderflow.R;
import ca.ualberta.cs.queueunderflow.R.drawable;
import ca.ualberta.cs.queueunderflow.R.id;
import ca.ualberta.cs.queueunderflow.R.layout;
import ca.ualberta.cs.queueunderflow.R.menu;
import ca.ualberta.cs.queueunderflow.R.string;
import ca.ualberta.cs.queueunderflow.models.QuestionList;
import android.app.Activity;
import android.app.Fragment;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

/* A lot of the navigation drawer stuff is modified from http://developer.android.com/training/implementing-navigation/nav-drawer.html 10-18-2014*/
public class MainActivity extends Activity {

	public static final int HOME_SCREEN_FRAGMENT = 1;
	public static final int FAVORITES_FRAGMENT = 2;
	public static final int MY_QUESTIONS_FRAGMENT = 3;
	public static final int READING_LIST_FRAGMENT = 4;
	
	private DrawerLayout drawerLayout;
	protected ActionBarDrawerToggle drawerToggle;
	private CharSequence drawerTitle;
	private CharSequence title;
	private ListView drawerList;
	private String[] navDrawerTitles;
	
	private int fragmentPosition;	// For sorting
	private QuestionList questionList; 	// For sorting
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        drawerTitle = "QueueUnderflow";
        title = getTitle();
        
        // Lets us use an icon in the menu bar to access the nav drawer too
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
        
        navDrawerTitles = new String[] {"Home", "My Favorites", "My Questions", "Reading List", "Set Username"};
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
        System.out.println("return fragment -->" + returnFragment);
        if (returnFragment != -1) {
        	System.out.println("selecting Item (returnFragment - 1) : " + (returnFragment-1));
        	fragmentPosition = returnFragment - 1;
        	selectItem(returnFragment-1);
        }
        // This automatically shows the HomeScreen after the app is first launched and a user hasn't selected a tab yet.
        else if (savedInstanceState == null) {
        	fragmentPosition = 0;
        	selectItem(0);
        }
        
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        
        if (drawerToggle.onOptionsItemSelected(item)) {
        	return true;
        }
		
		System.out.println("menu item clicked");
        switch(id) {
        case R.id.mostRecentMenu:
        	questionList.sortBy("most recent");
//        	Toast.makeText(getApplicationContext(), "click most recent", Toast.LENGTH_SHORT).show();
        	selectItem(fragmentPosition);
        	return true;
        case R.id.leastRecentMenu:
        	questionList.sortBy("least recent");
//        	Toast.makeText(getApplicationContext(), "click least recent", Toast.LENGTH_SHORT).show();
        	selectItem(fragmentPosition);
        	return true;
        case R.id.mostUpvotesMenu:
        	questionList.sortBy("most upvotes");
//        	Toast.makeText(getApplicationContext(), "click upvotes", Toast.LENGTH_SHORT).show();
        	selectItem(fragmentPosition);
        	return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    // This is called when we call invalidateOptionsMenu()
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
    	
    	// Finds if the nav drawer is currently open or closed
    	boolean isDrawerOpen = drawerLayout.isDrawerOpen(drawerList);
    	
    	// Hides menu bar items when nav drawer is open & displays menu bar items when nav drawer is closed
    	menu.findItem(R.id.searchMenu).setVisible(!isDrawerOpen);
    	menu.findItem(R.id.sortByMenu).setVisible(!isDrawerOpen);
    	
    	return super.onPrepareOptionsMenu(menu);
    }
    
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
    	super.onPostCreate(savedInstanceState);
    	drawerToggle.syncState();
    }
    
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
    	super.onConfigurationChanged(newConfig);
    	drawerToggle.onConfigurationChanged(newConfig);
    }
    
    private class DrawerItemClickListener implements ListView.OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			fragmentPosition = position;
			selectItem(position);
		}
    }
    
    // Sets the view to the fragment of the user selection from the nav drawer
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
    
    @Override
    public void setTitle(CharSequence newTitle) {
    	title = newTitle;
    	getActionBar().setTitle(title);
    }
    
    
}
