package classes;

import java.lang.reflect.Type;
import java.util.Date;

import model_classes.Question;


import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class QuestionSerializing implements JsonSerializer<Question> {

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
	    
	    questionObject.addProperty("imagePath", question.getImagePath());
	    questionObject.addProperty("encodedImage",question.getEncodedImage());
	    questionObject.addProperty("isFav",question.getIsFav());
	    questionObject.addProperty("isInReadingList",question.getIsInReadingList());
	    questionObject.addProperty("uniqueID",question.getID());
		
		//final GsonBuilder gsonBuilder2 = new GsonBuilder();
	    //final Gson gson2 = gsonBuilder2.create();

	    JsonElement answers= serialization_context.serialize(question.getAnswerList());
		questionObject.add("answerList",answers);

	    
	    return questionObject;
	}

	
}
