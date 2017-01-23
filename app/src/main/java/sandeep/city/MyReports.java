package sandeep.city;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by sandeep on 22/6/15.
 */
public class MyReports extends Fragment {

    DataHelp dh;
    ListView reports;
    ArrayList<SingleReport> reportList;
    public ReportsAdapter adapter;
    View v;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        dh = new DataHelp(getActivity());
        reportList = dh.getReports();

        if(reportList.size()>0) {

            v = inflater.inflate(R.layout.my_reports, container, false);

            reports = (ListView) v.findViewById(R.id.lvReports);

            adapter = new ReportsAdapter(reportList);
            reports.setAdapter(adapter);
            reports.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent i = new Intent(getActivity(), DetailedReport.class);
                    i.putExtra("id",reportList.get(position).getId());
                    startActivity(i);
                }
            });
        }else{
            v = inflater.inflate(R.layout.no_reports, container, false);
            RelativeLayout rl = (RelativeLayout) v.findViewById(R.id.rlNoReports);
            rl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FragmentTransaction transaction = getFragmentManager()
                            .beginTransaction();
                    transaction.setCustomAnimations(R.anim.enter, R.anim.exit);

                    transaction.replace(R.id.fragment, new SelectSector())
                            .addToBackStack("Secs");
                    transaction.commit();
                    HomeActivity.screenWatcher=7;
                }
            });
        }
        return v;
    }

    public class ReportsAdapter extends BaseAdapter {


        ArrayList<SingleReport> reportArrayList;
        SingleReport report;
        ReportHolder holder;

        ReportsAdapter(ArrayList<SingleReport> myReports) {
            reportArrayList = myReports;
        }

        @Override
        public int getCount() {
            return reportArrayList.size();
        }

        @Override
        public Object getItem(int position) {
            return reportArrayList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return reportArrayList.get(position).hashCode();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) getActivity()
                    .getSystemService(getActivity().LAYOUT_INFLATER_SERVICE);
            View v = inflater.inflate(R.layout.single_report, parent, false);
            report = reportArrayList.get(position);
            holder = new ReportHolder(v,report.getId());

            holder.title.setText(report.getTitle());
            holder.desc.setText(report.getDesc());

            File imgFile = new File("/sdcard/ChangePins/"+report.getImg_link()+".jpg");

            if(imgFile.exists()){

                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                holder.imageView.setImageBitmap(myBitmap);

            }else{
                Toast.makeText(getActivity(), "No image", Toast.LENGTH_SHORT).show();
            }
            return v;
        }

        class ReportHolder{
            TextView title,desc;
            ImageView imageView;
            int id;

            ReportHolder(View view,int id){
                title = (TextView) view.findViewById(R.id.tvTitleSingle);
                desc = (TextView) view.findViewById(R.id.tvDescSingle);
                imageView = (ImageView) view.findViewById(R.id.ivImageSingle);
                this.id = id;
            }

        }
    }
}
