package sandeep.city.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import sandeep.city.POJO.SingleStory;
import sandeep.city.R;

/**
 * Created by sandeep_chi on 1/24/2017.
 */

public class StoryRecyclerViewAdapter extends RecyclerView.Adapter<StoryRecyclerViewAdapter.ViewHolder> {

    private List<SingleStory> storyList;

    public StoryRecyclerViewAdapter(List<SingleStory> storyList) {
        this.storyList = storyList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView description, author;
        public ImageView storyImage;

        public ViewHolder(View v) {
            super(v);
            description = (TextView) v.findViewById(R.id.tvStoryDesc);
            author = (TextView) v.findViewById(R.id.tvStoryAuthor);
            storyImage = (ImageView) v.findViewById(R.id.ivStoryImage);
        }
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_story, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        SingleStory story = storyList.get(position);
        holder.description.setText(story.getDescription());
        holder.author.setText(story.getAuthor());
    }

    @Override
    public int getItemCount() {
        return storyList.size();
    }
}