package ca.ualberta.cs.queueunderflow.test.controllers;

import ca.ualberta.cs.queueunderflow.ListHandler;
import ca.ualberta.cs.queueunderflow.models.Question;
import junit.framework.TestCase;

//test for singleton implementation of list controller
public class ListHandlerTest extends TestCase {
	

	public void testListHandler() {
		//master question list test
		ListHandler.getMasterQList().clear();
		assertTrue("contorller initilized the list", ListHandler.getMasterQList().size() == 0);
		Question question1 = new Question("1", "a");
		ListHandler.getMasterQList().add(question1);
		assertTrue("contorller got the list", ListHandler.getMasterQList().size() == 1);
		Question question2 = new Question("2", "b");
		ListHandler.getMasterQList().add(question2);
		ListHandler.getMasterQList().remove(question2);
		assertTrue("contorller got the same list1", ListHandler.getMasterQList().size() == 1);
		//favourites list test
		ListHandler.getFavsList().clear();
		assertTrue("contorller initilized the list", ListHandler.getFavsList().size() == 0);
		ListHandler.getFavsList().add(question1);
		assertTrue("contorller got the list", ListHandler.getFavsList().size() == 1);
		ListHandler.getFavsList().add(question2);
		ListHandler.getFavsList().remove(question2);
		assertTrue("contorller got the same list2", ListHandler.getFavsList().size() == 1);
		//Reading list test
		ListHandler.getReadingList().clear();
		assertTrue("contorller initilized the list", ListHandler.getReadingList().size() == 0);
		ListHandler.getReadingList().add(question1);
		assertTrue("contorller got the list", ListHandler.getReadingList().size() == 1);
		ListHandler.getReadingList().add(question2);
		ListHandler.getReadingList().remove(question2);
		assertTrue("contorller got the same list3", ListHandler.getReadingList().size() == 1);
		//my question list test
		ListHandler.getMyQsList().clear();
		assertTrue("contorller initilized the list", ListHandler.getMyQsList().size() == 0);
		ListHandler.getMyQsList().add(question1);
		assertTrue("contorller got the list", ListHandler.getMyQsList().size() == 1);
		ListHandler.getMyQsList().add(question2);
		ListHandler.getMyQsList().remove(question2);
		assertTrue("contorller got the same list3", ListHandler.getMyQsList().size() == 1);
		//User test
		ListHandler.getUser().setUserName("person");
		assertTrue("contorller got the same user", ListHandler.getUser().getUserName()=="person");
		ListHandler.getUser();
		assertTrue("contorller got the same user1", ListHandler.getUser().getUserName()!="Anonymous");
	}	
}
