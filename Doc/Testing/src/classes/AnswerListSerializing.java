package classes;

import java.lang.reflect.Type;
import java.util.ArrayList;



import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class AnswerListSerializing implements JsonSerializer<AnswerList> {

	public JsonElement serialize(AnswerList answerList, Type arg1,
			JsonSerializationContext serialization_context) {
/*		final JsonArray answerListArray= new JsonArray();
		for (final Answer answer: answerList.getAnswerList()) {
		    GsonBuilder gsonBuilder = new GsonBuilder();
		    Gson gson = gsonBuilder.create();

		    gsonBuilder.registerTypeAdapter(Answer.class, new AnswerSerializing());
		    final String jsonAnswer= gson.toJson(answer);
		    JsonPrimitive element= new JsonPrimitive(jsonAnswer);
		    answerListArray.add(element);
		}
		
		return answerListArray;*/
		JsonObject jsonObject= new JsonObject();
		ArrayList<Answer> serialize= answerList.getAnswerList();
		for (int i=0; i<serialize.size(); i++) {
			Answer answer= serialize.get(i);
			JsonElement serial_answer=serialization_context.serialize(answer);
			jsonObject.add(answer.getAnswer(), serial_answer);
		}
		//JsonElement answers= serialization_context.serialize(serialize);
		//jsonObject.add(answerList.get, answers);
		return jsonObject;
	}

}
