package ca.ualberta.cs.queueunderflow.test.usecases;

import ca.ualberta.cs.queueunderflow.Picture;
import ca.ualberta.cs.queueunderflow.User;
import ca.ualberta.cs.queueunderflow.models.Answer;
import ca.ualberta.cs.queueunderflow.models.Question;
import junit.framework.TestCase;

public class UseCase8 extends TestCase {
	
	//Use CASE 8 (incorporates user story 7 and 8)
	public void testAddPhotoFromGallery() {
		User me= new User();
		me.setUserName("Me");
		
		String questionName= "How does this work?";
		Question questionTest= new Question(questionName,me.getUserName());
		Picture pic= new Picture(10);
		questionTest.setPicture(pic);
		
		assertTrue("Question has a picture", questionTest.getPicture()!=null);
		
		String answerName= "An answer";
		String authorName= "You";
		Answer testAnswer= new Answer(answerName,authorName);
		
		testAnswer.setPicture(pic);
		assertTrue("Answer has a picture", testAnswer.getPicture()!=null);
		
		//Exception: Photo is larger than 64 kb
		Picture picture2= new Picture(65);
		assertFalse("image is too large, select another photo", picture2.getSize()<64);
	}
}
