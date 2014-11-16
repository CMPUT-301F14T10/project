package ca.ualberta.cs.queueunderflow.serializers;

import java.lang.reflect.Type;
import java.util.Date;

import ca.ualberta.cs.queueunderflow.models.Question;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

/**
 * The Class QuestionSerializer.
 * @author group 10
 * @version 0.5
 */
public class QuestionSerializer implements JsonSerializer<Question>{

	/* (non-Javadoc)
	 * @see com.google.gson.JsonSerializer#serialize(java.lang.Object, java.lang.reflect.Type, com.google.gson.JsonSerializationContext)
	 */
	@Override
	public JsonElement serialize(Question question, Type arg1,
			JsonSerializationContext serialization_context) {
		
		final JsonObject questionObject= new JsonObject();
		questionObject.addProperty("questionName", question.getName());
		
        JsonElement replies=serialization_context.serialize(question.getReplies());
	    questionObject.add("questionReplies",replies);
		
		questionObject.addProperty("author",question.getAuthor());
     
	    
		questionObject.addProperty("upvote", question.getUpvotes());
		questionObject.addProperty("hasPicture",question.hasPicture());
		
	    Date question_date= question.getDate();

	    JsonElement date= serialization_context.serialize(question_date);

	    questionObject.add("date",date);
	    
	    //questionObject.addProperty("imagePath", question.getImagePath());
	    //questionObject.addProperty("encodedImage",question.getEncodedImage());
	    questionObject.addProperty("isFav",question.getIsFav());
	    questionObject.addProperty("isInReadingList",question.getIsInReadingList());
	    questionObject.addProperty("uniqueID",question.getID());
		
		//final GsonBuilder gsonBuilder2 = new GsonBuilder();
	    //final Gson gson2 = gsonBuilder2.create();

	    JsonElement answers= serialization_context.serialize(question.getAnswerList());
		questionObject.add("answerList",answers);
		
	    if (question.getEncodedImage()== null) {
	    	questionObject.addProperty("encodedImage","none");
	    }
	    else {
	    	questionObject.addProperty("encodedImage",question.getEncodedImage());
	    }
	    
	    if (question.getImagePath()==null) {
	    	questionObject.addProperty("imagePath","none");
	    }
	    else {
	    	questionObject.addProperty("imagePath",question.getImagePath());
	    }

	    
	    return questionObject;
	}

}
