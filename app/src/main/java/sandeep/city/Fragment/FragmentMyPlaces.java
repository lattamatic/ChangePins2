package sandeep.city.Fragment;

import android.app.Dialog;
import android.app.Fragment;
import android.os.AsyncTask;
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
import java.util.List;

import sandeep.city.Adapter.PlaceRecyclerViewAdapter;
import sandeep.city.POJO.SinglePlace;
import sandeep.city.R;
import sandeep.city.SQLiteClasses.PlacesDataSource;

/**
 * Created by sandeep_chi on 2/6/2017.
 */

public class FragmentMyPlaces extends Fragment {

    Dialog d;
    RecyclerView placesRecycler;
    List<SinglePlace> placesList;
    RecyclerView.LayoutManager layoutManager;
    PlacesDataSource dataSource;
    PlaceRecyclerViewAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.frag_myplaces, container, false);
        placesList = new ArrayList<SinglePlace>();
        placesRecycler = (RecyclerView) v.findViewById(R.id.rvPlaces);

        new AsyncGetPlaces().execute();

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
                    AsyncAddPlace addPlace = new AsyncAddPlace();
                    addPlace.execute(new SinglePlace(string));
                }
                d.dismiss();

            }
        });

        d.show();
    }

    private class AsyncGetPlaces extends AsyncTask<Void, Void, List<SinglePlace>>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected List<SinglePlace> doInBackground(Void... params) {
            dataSource = new PlacesDataSource(getActivity());
            dataSource.open();

            placesList = dataSource.getAllPlaces();
            dataSource.close();

            return placesList;
        }

        @Override
        protected void onPostExecute(List<SinglePlace> singlePlaces) {
            if (placesList.size() > 0) {
                layoutManager = new LinearLayoutManager(getActivity());
                placesRecycler.setLayoutManager(layoutManager);
                adapter =  new PlaceRecyclerViewAdapter(placesList, getActivity());
                placesRecycler.setAdapter(adapter);
            }
        }
    }

    private class AsyncAddPlace extends AsyncTask<SinglePlace,Void,SinglePlace> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }


        @Override
        protected SinglePlace doInBackground(SinglePlace... params) {
            PlacesDataSource dataSource = new PlacesDataSource(getActivity());
            dataSource.open();
            SinglePlace place = dataSource.createPlace(params[0].getTitle(),params[0].getAddress(),
                    params[0].getLatitute(), params[0].getLongitude());
            dataSource.close();
            return place;
        }


        @Override
        protected void onPostExecute(SinglePlace place) {
            placesList.add(place);
            synchronized (adapter){
                adapter.notifyAll();
            }
        }
    }
}
