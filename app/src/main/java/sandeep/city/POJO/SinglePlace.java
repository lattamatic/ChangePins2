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
import sandeep.city.LocationAddress;
import sandeep.city.R;

public class SinglePlace {


    String title, address;
    long latitute, longitude;
    int id;


    public SinglePlace(int id, String title, String address, long latitute, long longitude) {
        this.id = id;
        this.title = title;
        this.address = address;
        this.latitute = latitute;
        this.longitude = longitude;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public long getLatitute() {
        return latitute;
    }

    public void setLatitute(long latitute) {
        this.latitute = latitute;
    }

    public long getLongitude() {
        return longitude;
    }

    public void setLongitude(long longitude) {
        this.longitude = longitude;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
