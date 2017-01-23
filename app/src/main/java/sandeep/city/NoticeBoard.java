package sandeep.city;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class NoticeBoard extends Activity {


    ListView commentList;
    ArrayList<SingleComment> arrayList;
    Adapter adapter;
    EditText et;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActionBar().setBackgroundDrawable(null);

        arrayList = new ArrayList<SingleComment>();
//        arrayList.add(new SingleComment("Sandeep","Hey there's a change in venue. Please come to GC directly. We will start the work from there","9.00PM"));
//        arrayList.add(new SingleComment("Sandeep","Hey there is change in timing too. Please come by 0900. We should be able to finish it by noon.","9.30PM"));
        String comments = getIntent().getStringExtra("comments");
        int i;
        try {
            Log.d("json",comments);
            JSONObject json = new JSONObject(comments);
            JSONArray jsonArray = json.getJSONArray("comments");
            for( i=0;i<jsonArray.length() ;i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                arrayList.add(new SingleComment(jsonObject.getString("name"),jsonObject.getString("comment"),jsonObject.getString("time")));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        if(arrayList.size()>0) {

            setContentView(R.layout.notice_board);
            commentList = (ListView) findViewById(R.id.lvComments);

            adapter = new Adapter(arrayList);
            commentList.setAdapter(adapter);
        }else{
            setContentView(R.layout.no_reports);
            TextView textView = (TextView) findViewById(R.id.tvNoReports);
            textView.setText("No updates yet! Come back later");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_text, menu);
        View v = (View) menu.findItem(R.id.action_comment).getActionView();
        v.setVisibility(View.GONE);
        et = (EditText) v.findViewById(R.id.edit_text);
        ImageView b = (ImageView) v.findViewById(R.id.post);
        b.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (et.getText().toString() != null) {
                    Toast.makeText(NoticeBoard.this,et.getText().toString(),Toast.LENGTH_SHORT).show();
                }
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        if (item.getItemId() == R.id.action_comment) {

        }
        return true;
    }


    public class Adapter extends BaseAdapter{

        ArrayList<SingleComment> arrayList;
        LayoutInflater inflater;
        SingleComment singleComment;

        Adapter(ArrayList<SingleComment> arrayList){
            this.arrayList=arrayList;
        }

        @Override
        public int getCount() {
            return arrayList.size();
        }

        @Override
        public Object getItem(int position) {
            return arrayList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return arrayList.get(position).hashCode();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            inflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View itemView = inflater.inflate(R.layout.single_update, parent, false);
            TextView name = (TextView) itemView.findViewById(R.id.tvAdminName);
            TextView comment = (TextView) itemView.findViewById(R.id.tvComment);
            TextView time = (TextView) itemView.findViewById(R.id.tvTiming);

            singleComment = arrayList.get(position);

            name.setText(singleComment.getUser_name());
            comment.setText(singleComment.getComment());
            time.setText(singleComment.getTime());

            return itemView;
        }
    }


}