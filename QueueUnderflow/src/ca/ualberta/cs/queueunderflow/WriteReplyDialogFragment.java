package ca.ualberta.cs.queueunderflow;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

public class WriteReplyDialogFragment extends DialogFragment {

	public static final int HOME_SCREEN_FRAGMENT = 1;
	public static final int FAVORITES_FRAGMENT = 2;
	public static final int READING_LIST_FRAGMENT = 3;
	public static final int MY_QUESTIONS_FRAGMENT = 4;
	
	private static int TYPE_QUESTION = 0;
	private static int TYPE_ANSWER = 1;
	
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        
        final View view = inflater.inflate(R.layout.write_reply_dialog_fragment, null);
        builder.setView(view);
       
        
        // Retrieving info
        final int fromFragment = getArguments().getInt("fromFragment");
        final int questionPosition = getArguments().getInt("questionPosition");
        final int type = getArguments().getInt("type");
        //System.out.println(fromFragment + " | " + questionPosition + " | " + type);

        TextView authorText= (TextView) view.findViewById(R.id.authorText);
        authorText.setText(User.getUserName());
        
        builder.setPositiveButton("Reply", new OnClickListener() {
             
            @Override
            public void onClick(DialogInterface dialog, int which) {
                
                if (type == TYPE_QUESTION) {
                    Question question = findQuestion(fromFragment, questionPosition);
                    WriteReplyController controller = new WriteReplyController(getActivity(), view, question);
                    controller.addReply(TYPE_QUESTION);
                }
                else if (type == TYPE_ANSWER) {
                	int answerPosition = getArguments().getInt("answerPosition");
                    Answer answer = findQuestion(fromFragment, questionPosition).getAnswerList().getAnswer(answerPosition);
                    WriteReplyController controller = new WriteReplyController(getActivity(), view, answer);
                    controller.addReply(TYPE_ANSWER);
                }
 
            }
        });
         
        builder.setNegativeButton(R.string.cancel, new OnClickListener() {
             
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                 
            }
        });
         
        return builder.create();
    }
    
    private Question findQuestion(int fromFragment, int questionPosition) {
		switch (fromFragment) {
		case (HOME_SCREEN_FRAGMENT):
			return ListHandler.getMasterQList().get(questionPosition);
		case (FAVORITES_FRAGMENT):
			return ListHandler.getFavsList().get(questionPosition);
		case (READING_LIST_FRAGMENT):
			return ListHandler.getReadingList().get(questionPosition);
		case (MY_QUESTIONS_FRAGMENT):
			return ListHandler.getMyQsList().get(questionPosition);
		}
		
		// Just to satisfy Java
		return new Question("error", User.getUserName());
    }
	
}
