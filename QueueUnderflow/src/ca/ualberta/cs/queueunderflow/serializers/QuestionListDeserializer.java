package ca.ualberta.cs.queueunderflow.serializers;

import java.lang.reflect.Type;
import java.util.ArrayList;


import ca.ualberta.cs.queueunderflow.models.Question;
import ca.ualberta.cs.queueunderflow.models.QuestionList;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

public class QuestionListDeserializer implements JsonDeserializer<QuestionList>{

	public QuestionList deserialize(JsonElement jsonQuestionList, Type arg1,
			JsonDeserializationContext context) throws JsonParseException {
		
		JsonArray jsonArray= jsonQuestionList.getAsJsonArray();
		QuestionList deserialized= new QuestionList();
		
		Type listType = new TypeToken<ArrayList<Question>>() {}.getType();
	    ArrayList<Question> replyList= new Gson().fromJson(jsonArray, listType);
	    deserialized.setQuestionList(replyList);
		return deserialized;
	}

}
