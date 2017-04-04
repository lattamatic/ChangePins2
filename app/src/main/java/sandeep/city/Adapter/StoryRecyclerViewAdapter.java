package sandeep.city.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import sandeep.city.POJO.SingleStory;
import sandeep.city.R;

/**
 * Created by sandeep_chi on 1/24/2017.
 */

public class StoryRecyclerViewAdapter extends RecyclerView.Adapter<StoryRecyclerViewAdapter.ViewHolder> {

    private List<SingleStory> storyList;
    Context context;

    public StoryRecyclerViewAdapter(List<SingleStory> storyList, Context context) {
        this.storyList = storyList;
        this.context = context;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView description, author, share;
        public ImageView storyImage;

        public ViewHolder(View v) {
            super(v);
            description = (TextView) v.findViewById(R.id.tvStoryDesc);
            author = (TextView) v.findViewById(R.id.tvStoryAuthor);
            storyImage = (ImageView) v.findViewById(R.id.ivStoryImage);
            share = (TextView) v.findViewById(R.id.tvShareStory);
        }
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_story, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder h, final int position) {

        final ViewHolder holder = h;
        final SingleStory story = storyList.get(position);
        holder.description.setText(story.getDescription());
        holder.author.setText(story.getAuthor());
        holder.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(android.content.Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(android.content.Intent.EXTRA_SUBJECT,story.getTitle());
                i.putExtra(android.content.Intent.EXTRA_TEXT, story.getDescription());
                context.startActivity(Intent.createChooser(i,"Share via"));
            }
        });

        switch (position%4){
            case 0:
                holder.storyImage.setImageResource(R.drawable.storysampleimageone);
                break;
            case 1:
                holder.storyImage.setImageResource(R.drawable.storysampleimagetwo);
                break;
            case 2:
                holder.storyImage.setImageResource(R.drawable.storysampleimagethree);
                break;
            case 3:
                holder.storyImage.setImageResource(R.drawable.storysampleimagefour);
        }

        //Code to set image layout parameters to fit in the screen
        holder.storyImage.post(new Runnable() {
            @Override
            public void run() {
                int width = holder.storyImage.getWidth();
                int height = (int) (width/1.6);
                LinearLayout.LayoutParams lp= new LinearLayout.LayoutParams(width,height);
                holder.storyImage.setLayoutParams(lp);
                holder.storyImage.requestLayout();
            }
        });
    }

    @Override
    public int getItemCount() {
        return storyList.size();
    }
}