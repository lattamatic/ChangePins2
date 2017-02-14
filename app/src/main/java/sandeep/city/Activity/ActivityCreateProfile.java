package sandeep.city.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;

import sandeep.city.ChangePinsActivity;
import sandeep.city.R;

/**
 * Created by sandeep on 21/12/15.
 */
public class ActivityCreateProfile extends ChangePinsActivity {


    EditText name, email, phone;
    Button next;
    ImageView dp;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //instantiating the views
        email = (EditText) findViewById(R.id.etEmail);
        phone = (EditText) findViewById(R.id.etPhone);
        next = (Button) findViewById(R.id.bContinue);
        dp = (ImageView) findViewById(R.id.ivDP);
        name = (EditText) findViewById(R.id.etName);
        preferences=getSharedPreferences("user", Context.MODE_PRIVATE);
        name.setText(preferences.getString("user_name",""));

        if(getIntent().getStringExtra("email")!=""){
            email.setText(getIntent().getStringExtra("email"));
        }

        File imgFile = new File("/sdcard/ChangePins/changepins_dp.png");

        if(imgFile.exists()){

            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());

            dp.setImageBitmap(myBitmap);

        }else{
            Toast.makeText(getApplicationContext(), "No image", Toast.LENGTH_SHORT).show();
        }

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ActivityCreateProfile.this,ActivityHome.class));
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
}
