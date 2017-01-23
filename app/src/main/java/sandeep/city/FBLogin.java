package sandeep.city;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;

import com.facebook.AccessToken;


public class FBLogin extends ActionBarActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.fblogin);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new FBLoginFragment(this))
                    .commit();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(isLoggedIn()){
            Intent i = new Intent(this,HomeActivity.class);
            startActivity(i);
            finish();
        }
    }

    public boolean isLoggedIn() {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        return accessToken != null;
    }
}
