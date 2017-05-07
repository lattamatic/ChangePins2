package sandeep.city.RetrofitTest;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import sandeep.city.R;

/**
 * Created by sandeep_chi on 4/4/2017.
 */

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {
    private ArrayList<AndroidVersion> androidVersionArrayList;

    public DataAdapter(ArrayList<AndroidVersion> androidVersionArrayList) {
        this.androidVersionArrayList = androidVersionArrayList;
    }

    @Override
    public DataAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.single_story, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DataAdapter.ViewHolder holder1, int i) {
        final ViewHolder holder = holder1;
        final AndroidVersion androidVersion= androidVersionArrayList.get(i);
        holder.description.setText(androidVersion.getVer()+" "+androidVersion.getApi());
        holder.author.setText(androidVersion.getName());
        holder.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(android.content.Intent.ACTION_SEND);
//                i.setType("text/plain");
//                i.putExtra(android.content.Intent.EXTRA_SUBJECT,story.getTitle());
//                i.putExtra(android.content.Intent.EXTRA_TEXT, story.getDescription());
//                context.startActivity(Intent.createChooser(i,"Share via"));
            }
        });

        switch (i%4){
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
        return androidVersionArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView description, author, share;
        public ImageView storyImage;
        public ViewHolder(View v) {
            super(v);
            description = (TextView) v.findViewById(R.id.tvStoryDesc);
            author = (TextView) v.findViewById(R.id.tvStoryTitle);
            storyImage = (ImageView) v.findViewById(R.id.ivStoryImage);
            share = (TextView) v.findViewById(R.id.tvShareStory);
        }
    }

}