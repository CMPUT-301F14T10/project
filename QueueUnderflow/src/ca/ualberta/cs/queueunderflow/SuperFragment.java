package ca.ualberta.cs.queueunderflow;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

// This SuperFragment is used for handling/displaying the HomeScreen, FavoritesScreen, ReadingListScreen, & MyQuestionsScreen
public class SuperFragment extends Fragment implements TView<QuestionList>{
	
	public static final int HOME_SCREEN_FRAGMENT = 1;
	public static final int FAVORITES_FRAGMENT = 2;
	public static final int MY_QUESTIONS_FRAGMENT = 3;
	public static final int READING_LIST_FRAGMENT = 4;
	
	private QuestionListAdapter adapter;
	protected int fromFragment;
	
	public SuperFragment (int fromFragment) {
		this.fromFragment = fromFragment;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		
		
		// Put in controller - This must be done first before inflating the view & adding the view to the model else it may crash
		Buffer buffer = new Buffer();
		if (buffer.isFavBufferEmpty() == false) {
			buffer.flushFav();
		}
		if (buffer.isReadingListBufferEmpty() == false) {
			buffer.flushReadingList();
		}
		//
		
		View view = inflater.inflate(R.layout.fragment_home_screen, container, false);
		
		// Add view to the model
		QuestionList screenQList = findQuestionList();
		screenQList.addView(this);
		
		// Set up list adapter to display the questions
		ListView listView = (ListView) view.findViewById(R.id.homeListView);
		adapter = new QuestionListAdapter(getActivity(), R.layout.list_item_question, screenQList.getQuestionList());
		listView.setAdapter(adapter);
		
		return view;
	}

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

	@Override
	public void update(QuestionList model) {
		adapter.notifyDataSetChanged();
	}
	
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
