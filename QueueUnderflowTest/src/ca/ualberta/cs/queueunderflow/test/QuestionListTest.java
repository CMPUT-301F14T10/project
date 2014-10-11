package ca.ualberta.cs.queueunderflow.test;



import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import junit.framework.TestCase;
import ca.ualberta.cs.queueunderflow.Answer;
import ca.ualberta.cs.queueunderflow.AnswerList;
import ca.ualberta.cs.queueunderflow.Favorites;
import ca.ualberta.cs.queueunderflow.MasterAnswerList;
import ca.ualberta.cs.queueunderflow.Picture;
import ca.ualberta.cs.queueunderflow.QuestionList;
import ca.ualberta.cs.queueunderflow.Question;
import ca.ualberta.cs.queueunderflow.Reply;

public class QuestionListTest extends TestCase {
	
	//Use case 1
	public void testBrowseQuestions() {
		QuestionList questionList= new QuestionList();
		String questionName= "How does this work?";
		String author= "Me";
		Question questionTest= new Question(questionName,author);
		questionList.add(questionTest);
		
		assertTrue("Question List isn't empty", questionList.getQuestionList().contains(questionTest));
	}
	
	
	
	//Use case 4
	public void testAddQuestion() {
		QuestionList questionList= new QuestionList();
		String questionName= "How does this work?";

		String author= "Me";
		Question questionTest= new Question(questionName,author);
		questionList.add(questionTest);
		
		assertTrue("Question List isn't empty", questionList.size()==1);
	}
	
	
	//Use case 5
	public void testAnswerQuestion() {
		QuestionList questionList= new QuestionList();
		String questionName= "A question?";
		String author= "Me";
		Question questionTest= new Question(questionName,author);
		questionList.add(questionTest);
		
		//Testing the questionIndex method of questionlist class
		int question_index= questionList.questionIndex(questionTest);
		assertTrue("question index is 0", question_index==0);
		
		//Adding an answer
		Question sameQuestion= questionList.get(question_index);
		String answerName= "An answer";
		String authorName= "You";
		Answer testAnswer= new Answer(answerName,authorName);
		
		sameQuestion.addAnswer(testAnswer);
		
		//Testing to see if the answer_list of a question is not empty
		questionList.set(question_index, sameQuestion);
		assertTrue("Answer List isn't empty",questionList.get(question_index).getAnswerListSize()==1 ) ;
		
	}
	
	//use case 14
	public void testAnswersQuestionRec() {
		QuestionList questionList= new QuestionList();
		String questionName= "A question?";
		String author= "A author";
		//Add question_replies
		Question questionTest= new Question(questionName,author);
		questionList.add(questionTest);
		
		//Testing the questionIndex method of questionlist class
		int question_index= questionList.questionIndex(questionTest);
		assertTrue("question index is 0", question_index==0);
		
		//Adding an answer
		Question sameQuestion= questionList.get(question_index);
		String author2="Author 2";
		String answerName= "An answer";
		String answerName2= "Answer 2";
		String answerName3= "Answer 3";
		
		Answer testAnswer= new Answer(answerName,author2);
		Answer testAnswer2= new Answer(answerName2,author2);
		Answer testAnswer3= new Answer(answerName3,author2);
		
		sameQuestion.addAnswer(testAnswer);
		sameQuestion.addAnswer(testAnswer2);
		sameQuestion.addAnswer(testAnswer3);
		
		
		questionList.set(question_index, sameQuestion);
		
		//Testing to see how many answers a question has received
		int question_index2= questionList.questionIndex(sameQuestion);
		Question sameQuestion2= questionList.get(question_index2);
		assertTrue("The answers this question received=3", sameQuestion2.getAnswerListSize()==3);
	}
	
	//use case 3
	public void testRepliesReceived() {
		QuestionList questionList= new QuestionList();
		String questionName= "A question?";

		String author="A author";
		//Adding replies to question
		String reply_author= "I dunno";
		Reply q_reply= new Reply("Whats going on",reply_author);
		//question_replies.add(q_reply);
		
		Question questionTest= new Question(questionName,author);
		questionTest.addQuestionReply(q_reply);
		questionList.add(questionTest);
		
		//Testing the questionIndex method of questionlist class
		int question_index= questionList.questionIndex(questionTest);
		assertTrue("question index is 0", question_index==0);
		
		//Adding an answer
		Question sameQuestion= questionList.get(question_index);
		String answerName= "An answer";
		
		//Add replies to answer
		//answer_replies.add("Answer reply 1");
		
		String answer_author= "Me";
		String answer_r_author= "7-11";
		Answer testAnswer= new Answer(answerName,answer_author);
		Reply a_reply= new Reply("Go to stackoverflow",answer_r_author);
		testAnswer.addReply(a_reply);
		
		sameQuestion.addAnswer(testAnswer);
		
		
		questionList.set(question_index, sameQuestion);
		
		//Testing to see how many answers a question has received
		int question_index2= questionList.questionIndex(sameQuestion);
		Question sameQuestion2= questionList.get(question_index2);
		assertTrue("The answers this question received=1", sameQuestion2.getAnswerListSize()==1);
		
		//See replies to question and answer
		
		assertTrue("Question replies = 1", sameQuestion2.getSizeReplies()==1);
		assertTrue(sameQuestion2.getReplies().contains(q_reply));
		
		AnswerList answers= sameQuestion2.getAnswerList();
		Answer testAnswer2= answers.getAnswer(0);
		assertTrue("Answer replies=1", testAnswer2.getSizeReplies()==1 );
		assertTrue(testAnswer2.getReplies().contains(a_reply));
	}
	
	//Use case 23
	
	public void testSetUserName() {
		QuestionList questionList= new QuestionList();
		String questionName= "How does this work?";

		String author= "Me";
		Question questionTest= new Question(questionName,author);
		questionTest.setAuthor("Paul");
		//Testing set author name for question
		
		questionList.add(questionTest);
		String name= questionTest.getAuthor();
		assertTrue("Author name is Paul", questionTest.getAuthor().equals(name));
		

		int question_index= questionList.questionIndex(questionTest);
		assertTrue("question index is 0", question_index==0);
		
		//Adding an answer
		//Question sameQuestion= questionList.get(question_index);
		String answerName= "An answer";
		
		//Add replies to answer
		//answer_replies.add("Answer reply 1");
		
		String answer_author= "Me";
		String answer_r_author= "7-11";
		Answer testAnswer= new Answer(answerName,answer_author);
		
		String name2="Jack";
		testAnswer.setAuthor(name2);
		
		assertTrue("Answer author is jack",testAnswer.getAuthor().equals(name2));
		
		
		Reply a_reply= new Reply("Go to stackoverflow",answer_r_author);
		
		a_reply.setAuthor("Will");
		assertTrue ("reply author is Will", a_reply.getAuthor().equals("Will"));
		testAnswer.addReply(a_reply);
	}
	
	//Use case 6
	public void testReplyClarify() {
		QuestionList questionList= new QuestionList();
		String questionName= "A question?";

		String author= "A author";
		
		//Add question_replies
		Question questionTest= new Question(questionName,author);
		Reply a_reply=new Reply("What are you talking about", "Walter");
		questionTest.addQuestionReply(a_reply);
		
		assertTrue("Reply list isnt empty", questionTest.getSizeReplies()==1);
		
		questionList.add(questionTest);
		
		//Adding an answer

		String author2="Author 2";
		String answerName= "An answer";

		
		Answer testAnswer= new Answer(answerName,author2);
		Reply answer_reply= new Reply("What are you asking", "Jason");
		testAnswer.addReply(answer_reply);
		assertTrue("Reply list isn't empty", testAnswer.getSizeReplies()==1);
	
	}
	
	//Use case 12 and 13
	
	public void testUpvotes() {
		String questionName= "A question?";
		String author= "A author";
		
		//Add question_replies
		Question questionTest= new Question(questionName, author);
		questionTest.upvoteQuestion();
		assertTrue("Question upvoted by 1", questionTest.getUpvotes()==1);
		
		//Adding an answer

		String author2="Author 2";
		String answerName= "An answer";

		
		Answer testAnswer= new Answer(answerName,author2);
		testAnswer.upvoteAnswer();
		assertTrue("Answer upvoted by 1", testAnswer.getUpvotes()==1);
	}
	
	//Use case 2
	public void testViewQA() {
		String questionName= "A question?";
		String author= "Me";
		Question questionTest= new Question(questionName,author);
		

		
		//Adding an answer
		String answerName= "An answer";
		String authorName= "You";
		Answer testAnswer= new Answer(answerName,authorName);
		
		questionTest.addAnswer(testAnswer);
		
		//Testing to see if the answer_list of a question is not empty
		
		AnswerList question_answer= questionTest.getAnswerList();
		assertTrue("question_answer isn't empty", question_answer.size()==1);
	}
	
	//Use case 15
	
	public void testSearchQuestion() {
		QuestionList questionList= new QuestionList();
		MasterAnswerList master= new MasterAnswerList();
		String questionName= "A question?";
		String author= "Me";
		Question questionTest= new Question(questionName,author);
		questionList.add(questionTest);
		
		//Testing the questionIndex method of questionlist class
		int question_index= questionList.questionIndex(questionTest);
		assertTrue("question index is 0", question_index==0);
		
		//Adding an answer
		Question sameQuestion= questionList.get(question_index);
		String answerName= "An answer";
		String authorName= "You";
		Answer testAnswer= new Answer(answerName,authorName);
		
		sameQuestion.addAnswer(testAnswer);
		master.addAnswer(testAnswer);
		//Testing to see if the answer_list of a question is not empty
		questionList.set(question_index, sameQuestion);
		assertTrue("Answer List isn't empty",questionList.get(question_index).getAnswerListSize()==1 ) ;
		
		String search_question= "A question?";
		assertTrue("Search doesn't return null",questionList.search(search_question)!=null);
		
		String search_answer="An answer";
		assertTrue("Answer search doesn't return null",master.search(search_answer)!=null);
	}
	
	//Use case 18
	public void testSaveFavorites() {
		String questionName= "How does this work?";
		String author= "Me";
		Question questionTest= new Question(questionName,author);
		
		Favorites favorite_questions= new Favorites();
		favorite_questions.add(questionTest);
		
		assertTrue("Favorites size is equal 1", favorite_questions.size()==1);
		
	}
	
	//use case 9
	public void testhasPictures() {
		String questionName= "How does this work?";
		String author= "Me";
		Question questionTest= new Question(questionName,author);
		//The question has a picture
		Question questionTest2= new Question(questionName,author);
		Picture picture= new Picture(10);
		questionTest2.setPicture(picture);
		QuestionList questionList= new QuestionList();
		questionList.add(questionTest);
		questionList.add(questionTest2);
		
		ArrayList<Question> questionlist2= questionList.sortByPictures();
		assertTrue("questionlist2 has pictures",questionlist2.size()==1 );	
		
	}
	
	//Use case 7
	
	public void testInsertPictures() {
		String questionName= "How does this work?";
		String author= "Me";
		Question questionTest= new Question(questionName,author);
		Picture pic= new Picture(10);
		questionTest.setPicture(pic);
		
		assertTrue("Question has a picture", questionTest.getPicture()!=null);
		
		String answerName= "An answer";
		String authorName= "You";
		Answer testAnswer= new Answer(answerName,authorName);
		
		testAnswer.setPicture(pic);
		assertTrue("Answer has a picture", testAnswer.getPicture()!=null);
		
	}
	
	public void testPictureSize() {
		Picture pic= new Picture(10);
		
		assertTrue("Picture isn't too big", pic.getSize()<64);
	}
	

	
	// Use Case 10
	public void testSortByDate() {
		String questionName= "Oldest";
		String author= "long time ago";
		Question question1= new Question(questionName,author);
		
		for (int i = 0; i < 25000; i++) {
			// this is just so the timestamp won't be the same for each question
			// this will make them milliseconds off
		}
		
    	
		Calendar cal = Calendar.getInstance();
		cal.set(2013, Calendar.JANUARY, 9);
		//cal.setTime(question1.getDate());
		//cal.add(Calendar.DATE, -2);
		//cal.set(2000, 11, 9);
		Date question1date = cal.getTime();
		question1.setDate(question1date);
		
		String questionName2= "in between";
		String author2= "middle";
		Question question2= new Question(questionName2,author2);
		
		for (int i = 0; i < 25000; i++) {
			//this is just so the timestamp won't be the same for each question
			// this will make them milliseconds off
		}
		
		Calendar cal2 = Calendar.getInstance();  
		//cal2.setTime(question2.getDate());
		//cal2.set(2005, 11, 11);
		cal2.set(2014, Calendar.JANUARY, 11);

		//cal2.add(Calendar.DATE, -1);
		Date question2date = cal2.getTime();
		question2.setDate(question2date);
		
		String questionName3= "Freshest";
		String author3= "Now";
		Question question3= new Question(questionName3,author3);
		
		/*
		Calendar cal3 = Calendar.getInstance();
		cal3.setTime(question1.getDate());
		cal3.add(Calendar.HOUR, 1);
		Date question3date = cal.getTime();
		question3.setDate(question3date);
		*/
		
		QuestionList questionList = new QuestionList();
		
		/*
		questionList.add(question2);
		questionList.add(question1);
		questionList.add(question3);
		*/
		
		questionList.add(question1);
		questionList.add(question2);
		questionList.add(question3);
		
		ArrayList<Question> mostRecentList = new ArrayList<Question>();
		mostRecentList.add(question3);
		mostRecentList.add(question2);
		mostRecentList.add(question1);
		
		ArrayList<Question> leastRecentList = new ArrayList<Question>();
		leastRecentList.add(question1);
		leastRecentList.add(question2);
		leastRecentList.add(question3);
		
		//Most recent now works
		assertTrue("Question2 date more recent compared to question1", question2date.compareTo(question1date)>0);
		assertTrue("Question2 date is more recent than question1", question2.getDate().compareTo(question1.getDate())>0);
		
		ArrayList<Question> questionList3 = questionList.sortBy("most recent");
		assertTrue("Questionlist3 1st slot is question3", questionList3.get(0).equals(question3));
		assertTrue("Questionlist3 2nd slot is question2", questionList3.get(1).equals(question2));
		assertTrue("Questionlist3 3rd slot ins question1", questionList3.get(2).equals(question1));
		assertEquals("questionList3 is sorted by date, most recent to least recent", questionList3, mostRecentList);
		
		ArrayList<Question> questionList4 = questionList.sortBy("least recent");
		assertTrue("Questionlist4 1st slot is question1", questionList4.get(0).equals(question1));
		assertTrue("Questionlist4 2nd slot is question2", questionList4.get(1).equals(question2));
		assertTrue("Questionlist4 3rd slot ins question3", questionList4.get(2).equals(question3));
		assertEquals("questionList4 is sorted by date, least recent to most recent", questionList4, leastRecentList);
	}
	
	public void testSortByUpvotes() {
		Question question1 = new Question("one upvote", "author1");
		Question question2 = new Question("ten upvotes", "author2");
		Question question3 = new Question("five upvotes",  "author3");
	
		question1.setUpvotes(1);
		question2.setUpvotes(10);
		question3.setUpvotes(5);
		
		QuestionList questionList = new QuestionList();
		questionList.add(question1);
		questionList.add(question2);
		questionList.add(question3);
		
		ArrayList<Question> mostUpvotesList = new ArrayList<Question>();
		mostUpvotesList.add(question2);
		mostUpvotesList.add(question3);
		mostUpvotesList.add(question1);
		
		ArrayList<Question> leastUpvotesList = new ArrayList<Question>();
		leastUpvotesList.add(question1);
		leastUpvotesList.add(question3);
		leastUpvotesList.add(question2);
		
		ArrayList<Question> questionList1 = questionList.sortBy("most upvotes");
		assertEquals("questionList1 is sorted by most upvotes to least", mostUpvotesList, questionList1);
		
		ArrayList<Question> questionList2 = questionList.sortBy("least upvotes");
		assertEquals("questionList2 is sorted by least upvotes to most", leastUpvotesList, questionList2);
	}


}
