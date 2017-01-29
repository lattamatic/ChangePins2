package sandeep.city.Activity;

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

import sandeep.city.ChangePinsActivity;
import sandeep.city.DataHelp;
import sandeep.city.FinalComp;
import sandeep.city.R;
import sandeep.city.POJO.SingleReport;

/**
 * Created by sandeep on 26/10/15.
 */
public class ActivityMyReports extends ChangePinsActivity {

    DataHelp dh;
    ListView reports;
    ArrayList<SingleReport> reportList;
    ReportsAdapter adapter;
    View v;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dh = new DataHelp(this);
        reportList = dh.getReports();


        if (reportList.size() > 0) {

            reports = (ListView) findViewById(R.id.lvReports);

            adapter = new ReportsAdapter(reportList);
            reports.setAdapter(adapter);
            reports.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    FragmentTransaction transaction = getFragmentManager()
                            .beginTransaction();
                    transaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.came, R.anim.went);

                    transaction.replace(R.id.fragment1, new FinalComp(reportList.get(position).getId(), reportList))
                            .addToBackStack("Secs");
                    transaction.commit();
                }
            });
        } else {
            setContentView(R.layout.no_reports);
            TextView tv = (TextView) findViewById(R.id.tvNoReports);
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
//                    FragmentTransaction transaction = getFragmentManager()
//                            .beginTransaction();
//                    transaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.came, R.anim.went);
//
//                    transaction.replace(R.id.fragment1, new FragmentSelectSector())
//                            .addToBackStack("Secs");
//                    transaction.commit();
//                }
                }
            });

        }
    }

    @Override
    protected int getTitleText() {
        return R.string.myreports;
    }

    @Override
    protected int getLayout() {
        return R.layout.ac_myreports;
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
            LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
            View v = inflater.inflate(R.layout.single_report, parent, false);
            report = reportArrayList.get(position);
            holder = new ReportHolder(v, report.getId());

            holder.title.setText(report.getTitle());
            holder.desc.setText(report.getDesc());

            File imgFile = new File("/sdcard/ChangePins/" + report.getImg_link() + ".jpg");

            if (imgFile.exists()) {

                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                holder.imageView.setImageBitmap(myBitmap);

            } else {
                Toast.makeText(ActivityMyReports.this, "No image", Toast.LENGTH_SHORT).show();
            }
            return v;
        }

        class ReportHolder {
            TextView title, desc;
            ImageView imageView;
            int id;

            ReportHolder(View view, int id) {
                title = (TextView) view.findViewById(R.id.tvTitleSingle);
                desc = (TextView) view.findViewById(R.id.tvDescSingle);
                imageView = (ImageView) view.findViewById(R.id.ivImageSingle);
                this.id = id;
            }

        }
    }
}
