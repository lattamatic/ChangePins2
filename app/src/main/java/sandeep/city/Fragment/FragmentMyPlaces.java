package sandeep.city.Fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import sandeep.city.Activity.ActivityPlaceDialog;
import sandeep.city.Adapter.PlaceRecyclerViewAdapter;
import sandeep.city.POJO.SinglePlace;
import sandeep.city.R;
import sandeep.city.SQLiteClasses.PlacesDataSource;

/**
 * Created by sandeep_chi on 2/6/2017.
 */

public class FragmentMyPlaces extends Fragment implements PlaceRecyclerViewAdapter.PlaceRCVInterface {

    private RecyclerView placesRecycler;
    private List<SinglePlace> placesList;
    private RecyclerView.LayoutManager layoutManager;
    private PlacesDataSource dataSource;
    private PlaceRecyclerViewAdapter adapter;


    @Override
    public void OnClickEditPlace(SinglePlace place, int position) {
        Intent intent = new Intent(getActivity(), ActivityPlaceDialog.class);
        Log.d("place","id:"+place.getId()+" title:"+place.getTitle()+" address:"+place.getAddress()+
        " lat:"+place.getLatitute()+" lon:"+place.getLongitude()+" pos:"+position);
        intent.putExtra("id",place.getId());
        intent.putExtra("title",place.getTitle());
        intent.putExtra("address",place.getAddress());
        intent.putExtra("lat",place.getLatitute());
        intent.putExtra("lon",place.getLongitude());
        intent.putExtra("position",position);
        intent.putExtra("state", 2);
        startActivityForResult(intent, 2);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.frag_myplaces, container, false);
        placesList = new ArrayList<SinglePlace>();
        placesRecycler = (RecyclerView) v.findViewById(R.id.rvPlaces);

        new AsyncGetPlaces().execute(); //Fetching places from Internal DB

        FloatingActionButton fab = (FloatingActionButton) v.findViewById(R.id.fabAddPlace);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ActivityPlaceDialog.class);
                intent.putExtra("state", 1);
                startActivityForResult(intent, 1);
            }
        });

        return v;
    }

    //AsyncTask to fetch all the places from Internal DB
    private class AsyncGetPlaces extends AsyncTask<Void, Void, List<SinglePlace>> {

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
                adapter = new PlaceRecyclerViewAdapter(placesList, getActivity(), FragmentMyPlaces.this);
                placesRecycler.setAdapter(adapter);
            }
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == getActivity().RESULT_OK) {
            if (requestCode == 1) {
                SinglePlace place = new SinglePlace();
                place.setId(data.getExtras().getLong("place_id"));
                place.setTitle(data.getExtras().getString("place_title", place.getTitle()));
                place.setLatitute(data.getExtras().getLong("place_lat"));
                place.setLongitude(data.getExtras().getLong("place_lon"));
                place.setAddress(data.getExtras().getString("place_address"));
                adapter.addItem(0, place);
            } else {
                SinglePlace place = new SinglePlace();
                place.setId(data.getExtras().getLong("place_id"));
                place.setTitle(data.getExtras().getString("place_title", place.getTitle()));
                place.setLatitute(data.getExtras().getLong("place_lat"));
                place.setLongitude(data.getExtras().getLong("place_lon"));
                place.setAddress(data.getExtras().getString("place_address"));
                Log.d("index",data.getExtras().getInt("position",0)+" "+adapter.getItemCount());
                adapter.updateItem(data.getExtras().getInt("position"), place);
            }
        }
    }
}