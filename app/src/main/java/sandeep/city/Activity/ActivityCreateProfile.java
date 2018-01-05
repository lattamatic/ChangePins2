package sandeep.city.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import sandeep.city.ChangePinsActivity;
import sandeep.city.R;

/**
 * Created by sandeep on 21/12/15.
 */
public class ActivityCreateProfile extends ChangePinsActivity {

    private EditText name, email, phone;
    private Button next;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeViews(); //instantiating the views

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ActivityCreateProfile.this,ActivityHome.class));
                finish();
            }
        });
    }

    @Override
    protected int getTitleText() {
        return R.string.createprofile;
    }

    @Override
    protected int getLayout() {
        return R.layout.ac_createprofile;
    }

    private void initializeViews(){
        name = (EditText) findViewById(R.id.etUserName);
        email = (EditText) findViewById(R.id.etUserEmail);
        phone = (EditText) findViewById(R.id.etUserMobile);
        next = (Button) findViewById(R.id.bContinue);
    }
}
