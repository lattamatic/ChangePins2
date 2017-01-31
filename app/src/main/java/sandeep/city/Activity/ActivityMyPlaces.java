package sandeep.city.Activity;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import sandeep.city.Adapter.PlaceRecyclerViewAdapter;
import sandeep.city.ChangePinsActivity;
import sandeep.city.POJO.SinglePlace;
import sandeep.city.R;

public class ActivityMyPlaces extends ChangePinsActivity{

    Dialog d;
    RecyclerView placesRecycler;
    ArrayList<SinglePlace> placesList;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        placesRecycler = (RecyclerView) findViewById(R.id.rvPlaces);
        layoutManager = new LinearLayoutManager(this);
        placesRecycler.setLayoutManager(layoutManager);
        placesList = new ArrayList<SinglePlace>();
        placesRecycler.setAdapter(new PlaceRecyclerViewAdapter(placesList));



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabAddPlace);
        fab.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });

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
                    //dh.place_insert(string.toUpperCase(),0,0,"Click icon to set location");
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