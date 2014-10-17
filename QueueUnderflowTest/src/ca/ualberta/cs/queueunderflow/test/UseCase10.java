package ca.ualberta.cs.queueunderflow.test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import ca.ualberta.cs.queueunderflow.Question;
import ca.ualberta.cs.queueunderflow.QuestionList;
import junit.framework.TestCase;

public class UseCase10 extends TestCase {
<<<<<<< HEAD
=======
	
>>>>>>> b276022bec7b228e126fe6f42c8febf3b2091006
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
		
		ArrayList<Question> mostRecentList = new ArrayList<Question>();
		mostRecentList.add(question3);
		mostRecentList.add(question2);
		mostRecentList.add(question1);
		
		ArrayList<Question> leastRecentList = new ArrayList<Question>();
		leastRecentList.add(question1);
		leastRecentList.add(question2);
		leastRecentList.add(question3);
		
		
		//Checking if the sorting methods work
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
		
		ArrayList<Question> mostUpvotesList = new ArrayList<Question>();
		mostUpvotesList.add(question2);
		mostUpvotesList.add(question3);
		mostUpvotesList.add(question1);
		
		ArrayList<Question> leastUpvotesList = new ArrayList<Question>();
		leastUpvotesList.add(question1);
		leastUpvotesList.add(question3);
		leastUpvotesList.add(question2);
		
		ArrayList<Question> questionList5 = questionList.sortBy("most upvotes");
		assertEquals("questionList1 is sorted by most upvotes to least", mostUpvotesList, questionList5);
		
		ArrayList<Question> questionList6 = questionList.sortBy("least upvotes");
		assertEquals("questionList2 is sorted by least upvotes to most", leastUpvotesList, questionList6);
	}
<<<<<<< HEAD
	
	
=======
>>>>>>> b276022bec7b228e126fe6f42c8febf3b2091006
}
