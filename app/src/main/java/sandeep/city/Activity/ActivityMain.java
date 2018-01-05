package sandeep.city.Activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.facebook.AccessToken;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import sandeep.city.R;

public class ActivityMain extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_main);

        Thread splash = new Thread() {

            public void run() {
                try {

                    //instead of this we start background threads here to initiate all packages etc in Application
                    // File and handle it's finish here.
                    sleep(1000);

                    //
                    if (!isLoggedIn()) {
                        //If user is not logged in open Login Activity
                        Intent i = new Intent(ActivityMain.this, ActivityLogin.class);
                        startActivity(i);
                        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
                    } else {
                        //If the user is logged in open HomeScreen of the app
                        Intent i = new Intent(ActivityMain.this, ActivityHome.class);
                        i.putExtra("login",true);
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

    //returns if the user is logged in
    private boolean isLoggedIn() {

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if (acct != null) {
            //Return true saying that user already logged in
            return true;
        }

        if (AccessToken.getCurrentAccessToken()!=null){
            //Return true saying that user already logged in
            return true;
        }
        //If the user is not logged in using either Google or FB return false
        return false;
    }
}