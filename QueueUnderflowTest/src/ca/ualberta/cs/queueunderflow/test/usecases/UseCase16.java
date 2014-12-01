package ca.ualberta.cs.queueunderflow.test.usecases;

import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.AnalogClock;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import ca.ualberta.cs.queueunderflow.Favorites;
import ca.ualberta.cs.queueunderflow.R;
import ca.ualberta.cs.queueunderflow.adapters.QuestionListAdapter;
import ca.ualberta.cs.queueunderflow.models.Answer;
import ca.ualberta.cs.queueunderflow.models.Question;
import ca.ualberta.cs.queueunderflow.models.QuestionList;
import ca.ualberta.cs.queueunderflow.models.Reply;
import ca.ualberta.cs.queueunderflow.singletons.ListHandler;
import ca.ualberta.cs.queueunderflow.singletons.NetworkManager;
import ca.ualberta.cs.queueunderflow.views.MainActivity;
import junit.framework.TestCase;

//Use CASE 16(Incorporates user stories 18 and 19)
public class UseCase16 extends ActivityInstrumentationTestCase2<MainActivity> {
	
	Question questionTest;
	
	public UseCase16() {
		super(MainActivity.class);
	}
	
	public void testFavorites() {
		String questionName= "How does this work?";
		String author= "Me";
		Question questionTest= new Question(questionName,author);
		Favorites favorite_questions= new Favorites();
		favorite_questions.add(questionTest);
		assertTrue("Favorites size is equal 1", favorite_questions.size()==1);
		
		//testing exception 2 when there is no network connection
		QuestionList questionList= new QuestionList();
		String exception="";
		assertTrue("internet connection not available", questionList.getOnline()==false);
		if (questionList.getOnline()==false){
			exception="internet connection not available";
		}
		assertEquals("internet connection not available", exception);
	}
	
}
