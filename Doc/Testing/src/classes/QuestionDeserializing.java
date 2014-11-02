package classes;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

public class QuestionDeserializing implements JsonDeserializer<Question> {

	public Question deserialize(JsonElement jsonObject, Type arg1,
			JsonDeserializationContext arg2) throws JsonParseException {
		
		final JsonObject jsonQuestion= jsonObject.getAsJsonObject();
		final JsonElement jsonContent= jsonQuestion.get("questionName");
	    final String question_content = jsonContent.getAsString();
	    
	    final String author= jsonQuestion.get("author").getAsString();
	    final int upvote= jsonQuestion.get("upvote").getAsInt();
	  //  final boolean hasPicture= jsonQuestion.get("hasPicture").getAsBoolean();
	    final boolean inReadingList= jsonQuestion.get("IsInReadingList").getAsBoolean();
	    final boolean isFavorite= jsonQuestion.get("isFav").getAsBoolean();
	    
	    final int picture= jsonQuestion.get("picture").getAsInt();
	    Picture new_picture=new Picture(picture);
	    
		Type listType = new TypeToken<ArrayList<Reply>>() {}.getType();
	    ArrayList<Reply> replyList= new Gson().fromJson(jsonQuestion.get("questionReplies"), listType);
	    
	    final GsonBuilder gsonBuilder = new GsonBuilder();
	   // final Gson gson = gsonBuilder.create();
	    
	    Gson gson2=gsonBuilder.create();
		   
	    String date= jsonQuestion.get("date").getAsString();
		SimpleDateFormat formatter = new SimpleDateFormat("M/d/yy hh:mm a");
		Date converted= new Date();
		try {
			converted = formatter.parse(date);
			//System.out.println(date);
			//System.out.println(formatter.format(date));
	 
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		/*
	    Type listOfTestObject = new TypeToken<ArrayList<Answer>>(){}.getType();
		ArrayList <Answer> answerList2= gson2.fromJson(jsonQuestion.get("answerList"),listOfTestObject);
		AnswerList deserialized2= new AnswerList();
		deserialized2.setAnswerList(answerList2);
		*/
		
	
	    
		gsonBuilder.registerTypeAdapter(AnswerList.class, new AnswerListDeserialization());
		AnswerList deserialized2= gson2.fromJson(jsonQuestion.get("answerList"), AnswerList.class);
		
		Question deserialized= new Question("Name","Author");
		deserialized.setQuestion(question_content);
		deserialized.setAuthor(author);
		deserialized.setReplyList(replyList);
		deserialized.setDate(converted);
		deserialized.setUpvotes(upvote);
		deserialized.setPicture(new_picture);
		deserialized.setIsFav(isFavorite);
		deserialized.setIsInReadingList(inReadingList);
		deserialized.setAnswerList(deserialized2);
		
		return deserialized;
	}

}
