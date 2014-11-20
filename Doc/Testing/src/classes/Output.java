package classes;

import java.io.IOException;

import model_classes.Answer;
import model_classes.AnswerList;
import model_classes.Question;
import model_classes.QuestionList;
import model_classes.Reply;




import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Output {
	public static void main(final String[] args) throws IOException {
		
		/* Reply serialization testing
		
		
	    // Configure GSON
	    final GsonBuilder gsonBuilder = new GsonBuilder();
	    //gsonBuilder.registerTypeAdapter(Reply.class, new ReplySerializing());
	    
	    //This makes printing nicer
	    //gsonBuilder.setPrettyPrinting();
	    final Gson gson = gsonBuilder.create();

	    final Reply reply = new Reply("This is a reply","Anonymous");
	    

	    // Format to JSON
	    final String json = gson.toJson(reply);
	    System.out.println(json);
	    
	    */
		
		/* Answer serialization testing
		 
		 
		String answerName= "An answer";
		String authorName= "You";
		Answer testAnswer= new Answer(answerName,authorName);
		Reply answer_reply= new Reply("Please clarify what you're asking", "Anonymous");
		testAnswer.addReply(answer_reply);

		final GsonBuilder gsonBuilder2 = new GsonBuilder();
	    gsonBuilder2.registerTypeAdapter(Answer.class, new AnswerSerializing());
	    gsonBuilder2.setPrettyPrinting();
	    final Gson gson2 = gsonBuilder2.create();
	    final String json2=gson2.toJson(testAnswer);
	    System.out.println(json2);
		
		*/
		
		/*AnswerList serialization test
		
		String answerName= "An answer";
		String authorName= "You";
		Answer testAnswer= new Answer(answerName,authorName);
		AnswerList answerList= new AnswerList();
		answerList.add(testAnswer);
		
		final GsonBuilder gsonBuilder2 = new GsonBuilder();
	    gsonBuilder2.registerTypeAdapter(AnswerList.class, new AnswerListSerializing());
	    gsonBuilder2.setPrettyPrinting();
	    final Gson gson2 = gsonBuilder2.create();
	    final String json2=gson2.toJson(answerList);
	    System.out.println(json2);
		*/
		
		
		/*Question serialization test
		
		String questionName= "A question?";
		Question questionTest= new Question(questionName,"Anonymous");
		Reply question_reply= new Reply("Whats going on","Unknown");
		questionTest.addReply(question_reply);
		questionTest.setUpvotes(2);
		
		String answerName= "An answer";
		Answer testAnswer= new Answer(answerName,"dfaf");
		Reply answer_reply= new Reply("dafasf","ABCDEFG");
		testAnswer.addReply(answer_reply);
		questionTest.addAnswer(testAnswer);
		
		final GsonBuilder gsonBuilder2 = new GsonBuilder();
	    gsonBuilder2.registerTypeAdapter(Question.class, new QuestionSerializing());
	    gsonBuilder2.setPrettyPrinting();
	    final Gson gson2 = gsonBuilder2.create();
	    final String json2=gson2.toJson(questionTest);
	    System.out.println(json2);
		*/
		
		
		//QuestionList serializing
		
		String questionName= "A question?";
		String author="A author";
		Question questionTest= new Question(questionName,author);
	
		//Adding replies to question
		String reply_author= "I dunno";
		Reply question_reply= new Reply("Whats going on",reply_author);
	
		questionTest.addReply(question_reply);
		questionTest.setUpvotes(2);

		QuestionList questionList= new QuestionList();
		
		int question_index= questionList.questionIndex(questionTest);
		
		//Adding an answer
		String answerName= "An answer";
		String answer_author= "Me";
		String answer_reply_author= "7-11";
		Answer testAnswer= new Answer(answerName,answer_author);
		
		//Add replies to answer
		Reply answer_reply= new Reply("Go to stackoverflow",answer_reply_author);
		testAnswer.addReply(answer_reply);
		
		questionTest.addAnswer(testAnswer);
		
		questionList.add(questionTest);
		
		// Supposedly faster serialization for question list and have the question as the index
		final GsonBuilder gsonBuilder2 = new GsonBuilder();
	    gsonBuilder2.registerTypeAdapter(QuestionList.class, new QuestionListSerializer());
	    gsonBuilder2.setPrettyPrinting();
	    final Gson gson2 = gsonBuilder2.create();
	    final String json2=gson2.toJson(questionList);
	    System.out.println(json2);
	    
		
		/* Slower serialization for question list, don't have question as the index"
		final GsonBuilder gsonBuilder2 = new GsonBuilder();
	    gsonBuilder2.registerTypeAdapter(QuestionList.class, new SlowQuestionListSerializer());
	    gsonBuilder2.setPrettyPrinting();
	    final Gson gson2 = gsonBuilder2.create();
	    final String json2=gson2.toJson(questionList);
	    System.out.println(json2);
		*/
	  }
	
	
	
	
	
	
/*	[
	  {
	    "questionName": "A question?",
	    "questionReplies": [
	      {
	        "reply": "Whats going on",
	        "author": "I dunno"
	      }
	    ],
	    "author": "A author",
	    "upvote": 2,
	    "hasPicture": false,
	    "date": "Nov 13, 2014 8:17:47 PM",
	    "isFav": false,
	    "isInReadingList": false,
	    "uniqueID": "1b7a4b05-9ec6-4323-9b76-252c12b138c3",
	    "answerList": {
	      "answerList": [
	        {
	          "name": "An answer",
	          "replies": [
	            {
	              "reply": "Go to stackoverflow",
	              "author": "7-11"
	            }
	          ],
	          "author": "Me",
	          "upvote": 0,
	          "hasPicture": false,
	          "date": "Nov 13, 2014 8:17:47 PM",
	          "isFav": false,
	          "isInReadingList": false,
	          "uniqueID": "53e7dccb-7497-40b7-abaf-040bf57234ca"
	        }
	      ]
	    }
	  }
	];
	
	
	
	[
	  {
	    "answerList": {
	      "answerList": [
	        {
	          "name": "An answer",
	          "replies": [
	            {
	              "reply": "Go to stackoverflow",
	              "author": "7-11"
	            }
	          ],
	          "author": "Me",
	          "upvote": 0,
	          "hasPicture": false,
	          "date": "Nov 13, 2014 8:19:17 PM",
	          "isFav": false,
	          "isInReadingList": false,
	          "uniqueID": "8b62ebc9-247e-44f7-ae07-7aafd4265855"
	        }
	      ]
	    },
	    "name": "A question?",
	    "replies": [
	      {
	        "reply": "Whats going on",
	        "author": "I dunno"
	      }
	    ],
	    "author": "A author",
	    "upvote": 2,
	    "hasPicture": false,
	    "date": "Nov 13, 2014 8:19:17 PM",
	    "isFav": false,
	    "isInReadingList": false,
	    "uniqueID": "81a13825-22b8-4158-9634-86b471446a6d"
	  }
	]*/
	
			
			
			
			
			
	
	
}
