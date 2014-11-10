package ca.ualberta.cs.queueunderflow.test.serializers;

import java.lang.reflect.Type;
import java.util.ArrayList;

import ca.ualberta.cs.queueunderflow.Picture;
import ca.ualberta.cs.queueunderflow.models.Answer;
import ca.ualberta.cs.queueunderflow.models.Question;
import ca.ualberta.cs.queueunderflow.models.QuestionList;
import ca.ualberta.cs.queueunderflow.models.Reply;
import ca.ualberta.cs.queueunderflow.serializers.QuestionListSerializer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import junit.framework.TestCase;

public class QuestionListTest extends TestCase {

	public void testQuestionsAsked() {
		
	    //QuestionList test for serialization and deserialization
		String questionName= "A question?";
		String author="A author";
		Question questionTest= new Question(questionName,author);
	
		//Adding replies to question
		String reply_author= "I dunno";
		Reply question_reply= new Reply("Whats going on",reply_author);
	
		questionTest.addReply(question_reply);
		questionTest.setUpvotes(2);

		QuestionList questionList= new QuestionList();
		
		
		//Adding an answer
		String answerName= "An answer";
		String answer_author= "Me";
		String answer_reply_author= "7-11";
		Answer testAnswer= new Answer(answerName,answer_author);
		
		//Add replies to answer
		Reply answer_reply= new Reply("Go to stackoverflow",answer_reply_author);
		testAnswer.addReply(answer_reply);
		
		Picture pic= new Picture(10);
		testAnswer.setPicture(pic);
		questionTest.addAnswer(testAnswer);
		questionTest.setPicture(pic);
		
		questionList.add(questionTest);
		assertTrue("Question List isn't empty", questionList.size()==1);
		
		final GsonBuilder gsonBuilder2 = new GsonBuilder();
	    gsonBuilder2.registerTypeAdapter(QuestionList.class, new QuestionListSerializer());
	    final Gson gson2 = gsonBuilder2.create();
	    final String json2=gson2.toJson(questionList);
	    
	    Type listOfTestObject = new TypeToken<ArrayList<Question>>(){}.getType();
		ArrayList <Question> questionList2= gson2.fromJson(json2,listOfTestObject);
		QuestionList deserialized2= new QuestionList();
		deserialized2.setQuestionList(questionList2);
		assertTrue("Deserialized list isn't empty", deserialized2.size()==1);

		
		Question before= questionList.get(0);
		Question after= deserialized2.get(0);

		String before_answer= before.getAnswerList().getAnswer(0).getName();
		String after_answer= after.getAnswerList().getAnswer(0).getName();
		String before_reply=before.getReplies().get(0).getReply();
		String after_reply= after.getReplies().get(0).getReply();
		
		//Test the fields in question before serialization and deserialization to see
		// if they are the same; did this way because using the equals method made 
		//doesn't work for some reason in this case
		
		assertTrue("Both questions are the same",before.getName().equals(after.getName()));
		assertTrue("Both answers are the same",before_answer.equals(after_answer));
		assertTrue("Both replies are the same", before_reply.equals(after_reply));
		
	}
}
