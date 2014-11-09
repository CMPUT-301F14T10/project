package ca.ualberta.cs.queueunderflow.serializers;

import java.lang.reflect.Type;
import java.util.Date;

import ca.ualberta.cs.queueunderflow.models.Question;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

// TODO: Auto-generated Javadoc
/**
 * The Class QuestionSerializer.
 */
public class QuestionSerializer implements JsonSerializer<Question>{

	/* (non-Javadoc)
	 * @see com.google.gson.JsonSerializer#serialize(java.lang.Object, java.lang.reflect.Type, com.google.gson.JsonSerializationContext)
	 */
	@Override
	public JsonElement serialize(Question question, Type arg1,
			JsonSerializationContext serialization_context) {
		JsonObject jsonQuestion= new JsonObject();
		jsonQuestion.addProperty("questionName",question.getName());
		
		final GsonBuilder gsonBuilder2 = new GsonBuilder();
	    //final Gson gson2 = gsonBuilder2.create();

	    JsonElement answers= serialization_context.serialize(question.getAnswerList());
		jsonQuestion.add("answerList",answers);

        JsonElement replies=serialization_context.serialize(question.getReplies());
	    jsonQuestion.add("questionReplies",replies);
	   
		jsonQuestion.addProperty("author", question.getAuthor());
		jsonQuestion.addProperty("upvote", question.getUpvotes());
		jsonQuestion.addProperty("hasPicture",question.hasPicture());

		//The picture property will have to change when picture changes
		//int size=question.getPicture().getSize();
		int size = 0;
		jsonQuestion.addProperty("picture",size);
		
	    //May need a date serializer b/c date might end up being parsed wrong
	    gsonBuilder2.setDateFormat("M/d/yy hh:mm a");
	    Date question_date= question.getDate();
	    JsonElement date= serialization_context.serialize(question_date);
	    jsonQuestion.add("date",date);
	    
	    jsonQuestion.addProperty("isFav",question.getIsFav());
	    jsonQuestion.addProperty("IsInReadingList",question.getIsInReadingList());
	    
	    return jsonQuestion;
	}

}
