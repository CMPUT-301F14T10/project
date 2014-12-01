package ca.ualberta.cs.queueunderflow.test.usecases;

import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import ca.ualberta.cs.queueunderflow.models.Answer;
import ca.ualberta.cs.queueunderflow.models.Question;
import ca.ualberta.cs.queueunderflow.models.QuestionList;
import ca.ualberta.cs.queueunderflow.models.Reply;
import ca.ualberta.cs.queueunderflow.singletons.LocationHandler;
import ca.ualberta.cs.queueunderflow.singletons.NetworkManager;
import ca.ualberta.cs.queueunderflow.singletons.User;
import ca.ualberta.cs.queueunderflow.views.MainActivity;
import ca.ualberta.cs.queueunderflow.R;

//Use CASE 20(incorporates user story 27)
public class UseCase20 extends ActivityInstrumentationTestCase2<MainActivity>
{
	
	public UseCase20() {
		super(MainActivity.class);
	}
	
//	public void testLocationNear() throws Throwable { 
//		
//
//		// Start the SetLocationFragment
//		Intent intent = new Intent();
//		intent.putExtra("returnFragment", MainActivity.SET_USERNAME_FRAGMENT);
//		setActivityIntent(intent);
//		MainActivity activity = (MainActivity) getActivity();
//		
//		//Checks if the locations of the question and user are the same
//		LocationHandler loc = new LocationHandler(getActivity());
//		User me= new User();
//		
//		QuestionList questionList= new QuestionList();
//		String questionName= "How does this work?";
//		String author = "Ondra";
//		String location= "Edmonton";
//		
//		
//		me.setUserName(author);
//		me.setLocation(location);
//		Question questionTest= new Question(questionName,me.getUserName());
//		questionList.add(questionTest);
//		assertTrue("Locations are near", questionTest.getLocation().equals(me.getLocation()));
//	}
}