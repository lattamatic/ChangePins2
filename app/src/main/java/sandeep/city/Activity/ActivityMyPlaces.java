package sandeep.city.Activity;

import java.util.ArrayList;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import sandeep.city.ChangePinsActivity;
import sandeep.city.DataHelp;
import sandeep.city.POJO.SinglePlace;
import sandeep.city.R;

public class ActivityMyPlaces extends ChangePinsActivity{

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
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dh = new DataHelp(this);
        layout = (LinearLayout) findViewById(R.id.myPlaceLinlayout);
        display();
        add = (TextView) findViewById(R.id.tvAddLocation);
        addLoc= (ImageView) findViewById(R.id.ivAddLocation);
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
        d= new Dialog(this);
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
                    Toast.makeText(ActivityMyPlaces.this, "Location title cannot be empty",
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

    @Override
    protected int getTitleText() {
        return R.string.myplaces;
    }

    @Override
    protected int getLayout() {
        return R.layout.ac_myplaces;
    }
}