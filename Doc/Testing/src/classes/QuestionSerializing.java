package classes;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class QuestionSerializing implements JsonSerializer<Question> {

	public JsonElement serialize(Question question, Type arg1,
			JsonSerializationContext serialization_context) {
		
		JsonObject jsonQuestion= new JsonObject();
		jsonQuestion.addProperty("questionName",question.getQuestion());
		
		
		
		/*
	    GsonBuilder gsonBuilder = new GsonBuilder();
	    gsonBuilder.registerTypeAdapter(AnswerList.class, new AnswerListSerializing());
	    Gson gson = gsonBuilder.create();
	    String jsonAnswerList= gson.toJson(question.getAnswerList());
	    //JsonPrimitive element= new JsonPrimitive(jsonAnswerList);
	    jsonQuestion.addProperty("answerList", jsonAnswerList);
	    */
		
		/*
		final GsonBuilder gsonBuilder2 = new GsonBuilder();
	    gsonBuilder2.registerTypeAdapter(AnswerList.class, new AnswerListSerializing());
	    final Gson gson2 = gsonBuilder2.create();
	    //final String json2=gson2.toJson(question.getAnswerList());
	    JsonElement jsonAnswerList=gson2.toJsonTree(question.getAnswerList());
	    jsonQuestion.add("answerList",jsonAnswerList);
	    */
		final GsonBuilder gsonBuilder2 = new GsonBuilder();
	    final Gson gson2 = gsonBuilder2.create();

	    /*
		JsonArray jsonArray= new JsonArray();
		ArrayList <Answer> serialize= question.getAnswerList().getAnswerList();
		for (int i=0; i<serialize.size(); i++) {
			Answer answer= serialize.get(i);
			
			final GsonBuilder gsonBuilder3 = new GsonBuilder();
		    gsonBuilder3.registerTypeAdapter(Answer.class, new AnswerSerializing());
		    final Gson gson3 = gsonBuilder3.create();
		    final JsonElement json3=gson3.toJsonTree(answer);
		    jsonArray.add(json3);
		}
		jsonQuestion.add("answerList",jsonArray);
		*/
	    JsonElement answers= serialization_context.serialize(question.getAnswerList());
		jsonQuestion.add("answerList",answers);

        JsonElement replies=serialization_context.serialize(question.getReplies());
	    jsonQuestion.add("questionReplies",replies);
	    
	    /*
		final JsonArray jsonQuestionReplies= new JsonArray();
		for (final Reply reply: question.getReplies()) {
		
		    //GsonBuilder gsonBuilder = new GsonBuilder();
		   // Gson gson = gsonBuilder.create();

		    gsonBuilder.registerTypeAdapter(Reply.class, new ReplySerializing());
		    final String jsonReply= gson.toJson(reply);
		    JsonPrimitive element= new JsonPrimitive(jsonReply);
		    jsonQuestionReplies.add(element);
		}
		*/
		//jsonQuestion.add("questionReplies",jsonQuestionReplies);
		
		jsonQuestion.addProperty("author", question.getAuthor());
		jsonQuestion.addProperty("upvote", question.getUpvotes());
		jsonQuestion.addProperty("hasPicture",question.hasPicture());

		//The picture property will have to change when picture changes
		int size=question.getPicture().getSize();
		jsonQuestion.addProperty("picture",size);
		
	    //May need a date serializer b/c date might end up being parsed wrong
	    gsonBuilder2.setDateFormat("M/d/yy hh:mm a");
	   // Gson gson = gsonBuilder.create();
	    Date question_date= question.getDate();
	    //final String date= gson.toJson(question_date);
	    JsonElement date= serialization_context.serialize(question_date);
	    jsonQuestion.add("date",date);
	    
	    jsonQuestion.addProperty("isFav",question.getIsFav());
	    jsonQuestion.addProperty("IsInReadingList",question.getIsInReadingList());
	    
	    return jsonQuestion;
	}

	
}
