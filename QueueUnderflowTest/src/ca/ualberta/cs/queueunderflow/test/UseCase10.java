package ca.ualberta.cs.queueunderflow.test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import ca.ualberta.cs.queueunderflow.models.Question;
import ca.ualberta.cs.queueunderflow.models.QuestionList;
import junit.framework.TestCase;

public class UseCase10 extends TestCase {

	//Use CASE 10 (incorporates user stories 9 and 10)
	public void testSortQuestionBy() {
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

}
