package sandeep.city.Adapter;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

import sandeep.city.POJO.SingleReport;
import sandeep.city.R;

/**
 * Created by sandeep_chi on 1/31/2017.
 */

public class ReportRecyclerViewAdapter extends RecyclerView.Adapter<ReportRecyclerViewAdapter.ViewHolder> {

    private List<SingleReport> reportList;
    Context context;

    public ReportRecyclerViewAdapter(List<SingleReport> reportList, Context context) {
        this.reportList = reportList;
        this.context = context;
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
        final SingleReport report = reportList.get(position);
        holder.description.setText(report.getDescription());
        holder.title.setText(report.getTitle());
        if(report.getImage_path().equals("NoImage")){
            Glide.with(context)
                    .load(R.drawable.gallery).into(holder.reportThumbnail);
        }else{
            ContextWrapper cw = new ContextWrapper(context);
            File directory = cw.getDir("report_images", Context.MODE_PRIVATE);
            // Create imageDir

            File f=new File(directory, report.getImage_path());
            Uri imageUri = Uri.fromFile(f);
            Glide.with(context).load(imageUri).into(holder.reportThumbnail);
        }
    }

    @Override
    public int getItemCount() {
        return reportList.size();
    }


    private Bitmap loadImageFromStorage(String path)
    {
        ContextWrapper cw = new ContextWrapper(context);
        Bitmap b = null;
        try {
            File directory = cw.getDir("report_images", Context.MODE_PRIVATE);
            // Create imageDir

            File f=new File(directory, path);
            b = BitmapFactory.decodeStream(new FileInputStream(f));
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }

        return b;

    }
}