package sandeep.city;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by sandeep_chi on 1/24/2017.
 */

public class LocalActionGroupsAdapter extends RecyclerView.Adapter<LocalActionGroupsAdapter.ViewHolder> {

    public List<SingleLocalActionGroup> localActionGroups;
    public Context context;


    public LocalActionGroupsAdapter(List<SingleLocalActionGroup> localActionGroups, Context context){
        this.localActionGroups = localActionGroups;
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        public TextView title, location, date, noOfVols, noOfHours;
        public ImageView viewNoticeBoard;
        public LinearLayout joinGroup;

        public ViewHolder(View v) {
            super(v);
            title = (TextView) v.findViewById(R.id.tvLAGTitle);
            location = (TextView) v.findViewById(R.id.tvLAGLocation);
            date = (TextView) v.findViewById(R.id.tvLAGDate);
            noOfHours = (TextView) v.findViewById(R.id.tvLAGNoOfHours);
            noOfVols = (TextView) v.findViewById(R.id.tvLAGNoOfVols);
            joinGroup = (LinearLayout) v.findViewById(R.id.llJoinLAG);
            viewNoticeBoard = (ImageView) v.findViewById(R.id.ivLAGNoticeBoard);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.local_action_single, parent, false);
        return new LocalActionGroupsAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final SingleLocalActionGroup localActionGroup = localActionGroups.get(position);

        holder.noOfVols.setText(localActionGroup.getNoOfVols()+"");
        holder.noOfHours.setText(localActionGroup.getNoOfHours()+"");
        holder.joinGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "You will be added to " + localActionGroup.getTitle(), Toast.LENGTH_SHORT).show();
            }
        });
        holder.title.setText(localActionGroup.getTitle());
        holder.date.setText(localActionGroup.getDate());
        holder.location.setText(localActionGroup.getLocation());
    }

    @Override
    public int getItemCount() {
        return localActionGroups.size();
    }
}
