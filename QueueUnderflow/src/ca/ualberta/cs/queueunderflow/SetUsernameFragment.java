package ca.ualberta.cs.queueunderflow;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SetUsernameFragment extends Fragment {
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		getActivity().getActionBar().setTitle("Home");
		return inflater.inflate(R.layout.fragment_set_username, container, false);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		
		final TextView currentUsername = (TextView) view.findViewById(R.id.currentUsername);
		final EditText newUsername = (EditText) view.findViewById(R.id.newUsername);
		Button submitBtn = (Button) view.findViewById(R.id.submitBtn);
		
		/* Need to put some of this in a controller later */
		currentUsername.setText(User.getUserName());
		submitBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String username = newUsername.getText().toString();
				User user = new User();
				
				int flag = 0;
				try {
					user.setUserName(username);
				} catch (IllegalArgumentException e){
					currentUsername.setText(User.getUserName());
					Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
					flag = 1;
				}
				
				if (flag == 0){
					currentUsername.setText(User.getUserName());
					Toast.makeText(getActivity(), "Username successfully set to " + user.getUserName(), Toast.LENGTH_SHORT).show();
				}
			}
		});
		//
	}

}