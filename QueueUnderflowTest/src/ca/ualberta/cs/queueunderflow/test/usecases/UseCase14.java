package ca.ualberta.cs.queueunderflow.test.usecases;

import java.lang.reflect.Type;

import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import ca.ualberta.cs.queueunderflow.R;
import ca.ualberta.cs.queueunderflow.adapters.QuestionListAdapter;
import ca.ualberta.cs.queueunderflow.controllers.AskAnswerController;
import ca.ualberta.cs.queueunderflow.models.Answer;
import ca.ualberta.cs.queueunderflow.models.Picture;
import ca.ualberta.cs.queueunderflow.models.Question;
import ca.ualberta.cs.queueunderflow.models.QuestionList;
import ca.ualberta.cs.queueunderflow.models.Reply;
import ca.ualberta.cs.queueunderflow.serializers.QuestionListDeserializer;
import ca.ualberta.cs.queueunderflow.serializers.QuestionListSerializer;
import ca.ualberta.cs.queueunderflow.singletons.ListHandler;
import ca.ualberta.cs.queueunderflow.singletons.User;
import ca.ualberta.cs.queueunderflow.views.MainActivity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class UseCase14 extends ActivityInstrumentationTestCase2<MainActivity> {
        
        public UseCase14() {
                super(MainActivity.class);
        }

        public void testQuestionsAsked() {
                
                /* Old test for project part 2
                User me= new User();
                me.setUserName("Me");
                
                QuestionList questionList= new QuestionList();
                String questionName= "How does this work?";

                Question questionTest= new Question(questionName,me.getUserName());
                Picture pic= new Picture(32);
                questionTest.setPicture(pic);
                
                questionList.add(questionTest);
                assertTrue("Question List isn't empty", questionList.size()==1);
                */
                
            //QuestionList deserialization tests
                String questionName= "A question?";
                String author="A author";
                Question questionTest= new Question(questionName,author);
        
                //Adding replies to question
                String reply_author= "I dunno";
                Reply question_reply= new Reply("Whats going on",reply_author);
        
                questionTest.addReply(question_reply);
                questionTest.setUpvotes(2);

                QuestionList questionList= new QuestionList();
                
                //int question_index= questionList.questionIndex(questionTest);
                
                //Adding an answer
                String answerName= "An answer";
                String answer_author= "Me";
                String answer_reply_author= "7-11";
                Answer testAnswer= new Answer(answerName,answer_author);
                
                //Add replies to answer
                Reply answer_reply= new Reply("Go to stackoverflow",answer_reply_author);
                testAnswer.addReply(answer_reply);
                
                Picture pic= new Picture(10);
                testAnswer.setPicture(pic);
                questionTest.addAnswer(testAnswer);
                questionTest.setPicture(pic);
                
                questionList.add(questionTest);
                assertTrue("Question List isn't empty", questionList.size()==1);
                
                GsonBuilder gsonBuilder2 = new GsonBuilder();
            gsonBuilder2.registerTypeAdapter(QuestionList.class, new QuestionListSerializer());
            Gson gson2 = gsonBuilder2.create();
            final String json2=gson2.toJson(questionList);
            
            Type listOfTestObject = new TypeToken<QuestionList>() {}.getType();
            
            gsonBuilder2.registerTypeAdapter(QuestionList.class, new QuestionListDeserializer());
            gson2 = gsonBuilder2.create();
                QuestionList deserialized2= gson2.fromJson(json2,listOfTestObject);
                assertTrue("Deserialized list isn't empty", deserialized2.size()==1);

                
                Question before= questionList.get(0);
                Question after= deserialized2.get(0);

                String before_answer= before.getAnswerList().getAnswer(0).getName();
                String after_answer= after.getAnswerList().getAnswer(0).getName();
                String before_reply=before.getReplies().get(0).getReply();
                String after_reply= after.getReplies().get(0).getReply();
                
                assertTrue("Both questions are the same",before.getName().equals(after.getName()));
                assertTrue("Both answers are the same",before_answer.equals(after_answer));
                assertTrue("Both replies are the same", before_reply.equals(after_reply));
                
        }
        
        
}
