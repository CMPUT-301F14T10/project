package ca.ualberta.cs.queueunderflow.test.models;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import ca.ualberta.cs.queueunderflow.models.Answer;
import ca.ualberta.cs.queueunderflow.models.AnswerList;
import junit.framework.TestCase;

public class AnswerListModelTest extends TestCase{

	AnswerList answerList= new AnswerList();
	
	public void testAddAnswer_and_Size_Methods() {
		Answer answer1= new Answer("Answer", "Paul");
		answerList.add(answer1);
		assertTrue("Answerlist isn't empty",answerList.size()==1);
	}
	
	public void testGetAnswerMethod() {
		Answer answer1= new Answer("Answer", "Paul");
		answerList.add(answer1);
		Answer expected= answerList.getAnswer(0);
		assertTrue("Answer added is the same one retrieved from answerlist", answer1.equals(expected));
	}
	
	public void testGetAnswerListMethod() {
		ArrayList <Answer> expected= answerList.getAnswerList();
		assertTrue("The answerList is empty because nothing was added in this test", expected.size()==0);
	}
	
	public void testSetAnswerListMethod() {
		Answer answer1= new Answer("Answer", "Paul");
		Answer answer2= new Answer("Answer", "Paul");
		Answer answer3= new Answer("Answer", "Paul");

		ArrayList <Answer> answers= new ArrayList<Answer>();
		answers.add(answer1);
		answers.add(answer2);
		answers.add(answer3);
		
		answerList.setAnswerList(answers);
		
		assertTrue("The answerlist size is 3 because answerlist was set by setter method", answerList.size()==3);
	}
	
	public void testEqualsMethod() {
		Answer answer1= new Answer("Answer", "Paul");
		Answer answer2= new Answer("Answer", "Paul");
		Answer answer3= new Answer("Answer", "Paul");
		
		answerList.add(answer1);
		answerList.add(answer2);
		answerList.add(answer3);
		
		AnswerList answerList2= new AnswerList();
		answerList2.add(answer1);
		answerList2.add(answer2);
		answerList2.add(answer3);
		
		assertTrue("Both answerlists are equal", answerList.equals(answerList2));
	}
	
	public void testSortByMethod() {
		String answerName= "Oldest answer";
		String author= "long time ago";
		Answer answer1= new Answer(answerName,author);
		Calendar cal = Calendar.getInstance();
		cal.setTime(answer1.getDate());
		//Make the date 5 seconds behind current date
		cal.add(Calendar.SECOND,-5);
		Date answer1date = cal.getTime();
		answer1.setDate(answer1date);
		
		String answerName2= "Middle date answer";
		String author2= "middle";
		Answer answer2= new Answer(answerName2,author2);
		Calendar cal2 = Calendar.getInstance();  
		cal2.setTime(answer2.getDate());
		//Make the date 3 seconds behind current date
		cal2.add(Calendar.SECOND,-3);
		Date answer2date = cal2.getTime();
		answer2.setDate(answer2date);
		
		String answerName3= "Freshest answer";
		String author3= "Now";
		Answer answer3= new Answer(answerName3,author3);
		
		//Set the upvotes each answer has
		answer1.setUpvotes(1);
		answer2.setUpvotes(10);
		answer3.setUpvotes(5);
		
		AnswerList answerList = new AnswerList();
		answerList.add(answer2);
		answerList.add(answer1);
		answerList.add(answer3);
		
		// Note the AnswerList add method adds it to the front of the list thus we add them in reverse order here
		AnswerList mostRecentList = new AnswerList();
		mostRecentList.add(answer1);
		mostRecentList.add(answer2);
		mostRecentList.add(answer3);
		
		AnswerList leastRecentList = new AnswerList();
		leastRecentList.add(answer3);
		leastRecentList.add(answer2);
		leastRecentList.add(answer1);
		
		AnswerList mostUpvotesList = new AnswerList();
		mostUpvotesList.add(answer1);
		mostUpvotesList.add(answer3);
		mostUpvotesList.add(answer2);
		
		
		//Checking if the sorting methods work
		answerList.sortBy("most recent");
		//assertTrue("Answerlist 1st slot is answer3", answerList.getAnswer(0).equals(answer3));
		//assertTrue("Answerlist 2nd slot is answer2", answerList.getAnswer(1).equals(answer2));
		//assertTrue("Answerlist 3rd slot ins answer1", answerList.getAnswer(2).equals(answer1));
		assertEquals("AnswerList is sorted by date, most recent to least recent", answerList.getAnswerList(), mostRecentList.getAnswerList());

		answerList.sortBy("least recent");
		//assertTrue("Answerlist 1st slot is question1", answerList.getAnswer(0).equals(answer1));
		//assertTrue("Answerlist 2nd slot is question2", answerList.getAnswer(1).equals(answer2));
		//assertTrue("Answerlist 3rd slot ins question3", answerList.getAnswer(2).equals(answer3));
		assertEquals("answerList is sorted by date, least recent to most recent", answerList.getAnswerList(), leastRecentList.getAnswerList());
		
		answerList.sortBy("most upvotes");
		assertEquals("answerList1 is sorted by most upvotes to least", mostUpvotesList.getAnswerList(), answerList.getAnswerList());
	}
}
