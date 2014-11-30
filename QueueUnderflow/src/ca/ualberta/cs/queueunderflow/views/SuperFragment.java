package ca.ualberta.cs.queueunderflow.views;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import ca.ualberta.cs.queueunderflow.Buffer;
import ca.ualberta.cs.queueunderflow.ListHandler;
import ca.ualberta.cs.queueunderflow.NetworkController;
import ca.ualberta.cs.queueunderflow.NetworkManager;
import ca.ualberta.cs.queueunderflow.R;
import ca.ualberta.cs.queueunderflow.TView;
import ca.ualberta.cs.queueunderflow.adapters.QuestionListAdapter;
import ca.ualberta.cs.queueunderflow.models.QuestionList;


/**
 * The Class SuperFragment.
 * Handles displaying the Home screen / main screen, Favorites screen, ReadingList screen, & MyQuestions screen
 *@author group 10
 * @version 0.5
 */
public class SuperFragment extends Fragment implements TView<QuestionList>{
	
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
	
	/** The adapter. */
	private QuestionListAdapter adapter;
	
	/** The from fragment. */
	protected int fromFragment;
	
	/**
	 * Instantiates a new super fragment.
	 *
	 * @param fromFragment the from fragment
	 */
	public SuperFragment (int fromFragment) {
		this.fromFragment = fromFragment;
	}
	
	/* (non-Javadoc)
	 * @see android.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		// Put in controller - This must be done first before inflating the view & adding the view to the model else it may crash
		Buffer buffer = Buffer.getInstance();
		System.out.println("FAV BUFFER FLUSHING : "  + buffer.favBuffer);
		System.out.println("READINGLIST BUFFER FLUSHING : " + buffer.readingListBuffer);
		buffer.flushAll();
		//
		
	   	// Below deals with pushing Questions, Answers & Replies that weren't posted online while the device was offline
    	NetworkManager networkManager = NetworkManager.getInstance();
		if (networkManager.isOnline(getActivity().getApplicationContext())) {
    		networkManager.flushBuffer();
    	}
		
		// Refresh the masterlist - if in HomeScreenFragment
		if (networkManager.isOnline(getActivity().getApplicationContext()) && fromFragment == HOME_SCREEN_FRAGMENT) {
    		System.out.println("Starting populateMasterList");
    		NetworkController networkController = new NetworkController();
    		networkController.populateList(ListHandler.getMasterQList(), "");
    		System.out.println("Finished populateMasterList");
		}
		
		View view = inflater.inflate(R.layout.fragment_home_screen, container, false);

		// Add view to the model
		QuestionList screenQList = findQuestionList();
		screenQList.addView(this);
		
		// Set up list adapter to display the questions
		ListView listView = (ListView) view.findViewById(R.id.homeListView);
		adapter = new QuestionListAdapter(getActivity(), R.layout.list_item_question, screenQList.getQuestionList(), fromFragment);
		listView.setAdapter(adapter);
		
		return view;
	}

	/* (non-Javadoc)
	 * @see android.app.Fragment#onViewCreated(android.view.View, android.os.Bundle)
	 */
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		Button askQuestion = (Button) view.findViewById(R.id.askQBtn);
		askQuestion.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), AskAQuestionActivity.class);
				startActivity(intent);
			}
		});
		
		ListView listView = (ListView) view.findViewById(R.id.homeListView);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// Go to QAViewActivity to see the Q & A
				Intent intent = new Intent(getActivity(), QAViewActivity.class);
				// Pass position of question selected & the fragment so we can retrieve the question & inflate it in QAView
				intent.putExtra("position", position);
				intent.putExtra("fromFragment", fromFragment);
				startActivity(intent);
			}
			
		});
	
	}

	
	
	/* (non-Javadoc)
	 * @see android.app.Fragment#onResume()
	 */
	@Override
	public void onResume() {
		super.onResume();
		adapter.notifyDataSetChanged();
		
	   	// Below deals with pushing Questions, Answers & Replies that weren't posted online while the device was offline
    	NetworkManager networkManager = NetworkManager.getInstance();
		if (networkManager.isOnline(getActivity().getApplicationContext())) {
    		networkManager.flushBuffer();
    	}
		
	}

	/* (non-Javadoc)
	 * @see android.app.Fragment#onDestroyView()
	 */
	@Override
	public void onDestroyView() {
		super.onDestroyView();
		findQuestionList().deleteView(this);
	}

	/* (non-Javadoc)
	 * @see ca.ualberta.cs.queueunderflow.TView#update(java.lang.Object)
	 */
	@Override
	public void update(QuestionList model) {
		adapter.notifyDataSetChanged();
	}
	
	/**
	 * Find question list.
	 *
	 * @return the question list
	 */
	private QuestionList findQuestionList() {
		switch (fromFragment) {
		case (HOME_SCREEN_FRAGMENT):
			return ListHandler.getMasterQList();
		case (FAVORITES_FRAGMENT):
			return ListHandler.getFavsList();
		case (READING_LIST_FRAGMENT):
			return ListHandler.getReadingList();
		case (MY_QUESTIONS_FRAGMENT):
			return ListHandler.getMyQsList();
		}
		return null;
	}
	
}
