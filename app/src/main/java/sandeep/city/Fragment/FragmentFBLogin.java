package sandeep.city.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import sandeep.city.Activity.ActivityCreateProfile;
import sandeep.city.Activity.ActivityHome;
import sandeep.city.AnalyticsApplication;
import sandeep.city.Adapter.CustomPagerAdapter;
import sandeep.city.R;

/**
 * Created by sandeep on 22/5/15.
 */
public class FragmentFBLogin extends Fragment {

    private TextView tvSkip, signup;
    private CallbackManager callbackManager;
    private AccessToken accessToken;
    private Profile profile;
    private FacebookCallback<LoginResult> callback;
    private ProfileTracker profileTracker;
    Intent i;
    Context c;

    SharedPreferences preferences;
    String Preferences;
    SharedPreferences.Editor editor;
    Uri img_value = null;

    ViewPager _mViewPager;
    private CustomPagerAdapter _adapter;
    private RadioButton r1, r2, r3, r4;
    TextView title;
    LoginButton loginButton;
    Tracker mTracker;
    String TAG = "ActivityMain";
    AnalyticsApplication application;


    public FragmentFBLogin(){

    }

    public FragmentFBLogin(Context c) {
        this.c=c;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        application = (AnalyticsApplication) getActivity().getApplication(); //google analytics
        mTracker = application.getDefaultTracker();

        i = new Intent(getActivity(), ActivityCreateProfile.class);
        Preferences = getResources().getString(R.string.user);

        //initializing dummy data
        FacebookSdk.sdkInitialize(getActivity().getApplicationContext()); //ActivityFBLogin initialisation

        preferences = getActivity().getSharedPreferences(Preferences, Context.MODE_PRIVATE);
        editor = preferences.edit();

        callbackManager = CallbackManager.Factory.create();
        callback = new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                profileTracker = new ProfileTracker() {
                    @Override
                    protected void onCurrentProfileChanged(Profile profil, Profile profile2) {
                        profile = profile2;
                        img_value = profile.getProfilePictureUri(100, 100);
                        editor.putString("user_name", profile.getName());
                        editor.commit();
                        GraphRequest request = GraphRequest.newMeRequest(
                                accessToken,
                                new GraphRequest.GraphJSONObjectCallback() {
                                    @Override
                                    public void onCompleted(
                                            JSONObject object,
                                            GraphResponse response) {
                                        // Application code
                                        try {
                                            if (object.getString("email") != null) {
                                                i.putExtra("email", object.getString("email"));
                                            }

                                        } catch (JSONException e) {
                                            Log.e("email", "Error");
                                            i.putExtra("email", "");
                                        }
                                    }
                                });
                        Bundle parameters = new Bundle();
                        parameters.putString("fields", "id,name,link, email");
                        request.setParameters(parameters);
                        request.executeAsync();
                        img_value = profile.getProfilePictureUri(100, 100);

                        asyncTask at = new asyncTask();
                        at.execute();
                        profileTracker.stopTracking();
                    }
                };

                profileTracker.startTracking();
                accessToken = loginResult.getAccessToken();

                if (profile != null) {
                    editor.putString("user_name", profile.getName());
                    ActivityHome.userName.setText(profile.getName());                             //used to set user name in the navigation drawer

                    URL img_value = null;
                    try {
                        img_value = new URL("https://graph.facebook.com/me/picture?access_token=" + accessToken);
                    } catch (MalformedURLException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    Bitmap mIcon1 = null;
                    try {
                        mIcon1 = BitmapFactory.decodeStream(img_value.openConnection().getInputStream());
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    ActivityHome.userImage.setImageBitmap(mIcon1);                //used to set user image in the navigation drawer


                } else {
                    Log.d("name", "Problem with profile");
                }

            }

            @Override
            public void onCancel() {
                Log.d("cancel", "Cancelled");
            }

            @Override
            public void onError(FacebookException e) {
                Log.d("error", e.toString());
            }
        };
        editor.commit();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        List<String> permissions = new ArrayList<String>();
        permissions.add("public_profile");
        permissions.add("email");
        loginButton = (LoginButton) view.findViewById(R.id.login_button);
        loginButton.setReadPermissions(permissions);
        loginButton.setFragment(this);
        loginButton.registerCallback(callbackManager, callback);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_fblogin, container, false);

        tvSkip = (TextView) view.findViewById(R.id.tvSkip);
        tvSkip.setText(Html.fromHtml("<p><u>SKIP</u></p>"));

        signup = (TextView) view.findViewById(R.id.tvSignup);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(c, ActivityCreateProfile.class);
                startActivity(i);

            }
        });

        _mViewPager = (ViewPager) view.findViewById(R.id.registerPager);
        _adapter = new CustomPagerAdapter(getActivity());
        _mViewPager.setAdapter(_adapter);
        _mViewPager.setCurrentItem(0);

        sendScreenImageName();                                         //Analytics
        setupEvent(view, R.id.tvSkip, R.string.buttons, R.string.click, R.string.skip);

        r1 = (RadioButton) view.findViewById(R.id.rb1);
        r2 = (RadioButton) view.findViewById(R.id.rb2);
        r3 = (RadioButton) view.findViewById(R.id.rb3);
        r4 = (RadioButton) view.findViewById(R.id.rb4);
        r1.setChecked(true);
        title = (TextView) view.findViewById(R.id.tvAction);

        setTab();
        return view;
    }

    private void setTab() {
        _mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrollStateChanged(int position) {
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageSelected(int position) {
                // TODO Auto-generated method stub
                sendScreenImageName();
                r1.setChecked(false);
                r2.setChecked(false);
                r3.setChecked(false);
                r4.setChecked(false);
                switch (position) {
                    case 0:
                        r1.setChecked(true);
                        title.setText("Participate");
                        break;
                    case 1:
                        r2.setChecked(true);
                        title.setText("Engage");
                        break;
                    case 2:
                        r3.setChecked(true);
                        title.setText("Empower");
                        break;
                    case 3:
                        r4.setChecked(true);
                        title.setText("Enhance");
                        loginButton.setVisibility(View.VISIBLE);
                        loginButton.setClickable(true);
                        break;
                }
            }

        });

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (callbackManager.onActivityResult(requestCode, resultCode, data))
            return;
    }

    public class asyncTask extends AsyncTask<Void, Void, String> {

        ProgressDialog pd = new ProgressDialog(getActivity());
        Bitmap mIcon1 = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pd.setMessage("loading");
            pd.show();
        }

        @Override
        protected String doInBackground(Void... params) {

            mIcon1 = getImageBitmap(String.valueOf(img_value));
            return null;
        }


        @Override
        protected void onPostExecute(String aVoid) {
            super.onPostExecute(aVoid);
            File sdCardDirectory = Environment.getExternalStorageDirectory();
            sdCardDirectory = new File(sdCardDirectory.getAbsolutePath() + "/ChangePins/");
            if (!sdCardDirectory.exists()) {
                sdCardDirectory.mkdirs();
            }
            File image = new File(sdCardDirectory, "changepins_dp.png");
            boolean success = false;

            // Encode the file as a PNG image.
            FileOutputStream outStream;
            try {

                outStream = new FileOutputStream(image);
                mIcon1.compress(Bitmap.CompressFormat.PNG, 100, outStream);
        /* 100 to keep full quality of the image */

                outStream.flush();
                outStream.close();
                success = true;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (success) {
                pd.dismiss();
                i.putExtra("bmp", mIcon1);
                startActivity(i);
                getActivity().finish();
            } else {
                Toast.makeText(getActivity(),
                        "Error during image saving", Toast.LENGTH_LONG).show();
            }

        }
    }


    final private static Bitmap getImageBitmap(String url) {
        Bitmap bm = null;
        try {
            URLConnection conn = new URL(url).openConnection();
            conn.connect();
            InputStream is = conn.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is);
            bm = BitmapFactory.decodeStream(bis);
            bis.close();
            is.close();
        } catch (IOException e) {
            Log.e("error in bmp", "Error getting bitmap", e);
        }
        return bm;
    }

    private void sendScreenImageName() {
        String name = getCurrentImageTitle();

        // [START screen_view_hit]
        Log.i(TAG, "Setting screen name: " + name);
        mTracker.setScreenName("Image~" + name);
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
        // [END screen_view_hit]
    }

    private String getCurrentImageTitle() {
        int position = _mViewPager.getCurrentItem();
        String[] titles = {"Participate", "Engage", "Empower", "Enhance"};
        return titles[position];
    }

    private void clickedSkip() {
        mTracker.setScreenName("SKIP");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
    }


    private void setupEvent(View v, int buttonId, final int categoryId, final int actionId,
                            final int labelId) {
        final TextView pageviewButton = (TextView) v.findViewById(buttonId);
        pageviewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get tracker.
                Tracker t = application.getDefaultTracker();
                // Build and send an Event.
                t.send(new HitBuilders.EventBuilder()
                        .setCategory(getString(categoryId))
                        .setAction(getString(actionId))
                        .setLabel(getString(labelId))
                        .build());

                Intent i = new Intent(getActivity(), ActivityHome.class);
                startActivity(i);
            }
        });
    }
}