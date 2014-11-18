package ca.ualberta.cs.queueunderflow.views;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import ca.ualberta.cs.queueunderflow.LocationHandler;
import ca.ualberta.cs.queueunderflow.NetworkManager;
import ca.ualberta.cs.queueunderflow.R;

public class SetLocationFragment extends Fragment
{

	LocationHandler lHandler;
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getActivity().getActionBar().setTitle("Home");
        return inflater.inflate(R.layout.activity_set_location_fragment, container, false);
    }

    public void GPSButtonClicked(View view)
    {
    	// Move this into a controller once this works...
    	
    	
    	//GPS Button is clicked.
    	boolean error = false;
    	
    	//Create LocationHandler...
    	LocationHandler loc = new LocationHandler(getActivity());
    	
    	//See if GPS is enabled.
    	if(!loc.isGPSEnabled())
    	{
    		error = true;
    		Toast.makeText(getActivity(), "GPS is disabled. Enable it and try again.", Toast.LENGTH_SHORT).show();
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
    		lHandler = new LocationHandler(getActivity());
    		//lHandler.GPSListen(); //This doesn't work right yet.
    	}
    }
    
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        // Inflate the menu; this adds items to the action bar if it is present.
    }

}
