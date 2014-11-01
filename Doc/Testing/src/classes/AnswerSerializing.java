package classes;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;



import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class AnswerSerializing implements JsonSerializer<Answer> {

	public JsonElement serialize(Answer answer, Type arg1,
			JsonSerializationContext serialization_context) {
		final JsonObject answerObject= new JsonObject();
		answerObject.addProperty("answerName", answer.getAnswer());
		
		//DON'T NEED
		//For arraylist<reply>, have a jsonArray for it that contains
		//json objects (replies)
		
		
/*		final JsonArray jsonAnswerReplies= new JsonArray();
		for (final Reply reply: answer.getReplies()) {
		
		    GsonBuilder gsonBuilder = new GsonBuilder();
		    Gson gson = gsonBuilder.create();

		    gsonBuilder.registerTypeAdapter(Reply.class, new ReplySerializing());
		    final String jsonReply= gson.toJson(reply);
		    JsonPrimitive element= new JsonPrimitive(jsonReply);
		    //jsonAnswerReplies.add(element);
		    jsonAnswerReplies.add(element);
		}*/
		
/*		ArrayList<String> jsonReplies= new ArrayList<String>();
		for (final Reply reply: answer.getReplies()) {
		
		    GsonBuilder gsonBuilder = new GsonBuilder();
		    Gson gson = gsonBuilder.create();
		    gsonBuilder.registerTypeAdapter(Reply.class, new ReplySerializing());
		    final String jsonReply= gson.toJson(reply);
		    //jsonAnswerReplies.add(element);
		    jsonReplies.add(jsonReply);
		}
		*/
		//String s= jsonReplies.toString();
		
		//answerObject.add("answerReplies",jsonAnswerReplies);
		//answerObject.add("answerReplies",gson)

		
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
	    //String date= gson.toJson(answer_date);
	    JsonElement date= serialization_context.serialize(answer_date);
	    
        JsonElement replies=serialization_context.serialize(answer.getReplies());
	    answerObject.add("answerReplies",replies);
	/*    gsonBuilder.registerTypeAdapter(Date.class, new DateSerializing());
	    Gson gson= gsonBuilder.create();
	    final String date= gson.toJson(answer.getDate());*/
	    answerObject.add("date",date);
	    //gsonBuilder.registerTypeAdapter(Reply.class, new ReplySerializing());
	    
	    //String json= gson.toJson(answer.getReplies(),ArrayList.class);
	   // JsonArray jsonReplies= new JsonArray();
	   // String json= gson.toJson(answerReplies, ArrayList.class);
	    //answerObject.addProperty("answerReplies", json);
	   // answerObject.addProperty("date", date);
		return answerObject;
	}

}
