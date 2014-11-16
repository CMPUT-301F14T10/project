package ca.ualberta.cs.queueunderflow.legacy_code;

import java.lang.reflect.Type;
import java.util.Date;



import ca.ualberta.cs.queueunderflow.models.Answer;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;


/**
 * The Class AnswerSerializer.
 * @author group 10
 * @version 0.5
 */
public class AnswerSerializer implements JsonSerializer<Answer>
{

	/* (non-Javadoc)
	 * @see com.google.gson.JsonSerializer#serialize(java.lang.Object, java.lang.reflect.Type, com.google.gson.JsonSerializationContext)
	 */
	@Override
	public JsonElement serialize(Answer answer, Type arg1,
			JsonSerializationContext serialization_context)
	{
		final JsonObject answerObject= new JsonObject();
		answerObject.addProperty("answerName", answer.getName());
		
		answerObject.addProperty("author",answer.getAuthor());
		answerObject.addProperty("upvote", answer.getUpvotes());
		answerObject.addProperty("hasPicture",answer.hasPicture());
		
		int size=answer.getPicture().getSize();
		answerObject.addProperty("picture", size);
		//The picture property will have to change when picture changes
		/*
		if (answer.hasPicture()){
			int size=answer.getPicture().getSize();
			answerObject.addProperty("picture",size);
		}
		else {
			int size=0;
			answerObject.addProperty("picture",size);
		}
		*/
	    GsonBuilder gsonBuilder = new GsonBuilder();
	    
	    //May need a date serializer b/c date might end up being parsed wrong
	    gsonBuilder.setDateFormat("M/d/yy hh:mm a");
	    Date answer_date= answer.getDate();
	    JsonElement date= serialization_context.serialize(answer_date);
	    
        JsonElement replies=serialization_context.serialize(answer.getReplies());
	    answerObject.add("answerReplies",replies);
	    answerObject.add("date",date);
	
		return answerObject;
	}

}
