package sandeep.city.Adapter;

import android.app.Fragment;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import sandeep.city.POJO.SinglePlace;
import sandeep.city.R;

/**
 * Created by sandeep_chi on 1/31/2017.
 */

public class PlaceRecyclerViewAdapter extends RecyclerView.Adapter<PlaceRecyclerViewAdapter.ViewHolder> {

    private List<SinglePlace> placeList;
    private Context context;
    private PlaceRCVInterface placeRCVInterface;

    public interface PlaceRCVInterface {
        void OnClickEditPlace(SinglePlace place, int position);
    }

    public PlaceRecyclerViewAdapter(List<SinglePlace> placeList, Context context, Fragment placesFragment) {
        this.placeList = placeList;
        this.context = context;
        this.placeRCVInterface = (PlaceRCVInterface) placesFragment;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView title, address;
        public ImageView editPlace;

        public ViewHolder(View v) {
            super(v);
            title = (TextView) v.findViewById(R.id.tvPlaceTitle);
            address = (TextView) v.findViewById(R.id.tvPlaceAddress);
            editPlace = (ImageView) v.findViewById(R.id.ivEditPlace);
        }
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_place, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final SinglePlace place = placeList.get(position);
        holder.title.setText(place.getTitle());
        holder.address.setText(place.getAddress());
        holder.editPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Open dialog with actual data
                placeRCVInterface.OnClickEditPlace(placeList.get(position), position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return placeList.size();
    }

    //adds an item to the recyclerview
    public void addItem(int position, SinglePlace place) {
        placeList.add(position, place);
        notifyItemInserted(position);
    }

    //removes the Item from recyclerview
    public void removeItem(int position) {
        placeList.remove(position);
        notifyItemRemoved(position);
    }

    //Updates the recyclerView UI
    public void updateItem(int position, SinglePlace place){
        placeList.remove(position);
        placeList.add(position,place);
        notifyItemChanged(position);
    }
}