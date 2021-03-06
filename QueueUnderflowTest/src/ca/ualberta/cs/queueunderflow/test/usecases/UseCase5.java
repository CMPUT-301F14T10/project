package ca.ualberta.cs.queueunderflow.test.usecases;

import ca.ualberta.cs.queueunderflow.models.Answer;
import ca.ualberta.cs.queueunderflow.models.Question;
import ca.ualberta.cs.queueunderflow.models.QuestionList;
import ca.ualberta.cs.queueunderflow.singletons.User;
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
		int flag = 0; // indicates if the whitespace exception is caught & handled
		try {
			Answer answer1 = new Answer(emptyAnswer, me.getUserName());
			sameQuestion.addAnswer(answer1);
		} catch (IllegalArgumentException e) {
			flag = 1;
		}
		assertTrue("Whitespace answer exception caught & handled", flag == 1);
		assertTrue("Whitespace only answer is not added to the question", sameQuestion.getAnswerListSize() == 0);
		
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
