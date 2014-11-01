package classes;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

public class AnswerListDeserialization implements JsonDeserializer<AnswerList> {

	public AnswerList deserialize(JsonElement jsonAnswerList, Type arg1,
			JsonDeserializationContext arg2) throws JsonParseException {
		
		JsonObject jsonAList= jsonAnswerList.getAsJsonObject();
		AnswerList deserialized= new AnswerList();

		
		Set<Map.Entry<String,JsonElement>> entrySet= jsonAList.entrySet();
		for(Map.Entry<String,JsonElement> entry:entrySet) {
			GsonBuilder gsonBuilder= new GsonBuilder();
			gsonBuilder.registerTypeAdapter(Answer.class, new AnswerDeserializing());
			Gson gson= gsonBuilder.create();
			Answer answer= gson.fromJson(entry.getValue(),Answer.class);
			deserialized.add(answer);
		}
		
		//Type listType = new TypeToken<ArrayList<Answer>>() {}.getType();
	    //ArrayList<Answer> deserialized= new Gson().fromJson(jsonAnswerList.get("answerReplies"), listType);
		return deserialized;
	}
	

}
