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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import sandeep.city.POJO.SingleReport;
import sandeep.city.R;
import sandeep.city.SQLiteClasses.ReportsDataSource;

public class ActivityRegisterComplaint extends Activity {

    TextView category, location_set;
    ImageView upload, takePic, submit;
    EditText ettitle, description;
    ImageView but_location;
    public View layout;
    public ImageView imageView;
    public final int TAKE_PICTURE = 1;
    public final int SELECT_PIC = 2;
    private final int CROP_PIC = 3;
    public final int GET_LOC = 4;
    public final int LOCATION = 5;
    private TextView locMessage;
    private ImageView staticMap;
    private ProgressBar progressBar;
    ImageView back;
    Place place;

    private String location_string;
    private Uri uri;


    public interface OnSubmitReport {
        void onSubmitReport();
    }

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

        upload.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent GalIntent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(Intent.createChooser(GalIntent, "Select Image From Gallery"), SELECT_PIC);
            }
        });
        takePic.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, TAKE_PICTURE);             }
        });
        submit.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ettitle.getText().toString().matches("")) {
                    Toast.makeText(ActivityRegisterComplaint.this, "Title cannot be empty",
                            Toast.LENGTH_SHORT).show();
                } else if (description.getText().toString().matches("")) {
                    Toast.makeText(ActivityRegisterComplaint.this, "Description cannot be empty",
                            Toast.LENGTH_SHORT).show();
                } else if (imageView.getBackground() != null) {
                    AsyncSubmitReport async = new AsyncSubmitReport();
                    SingleReport report = new SingleReport(category.getText().toString(), ettitle.getText().toString(),
                            description.getText().toString(), "NoImage", 0);
                    async.execute(report);
                } else {
                    String saveImage = saveImageInMobile(((BitmapDrawable) imageView.getDrawable()).getBitmap());
                    AsyncSubmitReport async = new AsyncSubmitReport();
                    SingleReport report = new SingleReport(category.getText().toString(), ettitle.getText().toString(),
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
        //Setting category from the data sent from previous activity
        category.setText(getIntent().getStringExtra("category"));

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == TAKE_PICTURE && resultCode == RESULT_OK) {
            if (data != null) {
                imageView.setImageURI((Uri) data.getExtras().get("data"));
            }else{
                Toast.makeText(ActivityRegisterComplaint.this,"NULL",Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == SELECT_PIC && resultCode == RESULT_OK) {
            if (data != null) {
                ImageCropFunction(data.getData());
            }
        } else if (requestCode == CROP_PIC && resultCode == RESULT_OK) {
            if (data != null) {

                Bundle bundle = data.getExtras();

                Bitmap bitmap = bundle.getParcelable("data");

                imageView.setImageBitmap(bitmap);

            }

        } else if (requestCode == GET_LOC && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            location_string = (String) extras.get("location");
            Log.d("location", location_string);
            location_set.setVisibility(View.VISIBLE);
        } else if (requestCode == LOCATION && resultCode == RESULT_OK) {
            place = PlacePicker.getPlace(data, this);
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
            returnIntent.putExtra("result","submitted");
            setResult(Activity.RESULT_OK,returnIntent);
            Toast.makeText(ActivityRegisterComplaint.this, "Report submitted successfully!", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void ImageCropFunction(Uri uri) {
        // Image Crop Code
        try {
            Intent CropIntent = new Intent("com.android.camera.action.CROP");

            CropIntent.setDataAndType(uri, "image/*");

            CropIntent.putExtra("crop", "true");
//            CropIntent.putExtra("outputX", 180);
//            CropIntent.putExtra("outputY", 180);
//            CropIntent.putExtra("aspectX", 3);
//            CropIntent.putExtra("aspectY", 4);
            CropIntent.putExtra("scaleUpIfNeeded", true);
            CropIntent.putExtra("return-data", true);

            startActivityForResult(CropIntent, CROP_PIC);

        } catch (ActivityNotFoundException e) {

        }
    }
}