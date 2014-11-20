package ca.ualberta.cs.queueunderflow;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import android.util.Log;
import ca.ualberta.cs.queueunderflow.models.Answer;
import ca.ualberta.cs.queueunderflow.models.Question;
import ca.ualberta.cs.queueunderflow.models.QuestionList;
import ca.ualberta.cs.queueunderflow.models.Reply;
import ca.ualberta.cs.queueunderflow.serializers.QuestionDeserializer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;


//Adapted from elasticsearch in 301 labs

public class ESManager {

	private static final String SEARCH_URL = "http://cmput301.softwareprocess.es:8080/cmput301f14t10/myquestions/_search";
	private static final String RESOURCE_URL = "http://cmput301.softwareprocess.es:8080/cmput301f14t10/myquestions/";
	
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
	
	
	//Gets content from HTTP response, adapted from elasticsearch in 301 lab again
	public String getEntityContent(HttpResponse response) throws IOException {
		BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}
		return result.toString();
	}
	
	//Get the questionlist from the server, commented out until it is needed or not
	
	/*public QuestionList getQuestionList() {
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(RESOURCE_URL + "/_questionList");
		HttpResponse response;
		try {
			response = httpClient.execute(httpGet);
			String json = getEntityContent(response);

			final GsonBuilder gsonBuilder= new GsonBuilder();
			gsonBuilder.registerTypeAdapter(QuestionList.class,new QuestionListDeserializer());
			Gson gson= gsonBuilder.create();
			QuestionList deserialized= gson.fromJson(json,QuestionList.class);
			return deserialized;
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}*/
	
	/* This method is for potential future use; pulling questions from the server
	public Question getQuestion(int questionID) {
		
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(RESOURCE_URL + questionID);
		
		HttpResponse response;
		try {
			response = httpClient.execute(httpGet);
			String json = getEntityContent(response);

			final GsonBuilder gsonBuilder= new GsonBuilder();
			gsonBuilder.registerTypeAdapter(QuestionList.class,new QuestionDeserializer());
			Gson gson= gsonBuilder.create();
			Question deserialized= gson.fromJson(json,Question.class);
			return deserialized;
				
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	*/
	
	public void addQuestion(Question newQuestion) {
		System.out.println("INSIDE NETWORK MANAGER - ADDQUESTION METHOD");
		HttpClient httpClient = new DefaultHttpClient();
		
		try {
			HttpPost addRequest = new HttpPost(RESOURCE_URL + newQuestion.getID().toString());
			
			/* Can be used later or not
			GsonBuilder gsonBuilder= new GsonBuilder();
			gsonBuilder.registerTypeAdapter(Question.class, new QuestionSerializer());
			gson= gsonBuilder.create();
			*/
			
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

	public void addAnswer(String questionID, Answer newAnswer) {
		System.out.println("INSIDE NETWORK MANAGER - ADDANSWER METHOD");
		HttpClient httpClient = new DefaultHttpClient();
		
		try {
			// Just update the answerList of the question
			HttpPost addRequest = new HttpPost(RESOURCE_URL + questionID + "/_update");
			
			// get the question & add the answer
			Question question = getQuestion(questionID);
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
	
	public void upvoteQuestion(String questionID) {
		System.out.println("INSIDE NETWORK MANAGER -UPVOTEQUESTION METHOD");
		HttpClient httpClient = new DefaultHttpClient();
		
		try {
			// Just update the answerList of the question
			HttpPost addRequest = new HttpPost(RESOURCE_URL + questionID + "/_update");
			
			// get the question
			Question question = getQuestion(questionID);
			question.upvoteResponse();
			
			// For Testing Checks
			System.out.println("WANTED ID : " + questionID);
			for(Question q : ListHandler.getMasterQList().getQuestionList()) {
				System.out.println("-- New Question --");
				System.out.println("ID : " + q.getStringID());
				System.out.println("Name : " + q.getName());
				System.out.println("Author : " + q.getAuthor());
				System.out.println("Upvotes : " + q.getUpvotes());
				System.out.println("    ANSWERS");
				for (Answer a : q.getAnswerList().getAnswerList()) {
					System.out.println("New Answer -- > " + a.getName());
				}
				System.out.println();
			}
			
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
	

	public void upvoteAnswer(String questionID, String answerID) {
		System.out.println("INSIDE NETWORK MANAGER - UPVOTE ANSWER METHOD");
		HttpClient httpClient = new DefaultHttpClient();
		
		try {
			// Just update the answerList of the question
			HttpPost addRequest = new HttpPost(RESOURCE_URL + questionID + "/_update");
			
			// get the question & add the answer
			Question question = getQuestion(questionID);	
			Answer answer = question.getAnswerList().getAnswerFromID(answerID);

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
	
	public void addQReply(String questionID, Reply newReply) {
		System.out.println("INSIDE NETWORK MANAGER - ADDQREPLY METHOD");
		HttpClient httpClient = new DefaultHttpClient();
		
		try {
			// Just update the answerList of the question
			HttpPost addRequest = new HttpPost(RESOURCE_URL + questionID + "/_update");
			
			// get the question
			Question question = getQuestion(questionID);
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
	
	public void addAReply(String questionID, String answerID, Reply newReply) {
		System.out.println("INSIDE NETWORK MANAGER - ADDAREPLY METHOD");
		HttpClient httpClient = new DefaultHttpClient();
		
		try {
			// Just update the answerList of the question
			HttpPost addRequest = new HttpPost(RESOURCE_URL + questionID + "/_update");
			
			// get the question & the answer & add the reply
			Question question = getQuestion(questionID);	
			Answer answer = question.getAnswerList().getAnswerFromID(answerID);

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


	
	// Below is from the AndroidElasticSearch Lab
	/**
	 * Get Questions with the specified search string. If the search does not
	 * specify fields, it searches on all the fields.
	 */
	public List<Question> searchQuestions(String searchString, String field) {
		List<Question> result = new ArrayList<Question>();

		// TODO: Implement search Questions using ElasticSearch
		if (searchString == null || "".equals(searchString)) {
			searchString = "*"; // wildcard - search for everything
		}
		
		HttpClient httpClient = new DefaultHttpClient();
		
		try {
			HttpPost searchRequest = createSearchRequest(searchString, field);
			HttpResponse response = httpClient.execute(searchRequest);
			
			String status = response.getStatusLine().toString();
			Log.i("Search", status);
			
			SearchResponse<Question> esResponse = parseSearchResponse(response);
			Hits<Question> hits = esResponse.getHits();
			
			if (hits != null) {
				if(hits.getHits() != null) {
					for (SearchHit<Question> sesr : hits.getHits()) {
						result.add(sesr.getSource());
					}
				}
			}
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return result;
	}
	
	
	/**
	 * Creates a search request from a search string and a field
	 */
	private HttpPost createSearchRequest(String searchString, String field)	throws UnsupportedEncodingException {
		
		HttpPost searchRequest = new HttpPost(SEARCH_URL);

		String[] fields = null;
		if (field != null) {
			fields = new String[1];
			fields[0] = field;
		}
		
		SimpleSearchCommand command = new SimpleSearchCommand(searchString,	fields);
		
		String query = command.getJsonCommand();
		Log.i("Search", "Json command: " + query);

		StringEntity stringEntity;
		stringEntity = new StringEntity(query);

		searchRequest.setHeader("Accept", "application/json");
		searchRequest.setEntity(stringEntity);

		return searchRequest;
	}
	
	/**
	 * Parses the response of a search
	 */
	private SearchResponse<Question> parseSearchResponse(HttpResponse response) throws IOException {
		String json;
		json = getEntityContent(response);

		Type searchResponseType = new TypeToken<SearchResponse<Question>>() {
		}.getType();
		
		SearchResponse<Question> esResponse = gson.fromJson(json, searchResponseType);

		return esResponse;
	}


	public Question getQuestion(String questionID) {
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(RESOURCE_URL + questionID);
		
		HttpResponse response;
		try {
			response = httpClient.execute(httpGet);
			SearchHit<Question> sr = parseQuestionHit(response);
			return sr.getSource();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private SearchHit<Question> parseQuestionHit(HttpResponse response) {
		
		try {
			String json = getEntityContent(response);
			Type searchHitType = new TypeToken<SearchHit<Question>>() {}.getType();
			
			SearchHit<Question> sr = gson.fromJson(json, searchHitType);
			return sr;
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}

}
