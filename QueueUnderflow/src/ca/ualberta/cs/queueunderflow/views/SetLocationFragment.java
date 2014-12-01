package ca.ualberta.cs.queueunderflow.views;

import android.app.Activity;
import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import ca.ualberta.cs.queueunderflow.R;
import ca.ualberta.cs.queueunderflow.singletons.LoadSave;
import ca.ualberta.cs.queueunderflow.singletons.LocationHandler;
import ca.ualberta.cs.queueunderflow.singletons.NetworkManager;
import ca.ualberta.cs.queueunderflow.singletons.User;

/**
 * The Class SetLocationFragment.
 */
public class SetLocationFragment extends Fragment implements OnClickListener{

	/** The l handler. */
	LocationHandler lHandler;
	
	/** The gps thread. */
	private GetGPSBackground gpsThread;
	
	/* (non-Javadoc)
	 * @see android.app.Fragment#onStop()
	 */
	@Override
	public void onStop() {
		super.onStop();
		//Stop thread from continuing before we leave the activity
		if(gpsThread != null) gpsThread.cancel(true);
		//Stop GPS from listening before we leave the activity.
		if(lHandler != null) lHandler.GPSUnlisten();
	}
	
    /* (non-Javadoc)
     * @see android.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getActivity().getActionBar().setTitle("Home");
        return inflater.inflate(R.layout.activity_set_location_fragment, container, false);
    }

    /* (non-Javadoc)
     * @see android.view.View.OnClickListener#onClick(android.view.View)
     */
    public void onClick(View view)
    {
    	//Occurs when the get GPS button is clicked.
    	
    	final Activity act = getActivity();
    	final EditText latitude = (EditText) getView().findViewById(R.id.txtCity);
    	final EditText longitude = (EditText) getView().findViewById(R.id.txtCountry);
    	final TextView status = (TextView) getView().findViewById(R.id.textViewStatus);
    	// Move this into a controller once this works...

    	boolean error = false;
    	
    	//Create LocationHandler...
    	lHandler = new LocationHandler(getActivity());
    	
    	//See if GPS is enabled.
    	if(!lHandler.isNetworkEnabled() && !lHandler.isGPSEnabled())
    	{
    		error = true;
    		Toast.makeText(getActivity(), "GPS or network access is disabled. Enable either and try again.", Toast.LENGTH_SHORT).show();
    	}
    	
    	
    	//See if there is network connectivity.
    	NetworkManager network = NetworkManager.getInstance();
    	if(!network.isOnline(getActivity()) && !error)
    	{
    		error = true;
    		Toast.makeText(getActivity(), "Internet access is required to get your location.", Toast.LENGTH_SHORT).show();
    	}
    	
    	
    	//Try to get GPS lock if there are no errors
    	if(!error)
    	{
    		
    		lHandler.GetGPSLocation();
    		gpsThread = new GetGPSBackground();
    		gpsThread.execute();
    		status.setText("Waiting for response from GPS/Network...");
    	}
    	
    }
    
    /* (non-Javadoc)
     * @see android.app.Fragment#onViewCreated(android.view.View, android.os.Bundle)
     */
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

    	// Idea of using the class as OnClickListener from:
    	// http://stackoverflow.com/questions/6091194/how-to-handle-button-clicks-using-the-xml-onclick-within-fragments
    	// by Adorjan Princz
    	Button b = (Button) getActivity().findViewById(R.id.buttonGetLocation);
    	b.setOnClickListener(this); //Set get from GPS button listener to OnClick() in this class.

    	final CheckBox useLocation = (CheckBox) getActivity().findViewById(R.id.use_location_data);
    	final EditText city = (EditText) getActivity().findViewById(R.id.txtCity);
    	final EditText country = (EditText) getActivity().findViewById(R.id.txtCountry);
    	
    	//Set fields to data in user...
    	//useLocation.setChecked(User.getUseLocation());
    	
    	//Use an on click listener to detect whether checkbox is checked or not
    	//Source: http://www.mkyong.com/android/android-checkbox-example/
    	//Source: http://stackoverflow.com/questions/8386832/android-checkbox-listener
    	useLocation.setChecked(User.displayCheckbox());
    	
    	if (useLocation.isChecked()) {
			User.setUseLocation(true);
    	}
    	
    	useLocation.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (useLocation.isChecked()) {
					//If checked, use the location
					Toast.makeText(getActivity(), "Using location data", Toast.LENGTH_SHORT).show();
					User.setUseLocation(true);
					User.setCheckbox(true);
				}
				else {
					//If not, don't use the location
					Toast.makeText(getActivity(), "Not using location data",Toast.LENGTH_SHORT).show();
					User.setUseLocation(false);
					User.setCheckbox(false);
				}
			}
		});
    	
    	city.setText(User.getCity());
    	country.setText(User.getCountry());
    	
    	Button submit = (Button) getActivity().findViewById(R.id.buttonSubmit);
    	submit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				//To check if the user sets a whitespace only location, use trim on the string
				String sCity = city.getText().toString().trim();
				String sCountry = country.getText().toString().trim();
				
				boolean validCity= sCity.length()>0;
				boolean validCountry=sCountry.length()>0;
				
				if (validCity && validCountry) {
				
					boolean isChecked = useLocation.isChecked();
				
					//User.setUseLocation(isChecked);
					User.setCity(sCity);
					User.setCountry(sCountry);

					LoadSave ls = LoadSave.getInstance();
					ls.saveCity(sCity);
					ls.saveCountry(sCountry);
					ls.saveUseLocation(isChecked);

					Toast.makeText(getActivity(), "Location data successfully saved!", Toast.LENGTH_SHORT).show();
				}
				else {
					if (validCity) {
						
						boolean isChecked = useLocation.isChecked();
						User.setCity(sCity);
						User.setCountry("Unknown");

						LoadSave ls = LoadSave.getInstance();
						ls.saveCity(sCity);
						ls.saveCountry("Unknown");
						ls.saveUseLocation(isChecked);

						Toast.makeText(getActivity(), "City location saved!, country is invalid and set to Unknown", Toast.LENGTH_SHORT).show();
					}
					else if (validCountry) {
						boolean isChecked = useLocation.isChecked();
						User.setCity("Unknown");
						User.setCountry(sCountry);

						LoadSave ls = LoadSave.getInstance();
						ls.saveCity("Unknown");
						ls.saveCountry(sCountry);
						ls.saveUseLocation(isChecked);

						Toast.makeText(getActivity(), "Country location saved!, city is invalid and set to Unknown", Toast.LENGTH_SHORT).show();
					}
					
					else {
						boolean isChecked=useLocation.isChecked();
						User.setCity("Unknown");
						User.setCountry("Unknown");
						
						LoadSave ls = LoadSave.getInstance();
						ls.saveCity("Unknown");
						ls.saveCountry("Unknown");
						ls.saveUseLocation(isChecked);

						Toast.makeText(getActivity(), "Both country and city are invalid, both set to Unknown", Toast.LENGTH_SHORT).show();
					}
					
				}
				
			}
    		
    	});
    	
    }
    
    //GPS thread.
    /**
     * The Class GetGPSBackground.
     */
    public class GetGPSBackground extends AsyncTask<String, String, String> {

		/* (non-Javadoc)
		 * @see android.os.AsyncTask#doInBackground(Params[])
		 */
		@Override
		protected String doInBackground(String... params) {
			while(LocationHandler.listeningGPS){
				//Waiting for response...
			}
			
			//publishProgress("GPS Location Found!");
			
			//Now get city name...
			publishProgress("Getting locale information...");
			LocationHandler ls = new LocationHandler(getActivity());
			String locationString = ls.getLocationFromCoordinates(LocationHandler.latitude, LocationHandler.longitude);
			publishProgress("Location data found!");
			return locationString;
		}
    	
		/* (non-Javadoc)
		 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
		 */
		protected void onPostExecute(String result) {
			
			String city = null;
			String country = null;
			
			if(result == null)
			{
				//No idea where the user is. Set everything to unknown. Show coordinates maybe?
				city = "Unknown";
				country = "Unknown";
			}else{
				Log.d("test", result);
				String[] results = result.split("\\|"); //Split the array by |. Since it's a regular expression, need to escape it.
				Log.d("test", results[0]);
				Log.d("test", results[1]);
				if(results.length == 2)
				{
					city = results[0];
					country = results[1];
				}
			}
			
			EditText cityText = (EditText) getActivity().findViewById(R.id.txtCity);
			EditText countryText = (EditText) getActivity().findViewById(R.id.txtCountry);
			
			cityText.setText(city);
			countryText.setText(country);
			
		}
		
		/* (non-Javadoc)
		 * @see android.os.AsyncTask#onProgressUpdate(Progress[])
		 */
		protected void onProgressUpdate(String... result) {
			TextView status = (TextView) getActivity().findViewById(R.id.textViewStatus);
			status.setText(result[0]);
		}
		
    }

}
