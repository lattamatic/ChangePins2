package sandeep.city.Fragment;

import android.app.Dialog;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import sandeep.city.Adapter.PlaceRecyclerViewAdapter;
import sandeep.city.POJO.SinglePlace;
import sandeep.city.R;

/**
 * Created by sandeep_chi on 2/6/2017.
 */

public class FragmentMyPlaces extends Fragment {

    Dialog d;
    RecyclerView placesRecycler;
    ArrayList<SinglePlace> placesList;
    RecyclerView.LayoutManager layoutManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.frag_myplaces, container, false);

        placesRecycler = (RecyclerView) v.findViewById(R.id.rvPlaces);
        layoutManager = new LinearLayoutManager(getActivity());
        placesRecycler.setLayoutManager(layoutManager);
        placesList = new ArrayList<SinglePlace>();
        placesRecycler.setAdapter(new PlaceRecyclerViewAdapter(placesList));



        FloatingActionButton fab = (FloatingActionButton) v.findViewById(R.id.fabAddPlace);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });

        return v;
    }

    private void showDialog(){
        d= new Dialog(getActivity());
        d.setContentView(R.layout.dialog_location_name);
        d.setTitle("Location name:");

        final EditText editText = (EditText) d.findViewById(R.id.etCat);
        Button dialogButton = (Button) d.findViewById(R.id.bSetCat);
        // if button is clicked, close the custom dialog
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String gen = "";
                String string=editText.getText().toString();
                if (string.equals(gen)) {
                    d.dismiss();
                    Toast.makeText(getActivity(), "Location title cannot be empty",
                            Toast.LENGTH_SHORT).show();
                } else {
                    //dh.place_insert(string.toUpperCase(),0,0,"Click icon to set location");
                }
                d.dismiss();

            }
        });

        d.show();
    }
}
