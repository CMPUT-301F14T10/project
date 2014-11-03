package ca.ualberta.cs.queueunderflow.test;

import ca.ualberta.cs.queueunderflow.ReadingList;
import ca.ualberta.cs.queueunderflow.models.Question;
import ca.ualberta.cs.queueunderflow.models.QuestionList;
import junit.framework.TestCase;

public class UseCase15 extends TestCase {
	
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
