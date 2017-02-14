package sandeep.city.Activity;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.auth.api.credentials.internal.SaveRequest;
import com.google.android.gms.common.api.GoogleApiClient;

import sandeep.city.AnalyticsApplication;
import sandeep.city.Fragment.FragmentAbout;
import sandeep.city.Fragment.FragmentBuzz;
import sandeep.city.Fragment.FragmentHelp;
import sandeep.city.Fragment.FragmentMyPlaces;
import sandeep.city.Fragment.FragmentHomeScreen;
import sandeep.city.Fragment.FragmentMyReports;
import sandeep.city.Fragment.FragmentPrivateSector;
import sandeep.city.Fragment.FragmentPublicSector;
import sandeep.city.Fragment.FragmentSelectSector;
import sandeep.city.InterfaceOnClickCategory;
import sandeep.city.R;

/**
 * Created by sandeep on 25/10/15.
 */
public class ActivityHome extends ActionBarActivity implements View.OnClickListener, FragmentSelectSector.SelectSectorInterface, InterfaceOnClickCategory, FragmentMyReports.OnClickAddReport {

    DrawerLayout mDrawerLayout;
    ActionBarDrawerToggle toggle;
    ListView drawerList;
    public String[] drawer_menu;
    ImageView report, buzz;
    TextView title;
    Toolbar toolbar;
    Tracker mTracker;
    AnalyticsApplication application;
    public static TextView userName;
    public static ImageView userImage;
    public static String category;
    String Preferences;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;

    static final String homeScreen = "Home Screen";
    static final String reports = "Reports";
    static final String places = "Places";
    static final String about = "About";
    static final String help = "Help";
    static final String selectSector = "Select Sector";
    static final String buz = "Buzz";
    static final String publicSector = "Public Sector";
    static final String privateSector = "Private Sector";


    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_home);

        application = (AnalyticsApplication) getApplication();
        mTracker = application.getDefaultTracker();

        Preferences = getResources().getString(R.string.user);
        prefs = getSharedPreferences(
                Preferences, Context.MODE_PRIVATE);
        editor = prefs.edit();

        //instatiating the views
        drawerList = (ListView) findViewById(R.id.list_slidermenu);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        title = (TextView) findViewById(R.id.tvTitle);
        report = (ImageView) findViewById(R.id.ivReport);
        buzz = (ImageView) findViewById(R.id.ivBuzz);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        //ActionBar related
        setSupportActionBar(toolbar);
        final ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.drawer_icon);
        ab.setDisplayHomeAsUpEnabled(true);
        title.setOnClickListener(this);
        report.setOnClickListener(this);
        buzz.setOnClickListener(this);

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
                        popAFragment(reports);
                        break;
                    case 1:
                        popAFragment(places);
                        break;
                    case 2:
                        popAFragment(about);
                        break;
                    case 3:
                        popAFragment(help);
                        break;
                    case 4:
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
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.ivReport:
                mTracker.send(new HitBuilders.EventBuilder()
                        .setCategory(getString(R.string.views))
                        .setAction(getString(R.string.click))
                        .setLabel(getString(R.string.report))
                        .build());
                popAFragment(selectSector);
                break;

            case R.id.ivBuzz:
                mTracker.send(new HitBuilders.EventBuilder()
                        .setCategory(getString(R.string.views))
                        .setAction(getString(R.string.click))
                        .setLabel(getString(R.string.buzz))
                        .build());
                popAFragment(buz);
                break;
            case R.id.tvTitle:

                Log.d("title click", "" + getFragmentManager().findFragmentByTag(homeScreen).isVisible());

                if (!getFragmentManager().findFragmentByTag(homeScreen).isVisible()) {
                    popAFragment(homeScreen);
                } else {

                }
                break;
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
                "Home Page", // TODO: Define a title for the content shown.
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
                "Home Page", // TODO: Define a title for the content shown.
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

    @Override
    public void onBackPressed() {

        //Closes if Navigation Drawer is in open state
        //If the current fragment is homescreen, closes the activity
        //If the current fragment is either private or public sector category screen, takes back to the previous fragment(which is Choosing Sector)
        //If the current fragment is anything else takes back to homescreen

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

    @Override
    public void onClickPublic() {
        //This is onclick listener from the fragment choosing sector
        popAFragment(publicSector);
    }

    @Override
    public void onClickPrivate() {
        //This is onclick listener from the fragment choosing sector
        popAFragment(privateSector);
    }

    @Override
    public void onClickCategory(String category) {
        //This is onclick listener from the fragments public/private sectors
        Intent intent = new Intent(this, ActivityRegisterComplaint.class);
        intent.putExtra("category", category);
        startActivity(intent);
    }

    @Override
    public void onClickAddReport() {
        //onclick listener to add a new report from Reports fragment
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
                fragment1 = new FragmentPrivateSector();
                break;
            case publicSector:
                fragment1 = new FragmentPublicSector();
                break;
        }

        transaction.replace(R.id.fragment,fragment1,fragment);
        transaction.addToBackStack(fragment);
        transaction.commit();
    }
}