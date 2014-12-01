package ca.ualberta.cs.queueunderflow.test.models;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import ca.ualberta.cs.queueunderflow.models.Picture;
import ca.ualberta.cs.queueunderflow.models.Question;
import ca.ualberta.cs.queueunderflow.models.QuestionList;
import junit.framework.TestCase;

public class QuestionListModelTest extends TestCase {

	QuestionList questionList= new QuestionList();
	
	public void testAddQuestion_and_Size_Methods() {
		Question question1= new Question("A question", "Paul");
		questionList.add(question1);
		
		assertTrue("A question was added to questionlist and its size is one", questionList.size()==1);
	}
	
	public void testGetQuestionListMethod() {
		Question question1= new Question("A question", "Paul");
		questionList.add(question1);
		
		ArrayList<Question> questions= questionList.getQuestionList();
		Question expected= questions.get(0);
		
		assertTrue("Questionlist retrieved isn't empty", questions.size()==1);
		assertTrue("Question retrieved from arraylist is same", question1.equals(expected));
	}
	
	public void testQuestionIndexMethod() {
		Question question1= new Question("A question", "Paul");
		Question question2= new Question("Not the wanted question", "Paul");

		questionList.add(question1);
		questionList.add(question2);
		
		int index= questionList.questionIndex(question1);
		assertTrue("Question index is 0", index==0);
	}
	
	public void testGetMethod() {
		Question question1= new Question("A question", "Paul");
		questionList.add(question1);

		Question expected= questionList.get(0);
		
		assertTrue("Got the added question by getter method", question1.equals(expected));
	}
	
	public void testSearchMethod() {
		Question question1= new Question("A question", "Paul");
		questionList.add(question1);
		
		Question result= questionList.search("A question");
		assertTrue("Result isn't null", result!=null);
	}
	
	
	public void testSetMethod() {
		Question question1= new Question("A question", "Paul");
		Question question2= new Question("Not the wanted question", "Paul");

		questionList.add(question1);
		questionList.set(0, question2);
		
		Question result= questionList.get(0);
		assertTrue("Set the question in questionList to another question",result.equals(question2)==true);
	}
	
	public void testSortnByMethod() {
		String questionName= "Oldest question";
		String author= "long time ago";
		Question question1= new Question(questionName,author);
		Calendar cal = Calendar.getInstance();
		cal.setTime(question1.getDate());
		//Make the date 5 seconds behind current date
		cal.add(Calendar.SECOND,-5);
		Date question1date = cal.getTime();
		question1.setDate(question1date);
		
		String questionName2= "Middle date question";
		String author2= "middle";
		Question question2= new Question(questionName2,author2);
		Calendar cal2 = Calendar.getInstance();  
		cal2.setTime(question2.getDate());
		//Make the date 3 seconds behind current date
		cal2.add(Calendar.SECOND,-3);
		Date question2date = cal2.getTime();
		question2.setDate(question2date);
		
		String questionName3= "Freshest question";
		String author3= "Now";
		Question question3= new Question(questionName3,author3);
		
		//Set the upvotes each question has
		question1.setUpvotes(1);
		question2.setUpvotes(10);
		question3.setUpvotes(5);
		
		QuestionList questionList = new QuestionList();
		questionList.add(question2);
		questionList.add(question1);
		questionList.add(question3);
		
		// Note the QuestionList add method adds it to the front of the list thus we add them in reverse order here
		QuestionList mostRecentList = new QuestionList();
		mostRecentList.add(question1);
		mostRecentList.add(question2);
		mostRecentList.add(question3);
		
		QuestionList leastRecentList = new QuestionList();
		leastRecentList.add(question3);
		leastRecentList.add(question2);
		leastRecentList.add(question1);
		
		QuestionList mostUpvotesList = new QuestionList();
		mostUpvotesList.add(question1);
		mostUpvotesList.add(question3);
		mostUpvotesList.add(question2);
		
		
		//Checking if the sorting methods work
		questionList.sortBy("most recent");
		assertTrue("Questionlist 1st slot is question3", questionList.get(0).equals(question3));
		assertTrue("Questionlist 2nd slot is question2", questionList.get(1).equals(question2));
		assertTrue("Questionlist 3rd slot ins question1", questionList.get(2).equals(question1));
		assertEquals("questionList is sorted by date, most recent to least recent", questionList.getQuestionList(), mostRecentList.getQuestionList());
		
		questionList.sortBy("least recent");
		assertTrue("Questionlist 1st slot is question1", questionList.get(0).equals(question1));
		assertTrue("Questionlist 2nd slot is question2", questionList.get(1).equals(question2));
		assertTrue("Questionlist 3rd slot ins question3", questionList.get(2).equals(question3));
		assertEquals("questionList is sorted by date, least recent to most recent", questionList.getQuestionList(), leastRecentList.getQuestionList());
		
		questionList.sortBy("most upvotes");
		assertEquals("questionList1 is sorted by most upvotes to least", mostUpvotesList.getQuestionList(), questionList.getQuestionList());
	}
	
	public void testRemoveMethod() {
		Question question1= new Question("A question", "Paul");
		questionList.add(question1);
		
		questionList.remove(question1);
		assertTrue("question list size is 0 because a question was removed", questionList.size()==0);
	}
	
	public void testSetQuestionListMethod() {
		Question question1= new Question("A question", "Paul");
		Question question2= new Question("A question", "Paul");
		Question question3= new Question("A question", "Paul");
		
		ArrayList <Question> questions= new ArrayList<Question>();
		questions.add(question1);
		questions.add(question2);
		questions.add(question3);
		
		questionList.setQuestionList(questions);
		
		assertTrue("question list size changed from 0 to 3 because questionlist was set by setter", questionList.size()==3);

	}
	
	//Note: The online methods tested now are subject to change since this is a mock test only
	//Note: The sort by picture method tested is also a mock test and is subject to change
		public void testGetOnline_and_SetOnline_methods() {
			assertTrue("Initially not online", questionList.getOnline()==false);
			questionList.setOnline();
			assertTrue("Now online because of setter method", questionList.getOnline());
		}
		
		//This is a mock method being tested
		public void testPushOnlineMethod() {
			assertTrue("Not pushing online since no connectivity", questionList.pushOnline()==false);
		}
	
	public void testSortByPicturesMethod() {
		Question question1= new Question("A question", "Paul");
		Picture pic= new Picture(10);
		question1.setPicture(pic);
		Question question2= new Question("A question", "Paul");
		questionList.add(question1);
		questionList.add(question2);
		
		ArrayList <Question> sortByPics= questionList.sortByPictures();
		assertTrue("Size of sortByPics array is one because only 1 question has a picture", sortByPics.size()==1);
		
	}
}
