package ca.ualberta.cs.queueunderflow;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.UUID;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import ca.ualberta.cs.queueunderflow.legacy_code.AnswerSerializer;
import ca.ualberta.cs.queueunderflow.models.Answer;
import ca.ualberta.cs.queueunderflow.models.GenericResponse;
import ca.ualberta.cs.queueunderflow.models.Question;
import ca.ualberta.cs.queueunderflow.models.QuestionList;
import ca.ualberta.cs.queueunderflow.models.Reply;
import ca.ualberta.cs.queueunderflow.serializers.QuestionDeserializer;
import ca.ualberta.cs.queueunderflow.serializers.QuestionListDeserializer;
import ca.ualberta.cs.queueunderflow.serializers.QuestionListSerializer;
import ca.ualberta.cs.queueunderflow.serializers.QuestionSerializer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
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
	//private static final String QUESTIONLIST_URL="http://cmput301.softwareprocess.es:8080/cmput301f14t10/QUESTIONLIST/1";
	/** The Constant QUESTION_IDS. */
	private static final String QUESTION_IDS= "http://cmput301.softwareprocess.es:8080/cmput301f14t10/questionIDS/1";
	
	/** The gson. */
	private Gson gson;
	
	/**
	 * Instantiates a new ES manager.
	 */
	public ESManager() {
		gson = new Gson();
	}
	
	
	//This is to push a questionList to the server
	
/*	public void addQuestionList(QuestionList questionList) {
		System.out.print("INSIDE NETWORK MANAGER - ADDQUESTIONLIST METHOD");
		HttpClient httpClient = new DefaultHttpClient();
		
		try {
			HttpPut addRequest = new HttpPut(QUESTIONLIST_URL);

			final GsonBuilder gsonBuilder= new GsonBuilder();
			gsonBuilder.registerTypeAdapter(QuestionList.class,new QuestionListSerializer());
			Gson gson= gsonBuilder.create();

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
	
	//Get the questionlist from the server
	
/*	public QuestionList getQuestionList() {
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(QUESTIONLIST_URL);
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
	
	// This method is for pulling questions from the server by ID
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
			//String json = getEntityContent(response);

/*			final GsonBuilder gsonBuilder= new GsonBuilder();
			gsonBuilder.registerTypeAdapter(QuestionList.class,new QuestionDeserializer());
			Gson gson= gsonBuilder.create();
			Question deserialized= gson.fromJson(json,Question.class);
			return deserialized;*/
			
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
	
///------------THREE METHODS BELOW are to push list of question IDS in string to the server--------------------
//Doesn't affect anything at the moment
	
	/**
 * Adds the question ids.
 *
 * @param QuestionIDS the question ids
 */
public void addQuestionIDS(ArrayList <String> QuestionIDS) {
		HttpClient httpClient= new DefaultHttpClient();
		try {
			HttpPut addRequest = new HttpPut(QUESTION_IDS);
			
			StringEntity stringEntity = new StringEntity(gson.toJson(QuestionIDS));
			addRequest.setEntity(stringEntity);
			
			// Execute the request
			HttpResponse response = httpClient.execute(addRequest);
			String status = response.getStatusLine().toString();
			System.out.println("ADDQUESTIONIDS - HTTP STATUS ----- " + status);
			
			
		} catch (Exception e) {
			System.out.println("ADD QUESTIONIDS FAILED");
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Parses the question id hit.
	 *
	 * @param response the response
	 * @return the search hit
	 */
	private SearchHit <ArrayList<String>> parseQuestionIDHit(HttpResponse response) {
		try {
			String json = getEntityContent(response);
			Type searchHitType = new TypeToken<SearchHit<ArrayList<String>>>() {}.getType();
			SearchHit<ArrayList<String>> sr = gson.fromJson(json, searchHitType);
			return sr;
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Gets the question ids.
	 *
	 * @return the question ids
	 */
	public ArrayList<String> getQuestionIDS() {
		
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(QUESTION_IDS);
		
		HttpResponse response;
		try {
			response = httpClient.execute(httpGet);
			SearchHit<ArrayList<String>> sr = parseQuestionIDHit(response);
			return sr.getSource();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		ArrayList <String> empty= new ArrayList<String> ();
		return empty ;
	}
	
///-------------------------------------------------------------------------------	
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

	/**
	 * Adds the answer.
	 *
	 * @param questionID the question id
	 * @param newAnswer the new answer
	 */
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
	
	/**
	 * Adds the q reply.
	 *
	 * @param questionID the question id
	 * @param newReply the new reply
	 */
	public void addQReply(UUID questionID, Reply newReply) {
		System.out.println("INSIDE NETWORK MANAGER - ADDQREPLY METHOD");
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
	
	/**
	 * Adds the a reply.
	 *
	 * @param questionID the question id
	 * @param answerID the answer id
	 * @param newReply the new reply
	 */
	public void addAReply(UUID questionID, UUID answerID, Reply newReply) {
		System.out.println("INSIDE NETWORK MANAGER - ADDAREPLY METHOD");
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

	/**
	 * Upvote question.
	 *
	 * @param questionID the question id
	 */
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

	/**
	 * Upvote answer.
	 *
	 * @param questionID the question id
	 * @param answerID the answer id
	 */
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
