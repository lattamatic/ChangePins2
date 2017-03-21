package sandeep.city.Activity;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import sandeep.city.DownloadImageTask;
import sandeep.city.POJO.SingleReport;
import sandeep.city.R;
import sandeep.city.SQLiteClasses.ReportsDataSource;

public class ActivityRegisterComplaint extends Activity implements OnClickListener, DownloadImageTask.ShowMapImage {

    TextView category, location_set;
    ImageView upload, takePic, submit;
    EditText ettitle, description;
    ImageView but_location;
    public View layout;
    public ImageView imageView;
    public final int TAKE_PICTURE = 1;
    public final int SELECT_PIC = 10;
    private final int CROP_PIC = 100;
    public final int GET_LOC = 11;
    public final int LOCATION = 2;
    public Bitmap bitmap;
    private TextView locMessage;
    private ImageView staticMap;
    private ProgressBar progressBar;
    ImageView back;

    private String location_string;
    private Uri picUri;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_register_complaint);

        //Instantiating views
        category = (TextView) findViewById(R.id.tvCategory);
        location_set = (TextView) findViewById(R.id.tvLocationText);
        upload = (ImageView) findViewById(R.id.ivUploadImage);
        takePic = (ImageView) findViewById(R.id.ivTakePic);
        submit = (ImageView) findViewById(R.id.ivSubmit);
        layout = (View) findViewById(R.id.relLay);
        imageView = (ImageView) findViewById(R.id.ivPreview);
        ettitle = (EditText) findViewById(R.id.etComplaintTitle);
        description = (EditText) findViewById(R.id.etDescription);
        but_location = (ImageView) findViewById(R.id.bPickLocation);
        staticMap = (ImageView) findViewById(R.id.ivStaticMap);
        locMessage = (TextView) findViewById(R.id.tvLocMessage);
        back = (ImageView) findViewById(R.id.ivBack);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        upload.setOnClickListener(this);
        takePic.setOnClickListener(this);
        submit.setOnClickListener(this);
        but_location.setOnClickListener(this);
        back.setOnClickListener(this);

        //Setting category from the data sent from previous activity
        category.setText(getIntent().getStringExtra("category"));

    }


    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.ivUploadImage:
                Intent photoPic = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(photoPic, SELECT_PIC);
                overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
                break;
            case R.id.ivTakePic:
                Intent intent = new Intent(
                        android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, TAKE_PICTURE);
                overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
                break;
            case R.id.bPickLocation:
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                Context context = this;
                try {
                    startActivityForResult(builder.build(context), LOCATION);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.ivSubmit:
                if (ettitle.getText().toString().matches("")) {
                    Toast.makeText(this, "Title cannot be empty",
                            Toast.LENGTH_SHORT).show();
                } else if (description.getText().toString().matches("")) {
                    Toast.makeText(this, "Description cannot be empty",
                            Toast.LENGTH_SHORT).show();
//                } else if (location_set.getText().toString().matches("")) {
//                    Toast.makeText(this, "Turn on Location Service and add a location",
//                            Toast.LENGTH_SHORT).show();
//                } else if (staticMap.getVisibility() == View.GONE) {
//                    Toast.makeText(this, "Choose a location to proceed", Toast.LENGTH_SHORT).show();
//                    PlacePicker.IntentBuilder buildr = new PlacePicker.IntentBuilder();
//                    Context c = this;
//                    try {
//                        startActivityForResult(buildr.build(c), LOCATION);
//                    } catch (GooglePlayServicesRepairableException e) {
//                        e.printStackTrace();
//                    } catch (GooglePlayServicesNotAvailableException e) {
//                        e.printStackTrace();
//                    }
                } else if (imageView.getBackground()!=null){
                    ReportsDataSource dataSource = new ReportsDataSource(this);
                    dataSource.open();
                    SingleReport report = dataSource.createReport(category.getText().toString(), ettitle.getText().toString(),
                            description.getText().toString(), "NoImage");
                    dataSource.close();
                }else{
                ReportsDataSource dataSource = new ReportsDataSource(this);
                dataSource.open();
                String saveImage = saveImageInMobile(((BitmapDrawable) imageView.getDrawable()).getBitmap());
                SingleReport report = dataSource.createReport(category.getText().toString(), ettitle.getText().toString(),
                        description.getText().toString(), saveImage);
                dataSource.close();
                }
                break;
            case R.id.ivBack:
                finish();
                break;
        }

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == TAKE_PICTURE && resultCode == RESULT_OK) {
            if (bitmap != null) {
                bitmap.recycle();
            }
            picUri = data.getData();
            performCrop();
        } else if (requestCode == SELECT_PIC && resultCode == RESULT_OK) {
            picUri = data.getData();
            performCrop();
        } else if (requestCode == CROP_PIC && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap thePic = extras.getParcelable("data");
            imageView.setImageBitmap(thePic);
            imageView.setBackground(null);
        } else if (requestCode == GET_LOC && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            location_string = (String) extras.get("location");
            Log.d("location", location_string);
            location_set.setVisibility(View.VISIBLE);
        } else if (requestCode == LOCATION && resultCode == RESULT_OK) {
            Log.d("map", "working");
            Place place = PlacePicker.getPlace(data, this);
            LatLng ll = place.getLatLng();
            double lon = ll.longitude;
            double lat = ll.latitude;
            Log.d("location", "lat:" + lat + " lon:" + lon);
            String url = "https://maps.googleapis.com/maps/api/staticmap?center=" + lat + "," + lon + "&zoom=17&size=600x300&maptype=normal";
            new DownloadImageTask(ActivityRegisterComplaint.this).execute(url);
        }

    }

    @Override
    public void onResultsReceived(Bitmap result) {
        staticMap.setImageBitmap(result);
        staticMap.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onDownloadStart() {
        progressBar.setVisibility(View.VISIBLE);
        locMessage.setVisibility(View.GONE);
    }


    private class GeocoderHandler extends Handler {
        @Override
        public void handleMessage(Message message) {
            String locationAddress;
            switch (message.what) {
                case 1:
                    Bundle bundle = message.getData();
                    locationAddress = bundle.getString("address");
                    break;
                default:
                    locationAddress = null;
            }
            location_set.setText(locationAddress);
        }
    }

    private void performCrop() {
        // take care of exceptions
        try {
            // call the standard crop action intent (the user device may not
            // support it)
            Intent cropIntent = new Intent("com.android.camera.action.CROP");
            // indicate image type and Uri
            cropIntent.setDataAndType(picUri, "image/*");
            // set crop properties
            cropIntent.putExtra("crop", "true");
            // indicate aspect of desired crop, commenting as of now
//            cropIntent.putExtra("aspectX", 1);
//            cropIntent.putExtra("aspectY", 1);
//            // indicate output X and Y, commenting as of now
//            cropIntent.putExtra("outputX", 256);
//            cropIntent.putExtra("outputY", 256);
            // retrieve data on return
            cropIntent.putExtra("return-data", true);
            // start the activity - we handle returning in onActivityResult
            startActivityForResult(cropIntent, CROP_PIC);
        } catch (ActivityNotFoundException anfe) {
            Toast toast = Toast
                    .makeText(this, "This device doesn't support the crop action!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    private String saveImageInMobile(Bitmap bitmapImage) {


        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("report_images", Context.MODE_PRIVATE);
        // Create imageDir
        long currentDateTimeString = new Date().getTime();
        File mypath = new File(directory, currentDateTimeString + ".jpg");
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return currentDateTimeString + ".jpg";
    }

}