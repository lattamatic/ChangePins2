package sandeep.city.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import sandeep.city.DownloadImageTask;
import sandeep.city.POJO.SingleReport;
import sandeep.city.R;
import sandeep.city.SQLiteClasses.ReportsDataSource;

public class ActivityRegisterComplaint extends Activity implements  DownloadImageTask.DownloadImage{

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
    public Bitmap bitmap;
    private TextView locMessage;
    private ImageView staticMap;
    private ProgressBar progressBar;
    ImageView back;
    Place place;
    public static String submitIntent = "Submit Intent";

    private String location_string;
    private Uri mImageCaptureUri;


    public interface OnSubmitReport{
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
                Intent intent = new Intent();

                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);

                startActivityForResult(Intent.createChooser(intent,
                        "Complete action using"), SELECT_PIC);
            }
        });
        takePic.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * To take a photo from camera, pass intent action
                 * ‘MediaStore.ACTION_IMAGE_CAPTURE‘ to open the camera app.
                 */
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                /**
                 * Also specify the Uri to save the image on specified path
                 * and file name. Note that this Uri variable also used by
                 * gallery app to hold the selected image path.
                 */
                mImageCaptureUri = Uri.fromFile(new File(Environment
                        .getExternalStorageDirectory(), "tmp_avatar_"
                        + String.valueOf(System.currentTimeMillis())
                        + ".jpg"));

                intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT,
                        mImageCaptureUri);

                try {
                    intent.putExtra("return-data", true);

                    startActivityForResult(intent, TAKE_PICTURE);
                } catch (ActivityNotFoundException e) {
                    e.printStackTrace();
                }
            }
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
                            description.getText().toString(), "NoImage",0);
                    async.execute(report);
                } else {
                    String saveImage = saveImageInMobile(((BitmapDrawable) imageView.getDrawable()).getBitmap());
                    AsyncSubmitReport async = new AsyncSubmitReport();
                    SingleReport report = new SingleReport(category.getText().toString(), ettitle.getText().toString(),
                            description.getText().toString(), saveImage,0);
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
            if (bitmap != null) {
                bitmap.recycle();
            }
            doCrop();
        } else if (requestCode == SELECT_PIC && resultCode == RESULT_OK) {
            mImageCaptureUri = data.getData();
            doCrop();
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
            place = PlacePicker.getPlace(data, this);
            LatLng ll = place.getLatLng();
            double lat = ll.latitude;
            double lon = ll.longitude;
            String url = "https://maps.googleapis.com/maps/api/staticmap?center=" + lat + "," + lon + "&zoom=19&size=600x300&maptype=normal";
            new DownloadImageTask(ActivityRegisterComplaint.this).execute(url);
        }
    }

    @Override
    public void onImageDownloadCompleted(Bitmap result) {
        staticMap.setImageBitmap(result);
        staticMap.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
        locMessage.setVisibility(View.VISIBLE);
        locMessage.setText(place.getAddress());
    }

    @Override
    public void onImageDownloadStart() {
        progressBar.setVisibility(View.VISIBLE);
        locMessage.setVisibility(View.GONE);
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
            LocalBroadcastManager.getInstance(ActivityRegisterComplaint.this).sendBroadcast(new Intent(submitIntent));
            Toast.makeText(ActivityRegisterComplaint.this,"Report submitted successfully!", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    public class CropOptionAdapter extends ArrayAdapter<CropOption> {
        private ArrayList<CropOption> mOptions;
        private LayoutInflater mInflater;

        public CropOptionAdapter(Context context, ArrayList<CropOption> options) {
            super(context, R.layout.view_crop_selector, options);

            mOptions = options;

            mInflater = LayoutInflater.from(context);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup group) {
            if (convertView == null)
                convertView = mInflater.inflate(R.layout.view_crop_selector, null);

            CropOption item = mOptions.get(position);

            if (item != null) {
                ((ImageView) convertView.findViewById(R.id.iv_icon))
                        .setImageDrawable(item.icon);
                ((TextView) convertView.findViewById(R.id.tv_name))
                        .setText(item.title);

                return convertView;
            }

            return null;
        }
    }

    public class CropOption {
        public CharSequence title;
        public Drawable icon;
        public Intent appIntent;
    }
    private void doCrop() {
        final ArrayList<CropOption> cropOptions = new ArrayList<CropOption>();
        /**
         * Open image crop app by starting an intent
         * ‘com.android.camera.action.CROP‘.
         */
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setType("image/*");

        /**
         * Check if there is image cropper app installed.
         */
        List<ResolveInfo> list = getPackageManager().queryIntentActivities(
                intent, 0);

        int size = list.size();

        /**
         * If there is no image cropper app, display warning message
         */
        if (size == 0) {

            Toast.makeText(this, "Can not find image crop app",
                    Toast.LENGTH_SHORT).show();

            return;
        } else {
            /**
             * Specify the image path, crop dimension and scale
             */
            intent.setData(mImageCaptureUri);
            intent.putExtra("scale", true);
            intent.putExtra("return-data", true);
            /**
             * There is posibility when more than one image cropper app exist,
             * so we have to check for it first. If there is only one app, open
             * then app.
             */

            if (size == 1) {
                Intent i = new Intent(intent);
                ResolveInfo res = list.get(0);

                i.setComponent(new ComponentName(res.activityInfo.packageName,
                        res.activityInfo.name));

                startActivityForResult(i, CROP_PIC);
            } else {
                /**
                 * If there are several app exist, create a custom chooser to
                 * let user selects the app.
                 */
                for (ResolveInfo res : list) {
                    final CropOption co = new CropOption();

                    co.title = getPackageManager().getApplicationLabel(
                            res.activityInfo.applicationInfo);
                    co.icon = getPackageManager().getApplicationIcon(
                            res.activityInfo.applicationInfo);
                    co.appIntent = new Intent(intent);

                    co.appIntent
                            .setComponent(new ComponentName(
                                    res.activityInfo.packageName,
                                    res.activityInfo.name));

                    cropOptions.add(co);
                }

                CropOptionAdapter adapter = new CropOptionAdapter(
                        getApplicationContext(), cropOptions);

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Choose Crop App");
                builder.setAdapter(adapter,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int item) {
                                startActivityForResult(
                                        cropOptions.get(item).appIntent,
                                        TAKE_PICTURE);
                            }
                        });

                builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {

                        if (mImageCaptureUri != null) {
                            getContentResolver().delete(mImageCaptureUri, null,
                                    null);
                            mImageCaptureUri = null;
                        }
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();
            }
        }
    }
}