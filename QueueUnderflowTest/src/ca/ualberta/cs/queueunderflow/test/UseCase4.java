package ca.ualberta.cs.queueunderflow.test;

import ca.ualberta.cs.queueunderflow.Picture;
import ca.ualberta.cs.queueunderflow.Question;
import ca.ualberta.cs.queueunderflow.QuestionList;
import junit.framework.TestCase;


public class UseCase4 extends TestCase
{
	//Use CASE 4 (incorporates user stories 4 and 7)
	public void testAskQuestion() {
		
		//Add a question to the list
		QuestionList questionList= new QuestionList();
		String questionName= "How does this work?";

		String author= "Me";
		Question questionTest= new Question(questionName,author);
		Picture pic= new Picture(32);
		questionTest.setPicture(pic);
		
		questionList.add(questionTest);
		assertTrue("Question List isn't empty", questionList.size()==1);
		
		//Exception: Where the question entered is whitespaces only
		String questionName2= "        ";
		String empty= questionName2.trim();
		String result;
		if (empty.length()==0) {
			result="Reenter a question";
		}
		else {
			result=null;
		}
		assertTrue("Reenter a valid question", result!=null);
		
		/*Exception: Where there is no online connectivity
		 * 
		 By default, there is no network connectivity (questionList connectivity is initialized to be 0)
		 because this is a mock test, not the actual implementation.
		 */
		assertFalse("No network connectivity, push online later.",questionList.pushOnline());
		
		
	}
}
