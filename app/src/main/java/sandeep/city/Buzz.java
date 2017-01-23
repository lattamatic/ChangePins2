package sandeep.city;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import sandeep.city.Views.ImageIcon;

/**
 * Created by sandeep on 15/9/15.
 */
public class Buzz extends Fragment {

    ListView buzz;
    private ArrayList<String> list;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.buzz, container, false);
        list = new ArrayList<String>();
        list.add("Welcome to ChangePins. Report a problem or create a group!");
        list.add("A new group is formed in you locality");
        list.add("A user has requested to  your group");
        list.add("A group in your locality has finished their task");
        buzz = (ListView) v.findViewById(R.id.lvBuzz);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,list);
        buzz.setAdapter(adapter);
        return v;
    }
}
