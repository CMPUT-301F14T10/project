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
		
		//
		
	  }
}
