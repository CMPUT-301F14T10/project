package ca.ualberta.cs.queueunderflow.test;

import ca.ualberta.cs.queueunderflow.Favorites;
import ca.ualberta.cs.queueunderflow.Question;
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
	}
	
}
