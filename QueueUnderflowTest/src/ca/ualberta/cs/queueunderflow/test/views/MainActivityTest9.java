package ca.ualberta.cs.queueunderflow.test.views;

import ca.ualberta.cs.queueunderflow.User;
import ca.ualberta.cs.queueunderflow.views.MainActivity;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivityTest9 extends ActivityInstrumentationTestCase2<MainActivity>{

	public MainActivityTest9() {
		super(MainActivity.class);
	}
	public void testSetUsernameDisplay() throws Throwable {

		// Start the SetUsernameFragment
		Intent intent = new Intent();
		intent.putExtra("returnFragment", MainActivity.SET_USERNAME_FRAGMENT);
		setActivityIntent(intent);
		MainActivity activity = (MainActivity) getActivity();
		
		// Check that the default username is set to Anonymous upon the initial start
		TextView currentUsername = (TextView) activity.findViewById(ca.ualberta.cs.queueunderflow.R.id.currentUsername);
		assertEquals("Anonymous", currentUsername.getText().toString());
		assertEquals("Anonymous", User.getUserName());	// For this to work, you have to clear the data for the app on the phone first to mimic initial startup of the app for the first time
		
		// Change the username & click submit
		runTestOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				MainActivity activity = (MainActivity) getActivity();
				
				EditText newUsername = (EditText) activity.findViewById(ca.ualberta.cs.queueunderflow.R.id.newUsername);
				newUsername.setText("Geneva");
				
				assertEquals("Geneva", newUsername.getText().toString());
				Button submitBtn = (Button) activity.findViewById(ca.ualberta.cs.queueunderflow.R.id.submitBtn);
				submitBtn.performClick();	
				
			}
		});
		
		// Check that the username is has been set on the screen & in User
		assertEquals("Geneva", currentUsername.getText().toString());
		assertEquals("Geneva", User.getUserName());
	}

}
