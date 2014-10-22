package ca.ualberta.cs.queueunderflow;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;

public class HomeScreenFragment extends Fragment implements TView<QuestionList>{
	
	public static final int HOME_SCREEN_FRAGMENT = 1;
	QuestionListAdapter adapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		getActivity().getActionBar().setTitle("Home");
		View view = inflater.inflate(R.layout.fragment_home_screen, container, false);
		
		// Set up list adapter to display the questions
		ListView listView = (ListView) view.findViewById(R.id.homeListView);
		adapter = new QuestionListAdapter(getActivity(), R.layout.list_item_question, ListHandler.getMasterQList().getQuestionList());
		listView.setAdapter(adapter);
		
		// Add view to the model
		QuestionList homeScreenList = ListHandler.getMasterQList();
		homeScreenList.addView(this);
		
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
				
				// Pass position of question selected & the fragment so we can retreive the question & inflate it in QAView
				intent.putExtra("position", position);
				intent.putExtra("fromFragment", HOME_SCREEN_FRAGMENT);
				
				startActivity(intent);
			}
			
		});
		
		
	}

	@Override
	public void update(QuestionList model) {
		adapter.notifyDataSetChanged();
	}

}
