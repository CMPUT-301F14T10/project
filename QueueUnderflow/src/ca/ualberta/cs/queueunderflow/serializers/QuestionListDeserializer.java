package ca.ualberta.cs.queueunderflow.serializers;

import java.lang.reflect.Type;
import java.util.ArrayList;


import ca.ualberta.cs.queueunderflow.models.Question;
import ca.ualberta.cs.queueunderflow.models.QuestionList;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

/**
 * The Class QuestionListDeserializer.
 * @author group 10
 * @version 1.0
 */
public class QuestionListDeserializer implements JsonDeserializer<QuestionList>{

	/* (non-Javadoc)
	 * @see com.google.gson.JsonDeserializer#deserialize(com.google.gson.JsonElement, java.lang.reflect.Type, com.google.gson.JsonDeserializationContext)
	 */
	public QuestionList deserialize(JsonElement jsonQuestionList, Type arg1,
			JsonDeserializationContext context) throws JsonParseException {
		
		JsonObject jsonObject= jsonQuestionList.getAsJsonObject();
		//Get size of the questionlist
		int size= jsonObject.get("size").getAsInt();
		
		QuestionList deserialized= new QuestionList();
		
		for (int i=0; i<size; i++) {
			String number = Integer.toString(i);
			JsonElement jsonQuestion= jsonObject.get("question"+number);
			final GsonBuilder gsonBuilder2 = new GsonBuilder();
		    gsonBuilder2.registerTypeAdapter(Question.class, new QuestionDeserializer());
		    final Gson gson2 = gsonBuilder2.create();
		    Question question= gson2.fromJson(jsonQuestion, Question.class);
		    deserialized.add(question);
			
		}
		
		return deserialized;
	}

}
