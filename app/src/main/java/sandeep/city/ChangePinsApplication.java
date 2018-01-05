package sandeep.city;

/**
 * Created by sandeep on 30/10/15.
 */
import android.app.Application;

import com.facebook.FacebookSdk;

import io.hasura.sdk.Hasura;
import io.hasura.sdk.ProjectConfig;


public class ChangePinsApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        //Facebook Initialization
        FacebookSdk.sdkInitialize(getApplicationContext());

        //Hasura Initialization
        ProjectConfig config = new ProjectConfig.Builder().setProjectName("batterer83").build();
        Hasura.setProjectConfig(config)
                .enableLogs() // not included by default
                .initialise(this);

    }
}