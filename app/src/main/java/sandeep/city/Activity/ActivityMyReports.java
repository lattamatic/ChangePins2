package sandeep.city.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

import sandeep.city.Adapter.ReportRecyclerViewAdapter;
import sandeep.city.ChangePinsActivity;
import sandeep.city.POJO.SingleReport;
import sandeep.city.R;

/**
 * Created by sandeep on 26/10/15.
 */
public class ActivityMyReports extends ChangePinsActivity {

    RecyclerView reportsRecycler;
    ArrayList<SingleReport> reportList;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        reportList = new ArrayList<SingleReport>();


        if (reportList.size() > 0) {

            reportsRecycler = (RecyclerView) findViewById(R.id.rvReports);
            layoutManager = new LinearLayoutManager(this);
            reportsRecycler.setLayoutManager(layoutManager);

            reportsRecycler.setAdapter( new ReportRecyclerViewAdapter(reportList));

        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabAddReport);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ActivityMyReports.this,ActivityChooseCategory.class));
            }
        });
    }

    @Override
    protected int getTitleText() {
        return R.string.myreports;
    }

    @Override
    protected int getLayout() {
        return R.layout.ac_myreports;
    }
}
