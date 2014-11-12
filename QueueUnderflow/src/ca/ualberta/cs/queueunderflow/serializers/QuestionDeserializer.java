package ca.ualberta.cs.queueunderflow.serializers;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import ca.ualberta.cs.queueunderflow.Picture;
import ca.ualberta.cs.queueunderflow.models.AnswerList;
import ca.ualberta.cs.queueunderflow.models.Question;
import ca.ualberta.cs.queueunderflow.models.Reply;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

// TODO: Auto-generated Javadoc
/**
 * The Class QuestionDeserializer.
 * @author group 10
 * @version 0.5
 */
public class QuestionDeserializer implements JsonDeserializer<Question> {

	/* (non-Javadoc)
	 * @see com.google.gson.JsonDeserializer#deserialize(com.google.gson.JsonElement, java.lang.reflect.Type, com.google.gson.JsonDeserializationContext)
	 */
	@Override
	public Question deserialize(JsonElement jsonObject, Type arg1,
			JsonDeserializationContext context) throws JsonParseException {
	    final JsonObject jsonQuestion= jsonObject.getAsJsonObject();
		
	    final JsonElement jsonContent= jsonQuestion.get("questionName");
	    final String question_content = jsonContent.getAsString();
	    
	    final String author= jsonQuestion.get("author").getAsString();
	    final int upvote= jsonQuestion.get("upvote").getAsInt();
	  //  final boolean hasPicture= jsonQuestion.get("hasPicture").getAsBoolean();
	    final boolean inReadingList= jsonQuestion.get("IsInReadingList").getAsBoolean();
	    final boolean isFavorite= jsonQuestion.get("isFav").getAsBoolean();
	    //get ID, convert to UUID
	    final UUID uniqueID = UUID.fromString(jsonQuestion.get("id").getAsString());
	    
	    
	    final int picture= jsonQuestion.get("picture").getAsInt();
	    Picture new_picture=new Picture(picture);
	    
		Type listType = new TypeToken<ArrayList<Reply>>() {}.getType();
	    ArrayList<Reply> replyList= new Gson().fromJson(jsonQuestion.get("questionReplies"), listType);
	    
	    final GsonBuilder gsonBuilder = new GsonBuilder();
	    
	    //Gson gson2=gsonBuilder.create();
		   
	    String date= jsonQuestion.get("date").getAsString();
		SimpleDateFormat formatter = new SimpleDateFormat("M/d/yy hh:mm a");
		Date converted= new Date();
		try {
			converted = formatter.parse(date);
	 
		} catch (ParseException e) {
			e.printStackTrace();
		}
		

		JsonElement jsonAnswerList= jsonQuestion.get("answerList");

	    AnswerList answerList= context.deserialize(jsonAnswerList, AnswerList.class);
	    

		
		Question deserialized= new Question("Name","Author");
		deserialized.setName(question_content);
		deserialized.setAuthor(author);
		deserialized.setReplyList(replyList);
		deserialized.setDate(converted);
		deserialized.setUpvotes(upvote);
		deserialized.setPicture(new_picture);
		deserialized.setIsFav(isFavorite);
		deserialized.setIsInReadingList(inReadingList);
		deserialized.setAnswerList(answerList);
		deserialized.setID(uniqueID);
		return deserialized;
	}

}
