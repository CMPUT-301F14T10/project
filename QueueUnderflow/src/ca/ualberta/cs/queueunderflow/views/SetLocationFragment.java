package ca.ualberta.cs.queueunderflow.views;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import ca.ualberta.cs.queueunderflow.R;

public class SetLocationFragment extends Fragment
{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getActivity().getActionBar().setTitle("Home");
        return inflater.inflate(R.layout.activity_set_location_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        // Inflate the menu; this adds items to the action bar if it is present.
    }

}
