package ca.ualberta.cs.queueunderflow.serializers;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;

import org.json.JSONArray;

import ca.ualberta.cs.queueunderflow.Picture;
import ca.ualberta.cs.queueunderflow.models.Answer;
import ca.ualberta.cs.queueunderflow.models.Reply;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class AnswerDeserializer implements JsonDeserializer<Answer> {

	@Override
	public Answer deserialize(JsonElement jsonAnswer, Type arg1,
			JsonDeserializationContext arg2) throws JsonParseException {
		final JsonObject answer= jsonAnswer.getAsJsonObject();
		
	    final JsonElement jsonContent = answer.get("answerName");
	    final String content = jsonContent.getAsString();
	    
	    final GsonBuilder gsonBuilder = new GsonBuilder();
	    final Gson gson = gsonBuilder.create();
	    
	    JsonElement jsonArray= answer.get("answerReplies");
	    JsonArray old_array = jsonArray.getAsJsonArray();
	    ArrayList<Reply> replyList= new ArrayList <Reply>();
	    
	    for (int i=0; i<old_array.size(); i++) {
	        JsonElement serial_reply=old_array.get(i);
		    gsonBuilder.registerTypeAdapter(Reply.class, new ReplyDeserializer());
		    Gson gson2 = gsonBuilder.create();
		    Reply deserialized= gson2.fromJson(serial_reply,Reply.class);
		    replyList.add(deserialized);

	    }
	    
	    final String author= answer.get("author").getAsString();
	    final int upvote= answer.get("upvote").getAsInt();
	    final boolean hasPicture= answer.get("hasPicture").getAsBoolean();
	    
	    final int picture= answer.get("picture").getAsInt();
	    Picture new_picture=new Picture(picture);
	    
	    String date_string= answer.get("Date").getAsString();
	    gsonBuilder.registerTypeAdapter(Date.class, new DateDeserializer());
	    Date answer_date= gson.fromJson(date_string, Date.class);
	    
	    Answer deserialized_answer= new Answer(content, author);
	    deserialized_answer.setReplyArray(replyList);
	    deserialized_answer.setUpvotes(upvote);
	    deserialized_answer.setPicture(new_picture);
	    deserialized_answer.setDate(answer_date);
	    deserialized_answer.sethasPicture(hasPicture);

	    return deserialized_answer;
	}

}
