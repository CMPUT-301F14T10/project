package ca.ualberta.cs.queueunderflow.views;

import android.app.Fragment;
import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import ca.ualberta.cs.queueunderflow.R;
import ca.ualberta.cs.queueunderflow.adapters.QuestionListAdapter;
import ca.ualberta.cs.queueunderflow.models.QuestionList;

public class SearchFragment extends Fragment {

	private SearchView searchView;
	private ListView listView;
	private QuestionListAdapter adapter;
	private QuestionList resultsQuestionList;
	
	
	
	public SearchFragment(SearchView searchView) {
		this.searchView = searchView;
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		//searchView = (SearchView) menu.findItem(R.id.searchMenu);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_search, container, false);
		
		resultsQuestionList = new QuestionList();
		listView = (ListView) getActivity().findViewById(R.id.searchListView);
		//adapter = new QuestionListAdapter(getActivity(), R.layout.list_item_question, resultsQuestionList.getQuestionList(), MainActivity.SEARCH_FRAGMENT);
		//listView.setAdapter(adapter);
		
		return view;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		
		SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
		
		searchView.setOnQueryTextListener(new OnQueryTextListener() {
			
			@Override
			public boolean onQueryTextSubmit(String query) {
				//String query = searchView.getQuery().toString();
				System.out.println("Query = " + query);
				
				return false;
			}
			
			@Override
			public boolean onQueryTextChange(String newText) {
				// TODO Auto-generated method stub
				return false;
			}
		});

	}

}
