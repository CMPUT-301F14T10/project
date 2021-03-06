package classes;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;



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


public class AnswerDeserializing implements JsonDeserializer<Answer>
{

	public Answer deserialize(JsonElement jsonAnswer, Type arg1,
			JsonDeserializationContext arg2) throws JsonParseException
	{
final JsonObject answer= jsonAnswer.getAsJsonObject();
		
		
	    final JsonElement jsonContent = answer.get("answerName");
	    final String content = jsonContent.getAsString();
	    
	    final GsonBuilder gsonBuilder = new GsonBuilder();
	    final Gson gson = gsonBuilder.create();
	    
	    //JsonElement jsonArray= answer.get("answerReplies");
	   // JsonArray old_array = jsonArray.getAsJsonArray();
	    
	    
/*	    ArrayList<Reply> replyList= new ArrayList <Reply>();
	    
	    
	    for (int i=0; i<old_array.size(); i++) {
	       // JsonElement serial_reply=old_array.get(i);
	        JsonElement serial_reply= old_array.get(i);
		    gsonBuilder.registerTypeAdapter(Reply.class, new ReplyDeserializing());
		    Gson gson2 = gsonBuilder.create();
		    String s= serial_reply.toString();
		    Reply deserialized= gson2.fromJson(s,Reply.class);
		    replyList.add(deserialized);

	    }*/
	    
	    
/*	    Type listType = new TypeToken<ArrayList<Reply>>() {}.getType();
	    ArrayList<Reply> replyList= new Gson().fromJson(answer.get("answerReplies"), listType);*/
	    
	   // ArrayList<Reply> replyList= gson.fromJson(old_array,ArrayList.class);
	    
	    final String author= answer.get("author").getAsString();
	    final int upvote= answer.get("upvote").getAsInt();
	    final boolean hasPicture= answer.get("hasPicture").getAsBoolean();
	    
	    final int picture= answer.get("picture").getAsInt();
	    Picture new_picture=new Picture(picture);
	    
		Type listType = new TypeToken<ArrayList<Reply>>() {}.getType();
	    ArrayList<Reply> replyList= new Gson().fromJson(answer.get("answerReplies"), listType);
	    //String date = "\"2013-02-10T13:45:30+0100\"";
	    //String date_string= answer.get("date").getAsString();
	    //JsonElement jsonDate= answer.get("date");
	   // SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
	    
	   /* Date date= new Date();
		try {
			date = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH).parse(date_string);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	    
	    //gsonBuilder.registerTypeAdapter(Date.class, new DateDeserializing());
	    //gsonBuilder.setDateFormat("yy yy-MM-dd'T'HH:mm:ssZ");
	   // Date answer_date= gson.fromJson(date_string, Date.class);
	    
	    //String testdate = "\"2013-02-10T13:45:30+0100\"";
	    
	    Gson gson2=gsonBuilder.create();
	   
	    String date= answer.get("date").getAsString();
		SimpleDateFormat formatter = new SimpleDateFormat("M/d/yy hh:mm a");
		Date converted= new Date();
		try {
			converted = formatter.parse(date);
			//System.out.println(date);
			//System.out.println(formatter.format(date));
	 
		} catch (ParseException e) {
			e.printStackTrace();
		}
	    //Date answer_date= gson.fromJson(date_string,Date.class);
	   // DateFormat df = new SimpleDateFormat("M/d/yy hh:mm a");
		//String deserial_date= df.format(deserial)
		/*
		JsonElement jsonElement= answer.get("answerReplies");
		for (int i=0; i<jsonElement.)
		*/
	   // Type listType = new TypeToken<ArrayList<Reply>>() {}.getType();
	   // ArrayList<Reply> replyList= new Gson().fromJson(old_array, listType);
	    


/*		ArrayList <Reply> replyList= new ArrayList<Reply>();

		gsonBuilder.registerTypeAdapter(Reply.class,new ReplyDeserializing());
	    JsonParser parser = new JsonParser();
	    JsonArray array = (JsonArray) parser.parse(answer.get("answerReplies").getAsString());
		for (int i=0; i<array.size();i++) {
			String s= array.get(i).getAsString();
			Reply reply= gson.fromJson(s, Reply.class);
			replyList.add(reply);
		}*/
		
		//Type listOfTestObject = new TypeToken<ArrayList<Reply>>(){}.getType();
/*		ArrayList <Reply> replyList= new ArrayList<Reply>();
		gsonBuilder.registerTypeAdapter(Reply.class,new ReplyDeserializing());
		JsonArray deserialize= answer.get("answerReplies").getAsJsonArray();
		for (int i=0; i<deserialize.size();i++) {
			String s= deserialize.get(i).getAsString();
			Reply reply= gson.fromJson(s, Reply.class);
			replyList.add(reply);
		}*/
		//System.out.println(deserialize);
		//ArrayList<Reply> replyList = gson2.fromJson(deserialize,listOfTestObject);
		
		//ArrayList <Reply> replyList= new ArrayList<Reply>();
		//Reply reply= new Reply("dadf", "adfa");
		//replyList.add(reply);
		
	    
	    Answer deserialized_answer= new Answer(content, author);
	    deserialized_answer.setReplyArray(replyList);
	    deserialized_answer.setUpvotes(upvote);
	    deserialized_answer.setPicture(new_picture);
	    deserialized_answer.setDate(converted);
	    deserialized_answer.sethasPicture(hasPicture);
	   // deserialized_answer.setTest(deserialize);
	    return deserialized_answer;
	}

}
