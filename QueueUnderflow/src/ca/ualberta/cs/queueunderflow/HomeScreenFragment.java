package ca.ualberta.cs.queueunderflow;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

public class HomeScreenFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		getActivity().getActionBar().setTitle("Home");
		return inflater.inflate(R.layout.fragment_home_screen, container, false);
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
		
	}

}
