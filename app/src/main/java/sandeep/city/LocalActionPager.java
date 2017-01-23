package sandeep.city;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.Image;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by sandeep on 16/5/15.
 */
public class LocalActionPager extends PagerAdapter{
    Context mContext;
    LayoutInflater mLayoutInflater;
    DataHelp dh;
    ArrayList<SingleGroup> sg;
    SingleGroup singleGroup;
    ViewHolder holder;

    int[] mResources = {
            R.drawable.eunoia1,
            R.drawable.eunoia2,
            R.drawable.eunoia3,
            R.drawable.eunoia4,
            R.drawable.eunoia5,
    };
    


    public LocalActionPager(Context context) {
        mContext = context;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        dh=new DataHelp(mContext);
        sg = new ArrayList<SingleGroup>();
        getGroups();
    }

    private void getGroups() {

        sg.clear();
        Cursor c = dh.db.rawQuery("SELECT * FROM groups",null);

        if(c!= null)
        {
            if(c.moveToFirst())
            {
                do
                {
                    int id = c.getInt(c.getColumnIndex("group_id"));
                    String title= c.getString(c.getColumnIndex("title"));
                    String place = c.getString(c.getColumnIndex("place"));
                    String date = c.getString(c.getColumnIndex("date"));
                    int status = c.getInt(c.getColumnIndex("status"));
                    int no_of_vols=c.getInt(c.getColumnIndex("no_of_vols"));
                    int no_of_hours = c.getInt(c.getColumnIndex("no_of_hours"));
                    String comments = c.getString(c.getColumnIndex("comments"));

                    sg.add(new SingleGroup(id,no_of_hours,no_of_vols,status,title,place,date,comments));
                }
                while(c.moveToNext());
            }
        }
        else
        {
            Toast.makeText(mContext,"Table has nothing",Toast.LENGTH_SHORT).show();
        }
        c.close();

    }

    @Override
    public int getCount() {
        return sg.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((RelativeLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View itemView = mLayoutInflater.inflate(R.layout.local_action_single, container, false);

        singleGroup = sg.get(position);

        Log.d("singleGroup",singleGroup.getStatus()+" and "+singleGroup.getId());

        holder = new ViewHolder(itemView,singleGroup.getStatus(),singleGroup.getId(),singleGroup.getComments());
        itemView.setTag(holder);


        holder.imageView.setImageResource(mResources[position]);
        holder.vols.setText(singleGroup.getNo_vols()+"");
        holder.hours.setText(singleGroup.getNo_hours() + "");
        holder.cause.setText(singleGroup.getTitle());
        holder.location.setText(singleGroup.getPlace());
        holder.date.setText(singleGroup.getDate());

        Log.d("status",singleGroup.getStatus()+"");


        if(singleGroup.getStatus()==0){
            holder.noticeBoard.setImageResource(R.drawable.notif_read);
        }else if(singleGroup.getStatus()==1){
            holder.noticeBoard.setImageResource(R.drawable.notif_unread);
        }


        holder.join.setTag(holder);
        holder.join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                join.setVisibility(View.GONE);
//                join.setClickable(false);
//                noticeBoard.setVisibility(View.VISIBLE);
//                noticeBoard.setClickable(true);
                ViewHolder vh = (ViewHolder) v.getTag( );
                Toast.makeText(mContext,"You will be added to \""+vh.cause.getText().toString()+"\"",Toast.LENGTH_SHORT).show();
            }
        });

        holder.noticeBoard.setTag(holder);
        holder.noticeBoard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewHolder vh = (ViewHolder) v.getTag();
                vh.noticeBoard.setImageResource(R.drawable.notif_read);
                dh.group_update(vh.cause.getText().toString(),vh.location.getText().toString(),vh.date.getText().toString(),Integer.valueOf(vh.vols.getText().toString()),Integer.valueOf(vh.hours.getText().toString()),"",vh.id,0,vh.comments);
                Intent i = new Intent(mContext,NoticeBoard.class);
                i.putExtra("comments",vh.comments);
                Log.d("commentsss",vh.comments);
                getGroups();
                mContext.startActivity(i);
//                Toast.makeText(mContext,"Display notice board",Toast.LENGTH_SHORT).show();
            }
        });

        container.addView(itemView);
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout) object);
    }

    public class ViewHolder{

        int id;
        int status;
        String comments;
        ImageView imageView,noticeBoard;
        LinearLayout join;
        TextView vols,hours,cause,location,date;

        ViewHolder(View itemView,int status, int id,String comments) {

            this.status =status;
            this.id=id;
            this.comments=comments;

            imageView = (ImageView) itemView.findViewById(R.id.ivGroupImage);

            vols = (TextView) itemView.findViewById(R.id.tvNoVols);
            hours = (TextView) itemView.findViewById(R.id.tvNoHours);
            cause = (TextView) itemView.findViewById(R.id.tvCause);
            location = (TextView) itemView.findViewById(R.id.tvLocation);
            date = (TextView) itemView.findViewById(R.id.tvDate);
            join = (LinearLayout) itemView.findViewById(R.id.linearLayout);
            noticeBoard = (ImageView) itemView.findViewById(R.id.ivNoticeBoard);
        }
    }
}