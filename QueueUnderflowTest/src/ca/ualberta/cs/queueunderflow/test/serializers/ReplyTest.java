package ca.ualberta.cs.queueunderflow.test.serializers;



import ca.ualberta.cs.queueunderflow.models.Reply;
import ca.ualberta.cs.queueunderflow.serializers.ReplyDeserializer;
import ca.ualberta.cs.queueunderflow.serializers.ReplySerializer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import junit.framework.TestCase;

public class ReplyTest extends TestCase {

	public void testReplies() {
		//Serialize and deserialize a reply to see if it is the same 
		
		GsonBuilder gsonBuilder3= new GsonBuilder();
		Gson gson3= gsonBuilder3.create();
		final Reply reply3 = new Reply("This is a reply","Anonymous");
		String json3= gson3.toJson(reply3);
		
		System.out.println(json3);
	    final GsonBuilder gsonBuilder = new GsonBuilder();
	    gsonBuilder.registerTypeAdapter(Reply.class, new ReplySerializer());
	    
	    final Gson gson = gsonBuilder.create();

	    final Reply reply = new Reply("This is a reply","Anonymous");
	    

	    // Format to JSON
	    final String json = gson.toJson(reply);
	    
	    gsonBuilder.registerTypeAdapter(Reply.class, new ReplyDeserializer());
	    Reply deserialized= gson.fromJson(json,Reply.class);
	    assertTrue("Reply content before and after are the same", reply.getReply().equals(deserialized.getReply()));
	    assertTrue("Reply content before and after are the same", reply.getAuthor().equals(deserialized.getAuthor()));

	}
}
