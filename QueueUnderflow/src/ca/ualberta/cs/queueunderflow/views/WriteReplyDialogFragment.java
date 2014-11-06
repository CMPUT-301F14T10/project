package ca.ualberta.cs.queueunderflow.views;

import ca.ualberta.cs.queueunderflow.ListHandler;
import ca.ualberta.cs.queueunderflow.R;
import ca.ualberta.cs.queueunderflow.User;
import ca.ualberta.cs.queueunderflow.R.id;
import ca.ualberta.cs.queueunderflow.R.layout;
import ca.ualberta.cs.queueunderflow.R.string;
import ca.ualberta.cs.queueunderflow.controllers.WriteReplyController;
import ca.ualberta.cs.queueunderflow.models.Answer;
import ca.ualberta.cs.queueunderflow.models.Question;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

// TODO: Auto-generated Javadoc
/**
 * The Class WriteReplyDialogFragment.
 */
public class WriteReplyDialogFragment extends DialogFragment {
	
    /* (non-Javadoc)
     * @see android.app.DialogFragment#onCreateDialog(android.os.Bundle)
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        
        final View view = inflater.inflate(R.layout.write_reply_dialog_fragment, null);
        builder.setView(view);
       
        TextView authorText= (TextView) view.findViewById(R.id.authorText);
        authorText.setText(User.getUserName());
        
        builder.setPositiveButton("Reply", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				WriteReplyController controller = new WriteReplyController(getActivity(), view);
				controller.addReply(getArguments());
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

}
