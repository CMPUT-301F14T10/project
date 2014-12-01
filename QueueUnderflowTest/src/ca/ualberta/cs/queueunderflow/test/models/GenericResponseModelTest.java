package ca.ualberta.cs.queueunderflow.test.models;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import ca.ualberta.cs.queueunderflow.models.GenericResponse;
import ca.ualberta.cs.queueunderflow.models.Picture;
import ca.ualberta.cs.queueunderflow.models.Reply;
import ca.ualberta.cs.queueunderflow.models.ReplyList;
import junit.framework.TestCase;



// This test class tests GenericResponseModel and its methods

public class GenericResponseModelTest extends TestCase{
	
	GenericResponse testResponse= new GenericResponse("Test response","Paul");
	GenericResponse testResponse2=new GenericResponse("Test response","Paul");
	
	public void testEqualsMethod() {
		assertTrue("Both responses are equal", testResponse.equals(testResponse2));
	}
	
	public void testGetName_And_SetName_Methods() {
		testResponse.setName("New name");
		String expected= testResponse.getName();
		assertTrue("Both getter and setter method works", expected.equals("New name"));
	}
	public void testAddReplyMethod() {
		Reply reply= new Reply("This is a reply","Paul");
		testResponse.addReply(reply);
		assertTrue("There is a reply",testResponse.getSizeReplies()==1);
	}
	public void testGetReplyMethod() {
		Reply reply= new Reply("This is a reply","Paul");
		testResponse.addReply(reply);
		Reply expected= testResponse.getReplyAt(0);
		assertTrue("Reply's are equal",reply.equals(expected));
	}
	
	public void testGetAuthor_and_SetAuthor_Methods() {
		//Testing to see if both getter and setter methods work
		testResponse.setAuthor("Not Paul");
		assertEquals("Not Paul", testResponse.getAuthor());
	}
	
	public void testGetReplyListMethod() {
		//ArrayList <Reply> noReplies= testResponse.getReplies();
		ReplyList noReplies= testResponse.getReplies();
		assertTrue("No replies yet since none were added", noReplies.size()==0);
	}
	public void testSetReplyListMethod() {
		Reply reply1=new Reply("1", "Paul");
		Reply reply2=new Reply("2","Paul");
		Reply reply3= new Reply("3", "Paul");
		
		ReplyList three_replies= new ReplyList();
		three_replies.add(reply1);
		three_replies.add(reply2);
		three_replies.add(reply3);

		testResponse.setReplyList(three_replies);
		ReplyList expected= testResponse.getReplies();
		assertTrue("The reply list of response was set by the setter method", expected.size()==3);
		
	}
	
	public void testGetSizeRepliesMethod() {
		Reply reply= new Reply("This is a reply","Paul");
		testResponse.addReply(reply);
		Reply reply2= new Reply("This is a reply","Paul");
		testResponse.addReply(reply2);
		assertTrue("Two replies in generic response",testResponse.getSizeReplies()==2);
	}
	
	public void testUpvoteResponse_and_GetUpvotes_Methods() {
		testResponse.upvoteResponse();
		assertTrue("Upvote count is 1", testResponse.getUpvotes()==1);
	}
	
	public void testSetUpvotesMethod() {
		testResponse.setUpvotes(10);
		assertTrue("Upvotes is 10", testResponse.getUpvotes()==10);
	}
	
	
	//NOTE: The picture tests may end up failing once pictures are correctly implemented (not a bug)
	
	public void testHasPictureMethod() {
		//Should have no pictures yet since pictures are not implemented yet
		boolean result=testResponse.hasPicture();
		assertTrue("No pictures yet", result==false);
	}
	
	public void testSetPictureMethod() {
		Picture picture= new Picture (10);
		testResponse.setPicture(picture);
		assertTrue("There is a picture now", testResponse.hasPicture());
	}
	
	public void testGetPictureMethod() {
		Picture picture= new Picture (10);
		testResponse.setPicture(picture);
		
		Picture expected= testResponse.getPicture();
		assertTrue("The picture added and retrieved are the same", picture.equals(expected));
	}
	
	public void testGetDate_and_SetDate_Methods() {
		Date new_date= null;
		new_date= testResponse.getDate();
		assertTrue("The get date method works", new_date!=null);
		
;
		Calendar cal = Calendar.getInstance();  
		cal.setTime(testResponse.getDate());
		//Make the date 3 months behind current date
		cal.add(Calendar.MONTH,-3);
		Date old_date = cal.getTime();
		testResponse.setDate(old_date);
		
		assertTrue("The old date is older than the new one", testResponse.getDate().compareTo(new_date)==-1);
	}
	
	public void testGetIsFavMethod() {
		boolean expected= testResponse.getIsFav();
		//Expected should be false since it isn't favorited initially
		assertTrue("Expected is false",expected==false);
	}
	
	public void testSetIsFavMethod() {
		testResponse.setIsFav(true);
		assertTrue("Is fav is true because it was set by the setter", testResponse.getIsFav());
	}
	
	public void testGetIsInReadingListMethod() {
		assertTrue("The response isn't in reading list initially",testResponse.getIsInReadingList()==false);
	}
	public void testSetIsInReadingListMethod() {
		testResponse.setIsInReadingList(true);
		assertTrue("The response is in reading list because it was set by setter method", testResponse.getIsInReadingList());
	}
	
	
	
}
