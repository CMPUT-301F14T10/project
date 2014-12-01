package ca.ualberta.cs.queueunderflow.serializers;

import java.lang.reflect.Type;
import java.util.ArrayList;

import ca.ualberta.cs.queueunderflow.models.GenericResponse;
import ca.ualberta.cs.queueunderflow.models.Question;
import ca.ualberta.cs.queueunderflow.models.QuestionList;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.reflect.TypeToken;

/**
 * The Class QuestionListSerializer.
 * @author group 10
 * @version 1.0
 */
public class QuestionListSerializer implements JsonSerializer<QuestionList>{

	/* (non-Javadoc)
	 * @see com.google.gson.JsonSerializer#serialize(java.lang.Object, java.lang.reflect.Type, com.google.gson.JsonSerializationContext)
	 */
	public JsonElement serialize(QuestionList questionList, Type arg1,
			JsonSerializationContext context) {

		JsonObject jsonObject= new JsonObject();
		ArrayList <Question> questions= questionList.getQuestionList();
		for (int i=0; i<questions.size();i++) {
			Question question= questions.get(i);
			final GsonBuilder gsonBuilder2 = new GsonBuilder();
			gsonBuilder2.registerTypeAdapter(Question.class, new QuestionSerializer());
			final Gson gson2 = gsonBuilder2.create();
			final JsonElement json2=gson2.toJsonTree(question);
			String number= Integer.toString(i);
			jsonObject.add("question"+number,json2);
		}
		int size= questions.size();
		jsonObject.addProperty("size", size);
		return jsonObject;
	}

}
