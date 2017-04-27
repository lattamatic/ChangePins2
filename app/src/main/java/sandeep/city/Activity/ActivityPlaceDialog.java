package sandeep.city.Activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

import sandeep.city.POJO.SinglePlace;
import sandeep.city.R;
import sandeep.city.SQLiteClasses.PlacesDataSource;

/**
 * Created by sandeep_chi on 4/26/2017.
 */

public class ActivityPlaceDialog extends AppCompatActivity implements View.OnClickListener {

    private Button pickLocation, done, cancel;
    private TextView placeAddress;
    private Place place;
    private EditText placeName;
    private int state;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_set_place);

        pickLocation = (Button) findViewById(R.id.bPickLocation);
        done = (Button) findViewById(R.id.bLocationDone);
        cancel = (Button) findViewById(R.id.bLocationCancel);
        placeAddress = (TextView) findViewById(R.id.tvPlaceAddress);
        placeName = (EditText) findViewById(R.id.etPlaceName);

        state  = getIntent().getIntExtra("state",0);

        pickLocation.setOnClickListener(this);
        done.setOnClickListener(this);
        cancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bPickLocation:
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                try {
                    startActivityForResult(builder.build(this), 1);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                    Toast.makeText(this, "Google Play Services not available on this device",
                            Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.bLocationDone:
                String gen = "";
                String string = placeName.getText().toString();
                if (string.equals(gen)) {
                    Toast.makeText(this, "Location title cannot be empty",
                            Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    if(state==1){
                        if(place!=null) {
                            new AsyncAddPlace().execute
                                    (new SinglePlace(string, place.getLatLng().latitude, place.getLatLng().longitude));
                        }
                        else {
                            new AsyncAddPlace().execute(new SinglePlace(string));
                        }
                    }else if (state==2){
                        new AsyncUpdatePlace().execute
                                (new SinglePlace(string, place.getLatLng().latitude, place.getLatLng().longitude));
                    }
                }
                break;
            case R.id.bLocationCancel:
                finish();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 1) {
            place = PlacePicker.getPlace(data, this);
            placeAddress.setText("Location:\n"+place.getAddress());
        }
    }

    //AsyncTask to post a place to Internal DB
    private class AsyncAddPlace extends AsyncTask<SinglePlace, Void, SinglePlace> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected SinglePlace doInBackground(SinglePlace... params) {
            PlacesDataSource dataSource = new PlacesDataSource(ActivityPlaceDialog.this);
            dataSource.open();
            SinglePlace place = dataSource.createPlace(params[0]);
            dataSource.close();
            return place;
        }

        @Override
        protected void onPostExecute(SinglePlace place) {
            Intent intent = new Intent();
            intent.putExtra("place_title",place.getTitle() );
            intent.putExtra("place_lat",place.getLatitute());
            intent.putExtra("place_lon",place.getLongitude());
            intent.putExtra("place_address",place.getAddress());
            intent.putExtra("place_id", place.getId());
            setResult(RESULT_OK,intent);
            finish();
        }
    }

    //AsyncTask to post a place to Internal DB
    private class AsyncUpdatePlace extends AsyncTask<SinglePlace, Void, SinglePlace> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected SinglePlace doInBackground(SinglePlace... params) {
            PlacesDataSource dataSource = new PlacesDataSource(ActivityPlaceDialog.this);
            dataSource.open();
            SinglePlace place = dataSource.updatePlace(params[0]);
            dataSource.close();
            return place;
        }

        @Override
        protected void onPostExecute(SinglePlace place) {
            Intent intent = new Intent();
            intent.putExtra("place_title",place.getTitle() );
            intent.putExtra("place_lat",place.getLatitute());
            intent.putExtra("place_lon",place.getLongitude());
            intent.putExtra("place_address",place.getAddress());
            intent.putExtra("place_id", place.getId());
            setResult(RESULT_OK,intent);
            finish();
        }
    }
}
