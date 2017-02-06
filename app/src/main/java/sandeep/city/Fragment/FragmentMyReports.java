package sandeep.city.Fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import sandeep.city.Activity.ActivityChooseCategory;
import sandeep.city.Adapter.ReportRecyclerViewAdapter;
import sandeep.city.POJO.SingleReport;
import sandeep.city.R;

/**
 * Created by sandeep_chi on 2/6/2017.
 */

public class FragmentMyReports extends Fragment {

    RecyclerView reportsRecycler;
    ArrayList<SingleReport> reportList;
    RecyclerView.LayoutManager layoutManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frag_myreports,container,false);
        reportList = new ArrayList<SingleReport>();


        if (reportList.size() > 0) {

            reportsRecycler = (RecyclerView) v.findViewById(R.id.rvReports);
            layoutManager = new LinearLayoutManager(getActivity());
            reportsRecycler.setLayoutManager(layoutManager);

            reportsRecycler.setAdapter( new ReportRecyclerViewAdapter(reportList));

        }

        FloatingActionButton fab = (FloatingActionButton) v.findViewById(R.id.fabAddReport);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),ActivityChooseCategory.class));
            }
        });
        return v;
    }
}
