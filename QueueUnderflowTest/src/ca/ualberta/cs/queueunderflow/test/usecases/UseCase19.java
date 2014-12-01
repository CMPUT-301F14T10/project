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

//Use CASE 19(incorporates user story 24 and 26)
public class UseCase19 extends ActivityInstrumentationTestCase2<MainActivity>
{
	
	public UseCase19() {
		super(MainActivity.class);
	}
	
	public void testValidGPS() throws Throwable { 
		
		//Variable that says if the phone has a usable GPS
		boolean hasGPS = false;

		// Start the SetLocationFragment
		Intent intent = new Intent();
		intent.putExtra("returnFragment", MainActivity.SET_USERNAME_FRAGMENT);
		setActivityIntent(intent);
		MainActivity activity = (MainActivity) getActivity();
		
		//Check that there is a usable GPS on the phone
		LocationHandler loc = new LocationHandler(getActivity());
		hasGPS = loc.isGPSEnabled();
		if (loc.isGPSEnabled())
		{
			assertEquals(true,hasGPS);
		}
		else
		{
			assertEquals(false,hasGPS);
		}
	}

//	public void testSetLocation() {
//		User me= new User();
//		
//		QuestionList questionList= new QuestionList();
//		String questionName= "How does this work?";
//		String author = "Ondra"
//		String location= "Edmonton";
//		
//		// Checks if username is correctly set when making a question/answer/reply - valid username
//		me.setUserName(author);
//		me.setLocation(location);
//		Question questionTest= new Question(questionName,me.getUserName());
//		questionList.add(questionTest);
//		assertTrue("Author location is Edmonton", questionTest.getLocation().equals("Edmonton"));
//		
//		int question_index= questionList.questionIndex(questionTest);
//		assertTrue("question index is 0", question_index==0);
//		String answerName= "An answer";
//		Answer testAnswer= new Answer(answerName,author);
//
//		assertTrue("Answer location is Edmonton",testAnswer.getLocation().equals("Edmonton"));
//		Reply a_reply= new Reply("Go to stackoverflow",author);
//		assertTrue ("Reply location is Edmonton", a_reply.getLocation()).equals("Edmonton"));
//		testAnswer.addReply(a_reply);
//		
//		
//		
//		//Exception: No username is typed in or only whitespace is typed in
//		String no_location= "         ";
//		int flag = 0; // indicates if the whitespace exception is caught & handled
//		try {
//			me.setUserName(no_location);
//		} catch (IllegalArgumentException e) {
//			flag = 1;
//		}
//		assertTrue("Whitespace location exception caught & handled", flag == 1);
//		assertEquals("No location specified, location remains unknown", "Anonymous", me.getLocation());
//		
//		// Checks if username is correctly set when making a question - invalid username entered, set to Anonymous
//		Question questionTest1= new Question(questionName,me.getUserName());
//		questionList.add(questionTest1);
//		assertTrue("Location is Edmonton", questionTest1.getLocation().equals("Edmonton"));
//
//		
//
//	}
}