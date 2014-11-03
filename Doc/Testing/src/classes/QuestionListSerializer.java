package classes;

import java.lang.reflect.Type;
import java.util.ArrayList;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class QuestionListSerializer implements JsonSerializer<QuestionList>{

	public JsonElement serialize(QuestionList questionList, Type arg1,
			JsonSerializationContext context) {
		
		JsonArray jsonArray= new JsonArray();
		ArrayList <Question> questions= questionList.getQuestionList();
		for (int i=0; i<questions.size();i++) {
			Question question= questions.get(i);
			final GsonBuilder gsonBuilder2 = new GsonBuilder();
		    gsonBuilder2.registerTypeAdapter(Question.class, new QuestionSerializing());
		    final Gson gson2 = gsonBuilder2.create();
		    final JsonElement json2=gson2.toJsonTree(question);
			jsonArray.add(json2);
		}
		return jsonArray;
	}

}
 