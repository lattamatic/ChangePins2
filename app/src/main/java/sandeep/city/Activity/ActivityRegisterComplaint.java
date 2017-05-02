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
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import sandeep.city.POJO.SingleReport;
import sandeep.city.R;
import sandeep.city.SQLiteClasses.ReportsDataSource;

public class ActivityRegisterComplaint extends Activity {

    private TextView category, locMessage, catDescription;
    private ImageView takePic, but_location, back, staticMap, imageView;
    private EditText title, description;
    private final int LOCATION = 4;
    private ProgressBar progressBar;
    private Place place;
    private Button submit;
    private int locationFlag=0;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_register_complaint);
        initializeViews(); //Instantiating views
        initializeOnClicks(); //Sets on click listeners
        //Setting category from the data sent from previous activity
        category.setText(getIntent().getStringExtra("category"));
        catDescription.setText(getIntent().getStringExtra("categoryDescription"));

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {//Checks if the result of the Activity started is OK and proceeds
            switch (requestCode) {
                //Opens Cropping activity after selecting a pic from Camera or Gallery
                case CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE:
                    Uri imageUri = CropImage.getPickImageResultUri(this, data);
                    startCropImageActivity(imageUri);
                    break;
                //Posts the image in the image preview in the complaint form after cropping
                case CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE:
                    CropImage.ActivityResult result = CropImage.getActivityResult(data);
                    imageView.setImageURI(result.getUri());
                    imageView.setBackground(null);
                    break;
                //Shows the location in the Complaint form after choosing a place
                case LOCATION:
                    place = PlacePicker.getPlace(data, this);
                    if(place!=null){
                        locationFlag=1;
                        LatLng ll = place.getLatLng();
                        double lat = ll.latitude;
                        double lon = ll.longitude;
                        progressBar.setVisibility(View.VISIBLE);
                        locMessage.setVisibility(View.GONE);
                        String url = "https://maps.googleapis.com/maps/api/staticmap?center=" + lat + "," + lon + "&zoom=19&size=600x300&maptype=normal";
                        Glide.with(this).load(url).listener(new RequestListener<String, GlideDrawable>() {
                            @Override
                            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                                progressBar.setVisibility(View.GONE);
                                locMessage.setVisibility(View.VISIBLE);
                                locMessage.setText("Error retrieving location Map\n\n" + place.getAddress());
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                                staticMap.setVisibility(View.VISIBLE);
                                progressBar.setVisibility(View.GONE);
                                locMessage.setVisibility(View.VISIBLE);
                                locMessage.setText(place.getAddress());
                                return false;
                            }
                        }).into(staticMap);

                    }
                    break;
            }
        }
    }

    //Saves Image in Local Memory and returns randomly generated Filename
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

    //AsyncTask to post a Report to internal DB and sends result to HomeActivity
    private class AsyncSubmitReport extends AsyncTask<SingleReport, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(SingleReport... params) {
            ReportsDataSource dataSource = new ReportsDataSource(getApplicationContext());
            dataSource.open();
            SingleReport report = dataSource.createReport(params[0].getCategory(), params[0].getTitle(),
                    params[0].getDescription(), params[0].getImage_path());
            dataSource.close();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Intent returnIntent = new Intent();
            returnIntent.putExtra("result", "submitted");
            setResult(Activity.RESULT_OK, returnIntent);
            Toast.makeText(ActivityRegisterComplaint.this, "Report submitted successfully!",
                    Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    //Initialize all the UI elements for Dynamic action
    private void initializeViews() {
        category = (TextView) findViewById(R.id.tvCategory);
        catDescription = (TextView) findViewById(R.id.tvCategoryDescription);
        locMessage = (TextView) findViewById(R.id.tvLocMessage);

        back = (ImageView) findViewById(R.id.ivBack);
//        upload = (ImageView) findViewById(R.id.ivUploadImage);
        takePic = (ImageView) findViewById(R.id.ivTakePic);
        imageView = (ImageView) findViewById(R.id.ivPreview);
        staticMap = (ImageView) findViewById(R.id.ivStaticMap);
        but_location = (ImageView) findViewById(R.id.bPickLocation);

        submit = (Button) findViewById(R.id.bSubmit);

        title = (EditText) findViewById(R.id.etComplaintTitle);
        description = (EditText) findViewById(R.id.etDescription);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
    }

    //Sets on click listeners to those views needs to be clickable
    private void initializeOnClicks() {

        takePic.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                CropImage.startPickImageActivity(ActivityRegisterComplaint.this);
            }
        });
        submit.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (title.getText().toString().matches("")) {
                    Toast.makeText(ActivityRegisterComplaint.this, "Give a Title to your complaint",
                            Toast.LENGTH_SHORT).show();
                } else if (description.getText().toString().matches("")) {
                    Toast.makeText(ActivityRegisterComplaint.this, "Please give description to the issue",
                            Toast.LENGTH_SHORT).show();
                }else if(locationFlag!=1){
                    Toast.makeText(ActivityRegisterComplaint.this, "Tell us where the issue is by choosing a location",
                            Toast.LENGTH_SHORT).show();
                }else if (imageView.getBackground() != null) {
                    AsyncSubmitReport async = new AsyncSubmitReport();
                    SingleReport report = new SingleReport(category.getText().toString(), title.getText().toString(),
                            description.getText().toString(), "NoImage", 0);
                    async.execute(report);
                } else {
                    String saveImage = saveImageInMobile(((BitmapDrawable) imageView.getDrawable()).getBitmap());
                    AsyncSubmitReport async = new AsyncSubmitReport();
                    SingleReport report = new SingleReport(category.getText().toString(), title.getText().toString(),
                            description.getText().toString(), saveImage, 0);
                    async.execute(report);
                }
            }
        });
        but_location.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                Context context = ActivityRegisterComplaint.this;
                try {
                    startActivityForResult(builder.build(context), LOCATION);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                    Toast.makeText(context, "Google Play Services not available on this device",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
        back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void startCropImageActivity(Uri uri) {
        CropImage.activity(uri)
                .start(this);
    }
}