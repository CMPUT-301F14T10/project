package ca.ualberta.cs.queueunderflow;

import java.util.ArrayList;
import java.util.List;

import ca.ualberta.cs.queueunderflow.models.Answer;
import ca.ualberta.cs.queueunderflow.models.Question;
import ca.ualberta.cs.queueunderflow.models.QuestionList;
import ca.ualberta.cs.queueunderflow.models.Reply;

// Implement an interface?
// NOTE : This assumes that the MasterList in ListHandler is always completely online, thus we add to that list
// to ensure that there are no conflicts
public class NetworkController {

	private ESManager esManager;
	private List<Question> tempList;
	private Boolean populateThreadFinished = false;
	
	private Question tempQuestion;
	private Boolean getQuestionThreadFinished = false;
	
	public NetworkController() {
		esManager = new ESManager();
		tempList = new ArrayList<Question>();
	}
	
	public void addQuestion(Question newQuestion) {
		Thread thread = new AddQThread(newQuestion);
		thread.start();
		
//		ListHandler.getMasterQList().add(newQuestion);
//		ListHandler.getMyQsList().add(newQuestion);
	}

	public Question getQuestion(String questionID) {
		Thread thread = new GetQuestionThread(questionID);
		thread.start();
		
    	// Make sure populateThread is done and we retrived the results from network before we continue
    	while (getQuestionThreadFinished != true) {
    	}
    	
    	getQuestionThreadFinished = false;
    	
    	
		return tempQuestion;
	}
	
	class GetQuestionThread extends Thread {
		private String questionID;
		
		public GetQuestionThread(String questionID) {
			this.questionID = questionID;
		}

		@Override
		public void run() {
			//QuestionList masterList = ListHandler.getMasterQList();
			tempQuestion = esManager.getQuestion(questionID);
			
			// PRINTING FOR ME
			if (tempQuestion != null) {
				System.out.println("getQuestionThread : questionName ---> " + tempQuestion.getName());
			}
			getQuestionThreadFinished = true;
		}

	}
	
//	public void populateMasterList() {
//		Thread thread = new PopulateListThread("");
//		thread.start();
//		fillQuestionList(ListHandler.getMasterQList());
//	}
    
//	public void populateSearchResultsList(QuestionList resultsList, String search) {
//		Thread thread = new PopulateListThread(search);
//		thread.start();
//		fillQuestionList(resultsList);
//	}
	
	public void populateList(QuestionList list, String search) {
		Thread thread = new PopulateListThread(search);
		thread.start();
		fillQuestionList(list);
	}
    
    private void fillQuestionList(QuestionList questionList) {
    	System.out.println("INSIDE fillQuestionList");
    	
    	// Make sure populateThread is done and we retrived the results from network before we continue
    	while (populateThreadFinished != true) {
    	}
    	
    	populateThreadFinished = false;
    	
		// PRINTING FOR ME
		System.out.println("tempList size --> " + tempList.size());
		for (Question q : tempList) {
			System.out.println("-- Question --> " + q.getName());
		}
		
    	if (tempList.size() == 0) {
    		return;
    	}
    	
    	//QuestionList questionList = ListHandler.getMasterQList();
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
		
		// PRINTINT FOR GEM
		for (int i = 0 ; i < questionList.size(); i++) {
			System.out.println("QuestionName : " + questionList.get(i).getName());
		}
		
		System.out.println("DONE fillQuestionList");
		
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
			//QuestionList masterList = ListHandler.getMasterQList();
			tempList = esManager.searchQuestions(search, null);
			
			// PRINTING FOR ME
			System.out.println("tempList size --> " + tempList.size());
			for (Question q : tempList) {
				System.out.println("-- Question --> " + q.getName());
			}
			
			populateThreadFinished = true;
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

			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}

}
