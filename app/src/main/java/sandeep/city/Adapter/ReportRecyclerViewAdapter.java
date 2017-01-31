package sandeep.city.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import sandeep.city.POJO.SingleReport;
import sandeep.city.R;

/**
 * Created by sandeep_chi on 1/31/2017.
 */

public class ReportRecyclerViewAdapter extends RecyclerView.Adapter<ReportRecyclerViewAdapter.ViewHolder> {

    private List<SingleReport> reportList;

    public ReportRecyclerViewAdapter(List<SingleReport> reportList) {
        this.reportList = reportList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView description, title;
        public ImageView reportThumbnail;

        public ViewHolder(View v) {
            super(v);
            description = (TextView) v.findViewById(R.id.tvReportDescription);
            title = (TextView) v.findViewById(R.id.tvReportTitle);
            reportThumbnail = (ImageView) v.findViewById(R.id.ivReportThumbnail);
        }
    }


    @Override
    public ReportRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_report, parent, false);
        return new ReportRecyclerViewAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ReportRecyclerViewAdapter.ViewHolder holder, int position) {
        SingleReport report = reportList.get(position);
        holder.description.setText(report.getDescription());
        holder.title.setText(report.getTitle());
    }

    @Override
    public int getItemCount() {
        return reportList.size();
    }
}