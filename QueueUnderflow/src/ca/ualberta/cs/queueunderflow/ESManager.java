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
import ca.ualberta.cs.queueunderflow.serializers.QuestionSerializer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

public class ESManager {

	private static final String SEARCH_URL = "http://cmput301.softwareprocess.es:8080/cmput301f14t10/questions/_search";
	private static final String RESOURCE_URL = "http://cmput301.softwareprocess.es:8080/cmput301f14t10/questions/";
	
	public ESManager() {
		
	}
	
	public void addQuestion(Question newQuestion) {
		System.out.print("INSIDE NETWORK MANAGER - ADDQUESTION METHOD");
		HttpClient httpClient = new DefaultHttpClient();
		
		try {
			HttpPost addRequest = new HttpPost(RESOURCE_URL + newQuestion.getID().toString());
			
			// Serialize the question
			final GsonBuilder gsonBuilder2 = new GsonBuilder();
			Type generictype = new TypeToken<GenericResponse>() { }.getType();
		    gsonBuilder2.registerTypeAdapter(generictype, new QuestionSerializer());
		    final Gson gson2 = gsonBuilder2.create();
		    final JsonElement json2 = gson2.toJsonTree(newQuestion);
			
			StringEntity stringEntity = new StringEntity(json2.toString());
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
		System.out.print("INSIDE NETWORK MANAGER - ADDANSWER METHOD");
		HttpClient httpClient = new DefaultHttpClient();
		
		try {
			// Just update the answerList of the question
			HttpPost addRequest = new HttpPost(RESOURCE_URL + questionID.toString() + "/_update");
			
			// get the question & add the answer
			int questionIndex = ListHandler.getMasterQList().getIndexFromID(questionID);
			Question question = ListHandler.getMasterQList().get(questionIndex);
			question.addAnswer(newAnswer);
			
			// serialize the answerList with the newly added answer
			JsonArray jsonArray= new JsonArray();
			ArrayList <Answer> serialize= question.getAnswerList().getAnswerList();
			for (int i=0; i<serialize.size(); i++) {
				Answer answer= serialize.get(i);
				
				final GsonBuilder gsonBuilder2 = new GsonBuilder();
			    gsonBuilder2.registerTypeAdapter(Answer.class, new AnswerSerializer());
			    final Gson gson2 = gsonBuilder2.create();
			    final JsonElement json2=gson2.toJsonTree(answer);
			    jsonArray.add(json2);
			}
			
			System.out.println("{\"doc\": {\"answerList\":" + jsonArray.toString() + "}");
			StringEntity stringEntity = new StringEntity("{\"doc\": {\"answerList\":" + jsonArray.toString() + "}}");
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
	
}
