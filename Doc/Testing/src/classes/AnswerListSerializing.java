package classes;

import java.lang.reflect.Type;



import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class AnswerListSerializing implements JsonSerializer<AnswerList> {

	public JsonElement serialize(AnswerList answerList, Type arg1,
			JsonSerializationContext arg2) {
		final JsonArray answerListArray= new JsonArray();
		for (final Answer answer: answerList.getAnswerList()) {
		    GsonBuilder gsonBuilder = new GsonBuilder();
		    Gson gson = gsonBuilder.create();

		    gsonBuilder.registerTypeAdapter(Answer.class, new AnswerSerializing());
		    final String jsonAnswer= gson.toJson(answer);
		    JsonPrimitive element= new JsonPrimitive(jsonAnswer);
		    answerListArray.add(element);
		}
		
		return answerListArray;
	}

}
