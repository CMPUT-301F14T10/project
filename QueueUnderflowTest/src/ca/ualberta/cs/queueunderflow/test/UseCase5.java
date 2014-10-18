package ca.ualberta.cs.queueunderflow.test;

import ca.ualberta.cs.queueunderflow.Answer;
import ca.ualberta.cs.queueunderflow.Question;
import ca.ualberta.cs.queueunderflow.QuestionList;
import ca.ualberta.cs.queueunderflow.User;
import junit.framework.TestCase;


public class UseCase5 extends TestCase
{
	//Use CASE 5: Incorporates user stories 5 and 7
	
	public void testAddAnswer() {
		User me= new User();
		me.setUserName("Me");
		
		QuestionList questionList= new QuestionList();
		String questionName= "A question?";
		Question questionTest= new Question(questionName,me.getUserName());
		questionList.add(questionTest);
		int question_index= questionList.questionIndex(questionTest);
		
		Question sameQuestion= questionList.get(question_index);
		//Exception: Where the answer entered is whitespaces only
		String emptyAnswer= "          ";
		String empty= emptyAnswer.trim();
		String result;
		if (empty.length()==0) {
			result="Reenter an valid answer";
		}
		else {
			result=null;
		}
		assertTrue("emptyAnswer is empty", result!=null);
		
		//Adding an answer
		String answerName= "An answer";
		String authorName= "You";
		Answer testAnswer= new Answer(answerName,authorName);
		sameQuestion.addAnswer(testAnswer);
		questionList.set(question_index, sameQuestion);
		assertTrue("Answer List isn't empty",questionList.get(question_index).getAnswerListSize()==1 ) ;
		
		//Exception: Where there is no online connectivity
		assertFalse("No network connectivity, push online later.",questionList.pushOnline());
	}
}
