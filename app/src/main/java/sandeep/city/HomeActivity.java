package sandeep.city;

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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

/**
 * Created by sandeep on 25/10/15.
 */
public class HomeActivity extends ActionBarActivity implements View.OnClickListener {

    DrawerLayout mDrawerLayout;
    ActionBarDrawerToggle toggle;
    ListView drawerList;
    public String[] drawer_menu;
    ImageView title, report, laGroups, buzz;
    public static int screenWatcher = 0;
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
        setContentView(R.layout.home_activity);

        application = (AnalyticsApplication) getApplication();
        mTracker = application.getDefaultTracker();

        Preferences = getResources().getString(R.string.user);

        prefs = getSharedPreferences(
                Preferences, Context.MODE_PRIVATE);
        editor=prefs.edit();


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

        title = (ImageView) findViewById(R.id.ivTitle);
        laGroups = (ImageView) findViewById(R.id.ivLAGroups);
        report = (ImageView) findViewById(R.id.ivReport);
        buzz = (ImageView) findViewById(R.id.ivBuzz);

        title.setOnClickListener(this);
        laGroups.setOnClickListener(this);
        report.setOnClickListener(this);
        buzz.setOnClickListener(this);

        HomeScreen frament = new HomeScreen();
        FragmentTransaction trans = getFragmentManager().beginTransaction();
        trans.replace(R.id.fragment, frament);
        trans.addToBackStack(null);
        trans.commit();
        screenWatcher = 1;


        drawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                switch (position) {
                    case 0:
                        if (screenWatcher != 2) {
                            screenWatcher = 2;
                            MyReports reports = new MyReports();
                            FragmentTransaction atrans = getFragmentManager().beginTransaction();
                            atrans.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.came, R.anim.went);
                            atrans.replace(R.id.fragment, reports);
                            atrans.addToBackStack(null);
                            atrans.commit();
                        }
                        break;
                    case 1:
                        if (screenWatcher != 3) {
                            screenWatcher = 3;
                            MyGroups groups = new MyGroups();
                            FragmentTransaction transs = getFragmentManager().beginTransaction();
                            transs.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.came, R.anim.went);
                            transs.replace(R.id.fragment, groups);
                            transs.addToBackStack(null);
                            transs.commit();
                        }
                        break;
                    case 2:
                        if (screenWatcher != 4) {
                            screenWatcher = 4;
                            MyPlaces frament = new MyPlaces();
                            FragmentTransaction trans = getFragmentManager().beginTransaction();
                            trans.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.came, R.anim.went);
                            trans.replace(R.id.fragment, frament);
                            trans.addToBackStack(null);
                            trans.commit();
                        }
                        break;
                    case 3:
                        if (screenWatcher != 5) {
                            screenWatcher = 5;
                            About about = new About();
                            FragmentTransaction abtrans = getFragmentManager().beginTransaction();
                            abtrans.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.came, R.anim.went);
                            abtrans.replace(R.id.fragment, about);
                            abtrans.addToBackStack(null);
                            abtrans.commit();
                        }
                        break;
                    case 4:
                        if (screenWatcher != 6) {
                            screenWatcher = 6;
                            Help help = new Help();
                            FragmentTransaction helptrans = getFragmentManager().beginTransaction();
                            helptrans.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.came, R.anim.went);
                            helptrans.replace(R.id.fragment, help);
                            helptrans.addToBackStack(null);
                            helptrans.commit();
                        }
                        break;
                    case 5:
                        Intent i = new Intent(HomeActivity.this, FBLogin.class);
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
    public void onBackPressed() {
        super.onBackPressed();
        if (screenWatcher != 1) {
            HomeScreen frament = new HomeScreen();
            FragmentTransaction trans = getFragmentManager().beginTransaction();
            trans.replace(R.id.fragment, frament);
            trans.addToBackStack(null);
            trans.commit();
            screenWatcher = 1;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.ivReport:
                if (screenWatcher != 7) {
                    screenWatcher = 7;

                    mTracker.send(new HitBuilders.EventBuilder()
                            .setCategory(getString(R.string.views))
                            .setAction(getString(R.string.click))
                            .setLabel(getString(R.string.report))
                            .build());

                    SelectSector f = new SelectSector();
                    FragmentTransaction t = getFragmentManager()
                            .beginTransaction();
                    t.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.came, R.anim.went);
                    t.replace(R.id.fragment, f);
                    t.addToBackStack(null);
                    t.commit();
                }
                break;
            case R.id.ivLAGroups:
                if (screenWatcher != 8) {
                    screenWatcher = 8;

                    mTracker.send(new HitBuilders.EventBuilder()
                            .setCategory(getString(R.string.views))
                            .setAction(getString(R.string.click))
                            .setLabel(getString(R.string.lagroups))
                            .build());

                    AllLocalActionGroups fragment = new AllLocalActionGroups();
                    FragmentTransaction transaction = getFragmentManager()
                            .beginTransaction();
                    transaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.came, R.anim.went);
                    transaction.replace(R.id.fragment, fragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
                break;
            case R.id.ivBuzz:
                if (screenWatcher != 9) {

                    screenWatcher = 9;

                    mTracker.send(new HitBuilders.EventBuilder()
                            .setCategory(getString(R.string.views))
                            .setAction(getString(R.string.click))
                            .setLabel(getString(R.string.buzz))
                            .build());

                    Buzz fragmen = new Buzz();
                    FragmentTransaction transact = getFragmentManager()
                            .beginTransaction();
                    transact.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.came, R.anim.went);
                    transact.replace(R.id.fragment, fragmen);
                    transact.addToBackStack(null);
                    transact.commit();
                }
                break;
            case R.id.ivTitle:
                if (screenWatcher != 1) {
                    screenWatcher = 1;

                    mTracker.send(new HitBuilders.EventBuilder()
                            .setCategory(getString(R.string.views))
                            .setAction(getString(R.string.click))
                            .setLabel(getString(R.string.home))
                            .build());

                    HomeScreen frament = new HomeScreen();
                    FragmentTransaction trans = getFragmentManager().beginTransaction();
                    trans.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.came, R.anim.went);
                    trans.replace(R.id.fragment, frament);
                    trans.addToBackStack(null);
                    trans.commit();
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
}
