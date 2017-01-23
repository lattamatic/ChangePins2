package sandeep.city;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by sandeep on 27/9/15.
 */
public class MyGroups extends Fragment {

    DataHelp dh;
    ListView groups;
    ArrayList<SingleGroup> groupList;
    public GroupsAdapter adapter;
    View v;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        dh = new DataHelp(getActivity());
        groupList = dh.getGroups();

        if(groupList.size()>0) {

            v = inflater.inflate(R.layout.my_groups, container, false);

            groups = (ListView) v.findViewById(R.id.lvGroups);

            adapter = new GroupsAdapter(groupList);
            groups.setAdapter(adapter);
            groups.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    FragmentTransaction transaction = getFragmentManager()
                            .beginTransaction();
                    transaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.came, R.anim.went);

                    transaction.commit();
                }
            });
        }else{
            v = inflater.inflate(R.layout.no_reports, container, false);
            TextView tv = (TextView) v.findViewById(R.id.tvNoReports);
            tv.setText("You haven't been following any groups.\\nClick here to view existing groups");
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FragmentTransaction transaction = getFragmentManager()
                            .beginTransaction();
                    transaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.came, R.anim.went);

                    transaction.replace(R.id.fragment1, new AllLocalActionGroups())
                            .addToBackStack("Secs");
                    transaction.commit();
                }
            });
        }
        return v;
    }

    public class GroupsAdapter extends BaseAdapter {


        ArrayList<SingleGroup> groupArrayList;
        SingleGroup group;
        GroupHolder holder;

        GroupsAdapter(ArrayList<SingleGroup> myGroups) {
           groupArrayList = myGroups;
        }

        @Override
        public int getCount() {
            return groupArrayList.size();
        }

        @Override
        public Object getItem(int position) {
            return groupArrayList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return groupArrayList.get(position).hashCode();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) getActivity()
                    .getSystemService(getActivity().LAYOUT_INFLATER_SERVICE);
            View v = inflater.inflate(R.layout.single_group, parent, false);
            group = groupArrayList.get(position);
            holder = new GroupHolder(v,group.getId());

            holder.title.setText(group.getTitle());
            holder.place.setText(group.getPlace());
            holder.date.setText(group.getDate());

//            File imgFile = new File("/sdcard/ChangePins/"+group.getImg_link()+".jpg");

//            if(imgFile.exists()){
//
//                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
//                holder.imageView.setImageBitmap(myBitmap);
//
//            }else{
//                Toast.makeText(getActivity(), "No image", Toast.LENGTH_SHORT).show();
//            }
            return v;
        }

        class GroupHolder{
            TextView title,place,date;
            ImageView imageView;
            int id;

            GroupHolder(View view,int id){
                title = (TextView) view.findViewById(R.id.tvSingleGroupTitle);
                place = (TextView) view.findViewById(R.id.tvSingleGroupPlace);
                date = (TextView) view.findViewById(R.id.tvSingleGroupDate);
                imageView = (ImageView) view.findViewById(R.id.ivSingleGroupImage);
                this.id = id;
            }

        }
    }
}
