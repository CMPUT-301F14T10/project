package ca.ualberta.cs.queueunderflow.test.serializers;


import junit.framework.TestCase;
import ca.ualberta.cs.queueunderflow.models.Reply;
import ca.ualberta.cs.queueunderflow.serializers.ReplyDeserializer;
import ca.ualberta.cs.queueunderflow.serializers.ReplySerializer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ReplyTest extends TestCase{
	
	public void testReplies() {
	GsonBuilder gsonBuilder3= new GsonBuilder();
	Gson gson3= gsonBuilder3.create();
	final Reply reply3 = new Reply("This is a reply","Anonymous");
	String json3= gson3.toJson(reply3);
	
    GsonBuilder gsonBuilder = new GsonBuilder().registerTypeAdapter(Reply.class, new ReplySerializer());
    Gson gson = gsonBuilder.create();

    final Reply reply = new Reply("This is a reply","Anonymous");

    // Format to JSON
    String json = gson.toJson(reply);
    
    gsonBuilder = new GsonBuilder().registerTypeAdapter(Reply.class, new ReplyDeserializer());
    gson = gsonBuilder.create();

    //gsonBuilder.registerTypeAdapter(Reply.class, new ReplyDeserializing());
    Reply deserialized= gson.fromJson(json,Reply.class);
   // System.out.println(deserialized.equals(reply));
    //System.out.println(reply.getReply()+" "+reply.getAuthor());
    //System.out.println(deserialized.getReply()+" "+deserialized.getAuthor());
    boolean result = deserialized.equals(reply);
    assertTrue("Serialized and deserialized object are the same", result);
	}
}
