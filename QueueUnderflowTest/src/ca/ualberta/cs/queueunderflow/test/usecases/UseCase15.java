package ca.ualberta.cs.queueunderflow.test.usecases;

import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import ca.ualberta.cs.queueunderflow.R;
import ca.ualberta.cs.queueunderflow.ReadingList;
import ca.ualberta.cs.queueunderflow.adapters.QuestionListAdapter;
import ca.ualberta.cs.queueunderflow.models.Question;
import ca.ualberta.cs.queueunderflow.models.QuestionList;
import ca.ualberta.cs.queueunderflow.singletons.ListHandler;
import ca.ualberta.cs.queueunderflow.singletons.NetworkManager;
import ca.ualberta.cs.queueunderflow.views.MainActivity;

//Use CASE 15(Incorporates user stories 17)
public class UseCase15 extends ActivityInstrumentationTestCase2<MainActivity> {
	
	public UseCase15() {
		super(MainActivity.class);
	}
	
	public void testReadingList() {
		String questionName= "How does this work?";
		String author= "Me";
		Question questionTest= new Question(questionName,author);
		
		ReadingList readingList= new ReadingList();
		readingList.add(questionTest);
		
		assertTrue("Reading list isn't empty", readingList.size()==1);
		
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
