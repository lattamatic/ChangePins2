package sandeep.city.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.analytics.Tracker;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;

import sandeep.city.AnalyticsApplication;
import sandeep.city.DownloadImageTask;
import sandeep.city.R;

public class ActivityRegisterComplaint extends Activity implements OnClickListener {

    TextView title, location_set;
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
    public static TextView locMessage;
    public static ImageView staticMap;
    public static ProgressBar progressBar;
    ImageView back;

    private String location_string;
    private Uri picUri;

    Tracker mTracker;
    AnalyticsApplication application;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_register_complaint);

        application = (AnalyticsApplication) getApplication();
        mTracker = application.getDefaultTracker();


      //  dh = new DataHelp(this);
        title = (TextView) findViewById(R.id.tvCategory);
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


        title.setText(getIntent().getStringExtra("category"));

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
//                if (ettitle.getText().toString().matches("")) {
//                    Toast.makeText(this, "Title cannot be empty",
//                            Toast.LENGTH_SHORT).show();
//                } else if (description.getText().toString().matches("")) {
//                    Toast.makeText(this, "Description cannot be empty",
//                            Toast.LENGTH_SHORT).show();
//                }
//// else if (location_set.getText().toString().matches("")) {
////                    Toast.makeText(this, "Turn on GPS and add location",
////                            Toast.LENGTH_SHORT).show();
////                }
//                else if (staticMap.getVisibility() == View.GONE) {
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
//                } else {
//                    mTracker.send(new HitBuilders.EventBuilder()
//                            .setCategory(getString(R.string.views))
//                            .setAction(getString(R.string.click))
//                            .setLabel(getString(R.string.succesfulreport))
//                            .build());
//
//                    tit = ettitle.getText().toString();
//                    descString = description.getText().toString();
//                    //loc_address = location_set.getText().toString();
//
//                    BitmapDrawable bitmapDrawable = (BitmapDrawable) imageView.getDrawable();
//                    Bitmap mIcon = bitmapDrawable.getBitmap();
//
//
//                  //  ArrayList<SingleReport> gen = dh.getReports();
//                   // String file_id = String.valueOf(gen.size() + 1);
//
//                    File sdCardDirectory = Environment.getExternalStorageDirectory();
//                    sdCardDirectory = new File(sdCardDirectory.getAbsolutePath() + "/ChangePins/");
//                    sdCardDirectory.mkdirs();
//                    //File image = new File(sdCardDirectory, file_id + ".jpg");
//                    //String file_path = image.getAbsolutePath();
//                    boolean success = false;
//
//                    // Encode the file as a PNG image.
//                    FileOutputStream outStream;
//                    try {
//
//                      //  outStream = new FileOutputStream(image);
//                        //mIcon.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
//        /* 100 to keep full quality of the image */
//
//                        //outStream.flush();
//                        //outStream.close();
//                        success = true;
//                    } catch (FileNotFoundException e) {
//                        e.printStackTrace();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                    if (success) {
//                        Toast.makeText(this, "Reported successfully",
//                                Toast.LENGTH_LONG).show();
//                        dh.report_insert(tit, descString, file_id, latitude, longitude, loc_address, title.getText().toString());
//
//                        imageView.setImageResource(R.drawable.frame);
//                        locMessage.setVisibility(View.VISIBLE);
//                        staticMap.setVisibility(View.GONE);
//                        ettitle.setText("");
//                        description.setText("");
//
//
//                    } else {
//                        Toast.makeText(this,
//                                "Error during image saving", Toast.LENGTH_LONG).show();
//                    }
//
//                }
//
//
//                break;
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

            new DownloadImageTask(staticMap, locMessage, progressBar).execute(url);
        }

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

    public void showSettingsAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                this);
        alertDialog.setTitle("SETTINGS");
        alertDialog.setMessage("Enable Location Provider! Go to settings menu?");
        alertDialog.setPositiveButton("Settings",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(
                                Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(intent);
                    }
                });
        alertDialog.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        alertDialog.show();
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
            // indicate aspect of desired crop
            cropIntent.putExtra("aspectX", 1);
            cropIntent.putExtra("aspectY", 1);
            // indicate output X and Y
            cropIntent.putExtra("outputX", 256);
            cropIntent.putExtra("outputY", 256);
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
}