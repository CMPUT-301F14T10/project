package classes;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

public class AnswerListDeserialization implements JsonDeserializer<AnswerList> {

	public AnswerList deserialize(JsonElement jsonAnswerList, Type arg1,
			JsonDeserializationContext context) throws JsonParseException {
	   // JsonParser parser = new JsonParser();
		JsonArray jsonArray= jsonAnswerList.getAsJsonArray();
		AnswerList deserialized= new AnswerList();
		
		Type listType = new TypeToken<ArrayList<Answer>>() {}.getType();
	    ArrayList<Answer> replyList= new Gson().fromJson(jsonArray, listType);
	    deserialized.setAnswerList(replyList);
		return deserialized;
		/*
		JsonObject jsonObject= jsonAnswerList.getAsJsonObject();
		AnswerList deserialized= new AnswerList();

		Set<Map.Entry<String,JsonElement>> mySet = jsonObject.entrySet();
		//System.out.println("mySet as 'key : value'");
		for(Map.Entry<String,JsonElement> singleItem : mySet)
		{
		    //System.out.println(singleItem.getKey() + " : " + singleItem.getValue());
			JsonObject jsonAnswer= (JsonObject) singleItem.getValue();
			GsonBuilder gsonBuilder= new GsonBuilder();
			gsonBuilder.registerTypeAdapter(Answer.class, new AnswerDeserializing());
			Gson gson= gsonBuilder.create();
			Answer answer= gson.fromJson(jsonAnswer,Answer.class);
			deserialized.add(answer);
		}
		return deserialized;
		*/
		//Read more: http://www.physicsforums.com

		/*
		for (Entry<String, JsonElement> entry : jsonAList.entrySet()) {
	        String key = entry.getKey();
	        JsonElement value = entry.getValue();
		   // final JsonElement jsonContent = jsonAList.get(key);
			GsonBuilder gsonBuilder= new GsonBuilder();
			gsonBuilder.registerTypeAdapter(Answer.class, new AnswerDeserializing());
			Gson gson= gsonBuilder.create();
			Answer answer= gson.fromJson(value,Answer.class);
			deserialized.add(answer);
		}
		*/
		
		
		
		
/*		Set<Map.Entry<String,JsonElement>> entrySet= jsonAList.entrySet();
		for(Map.Entry<String,JsonElement> entry:entrySet) {
			GsonBuilder gsonBuilder= new GsonBuilder();
			gsonBuilder.registerTypeAdapter(Answer.class, new AnswerDeserializing());
			Gson gson= gsonBuilder.create();
			Answer answer= gson.fromJson(entry.getValue(),Answer.class);
			deserialized.add(answer);
		}*/
		
		//Type listType = new TypeToken<ArrayList<Answer>>() {}.getType();
	    //ArrayList<Answer> deserialized= new Gson().fromJson(jsonAnswerList.get("answerReplies"), listType);
		//return deserialized;
	}
	

}
