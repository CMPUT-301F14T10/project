package ca.ualberta.cs.queueunderflow.test;

import ca.ualberta.cs.queueunderflow.Picture;
import ca.ualberta.cs.queueunderflow.Question;
import junit.framework.TestCase;

public class UseCase9 extends TestCase {

	//Use CASE 9 (incorporates user story 8)
	public void testPhotoSizeLimit() {
		String questionName= "How does this work?";
		String author= "Me";
		Question questionTest= new Question(questionName,author);
		Picture pic= new Picture(65);
		assertFalse("image is too large, select another photo", pic.getSize()<64);

		//Exception: Photo is smaller than 64kb, no complaints
		
		Picture picture2= new Picture(61);
		assertTrue("No complaints about picture size", picture2.getSize()<=64);
		questionTest.setPicture(picture2);
	}
	
}
