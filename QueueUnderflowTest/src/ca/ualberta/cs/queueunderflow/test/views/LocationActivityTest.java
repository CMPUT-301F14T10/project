package ca.ualberta.cs.queueunderflow.test.views;

import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.TextView;
import ca.ualberta.cs.queueunderflow.singletons.User;
import ca.ualberta.cs.queueunderflow.views.MainActivity;

public class LocationActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {

	public LocationActivityTest() {
		super(MainActivity.class);
		// TODO Auto-generated constructor stub
	}

	protected void setUp() throws Exception {
		User.setCity("Unknown");
		User.setCountry("Unknown");
	}

	public void testSetLocation() throws Throwable {
		
		final String cityString = "TestCity";
		final String countryString = "TestCountry";
		
		Intent intent = new Intent();
		intent.putExtra("returnFragment", MainActivity.SET_LOCATION_FRAGMENT);
		setActivityIntent(intent);
		
		
		getActivity();
		
		runTestOnUiThread(new Runnable() {

			@Override
			public void run() {
				MainActivity activity = (MainActivity) getActivity();
				
				TextView cityT = (TextView) activity.findViewById(ca.ualberta.cs.queueunderflow.R.id.txtCity);
				TextView countryT = (TextView) activity.findViewById(ca.ualberta.cs.queueunderflow.R.id.txtCountry);
				Button submit = (Button) activity.findViewById(ca.ualberta.cs.queueunderflow.R.id.buttonSubmit);
				
				cityT.setText(cityString);
				countryT.setText(countryString);
				
				submit.performClick();
				
			}
			
		});
		
		//Check to see if location has changed.
		assertEquals(cityString, User.getCity());
		assertEquals(countryString, User.getCountry());
		
	}
	
}
