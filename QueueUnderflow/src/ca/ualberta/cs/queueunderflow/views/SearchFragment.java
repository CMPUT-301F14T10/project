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
import ca.ualberta.cs.queueunderflow.ListHandler;
import ca.ualberta.cs.queueunderflow.NetworkController;
import ca.ualberta.cs.queueunderflow.NetworkManager;
import ca.ualberta.cs.queueunderflow.R;
import ca.ualberta.cs.queueunderflow.TView;
import ca.ualberta.cs.queueunderflow.adapters.QuestionListAdapter;
import ca.ualberta.cs.queueunderflow.models.Question;
import ca.ualberta.cs.queueunderflow.models.QuestionList;

public class SearchFragment extends Fragment implements TView<QuestionList>{

	private SearchView searchView;
	private ListView listView;
	private QuestionListAdapter adapter;
	private QuestionList resultsQuestionList;
	
	
	
	public SearchFragment(SearchView searchView) {
		this.searchView = searchView;
		resultsQuestionList = ListHandler.getResultsList();
	}
	
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
        
        // This is never called - can't figure out why
        if (id == android.R.id.home) {
        	Toast.makeText(getActivity(), "collapsing after click", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
	
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
				//String query = searchView.getQuery().toString();
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
				// TODO Auto-generated method stub
				return false;
			}
		});

	}

	
	
	@Override
	public void update(QuestionList model) {
		adapter.notifyDataSetChanged();
	}
	
	@Override
	public void onDestroyView() {
		super.onDestroyView();
		resultsQuestionList.deleteView(this);
	}

}