package sandeep.city;

import java.io.File;
import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import sandeep.city.POJO.SingleReport;

//This class is not being used
//
//
//This class is not being used
public class FinalComp extends Fragment{

    int id;
    ArrayList<SingleReport> arrayList;
    SingleReport singleReport;
    int i=0;

    public FinalComp(){

    }
    
    @SuppressLint("ValidFragment")
    public FinalComp(int id, ArrayList<SingleReport> arrayList){
        this.id =id;
        this.arrayList = arrayList;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // TODO Auto-generated method stub
        View v = inflater.inflate(R.layout.complaint_view, container, false);
        TextView category = (TextView) v.findViewById(R.id.tvCategory);
        TextView title= (TextView) v.findViewById(R.id.etComplainTitle);
        TextView desc= (TextView) v.findViewById(R.id.etDescription);
        TextView location = (TextView) v.findViewById(R.id.location_set);
        ImageView imageView= (ImageView) v.findViewById(R.id.ivPreview);


        for(i=0;i<arrayList.size();i++){
            if(id==arrayList.get(i).getId()){
                break;
            }
        }

        singleReport = arrayList.get(i);

        category.setText(singleReport.getCategory());
        title.setText(singleReport.getTitle());
        desc.setText(singleReport.getDescription());
        location.setText("Latitude:"+singleReport.getLat()+"\nLongitude: "+singleReport.getLon()+"\n\n"+singleReport.getLocAddress());
        File imgFile = new File("/sdcard/ChangePins/"+singleReport.getImg_link()+".jpg");

        if(imgFile.exists()){

            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            imageView.setImageBitmap(myBitmap);

        }else{
            Toast.makeText(getActivity(), "No image for "+singleReport.getTitle(), Toast.LENGTH_SHORT).show();
        }

        return v;
    }
}