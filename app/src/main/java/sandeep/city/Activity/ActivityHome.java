package sandeep.city.Activity;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONException;
import org.json.JSONObject;

import sandeep.city.ChangePinsApplication;
import sandeep.city.Fragment.FragmentAbout;
import sandeep.city.Fragment.FragmentBuzz;
import sandeep.city.Fragment.FragmentHelp;
import sandeep.city.Fragment.FragmentHomeScreen;
import sandeep.city.Fragment.FragmentMyPlaces;
import sandeep.city.Fragment.FragmentMyReports;
import sandeep.city.Fragment.FragmentPublicSector;
import sandeep.city.Fragment.FragmentSelectSector;
import sandeep.city.Fragment.FragmentSocialSector;
import sandeep.city.InterfaceOnClickCategory;
import sandeep.city.R;

/**
 * Created by sandeep on 25/10/15.
 */
public class ActivityHome extends AppCompatActivity implements FragmentSelectSector.SelectSectorInterface,
        InterfaceOnClickCategory, FragmentMyReports.OnClickAddReport{

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle toggle;
    private ListView drawerList;
    private String drawer_menu[], Preferences;
    private ImageView report, buzz, profilePic;
    private TextView title, profileName, categoryDescription;
    private Toolbar toolbar;
    private Tracker mTracker;
    private ChangePinsApplication application;
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;

    private static final String homeScreen = "Home Screen",
            reports = "Reports",
            places = "Places",
            about = "About",
            help = "Help",
            selectSector = "Select Sector",
            buz = "Buzz",
            publicSector = "Public Sector",
            privateSector = "Private Sector";

    private static final int REPORTED_COMPLAINT = 100;

    private GoogleApiClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_home);
        application = (ChangePinsApplication) getApplication();
        mTracker = application.getDefaultTracker();
        Preferences = getResources().getString(R.string.user_preferences);
        prefs = getSharedPreferences(
                Preferences, Context.MODE_PRIVATE);
        editor = prefs.edit();

        initializeViews(); //instatiating the views
        //ActionBar related
        setSupportActionBar(toolbar);
        final ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.drawer_icon);
        ab.setDisplayHomeAsUpEnabled(true);
        title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!getFragmentManager().findFragmentByTag(homeScreen).isVisible()) {
                    popAFragment(homeScreen);
                }
            }
        });
        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTracker.send(new HitBuilders.EventBuilder()
                        .setCategory(getString(R.string.views))
                        .setAction(getString(R.string.click))
                        .setLabel(getString(R.string.report))
                        .build());
                popAFragment(selectSector);
            }
        });
        buzz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTracker.send(new HitBuilders.EventBuilder()
                        .setCategory(getString(R.string.views))
                        .setAction(getString(R.string.click))
                        .setLabel(getString(R.string.buzz))
                        .build());
                popAFragment(buz);
            }
        });

        //Drawer related
        toggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.app_name, R.string.app_name);
        mDrawerLayout.setDrawerListener(toggle);
        drawer_menu = getResources().getStringArray(
                R.array.nav_drawer_list_menu);
        drawerList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, drawer_menu));
        drawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                switch (position) {
                    case 0:
                        popAFragment(homeScreen);
                        break;
                    case 1:
                        popAFragment(reports);
                        break;
                    case 2:
                        popAFragment(places);
                        break;
                    case 3:
                        popAFragment(about);
                        break;
                    case 4:
                        popAFragment(help);
                        break;
                    case 5:
                        Intent i = new Intent(ActivityHome.this, ActivityFBLogin.class);
                        startActivity(i);
                        finish();
                        break;
                }
                mDrawerLayout.closeDrawers();
            }
        });

        //Adding fragment to activity
        popAFragment(privateSector);
        popAFragment(publicSector);
        popAFragment(homeScreen);

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

        if (AccessToken.getCurrentAccessToken() != null) {
            GraphRequest request = GraphRequest.newMeRequest(
                    AccessToken.getCurrentAccessToken(),
                    new GraphRequest.GraphJSONObjectCallback() {
                        @Override
                        public void onCompleted(
                                JSONObject object,
                                GraphResponse response) {
                            // Application code
                            try {
                                profileName.setText(object.getString("name"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
            Bundle parameters = new Bundle();
            parameters.putString("fields", "id,name,link, email, location, birthday, gender");
            request.setParameters(parameters);
            request.executeAsync();

            Bundle params = new Bundle();
            params.putBoolean("redirect", false);
            params.putInt("height", 100);
            params.putInt("width", 100);
            new GraphRequest(
                    AccessToken.getCurrentAccessToken(),
                    "/" + AccessToken.getCurrentAccessToken().getUserId() + "/picture",
                    params,
                    HttpMethod.GET,
                    new GraphRequest.Callback() {
                        public void onCompleted(GraphResponse response) {
                                /* handle the result */
                            if (response != null) {
                                Log.d("response image", response.toString());
                                try {
                                    JSONObject data = response.getJSONObject().getJSONObject("data");
                                    Log.d("data", data.toString());
                                    if (data.has("url")) {
                                        String profilePicUrl = data.getString("url");
                                        Glide.with(ActivityHome.this).load(profilePicUrl).
                                                into(profilePic);
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            } else {
                                Log.d("fb response image", "null");
                            }
                        }
                    }
            ).executeAsync();
        }
        else{
            Toast.makeText(this,"Not logged into FB",Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Home Page", // TODO: Define a category for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://sandeep.city/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Home Page", // TODO: Define a category for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://sandeep.city/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }

    //Closes if Navigation Drawer is in open state
    //If the current fragment is homescreen, closes the activity
    //If the current fragment is either private or public sector category screen, takes back to the previous fragment(which is Choosing Sector)
    //If the current fragment is anything else takes back to homescreen
    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawers();
        } else {
            if (getFragmentManager().findFragmentByTag(homeScreen).isVisible()) {
                finish();
            } else {
                Fragment f1 = getFragmentManager().findFragmentByTag(privateSector);
                Fragment f2 = getFragmentManager().findFragmentByTag(publicSector);
                if (f1.isVisible() || f2.isVisible()) {
                    super.onBackPressed();
                } else {
                    popAFragment(homeScreen);
                }
            }
        }
    }

    //OnActivity result to see if the report is posted properly
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REPORTED_COMPLAINT&&resultCode==RESULT_OK){
            popAFragment(homeScreen);
        }
    }

    //This is onclick listener from the fragment choosing sector
    @Override
    public void onClickPublic() {
        popAFragment(publicSector);
    }

    //This is onclick listener from the fragment choosing sector
    @Override
    public void onClickPrivate() {
        popAFragment(privateSector);
    }

    //This is onclick listener from the fragments public/private sectors
    @Override
    public void onClickCategory(String category) {
        Intent intent = new Intent(this, ActivityRegisterComplaint.class);
        intent.putExtra("category", category);
        startActivityForResult(intent, REPORTED_COMPLAINT);
    }

    @Override
    public void onLongClickCategory(String category, String content) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(category);
        categoryDescription = new TextView(this);
        categoryDescription.setText(content);
        builder.setView(categoryDescription);
        builder.show();
    }

    //onclick listener to add a new report from Reports fragment
    @Override
    public void onClickAddReport() {
        popAFragment(selectSector);
    }

    //This method lays the fragment on click of something
    private void popAFragment(String fragment){
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        Fragment fragment1 = null;
        switch (fragment){
            case homeScreen:
                fragment1 = new FragmentHomeScreen();
                break;
            case reports:
                fragment1 = new FragmentMyReports();
                break;
            case places:
                fragment1 = new FragmentMyPlaces();
                break;
            case about:
                fragment1 = new FragmentAbout();
                break;
            case help:
                fragment1 = new FragmentHelp();
                break;
            case selectSector:
                fragment1 = new FragmentSelectSector();
                break;
            case buz:
                fragment1 = new FragmentBuzz();
                break;
            case privateSector:
                fragment1 = new FragmentSocialSector();
                break;
            case publicSector:
                fragment1 = new FragmentPublicSector();
                break;
        }
        transaction.replace(R.id.fragment,fragment1,fragment);
        transaction.addToBackStack(fragment);
        transaction.commitAllowingStateLoss();
    }


    //initializes UI elements
    private void initializeViews(){
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerList = (ListView) findViewById(R.id.list_slidermenu);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        report = (ImageView) findViewById(R.id.ivReport);
        buzz = (ImageView) findViewById(R.id.ivBuzz);
        profilePic = (ImageView) findViewById(R.id.ivProfilepic);
        title = (TextView) findViewById(R.id.tvTitle);
        profileName = (TextView) findViewById(R.id.tvProfileName);
    }
}