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
        FacebookSdk.sdkInitialize(this); //FB SDK initialization
        setContentView(R.layout.ac_main);

        Preferences = getResources().getString(R.string.user);

        Thread splash = new Thread() {
            public void run() {
                try {

                    //instead of this we start background threads here to initiate all packages etc.
                    sleep(2000);

                    //
                    SharedPreferences sharedPreferences = getSharedPreferences(
                            Preferences, Context.MODE_PRIVATE);
                    editor = sharedPreferences.edit();
                    if (!isLoggedIn()) {//If user is not logged in open Login Activity
                        Intent i = new Intent(ActivityMain.this, ActivityFBLogin.class);
                        startActivity(i);
                        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
                    } else {//If the user is logged in open HomeScreen of the app
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

    //returns true if accessToken exists
    private boolean isLoggedIn() {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        return accessToken != null;
    }
}