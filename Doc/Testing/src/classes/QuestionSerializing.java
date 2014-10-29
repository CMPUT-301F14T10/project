package classes;

import java.lang.reflect.Type;
import java.util.Date;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class QuestionSerializing implements JsonSerializer<Question> {

	public JsonElement serialize(Question question, Type arg1,
			JsonSerializationContext arg2) {
		JsonObject jsonQuestion= new JsonObject();
		jsonQuestion.addProperty("questionName",question.getQuestion());
		
	    GsonBuilder gsonBuilder = new GsonBuilder();
	    gsonBuilder.registerTypeAdapter(AnswerList.class, new AnswerListSerializing());
	    Gson gson = gsonBuilder.create();
	    String jsonAnswerList= gson.toJson(question.getAnswerList());
	    //JsonPrimitive element= new JsonPrimitive(jsonAnswerList);
	    jsonQuestion.addProperty("answerList", jsonAnswerList);
	    
		final JsonArray jsonQuestionReplies= new JsonArray();
		for (final Reply reply: question.getReplies()) {
		
		    //GsonBuilder gsonBuilder = new GsonBuilder();
		   // Gson gson = gsonBuilder.create();

		    gsonBuilder.registerTypeAdapter(Reply.class, new ReplySerializing());
		    final String jsonReply= gson.toJson(reply);
		    JsonPrimitive element= new JsonPrimitive(jsonReply);
		    jsonQuestionReplies.add(element);
		}
		jsonQuestion.add("questionReplies",jsonQuestionReplies);
		
		jsonQuestion.addProperty("author", question.getAuthor());
		jsonQuestion.addProperty("upvote", question.getUpvotes());
		jsonQuestion.addProperty("hasPicture",question.hasPicture());

		//The picture property will have to change when picture changes
		int size=question.getPicture().getSize();
		jsonQuestion.addProperty("picture",size);
		
	    //May need a date serializer b/c date might end up being parsed wrong
	    gsonBuilder.setDateFormat("M/d/yy hh:mm a");
	   // Gson gson = gsonBuilder.create();
	    Date question_date= question.getDate();
	    final String date= gson.toJson(question_date);
	    jsonQuestion.addProperty("date",date);
	    
	    jsonQuestion.addProperty("isFav",question.getIsFav());
	    jsonQuestion.addProperty("IsInReadingList",question.getIsInReadingList());
	    
	    return jsonQuestion;
	}

	
}
