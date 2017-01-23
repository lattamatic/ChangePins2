package sandeep.city;

import java.util.ArrayList;
import java.util.Set;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MyPlaces extends Fragment{

	LinearLayout layout;
	SinglePlace place, close;
	TextView add;
    ImageView addLoc;
	SharedPreferences preferences;
	SharedPreferences.Editor editor;
	int count;
    Dialog d;
    ArrayList<SinglePlace> places;


    private DataHelp dh;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.my_places, container, false);
        dh = new DataHelp(getActivity());
		layout = (LinearLayout) v.findViewById(R.id.myPlaceLinlayout);
        display();
		add = (TextView) v.findViewById(R.id.tvAddLocation);
        addLoc= (ImageView) v.findViewById(R.id.ivAddLocation);
		add.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showDialog();
			}
		});
        addLoc.setOnClickListener(new OnClickListener(){

            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
		return v;
	}

    private void display() {
        places = dh.getPlaces();
        count =places.size();
        layout.removeAllViews();
        for (int i = 0; i < count; i++) {
            place = places.get(i);
            layout.addView(place, i);
        }
    }

    private void showDialog(){
        d= new Dialog(getActivity());
        d.setContentView(R.layout.dialog_location_name);
        d.setTitle("Location name:");

        final EditText editText = (EditText) d.findViewById(R.id.etCat);
        Button dialogButton = (Button) d.findViewById(R.id.bSetCat);
        // if button is clicked, close the custom dialog
        dialogButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String gen = "";
                String string=editText.getText().toString();
                if (string.equals(gen)) {
                    d.dismiss();
                    Toast.makeText(getActivity(), "Location title cannot be empty",
                            Toast.LENGTH_SHORT).show();
                } else {
                    dh.place_insert(string.toUpperCase(),0,0,"Click icon to set location");
                    display();
                }
                d.dismiss();

            }
        });

        d.show();
    }
}
