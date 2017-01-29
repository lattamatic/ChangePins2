package sandeep.city.POJO;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import sandeep.city.AppLocationService;
import sandeep.city.DataHelp;
import sandeep.city.LocationAddress;
import sandeep.city.R;

public class SinglePlace extends RelativeLayout implements View.OnClickListener {

	LayoutInflater inflater;
	TextView location,address;
    ImageView setLocation;
    String locName,locAddress;
    Location gpsLocation;
    AppLocationService appLocationService;
    Context c;
    Dialog d;
    String string= null;
    double latitude,longitude;
    DataHelp dh;
    public int id;


    public SinglePlace(final Context context, final int id, String locName, final double lat, double lon, final String locAddress) {
		super(context);
		// TODO Auto-generated constructor stub
        c=context;
        dh = new DataHelp(c);
        this.id=id;
		inflater = (LayoutInflater) getContext().getSystemService(
				Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.single_place, this, true);
		location=(TextView) findViewById(R.id.tvLAGLocation);
        setLocation= (ImageView) findViewById(R.id.ivSetLocation);
        address= (TextView) findViewById(R.id.tvAddress);
        setLocation.setOnClickListener(this);
        this.locName = locName;
        this.locAddress = locAddress;
        this.latitude = lat;
        this.longitude = lon;
		location.setText(locName);
        address.setText("Latitude: "+lat+"Longitude: "+lon+"\n\n"+locAddress);

        location.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                d= new Dialog(context);
                d.setContentView(R.layout.dialog_location_name);
                d.setTitle("Location name:");

                final EditText category = (EditText) d.findViewById(R.id.etCat);
                Button dialogButton = (Button) d.findViewById(R.id.bSetCat);
                // if button is clicked, close the custom dialog
                dialogButton.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String gen = "";
                        string=category.getText().toString();
                        if (string.equals(gen)) {
                            d.dismiss();
                            Toast.makeText(context, "Location title cannot be empty",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                         location.setText(string.toUpperCase());
                          dh.place_update(id,string.toUpperCase(),latitude,longitude,locAddress);
                        }
                        d.dismiss();

                    }
                });

                d.show();

                return false;
            }
        });
	}

    @Override
    public void onClick(View v) {
        appLocationService = new AppLocationService(c);

        gpsLocation = appLocationService
                .getLocation(LocationManager.GPS_PROVIDER);

        if (gpsLocation != null) {
            latitude = gpsLocation.getLatitude();
            longitude = gpsLocation.getLongitude();
            LocationAddress locationAddress = new LocationAddress();
            locationAddress.getAddressFromLocation(latitude, longitude,
                    c.getApplicationContext(), new GeocoderHandler());
        } else {
            showSettingsAlert();
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
            address.setText("Latitude: " + latitude + " Longitude: " + longitude +"\n\n"+locationAddress);
            dh.place_update(id,locName,latitude,longitude,locationAddress);
        }
    }

    public void showSettingsAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                c);
        alertDialog.setTitle("SETTINGS");
        alertDialog.setMessage("Enable Location Provider! Go to settings menu?");
        alertDialog.setPositiveButton("Settings",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(
                                Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        c.startActivity(intent);
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

}
