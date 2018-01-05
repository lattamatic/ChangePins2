package sandeep.city.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SizeReadyCallback;

import java.util.List;

import sandeep.city.POJO.SingleStory;
import sandeep.city.R;

/**
 * Created by sandeep_chi on 1/24/2017.
 */

public class StoryRecyclerViewAdapter extends RecyclerView.Adapter<StoryRecyclerViewAdapter.ViewHolder> {

    private List<SingleStory> storyList;
    private Context context;

    public StoryRecyclerViewAdapter(List<SingleStory> storyList, Context context) {
        this.storyList = storyList;
        this.context = context;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView author, title, description, share, date;
        private ImageView storyImage;

        public ViewHolder(View v) {
            super(v);
            title = (TextView) v.findViewById(R.id.tvStoryTitle);
            description = (TextView) v.findViewById(R.id.tvStoryDesc);
            author = (TextView) v.findViewById(R.id.tvStoryAuthor);
            share = (TextView) v.findViewById(R.id.tvShareStory);
            date = (TextView) v.findViewById(R.id.tvStoryDate);
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
        holder.title.setText(story.getTitle());
        holder.date.setText(story.getDate());

        switch (position%4){
            case 0:
                Glide.with(context).load("http://boundary.london/wp-content/uploads/2016/03/bound_rooms_768w_2.1-768x420.jpg").
                        into(holder.storyImage).getSize(new SizeReadyCallback() {
                    @Override
                    public void onSizeReady(int width, int height) {
//                        height = (int) (width/1.6);
                        LinearLayout.LayoutParams lp= new LinearLayout.LayoutParams(width,height);
                        holder.storyImage.setLayoutParams(lp);
                        holder.storyImage.requestLayout();
                    }
                });
                break;
            case 1:
                Glide.with(context)
                        .load(R.drawable.storysampleimagetwo).into(holder.storyImage).getSize(new SizeReadyCallback() {
                    @Override
                    public void onSizeReady(int width, int height) {
                        height = (int) (width/1.6);
                        LinearLayout.LayoutParams lp= new LinearLayout.LayoutParams(width,height);
                        holder.storyImage.setLayoutParams(lp);
                        holder.storyImage.requestLayout();
                    }
                });
                break;
            case 2:
                Glide.with(context)
                        .load(R.drawable.storysampleimagethree).into(holder.storyImage).getSize(new SizeReadyCallback() {
                    @Override
                    public void onSizeReady(int width, int height) {
                        height = (int) (width/1.6);
                        LinearLayout.LayoutParams lp= new LinearLayout.LayoutParams(width,height);
                        holder.storyImage.setLayoutParams(lp);
                        holder.storyImage.requestLayout();
                    }
                });
                break;
            case 3:
                Glide.with(context)
                        .load(R.drawable.storysampleimagefour).into(holder.storyImage).getSize(new SizeReadyCallback() {
                    @Override
                    public void onSizeReady(int width, int height) {
                        height = (int) (width/1.6);
                        LinearLayout.LayoutParams lp= new LinearLayout.LayoutParams(width,height);
                        holder.storyImage.setLayoutParams(lp);
                        holder.storyImage.requestLayout();
                    }
                });
                break;
        }
    }

    @Override
    public int getItemCount() {
        return storyList.size();
    }
}