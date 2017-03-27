package sandeep.city.Activity;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import sandeep.city.DownloadImageTask;
import sandeep.city.POJO.SingleReport;
import sandeep.city.R;
import sandeep.city.SQLiteClasses.ReportsDataSource;

public class ActivityRegisterComplaint extends Activity implements OnClickListener, DownloadImageTask.DownloadImage{

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

    public interface OnSubmitReport{
        void onSubmitReport();
    };


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
//            case R.id.bPickLocation:
//                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
//                Context context = this;
//                try {
//                    startActivityForResult(builder.build(context), LOCATION);
//                } catch (GooglePlayServicesRepairableException e) {
//                    e.printStackTrace();
//                } catch (GooglePlayServicesNotAvailableException e) {
//                    e.printStackTrace();
//                }
//                break;
            case R.id.ivSubmit:
                if (ettitle.getText().toString().matches("")) {
                    Toast.makeText(this, "Title cannot be empty",
                            Toast.LENGTH_SHORT).show();
                } else if (description.getText().toString().matches("")) {
                    Toast.makeText(this, "Description cannot be empty",
                            Toast.LENGTH_SHORT).show();
                } else if (imageView.getBackground() != null) {
                    AsyncSubmitReport async = new AsyncSubmitReport();
                    SingleReport report = new SingleReport(category.getText().toString(), ettitle.getText().toString(),
                            description.getText().toString(), "NoImage",0);
                    async.execute(report);
                    } else {
                    String saveImage = saveImageInMobile(((BitmapDrawable) imageView.getDrawable()).getBitmap());
                    AsyncSubmitReport async = new AsyncSubmitReport();
                    SingleReport report = new SingleReport(category.getText().toString(), ettitle.getText().toString(),
                            description.getText().toString(), saveImage,0);
                    async.execute(report);
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
//            Log.d("map", "working");
//            Place place = PlacePicker.getPlace(data, this);
//            LatLng ll = place.getLatLng();
//            double lon = ll.longitude;
//            double lat = ll.latitude;
//            Log.d("location", "lat:" + lat + " lon:" + lon);
//            String url = "https://maps.googleapis.com/maps/api/staticmap?center=" + lat + "," + lon + "&zoom=17&size=600x300&maptype=normal";
//            new DownloadImageTask(ActivityRegisterComplaint.this).execute(url);
        }

    }

    @Override
    public void onImageDownloadCompleted(Bitmap result) {
        staticMap.setImageBitmap(result);
        staticMap.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onImageDownloadStart() {
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
            Intent cropIntent = new Intent("com.android.camera.action.CROP");
            cropIntent.setDataAndType(picUri, "image/*");
            cropIntent.putExtra("crop", "true");
//            cropIntent.putExtra("aspectX", 1);
//            cropIntent.putExtra("aspectY", 1);
            cropIntent.putExtra("return-data", true);
            startActivityForResult(cropIntent, CROP_PIC);
        } catch (ActivityNotFoundException anfe) {
            Toast toast = Toast
                    .makeText(this, "This device doesn't support the crop action!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    private String saveImageInMobile(Bitmap bitmapImage) {
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        // path to the directory where images are saved
        File directory = cw.getDir("report_images", Context.MODE_PRIVATE);
        long currentDateTimeString = new Date().getTime();
        File imagePath = new File(directory, currentDateTimeString + ".jpg");
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(imagePath);
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

    private class AsyncSubmitReport extends AsyncTask<SingleReport,Void,Void>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }


        @Override
        protected Void doInBackground(SingleReport... params) {
            ReportsDataSource dataSource = new ReportsDataSource(getApplicationContext());
            dataSource.open();
            SingleReport report = dataSource.createReport(params[0].getCategory(),params[0].getTitle(),
                    params[0].getDescription(), params[0].getImage_path());
            dataSource.close();
            return null;
        }


        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            LocalBroadcastManager.getInstance(ActivityRegisterComplaint.this).sendBroadcast(new Intent("Intent filter"));
            Toast.makeText(ActivityRegisterComplaint.this,"Report submitted successfully!", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}