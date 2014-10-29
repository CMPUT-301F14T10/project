package ca.ualberta.cs.queueunderflow;

import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class QuestionListSerializer implements JsonSerializer<QuestionList> {

	@Override
	public JsonElement serialize(QuestionList questionList, Type arg1,
			JsonSerializationContext arg2) {
		final JsonArray questionListArray= new JsonArray();
		for(Question question: questionList.getQuestionList()) {
			  GsonBuilder gsonBuilder = new GsonBuilder();
			    gsonBuilder.registerTypeAdapter(Answer.class, new AnswerSerializer());

			    Gson gson = gsonBuilder.create();

			    final String jsonQuestion= gson.toJson(question);
			    JsonPrimitive element= new JsonPrimitive(jsonQuestion);
			    questionListArray.add(element);
		}
		JsonPrimitive element= new JsonPrimitive(questionList.getOnline());
		questionListArray.add(element);
		return questionListArray;

	}
	
}
