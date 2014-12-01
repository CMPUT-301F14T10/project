package ca.ualberta.cs.queueunderflow.elasticsearch;

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
import ca.ualberta.cs.queueunderflow.singletons.ListHandler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;


//Adapted from elasticsearch in 301 labs

/**
 * The Class ESManager.
 */
public class ESManager {

	/** The Constant SEARCH_URL. */
	private static final String SEARCH_URL = "http://cmput301.softwareprocess.es:8080/cmput301f14t10/questions/_search";
	
	/** The Constant RESOURCE_URL. */
	private static final String RESOURCE_URL = "http://cmput301.softwareprocess.es:8080/cmput301f14t10/questions/";
	
	/** The gson. */
	private Gson gson;
	
	/**
	 * Instantiates a new ES manager.
	 */
	public ESManager() {
		gson = new Gson();
	}
	
	//adapted from elasticsearch in 301 lab again
	/**
	 * Gets the entity content.
	 *
	 * @param response the response
	 * @return the entity content
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public String getEntityContent(HttpResponse response) throws IOException {
		BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}
		return result.toString();
	}
	
	/**
	 * Adds the question.
	 *
	 * @param newQuestion the new question
	 */
	public void addQuestion(Question newQuestion) {
		System.out.println("INSIDE NETWORK MANAGER - ADDQUESTION METHOD");
		HttpClient httpClient = new DefaultHttpClient();
		
		try {
			HttpPost addRequest = new HttpPost(RESOURCE_URL + newQuestion.getID().toString());
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

	/**
	 * Adds the answer.
	 *
	 * @param questionID the question id
	 * @param newAnswer the new answer
	 */
	public void addAnswer(String questionID, Answer newAnswer) {
		System.out.println("INSIDE NETWORK MANAGER - ADDANSWER METHOD");
		HttpClient httpClient = new DefaultHttpClient();
		
		try {
			// Just update the answerList of the question
			HttpPost addRequest = new HttpPost(RESOURCE_URL + questionID + "/_update");
			
			// get the question & add the answer
			Question question = getQuestion(questionID);
			question.addAnswer(newAnswer);
	
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
	
	/**
	 * Upvote question.
	 *
	 * @param questionID the question id
	 */
	public void upvoteQuestion(String questionID) {
		System.out.println("INSIDE NETWORK MANAGER -UPVOTEQUESTION METHOD");
		HttpClient httpClient = new DefaultHttpClient();
		
		try {
			// Just update the answerList of the question
			HttpPost addRequest = new HttpPost(RESOURCE_URL + questionID + "/_update");
			
			// get the question
			Question question = getQuestion(questionID);
			question.upvoteResponse();
			
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
	

	/**
	 * Upvote answer.
	 *
	 * @param questionID the question id
	 * @param answerID the answer id
	 */
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
	
	/**
	 * Adds the q reply.
	 *
	 * @param questionID the question id
	 * @param newReply the new reply
	 */
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
	
	/**
	 * Adds the a reply.
	 *
	 * @param questionID the question id
	 * @param answerID the answer id
	 * @param newReply the new reply
	 */
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


	
	// Below is very slightly modified from the AndroidElasticSearch Lab
	/**
	 * Get Questions with the specified search string. If the search does not
	 * specify fields, it searches on all the fields.
	 *
	 * @param searchString the search string
	 * @param field the field
	 * @return the list
	 */
	public List<Question> searchQuestions(String searchString, String field) {
		List<Question> result = new ArrayList<Question>();

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
	 * Creates a search request from a search string and a field.
	 *
	 * @param searchString the search string
	 * @param field the field
	 * @return the http post
	 * @throws UnsupportedEncodingException the unsupported encoding exception
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
	 * Parses the response of a search.
	 *
	 * @param response the response
	 * @return the search response
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private SearchResponse<Question> parseSearchResponse(HttpResponse response) throws IOException {
		String json;
		json = getEntityContent(response);

		Type searchResponseType = new TypeToken<SearchResponse<Question>>() {
		}.getType();
		
		SearchResponse<Question> esResponse = gson.fromJson(json, searchResponseType);

		return esResponse;
	}


	/**
	 * Gets the question.
	 *
	 * @param questionID the question id
	 * @return the question
	 */
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
	
	/**
	 * Parses the question hit.
	 *
	 * @param response the response
	 * @return the search hit
	 */
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
