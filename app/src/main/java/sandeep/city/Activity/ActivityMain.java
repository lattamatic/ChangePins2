package sandeep.city.Activity;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.facebook.AccessToken;
import com.facebook.FacebookSdk;

import sandeep.city.R;

public class ActivityMain extends Activity {

    String Preferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(this); //necessary because we are checking if the user is already logged in
        setContentView(R.layout.ac_main);

        Preferences = getResources().getString(R.string.user);

        Thread splash = new Thread() {
            public void run() {
                try {

                    //change to other time when done with development
                    sleep(500);

                    SharedPreferences sharedPreferences = getSharedPreferences(
                            Preferences, Context.MODE_PRIVATE);
                    editor = sharedPreferences.edit();
                    if (!isLoggedIn()) {
                        Intent i = new Intent(ActivityMain.this, ActivityFBLogin.class);
                        startActivity(i);
                        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
                    } else {
                        Intent i = new Intent(ActivityMain.this, ActivityHome.class);
                        String value = "Not the first time and logged in";
                        i.putExtra("Check", value);
                        startActivity(i);
                        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
                        finish();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        splash.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    private boolean isLoggedIn() {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        return accessToken != null;
    }
}