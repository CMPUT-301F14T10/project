package ca.ualberta.cs.queueunderflow.test.controllers;

import android.test.ActivityInstrumentationTestCase2;
import ca.ualberta.cs.queueunderflow.controllers.NetworkController;
import ca.ualberta.cs.queueunderflow.models.Answer;
import ca.ualberta.cs.queueunderflow.models.Question;
import ca.ualberta.cs.queueunderflow.models.QuestionList;
import ca.ualberta.cs.queueunderflow.models.Reply;
import ca.ualberta.cs.queueunderflow.views.MainActivity;

// This set of tests will test pushing questions, answers, replies & upvotes online to the network
public class NetworkControllerTest extends ActivityInstrumentationTestCase2<MainActivity> {

	NetworkController networkController = new NetworkController();
	Question defaultQuestion;
	Answer defaultAnswer;
	
	public NetworkControllerTest() {
		super(MainActivity.class);
	}
	
	public void setUp() {
		defaultQuestion = new Question("This is the default question for testing", "Me");
		defaultAnswer = new Answer("This is the default answer for testing", "You");
		defaultQuestion.addAnswer(defaultAnswer);
		networkController.addQuestion(defaultQuestion);	
	}

	public void testAskQuestion() {
		Question newQuestion = new Question("Push question online", "Me");
		networkController.addQuestion(newQuestion);
		
		callThreadSleep();
		
		QuestionList resultsList = new QuestionList();
		networkController.populateList(resultsList, "Push question online");
		
		assertTrue(resultsList.size() != 0);
		int flag = 0;
		for (int i = 0; i < resultsList.size(); i++) {
			Question resultQuestion = resultsList.get(i);
			if (resultQuestion.equals(newQuestion)) {
				flag = 1;
				break;
			}
		}

		assertTrue(flag == 1);
	}

	public void testAddAnswer() {
		Answer newAnswer = new Answer("Push answer online", "Me");

		Question resultQuestion = null;
		while (resultQuestion == null) {
			resultQuestion = networkController.getQuestion(defaultQuestion.getStringID());
		}
		
		networkController.addAnswer(defaultQuestion.getStringID(), newAnswer);

		callThreadSleep();
		
		resultQuestion = null;
		resultQuestion = networkController.getQuestion(defaultQuestion.getStringID());
		
		int flag = 0;
		for(int i = 0; i < resultQuestion.getAnswerListSize(); i++) {
			Answer resultAnswer = resultQuestion.getAnswerList().getAnswer(i);
			if (resultAnswer.equals(newAnswer)) {
				flag = 1;
				break;
			}
		}
		
		assertTrue(flag == 1);
		
	}
	
	public void testAddQuestionReply() {
		Reply newQReply = new Reply("Push question reply online", "Me");

		Question resultQuestion = null;
		while (resultQuestion == null) {
			resultQuestion = networkController.getQuestion(defaultQuestion.getStringID());
		}
		
		networkController.addQReply(defaultQuestion.getStringID(), newQReply);

		callThreadSleep();
		
		resultQuestion = null;
		resultQuestion = networkController.getQuestion(defaultQuestion.getStringID());
		
		int flag = 0;
		for(int i = 0; i < resultQuestion.getSizeReplies(); i++) {
			Reply resultReply = resultQuestion.getReplyAt(i);
			if (resultReply.equals(newQReply)) {
				flag = 1;
				break;
			}
		}
		
		assertTrue(flag == 1);
		
	}
	
	
	public void testAddAnswerReply() {
		Reply newAReply = new Reply("Push answer reply online", "Me");
		
		// Ensure the default question was pushed online
		Question resultQuestion = null;
		while (resultQuestion == null) {
			resultQuestion = networkController.getQuestion(defaultQuestion.getStringID());
		}
		
		// At this point, the default question should be pushed online. Now we ensure that the default answer was pushed online before continuing
		Answer resultAnswer = null;
		while (resultAnswer == null) {
			resultQuestion = networkController.getQuestion(defaultQuestion.getStringID());
			resultAnswer = resultQuestion.getAnswerList().getAnswerFromID(defaultAnswer.getStringID());
		}
		
		// Now both should be pushed online
		// Add the reply to the default answer
		networkController.addAReply(defaultQuestion.getStringID(), defaultAnswer.getStringID(), newAReply);

		callThreadSleep();
		
		resultQuestion = networkController.getQuestion(defaultQuestion.getStringID());
		resultAnswer = resultQuestion.getAnswerList().getAnswerFromID(defaultAnswer.getStringID());
		
		int flag = 0;
		for(int i = 0; i < resultAnswer.getSizeReplies(); i++) {
			Reply resultReply = resultAnswer.getReplyAt(i);
			if (resultReply.equals(newAReply)) {
				flag = 1;
				break;
			}
		}
		
		assertTrue(flag == 1);
	}
	
	public void testUpvoteQuestion() {

		Question resultQuestion = null;
		while (resultQuestion == null) {
			resultQuestion = networkController.getQuestion(defaultQuestion.getStringID());
		}
		
		networkController.upvoteQuestion(defaultQuestion.getStringID());

		callThreadSleep();
		
		resultQuestion = null;
		resultQuestion = networkController.getQuestion(defaultQuestion.getStringID());
		
		assertTrue(resultQuestion.getUpvotes() == 1);
		
	}
	
	public void testUpvoteAnswer() {
		// Ensure the default question was pushed online
		Question resultQuestion = null;
		while (resultQuestion == null) {
			resultQuestion = networkController.getQuestion(defaultQuestion.getStringID());
		}
		
		// At this point, the default question should be pushed online. Now we ensure that the default answer was pushed online before continuing
		Answer resultAnswer = null;
		while (resultAnswer == null) {
			resultQuestion = networkController.getQuestion(defaultQuestion.getStringID());
			resultAnswer = resultQuestion.getAnswerList().getAnswerFromID(defaultAnswer.getStringID());
		}
		
		// Now both should be pushed online
		// Upvote the default answer
		networkController.upvoteAnswer(defaultQuestion.getStringID(), defaultAnswer.getStringID());

		callThreadSleep();
		
		resultQuestion = networkController.getQuestion(defaultQuestion.getStringID());
		resultAnswer = resultQuestion.getAnswerList().getAnswerFromID(defaultAnswer.getStringID());
		
		assertTrue(resultAnswer.getUpvotes() == 1);
	}
	
	private void callThreadSleep() {
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
