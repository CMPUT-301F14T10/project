package ca.ualberta.cs.queueunderflow.test;

import ca.ualberta.cs.queueunderflow.Question;
import ca.ualberta.cs.queueunderflow.ReadingList;
import junit.framework.TestCase;

public class UseCase15 extends TestCase {
	
	public void testReadingList() {
		String questionName= "How does this work?";
		String author= "Me";
		Question questionTest= new Question(questionName,author);
		
		ReadingList readingList= new ReadingList();
		readingList.add(questionTest);
		
		assertTrue("Reading list isn't empty", readingList.size()==1);
	}
}
