package ca.ualberta.cs.queueunderflow.views;

import android.app.Fragment;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.SearchView.OnQueryTextListener;
import ca.ualberta.cs.queueunderflow.R;
import ca.ualberta.cs.queueunderflow.TView;
import ca.ualberta.cs.queueunderflow.adapters.QuestionListAdapter;
import ca.ualberta.cs.queueunderflow.controllers.NetworkController;
import ca.ualberta.cs.queueunderflow.models.Question;
import ca.ualberta.cs.queueunderflow.models.QuestionList;
import ca.ualberta.cs.queueunderflow.singletons.ListHandler;
import ca.ualberta.cs.queueunderflow.singletons.NetworkManager;

/**
 * The Class SearchFragment.
 */
public class SearchFragment extends Fragment implements TView<QuestionList>{

	/** The search view. */
	private SearchView searchView;
	
	/** The list view. */
	private ListView listView;
	
	/** The adapter. */
	private QuestionListAdapter adapter;
	
	/** The results question list. */
	private QuestionList resultsQuestionList;
	
	
	
	/**
	 * Instantiates a new search fragment.
	 *
	 * @param searchView the search view
	 */
	public SearchFragment(SearchView searchView) {
		this.searchView = searchView;
		resultsQuestionList = ListHandler.getResultsList();
	}
	
	/* (non-Javadoc)
	 * @see android.app.Fragment#onCreateOptionsMenu(android.view.Menu, android.view.MenuInflater)
	 */
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		//searchView = (SearchView) menu.findItem(R.id.searchMenu);
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
        
        if (id == android.R.id.home) {
        	Toast.makeText(getActivity(), "collapsing after click", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
	
	/* (non-Javadoc)
	 * @see android.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_search, container, false);
		
		resultsQuestionList.clear();
		resultsQuestionList.addView(this);
		
		// Set up list adapter to display the questions
		listView = (ListView) view.findViewById(R.id.searchListView);
		adapter = new QuestionListAdapter(getActivity(), R.layout.list_item_question, resultsQuestionList.getQuestionList(), MainActivity.SEARCH_FRAGMENT);
		listView.setAdapter(adapter);
		return view;
		
	}

	
	/* (non-Javadoc)
	 * @see android.app.Fragment#onViewCreated(android.view.View, android.os.Bundle)
	 */
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		
		SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
		
		final NetworkController networkController = new NetworkController();
		
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// Go to QAViewActivity to see the Q & A
				Intent intent = new Intent(getActivity(), QAViewActivity.class);
				// Pass position of question selected & the fragment so we can retrieve the question & inflate it in QAView
				intent.putExtra("position", position);
				intent.putExtra("fromFragment", MainActivity.SEARCH_FRAGMENT);
				//intent.putExtra("questionID", resultsQuestionList.get(position).getStringID());
				startActivity(intent);
			}
			
		});
		
		searchView.setOnQueryTextListener(new OnQueryTextListener() {
			
			@Override
			public boolean onQueryTextSubmit(String query) {
				System.out.println("Query = " + query);
				
				if (!NetworkManager.getInstance().isOnline(getActivity())) {
					Toast.makeText(getActivity(), "Please try again when you have a network connection.", Toast.LENGTH_LONG).show();
				}
				else {
					networkController.populateList(resultsQuestionList, query);
				}
				return false;
			}
			
			@Override
			public boolean onQueryTextChange(String newText) {
				return false;
			}
		});

	}

	
	
	/* (non-Javadoc)
	 * @see ca.ualberta.cs.queueunderflow.TView#update(java.lang.Object)
	 */
	@Override
	public void update(QuestionList model) {
		adapter.notifyDataSetChanged();
	}
	
	/* (non-Javadoc)
	 * @see android.app.Fragment#onDestroyView()
	 */
	@Override
	public void onDestroyView() {
		super.onDestroyView();
		resultsQuestionList.deleteView(this);
	}

}