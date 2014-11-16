package classes;

import java.lang.reflect.Type;


import model_classes.Question;
import model_classes.QuestionList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class QuestionListDeserializer implements JsonDeserializer<QuestionList>{

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
		    gsonBuilder2.registerTypeAdapter(Question.class, new QuestionDeserializing());
		    final Gson gson2 = gsonBuilder2.create();
		    Question question= gson2.fromJson(jsonQuestion, Question.class);
		    deserialized.add(question);
			
		}
		
		return deserialized;
	}

}
