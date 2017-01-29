package sandeep.city.Activity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import sandeep.city.ChangePinsActivity;
import sandeep.city.R;

/**
 * Created by sandeep on 15/9/15.
 */
public class ActivityBuzz extends ChangePinsActivity {

    ListView buzz;
    private ArrayList<String> list;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        list = new ArrayList<String>();
        list.add("Welcome to ChangePins. Report a problem or create a group!");
        list.add("A new group is formed in you locality");
        list.add("A user has requested to  your group");
        list.add("A group in your locality has finished their task");
        buzz = (ListView) findViewById(R.id.lvBuzz);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list);
        buzz.setAdapter(adapter);
    }

    @Override
    protected int getTitleText() {
        return R.string.buzz;
    }

    @Override
    protected int getLayout() {
        return R.layout.ac_buzz;
    }


}
