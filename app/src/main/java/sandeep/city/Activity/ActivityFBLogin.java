package sandeep.city.Activity;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.facebook.AccessToken;

import sandeep.city.Fragment.FragmentFBLogin;
import sandeep.city.R;


public class ActivityFBLogin extends AppCompatActivity implements FragmentFBLogin.FBLoginInterface {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_fblogin);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new FragmentFBLogin())
                    .commit();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //If navigated to this screen to logout, on back click go back to homescreen
        if(isLoggedIn()){
            Intent i = new Intent(this,ActivityHome.class);
            startActivity(i);
            finish();
        }
    }

    public boolean isLoggedIn() {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        return accessToken != null;
    }

    @Override
    public void OnClickSignUp() {
        Intent i = new Intent(this,ActivityCreateProfile.class);
        startActivity(i);
    }

    @Override
    public void OnClickSkip() {
        Intent i = new Intent(this,ActivityHome.class);
        startActivity(i);
        finish();
    }

    @Override
    public void OnSuccessfulLogin() {
        Intent i = new Intent(ActivityFBLogin.this,ActivityHome.class);
        startActivity(i);
    }

    @Override
    public void OnCancelLogin() {
        Toast.makeText(this,"Login Canceled", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void OnLoginError() {
        Toast.makeText(this,"An error occured while trying to Login, please trying again.", Toast.LENGTH_SHORT).show();
    }
}