package ca.ualberta.cs.queueunderflow.serializers;

import java.lang.reflect.Type;
import java.util.ArrayList;



import ca.ualberta.cs.queueunderflow.models.Answer;
import ca.ualberta.cs.queueunderflow.models.AnswerList;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

/**
 * The Class AnswerListDeserializer.
 * @author group 10
 * @version 0.5
 */
public class AnswerListDeserializer implements JsonDeserializer<AnswerList>{

	/* (non-Javadoc)
	 * @see com.google.gson.JsonDeserializer#deserialize(com.google.gson.JsonElement, java.lang.reflect.Type, com.google.gson.JsonDeserializationContext)
	 */
	@Override
	public AnswerList deserialize(JsonElement jsonAnswerList, Type arg1,
			JsonDeserializationContext context) throws JsonParseException {
		JsonArray jsonArray= jsonAnswerList.getAsJsonArray();
		AnswerList deserialized= new AnswerList();
		
		Type listType = new TypeToken<ArrayList<Answer>>() {}.getType();
	    ArrayList<Answer> replyList= new Gson().fromJson(jsonArray, listType);
	    deserialized.setAnswerList(replyList);
		return deserialized;
	}

}
