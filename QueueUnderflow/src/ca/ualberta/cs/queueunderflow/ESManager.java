package ca.ualberta.cs.queueunderflow;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.UUID;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import ca.ualberta.cs.queueunderflow.legacy_code.AnswerSerializer;
import ca.ualberta.cs.queueunderflow.models.Answer;
import ca.ualberta.cs.queueunderflow.models.GenericResponse;
import ca.ualberta.cs.queueunderflow.models.Question;
import ca.ualberta.cs.queueunderflow.models.QuestionList;
import ca.ualberta.cs.queueunderflow.models.Reply;
import ca.ualberta.cs.queueunderflow.serializers.QuestionListSerializer;
import ca.ualberta.cs.queueunderflow.serializers.QuestionSerializer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

public class ESManager {

	private static final String SEARCH_URL = "http://cmput301.softwareprocess.es:8080/cmput301f14t10/questions/_search";
	private static final String RESOURCE_URL = "http://cmput301.softwareprocess.es:8080/cmput301f14t10/questions/";
	
	private Gson gson;
	
	public ESManager() {
		gson = new Gson();
	}
	
	
	//This is to push the questionList to the server, commented out for now though until decided whether needed or not
/*	public void addQuestionList(QuestionList questionList) {
		System.out.print("INSIDE NETWORK MANAGER - ADDQUESTIONLIST METHOD");
		HttpClient httpClient = new DefaultHttpClient();
		
		try {
			HttpPost addRequest = new HttpPost(RESOURCE_URL + "/_questionList");

			final GsonBuilder gsonBuilder2= new GsonBuilder();
			gsonBuilder2.registerTypeAdapter(QuestionList.class,new QuestionListSerializer());
			StringEntity stringEntity = new StringEntity(gson.toJson(questionList));
			addRequest.setEntity(stringEntity);
			
			// Execute the request
			HttpResponse response = httpClient.execute(addRequest);
			String status = response.getStatusLine().toString();
			System.out.println("ADDQUESTIONLIST - HTTP STATUS ----- " + status);
			
			
		} catch (Exception e) {
			System.out.println("ADD QUESTION FAILED");
			e.printStackTrace();
		}
	}*/
	
	public void addQuestion(Question newQuestion) {
		System.out.print("INSIDE NETWORK MANAGER - ADDQUESTION METHOD");
		HttpClient httpClient = new DefaultHttpClient();
		
		try {
			HttpPost addRequest = new HttpPost(RESOURCE_URL + newQuestion.getID().toString());
			
			// Serialize the question
//			final GsonBuilder gsonBuilder2 = new GsonBuilder();
//			Type generictype = new TypeToken<GenericResponse>() { }.getType();
//		    gsonBuilder2.registerTypeAdapter(generictype, new QuestionSerializer());
//		    final Gson gson2 = gsonBuilder2.create();
//		    final JsonElement json2 = gson2.toJsonTree(newQuestion);
//			
//			StringEntity stringEntity = new StringEntity(json2.toString());
			
			StringEntity stringEntity = new StringEntity(gson.toJson(newQuestion));
			addRequest.setEntity(stringEntity);
			
			// Execute the request
			HttpResponse response = httpClient.execute(addRequest);
			String status = response.getStatusLine().toString();
			System.out.println("ADDQUESTION - HTTP STATUS ----- " + status);
			
			
		} catch (Exception e) {
			System.out.println("ADD QUESTION FAILED");
			e.printStackTrace();
		}
	}

	public void addAnswer(UUID questionID, Answer newAnswer) {
		System.out.println("INSIDE NETWORK MANAGER - ADDANSWER METHOD");
		HttpClient httpClient = new DefaultHttpClient();
		
		try {
			// Just update the answerList of the question
			HttpPost addRequest = new HttpPost(RESOURCE_URL + questionID.toString() + "/_update");
			
			// get the question & add the answer
			int questionIndex = ListHandler.getMasterQList().getIndexFromID(questionID);
			Question question = ListHandler.getMasterQList().get(questionIndex);
			question.addAnswer(newAnswer);
	
			System.out.println("{\"doc\": {\"answerList\":" + gson.toJson(question.getAnswerList()) + "}");
			StringEntity stringEntity = new StringEntity("{\"doc\": {\"answerList\":" + gson.toJson(question.getAnswerList()) + "}}");
			addRequest.setEntity(stringEntity);
			
			// Execute the request
			HttpResponse response = httpClient.execute(addRequest);
			String status = response.getStatusLine().toString();
			System.out.println("ADDANSWER - HTTP STATUS ----- " + status);
			
			
		} catch (Exception e) {
			System.out.println("ADD ANSWER FAILED");
			e.printStackTrace();
		}
	}
	
	public void addQReply(UUID questionID, Reply newReply) {
		System.out.print("INSIDE NETWORK MANAGER - ADDQREPLY METHOD");
		HttpClient httpClient = new DefaultHttpClient();
		
		try {
			// Just update the answerList of the question
			HttpPost addRequest = new HttpPost(RESOURCE_URL + questionID.toString() + "/_update");
			
			// get the question & add the answer
			int questionIndex = ListHandler.getMasterQList().getIndexFromID(questionID);
			Question question = ListHandler.getMasterQList().get(questionIndex);
			question.addReply(newReply);
			
			// serialize the replies with the newly added reply
			System.out.println("{\"doc\": {\"replies\":" + gson.toJson(question.getReplies()) + "}}");
			StringEntity stringEntity = new StringEntity("{\"doc\": {\"replies\":" + gson.toJson(question.getReplies()) + "}}");
			addRequest.setEntity(stringEntity);
			
			// Execute the request
			HttpResponse response = httpClient.execute(addRequest);
			String status = response.getStatusLine().toString();
			System.out.println("ADDQREPLY - HTTP STATUS ----- " + status);
			
			
		} catch (Exception e) {
			System.out.println("ADD Q REPLY FAILED");
			e.printStackTrace();
		}
	}
	
	public void addAReply(UUID questionID, UUID answerID, Reply newReply) {
		System.out.print("INSIDE NETWORK MANAGER - ADDAREPLY METHOD");
		HttpClient httpClient = new DefaultHttpClient();
		
		try {
			// Just update the answerList of the question
			HttpPost addRequest = new HttpPost(RESOURCE_URL + questionID.toString() + "/_update");
			
			// get the question & add the answer
			int questionIndex = ListHandler.getMasterQList().getIndexFromID(questionID);
			Question question = ListHandler.getMasterQList().get(questionIndex);
			
			int answerIndex = question.getAnswerList().getIndexFromID(answerID);
			Answer answer = question.getAnswerList().getAnswer(answerIndex);
	
			answer.addReply(newReply);
			
			// serialize the entire answerList again
			System.out.println("{\"doc\": {\"answerList\":" + gson.toJson(question.getAnswerList()) + "}");
			StringEntity stringEntity = new StringEntity("{\"doc\": {\"answerList\":" + gson.toJson(question.getAnswerList()) + "}}");
			addRequest.setEntity(stringEntity);
			
			// Execute the request
			HttpResponse response = httpClient.execute(addRequest);
			String status = response.getStatusLine().toString();
			System.out.println("ADDAREPLY - HTTP STATUS ----- " + status);
			
			
		} catch (Exception e) {
			System.out.println("ADD A REPLY FAILED");
			e.printStackTrace();
		}
	}

	public void upvoteQuestion(UUID questionID) {
		System.out.println("INSIDE NETWORK MANAGER -UPVOTEQUESTION METHOD");
		HttpClient httpClient = new DefaultHttpClient();
		
		try {
			// Just update the answerList of the question
			HttpPost addRequest = new HttpPost(RESOURCE_URL + questionID.toString() + "/_update");
			
			// get the question
			int questionIndex = ListHandler.getMasterQList().getIndexFromID(questionID);
			Question question = ListHandler.getMasterQList().get(questionIndex);
			question.upvoteResponse();
			
			System.out.println("{\"doc\": {\"upvote\":" + gson.toJson(question.getUpvotes()) + "}");
			StringEntity stringEntity = new StringEntity("{\"doc\": {\"upvote\":" + gson.toJson(question.getUpvotes()) + "}}");
			addRequest.setEntity(stringEntity);
			
			// Execute the request
			HttpResponse response = httpClient.execute(addRequest);
			String status = response.getStatusLine().toString();
			System.out.println("UPVOTEQUESTION - HTTP STATUS ----- " + status);
			
			
		} catch (Exception e) {
			System.out.println("UPVOTE QUESTION FAILED");
			e.printStackTrace();
		}
	}

	public void upvoteAnswer(UUID questionID, UUID answerID) {
		System.out.println("INSIDE NETWORK MANAGER - UPVOTE ANSWER METHOD");
		HttpClient httpClient = new DefaultHttpClient();
		
		try {
			// Just update the answerList of the question
			HttpPost addRequest = new HttpPost(RESOURCE_URL + questionID.toString() + "/_update");
			
			// get the question & add the answer
			int questionIndex = ListHandler.getMasterQList().getIndexFromID(questionID);
			Question question = ListHandler.getMasterQList().get(questionIndex);
			
			int answerIndex = question.getAnswerList().getIndexFromID(answerID);
			Answer answer = question.getAnswerList().getAnswer(answerIndex);
	
			answer.upvoteResponse();
			
			System.out.println("{\"doc\": {\"answerList\":" + gson.toJson(question.getAnswerList()) + "}");
			StringEntity stringEntity = new StringEntity("{\"doc\": {\"answerList\":" + gson.toJson(question.getAnswerList()) + "}}");
			addRequest.setEntity(stringEntity);
			
			// Execute the request
			HttpResponse response = httpClient.execute(addRequest);
			String status = response.getStatusLine().toString();
			System.out.println("UPVOTEANSWER - HTTP STATUS ----- " + status);
			
			
		} catch (Exception e) {
			System.out.println("UPVOTE ANSWER FAILED");
			e.printStackTrace();
		}
	}
	
}
