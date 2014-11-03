package ca.ualberta.cs.queueunderflow.test;

import ca.ualberta.cs.queueunderflow.Favorites;
import ca.ualberta.cs.queueunderflow.models.Question;
import ca.ualberta.cs.queueunderflow.models.QuestionList;
import junit.framework.TestCase;

public class UseCase16 extends TestCase {
	//Use CASE 16(Incorporates user stories 18 and 19)
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
