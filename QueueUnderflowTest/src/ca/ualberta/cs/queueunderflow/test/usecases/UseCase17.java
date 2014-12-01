package ca.ualberta.cs.queueunderflow.test.usecases;

import ca.ualberta.cs.queueunderflow.models.Answer;
import ca.ualberta.cs.queueunderflow.models.Question;
import ca.ualberta.cs.queueunderflow.models.QuestionList;
import ca.ualberta.cs.queueunderflow.models.Reply;
import ca.ualberta.cs.queueunderflow.singletons.User;
import junit.framework.TestCase;

public class UseCase17 extends TestCase {

	//Use CASE 17 (incorporates user stories 20 and 21)
	public void testPush() {
		User me= new User();
		me.setUserName("Me");
		
		String questionName= "A question?";
		Question questionTest= new Question(questionName,me.getUserName());
		
		//Adding replies to question
		Reply question_reply= new Reply("Whats going on",me.getUserName());
		
		questionTest.addReply(question_reply);
		QuestionList questionList= new QuestionList();
		questionList.add(questionTest);
		
		int question_index= questionList.questionIndex(questionTest);
		
		//Adding an answer
		Question sameQuestion= questionList.get(question_index);
		String answerName= "An answer";
		
		Answer testAnswer= new Answer(answerName,me.getUserName());
		
		//Add replies to answer
		Reply answer_reply= new Reply("Go to stackoverflow",me.getUserName());
		testAnswer.addReply(answer_reply);
		sameQuestion.addAnswer(testAnswer);
		questionList.set(question_index, sameQuestion);
		
		assertTrue("No network connection", questionList.getOnline()==false);
		//Once have network connectivity, push replies, questions, answers online
		questionList.setOnline();
		assertTrue("Push stuff online", questionList.getOnline());
	}
	
}
