package classes;

import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class DeserializeTests {
	public static void main(final String[] args) throws IOException {
		
		/* Reply deserialization testing
		
		
	    // Configure GSON
	    final GsonBuilder gsonBuilder = new GsonBuilder();
	    gsonBuilder.registerTypeAdapter(Reply.class, new ReplySerializing());
	    
	    //This makes printing nicer
	    //gsonBuilder.setPrettyPrinting();
	    final Gson gson = gsonBuilder.create();

	    final Reply reply = new Reply("This is a reply","Anonymous");
	    

	    // Format to JSON
	    final String json = gson.toJson(reply);
	    System.out.println(json);
	    
	    gsonBuilder.registerTypeAdapter(Reply.class, new ReplyDeserializing());
	    Reply deserialized= gson.fromJson(json,Reply.class);
	   // System.out.println(deserialized.equals(reply));
	    System.out.println(reply.getReply()+" "+reply.getAuthor());
	    System.out.println(deserialized.getReply()+" "+deserialized.getAuthor());
		
		*/
		
		//Answer deserialization test
		
		String answerName= "An answer";
		String authorName= "You";
		Answer testAnswer= new Answer(answerName,authorName);
		Reply answer_reply= new Reply("Please clarify what you're asking", "Anonymous");
		testAnswer.addReply(answer_reply);
		Picture pic= new Picture(10);
		testAnswer.setPicture(pic);
	
		//Serialize the answer first then deserialize it and see if its the same
		final GsonBuilder gsonBuilder2 = new GsonBuilder();
	    gsonBuilder2.registerTypeAdapter(Answer.class, new AnswerSerializing());
	    gsonBuilder2.setPrettyPrinting();
	    final Gson gson2 = gsonBuilder2.create();
	    final String json2=gson2.toJson(testAnswer);
	    System.out.println(json2);
	    
	    gsonBuilder2.registerTypeAdapter(Reply.class, new AnswerDeserializing());
	    Answer deserialized= gson2.fromJson(json2,Answer.class);
	   // System.out.println(answerName);
	    System.out.println(deserialized.getAnswer());
		
	  }
}
