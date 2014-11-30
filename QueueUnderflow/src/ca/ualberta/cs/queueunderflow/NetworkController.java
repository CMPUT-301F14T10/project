package ca.ualberta.cs.queueunderflow;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import ca.ualberta.cs.queueunderflow.models.Answer;
import ca.ualberta.cs.queueunderflow.models.Question;
import ca.ualberta.cs.queueunderflow.models.QuestionList;
import ca.ualberta.cs.queueunderflow.models.Reply;

// Implement an interface?
public class NetworkController {

	private ESManager esManager;
	
	// For retrieving a list or a question
	private List<Question> tempList;
	private Question tempQuestion;
	
	 /*	Index Location - What it tracks
	  * 0 - populateListThread
	  * 1 - addQuestionThread - not used
	  * 2 - getQuestionThread
	  * 3 - addAnswerThread
	  * 4 - addQReplyThread
	  * 5 - addAReplyThread
	  * 6 - upvoteQuestionThread - not used
	  * 7 - upvoteAnswerThread
	  * 
	  * Note : Some are not used but are in the vector in case it may be needed in the future & to keep the logical groupings
	  */
	private Vector<Boolean> isThreadFinished;
	
	public NetworkController() {
		esManager = new ESManager();
		tempList = new ArrayList<Question>();
		
		isThreadFinished = new Vector<Boolean>(8);
		for (int i = 0; i < 8; i++) {
			isThreadFinished.add(false);
		}
	}
	
	public void addQuestion(Question newQuestion) {
		Thread thread = new AddQThread(newQuestion);
		thread.start();
	}

	public Question getQuestion(String questionID) {
		Thread thread = new GetQuestionThread(questionID);
		thread.start();
		
    	// Make sure populateThread is done and we retrieved the results from network before we continue to ensure we're not returning null
    	while (isThreadFinished.get(2) != true) {
    	}
    	
    	isThreadFinished.set(2, false);
    	
    	
		return tempQuestion;
	}
	
	class GetQuestionThread extends Thread {
		private String questionID;
		
		public GetQuestionThread(String questionID) {
			this.questionID = questionID;
		}

		@Override
		public void run() {
			tempQuestion = esManager.getQuestion(questionID);
			
			// PRINTING FOR ME
			if (tempQuestion != null) {
				System.out.println("getQuestionThread : questionName ---> " + tempQuestion.getName());
			}
			
			isThreadFinished.set(2, true);
		}

	}
	
	public void populateList(QuestionList list, String search) {
		Thread thread = new PopulateListThread(search);
		thread.start();
		fillQuestionList(list);
	}
    
    private void fillQuestionList(QuestionList questionList) {
    	System.out.println("INSIDE fillQuestionList");
    	
    	// Make sure populateThread is done and we retrieved the results from network before we continue
    	while (isThreadFinished.get(0) != true) {
    	}
    	isThreadFinished.set(0, false);
    	
		// PRINTING FOR ME
		System.out.println("tempList size --> " + tempList.size());
		for (Question q : tempList) {
			System.out.println("-- Question --> " + q.getName());
		}
		
    	if (tempList.size() == 0) {
    		return;
    	}
    	
		ArrayList<Question> list = questionList.getQuestionList();
		list.clear();
		
		for (Question q : tempList) {
			if (ListHandler.isInFavs(q.getStringID())) {
				q.setIsFav(true);
			}
			else {
				q.setIsFav(false);
			}
			if (ListHandler.isInReadingList(q.getStringID())) {
				q.setIsInReadingList(true);
			}
			else {
				q.setIsInReadingList(false);
			}
			list.add(q);
		}
		
		// PRINTINT FOR DEBUGGING
		for (int i = 0 ; i < questionList.size(); i++) {
			System.out.println("QuestionName : " + questionList.get(i).getName());
		}
		
		System.out.println("DONE fillQuestionList");
		
		// Sort list by most recent
		questionList.sortBy("most recent");
		questionList.notifyViews();
	}


	// BELOW - Maybe move this somewhere else later
	// This is modified from https://github.com/dfserrano/AndroidElasticSearch 11-15-2014
	class AddQThread extends Thread {

		private Question question;
		
		public AddQThread(Question question) {
			this.question = question;
		}
		
		@Override
		public void run() {
			esManager.addQuestion(question);
			
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	class PopulateListThread extends Thread {
		private String search;
	
		public PopulateListThread(String s) {
			search = s;
		}

		@Override
		public void run() {
			tempList = esManager.searchQuestions(search, null);
			
			// PRINTING FOR ME
			System.out.println("tempList size --> " + tempList.size());
			for (Question q : tempList) {
				System.out.println("-- Question --> " + q.getName());
			}
			
			isThreadFinished.set(0, true);
		}

	}
	
//	class SearchThread extends Thread {
//		private String search;
//	
//		public SearchThread(String s) {
//			search = s;
//		}
//
//		@Override
//		public void run() {
//			QuestionList searchResultsList = new QuestionList();
//			
//		}
//
//	}
	
	public void addAnswer(String questionID, Answer newAnswer) {
		System.out.println("in NetworkController - addAnswer - questionID --> " + questionID);
		Thread thread = new AddAThread(questionID, newAnswer);
		thread.start();
		
		while (isThreadFinished.get(3) != true) {
		}
		isThreadFinished.set(3, false);
	}

	class AddAThread extends Thread {

		private String questionID;
		private Answer answer;
		
		public AddAThread(String questionID, Answer answer) {
			this.questionID = questionID;
			this.answer = answer;
		}
		
		@Override
		public void run() {
			esManager.addAnswer(questionID, answer);
			
			isThreadFinished.set(3, true);
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	
	public void upvoteQuestion(String questionID) {
		Thread thread = new UpvoteQuestionThread(questionID);
		thread.start();
		
	}
	
	class UpvoteQuestionThread extends Thread {
		
		private String questionID;
		
		public UpvoteQuestionThread(String questionID) {
			this.questionID = questionID;
		}

		@Override
		public void run() {
			esManager.upvoteQuestion(questionID);

			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	// UPVOTE ANSWER
	public void upvoteAnswer(String questionID, String answerID) {
		Thread thread = new UpvoteAnswerThread(questionID, answerID);
		thread.start();
		
		while (isThreadFinished.get(7) != true) {
		}
		
		isThreadFinished.set(7, false);
	}
	
	class UpvoteAnswerThread extends Thread {
		
		private String questionID;
		private String answerID;
		
		public UpvoteAnswerThread(String questionID, String answerID) {
			this.questionID = questionID;
			this.answerID = answerID;
		}

		@Override
		public void run() {
			esManager.upvoteAnswer(questionID, answerID);

			isThreadFinished.set(7, true);
			
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	// ADD QUESTION REPLY
	public void addQReply(String questionID, Reply newReply) {
		Thread thread = new AddQReplyThread(questionID, newReply);
		thread.start();
		
		while (isThreadFinished.get(4) != true) {
		}
		
		isThreadFinished.set(4, false);
	}
	
	class AddQReplyThread extends Thread {
		
		private String questionID;
		private Reply reply;
		
		public AddQReplyThread(String questionID, Reply reply) {
			this.questionID = questionID;
			this.reply = reply;
		}

		@Override
		public void run() {
			esManager.addQReply(questionID, reply);

			isThreadFinished.set(4, true);
			
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	// ADD ANSWER REPLY
	public void addAReply(String questionID, String answerID, Reply reply) {
		Thread thread = new AddAReplyThread(questionID, answerID, reply);
		thread.start();
		
		while (isThreadFinished.get(5) != true) {
		}
		
		isThreadFinished.set(5,  false);
	}
    
	class AddAReplyThread extends Thread {
		
		private String questionID;
		private String answerID;
		private Reply reply;
		
		public AddAReplyThread(String questionID, String answerID, Reply reply) {
			this.questionID = questionID;
			this.answerID = answerID;
			this.reply = reply;
		}

		@Override
		public void run() {
			esManager.addAReply(questionID, answerID, reply);

			isThreadFinished.set(5, true);
			
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}

	public Vector<Boolean> getThreadStatus() {
		return isThreadFinished;
	}
}
