package sandeep.city.Fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import sandeep.city.R;

/**
 * Created by sandeep_chi on 2/6/2017.
 */

public class FragmentBuzz extends Fragment {

    ListView buzz;
    private ArrayList<String> list;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frag_buzz,container,false);
        list = new ArrayList<String>();

        //Adding static content as notifications
        list.add("Welcome to ChangePins. SingleReport a problem or create a group!");
        list.add("A new group is formed in you locality");
        list.add("A user has requested to  your group");
        list.add("A group in your locality has finished their task");

        buzz = (ListView) v.findViewById(R.id.lvBuzz);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,list);
        buzz.setAdapter(adapter);
        return v;
    }
}