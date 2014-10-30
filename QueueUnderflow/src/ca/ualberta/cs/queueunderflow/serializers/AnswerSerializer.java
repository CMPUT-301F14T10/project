package ca.ualberta.cs.queueunderflow.serializers;

import java.lang.reflect.Type;
import java.util.Date;



import ca.ualberta.cs.queueunderflow.models.Answer;
import ca.ualberta.cs.queueunderflow.models.Reply;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;


public class AnswerSerializer implements JsonSerializer<Answer>
{

	@Override
	public JsonElement serialize(Answer answer, Type arg1,
			JsonSerializationContext arg2)
	{
		final JsonObject answerObject= new JsonObject();
		answerObject.addProperty("answerName", answer.getAnswer());
		//For arraylist<reply>, have a jsonArray for it that contains
		//json objects (replies)
		final JsonArray jsonAnswerReplies= new JsonArray();
		for (final Reply reply: answer.getReplies()) {
		
		    GsonBuilder gsonBuilder = new GsonBuilder();
		    Gson gson = gsonBuilder.create();

		    gsonBuilder.registerTypeAdapter(Reply.class, new ReplySerializer());
		    final String jsonReply= gson.toJson(reply);
		    JsonPrimitive element= new JsonPrimitive(jsonReply);
		    jsonAnswerReplies.add(element);
		}
		answerObject.add("answerReplies",jsonAnswerReplies);
		answerObject.addProperty("author",answer.getAuthor());
		answerObject.addProperty("upvote", answer.getUpvotes());
		answerObject.addProperty("hasPicture",answer.hasPicture());
		//The picture property will have to change when picture changes
		int size=answer.getPicture().getSize();
		answerObject.addProperty("picture",size);
		
	    GsonBuilder gsonBuilder = new GsonBuilder();
	    
	    //May need a date serializer b/c date might end up being parsed wrong
	    gsonBuilder.setDateFormat("M/d/yy hh:mm a");
	    Gson gson = gsonBuilder.create();
	    Date answer_date= answer.getDate();
	    final String date= gson.toJson(answer_date);
	    answerObject.addProperty("date",date);
	    
		return answerObject;
	}

}
