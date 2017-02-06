package sandeep.city.Activity;

import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
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
import com.google.android.gms.common.api.GoogleApiClient;

import sandeep.city.AnalyticsApplication;
import sandeep.city.Fragment.FragmentAbout;
import sandeep.city.Fragment.FragmentHelp;
import sandeep.city.Fragment.FragmentMyPlaces;
import sandeep.city.Fragment.FragmentHomeScreen;
import sandeep.city.Fragment.FragmentMyReports;
import sandeep.city.R;

/**
 * Created by sandeep on 25/10/15.
 */
public class ActivityHome extends ActionBarActivity implements View.OnClickListener {

    DrawerLayout mDrawerLayout;
    ActionBarDrawerToggle toggle;
    ListView drawerList;
    public String[] drawer_menu;
    ImageView  report, buzz;
    TextView title;
    Tracker mTracker;
    AnalyticsApplication application;
    public static TextView userName;
    public static ImageView userImage;
    public static String category;
    String Preferences;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;

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


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.drawer_icon);
        ab.setDisplayHomeAsUpEnabled(true);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.app_name, R.string.app_name);
        mDrawerLayout.setDrawerListener(toggle);

        drawerList = (ListView) findViewById(R.id.list_slidermenu);

        drawer_menu = getResources().getStringArray(
                R.array.nav_drawer_list_menu);
        drawerList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, drawer_menu));

        title = (TextView) findViewById(R.id.tvTitle);
        report = (ImageView) findViewById(R.id.ivReport);
        buzz = (ImageView) findViewById(R.id.ivBuzz);

        title.setOnClickListener(this);
        report.setOnClickListener(this);
        buzz.setOnClickListener(this);

        FragmentHomeScreen frament = new FragmentHomeScreen();
        FragmentTransaction trans = getFragmentManager().beginTransaction();
        trans.add(R.id.fragment, frament,"Home Screen");
        trans.commit();


        drawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                switch (position) {
                    case 0:
                        FragmentMyReports fragmentMyReports = new FragmentMyReports();
                        FragmentTransaction fTransaction = getFragmentManager().beginTransaction();
                        fTransaction.replace(R.id.fragment, fragmentMyReports,"My Reports");
                        fTransaction.addToBackStack(null);
                        fTransaction.commit();
                        break;

                    case 1:
                        FragmentMyPlaces frament = new FragmentMyPlaces();
                        FragmentTransaction trans = getFragmentManager().beginTransaction();
                        trans.replace(R.id.fragment, frament,"My Places");
                        trans.addToBackStack(null);
                        trans.commit();
                        break;
                    case 2:
                        FragmentAbout fragmentAbout = new FragmentAbout();
                        FragmentTransaction tr = getFragmentManager().beginTransaction();
                        tr.replace(R.id.fragment,fragmentAbout,"About");
                        tr.addToBackStack(null);
                        tr.commit();
                        break;
                    case 3:
                        FragmentHelp fragmentHelp = new FragmentHelp();
                        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.fragment, fragmentHelp,"Help");
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
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
                startActivity(new Intent(ActivityHome.this, ActivityChooseCategory.class));
                break;

            case R.id.ivBuzz:
                mTracker.send(new HitBuilders.EventBuilder()
                        .setCategory(getString(R.string.views))
                        .setAction(getString(R.string.click))
                        .setLabel(getString(R.string.buzz))
                        .build());

                startActivity(new Intent(ActivityHome.this, ActivityBuzz.class));
                break;
            case R.id.tvTitle:

                Log.d("title click",""+getFragmentManager().findFragmentByTag("Home Screen").isVisible());

                if (!getFragmentManager().findFragmentByTag("Home Screen").isVisible()) {
                    FragmentHomeScreen frament = new FragmentHomeScreen();
                    FragmentTransaction trans = getFragmentManager().beginTransaction();
                    trans.replace(R.id.fragment, frament,"Home Screen");
                    trans.addToBackStack(null);
                    trans.commit();
                }else{

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
        Log.d("back click",""+getFragmentManager().findFragmentByTag("Home Screen").isVisible());
        if (!getFragmentManager().findFragmentByTag("Home Screen").isVisible()) {
            FragmentHomeScreen frament = new FragmentHomeScreen();
            FragmentTransaction trans = getFragmentManager().beginTransaction();
            trans.add(R.id.fragment, frament,"Home Screen");
            trans.commit();
        } else {
            finish();
        }
    }
}