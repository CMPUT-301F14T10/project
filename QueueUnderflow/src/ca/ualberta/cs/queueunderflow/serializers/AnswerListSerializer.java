package ca.ualberta.cs.queueunderflow.serializers;

import java.lang.reflect.Type;

import ca.ualberta.cs.queueunderflow.models.Answer;
import ca.ualberta.cs.queueunderflow.models.AnswerList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class AnswerListSerializer implements JsonSerializer<AnswerList> {

	@Override
	public JsonElement serialize(AnswerList answerList, Type arg1,
			JsonSerializationContext arg2) {
		final JsonArray answerListArray= new JsonArray();
		for (final Answer answer: answerList.getAnswerList()) {
		    GsonBuilder gsonBuilder = new GsonBuilder();
		    gsonBuilder.registerTypeAdapter(Answer.class, new AnswerSerializer());

		    Gson gson = gsonBuilder.create();

		    final String jsonAnswer= gson.toJson(answer);
		    JsonPrimitive element= new JsonPrimitive(jsonAnswer);
		    answerListArray.add(element);
		}
		
		return answerListArray;
	}

}
