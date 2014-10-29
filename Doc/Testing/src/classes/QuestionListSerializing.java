package classes;

import java.io.IOException;



import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

public class QuestionListSerializing extends TypeAdapter<QuestionList> {

	@Override
	public QuestionList read(JsonReader arg0) throws IOException {
	    throw new UnsupportedOperationException("Coming soon");		

	}

	@Override
	public void write(JsonWriter output, QuestionList questionList) throws IOException {
	    output.beginObject();
	    for (Question question: questionList.getQuestionList()) {
		    GsonBuilder gsonBuilder = new GsonBuilder();
		    gsonBuilder.registerTypeAdapter(Question.class, new QuestionSerializing());
		    Gson gson = gsonBuilder.create();
		    String question_name= question.getQuestion();
		    final String jsonQuestion= gson.toJson(question);
		   // JsonPrimitive element= new JsonPrimitive(jsonQuestion);
		    output.name(question_name);
		    output.value(jsonQuestion);
	    }
	    output.name("online");
	    output.value(questionList.getOnline());
	    output.endObject();
		
	}

}
