package sandeep.city.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import sandeep.city.POJO.SinglePlace;
import sandeep.city.R;

/**
 * Created by sandeep_chi on 1/31/2017.
 */

public class PlaceRecyclerViewAdapter extends RecyclerView.Adapter<PlaceRecyclerViewAdapter.ViewHolder> {

    private List<SinglePlace> placeList;
    private Context context;

    public PlaceRecyclerViewAdapter(List<SinglePlace> placeList, Context context){
        this.placeList = placeList;
        this.context = context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView title, address;
        public ImageView setLocation;
        public ViewHolder(View v) {
            super(v);
            title = (TextView) v.findViewById(R.id.tvPlaceTitle);
            address = (TextView) v.findViewById(R.id.tvPlaceAddress);
            setLocation = (ImageView) v.findViewById(R.id.ivPlaceSetLocation);
        }
    }




    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_place, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        SinglePlace place = placeList.get(position);
        holder.title.setText(place.getTitle());
        holder.address.setText(place.getAddress());
        holder.setLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Open Maps to pick a location
            }
        });
    }

    @Override
    public int getItemCount() {
        return placeList.size();
    }
}
