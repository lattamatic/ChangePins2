package sandeep.city.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import sandeep.city.R;

/**
 * Created by sandeep on 22/5/15.
 */
public class FragmentFBLogin extends Fragment{

    private TextView tvSkip, signup;
    private CallbackManager callbackManager;
    private AccessToken accessToken;
    private Profile profile;
    private FacebookCallback<LoginResult> callback;
    private ProfileTracker profileTracker;
    private AccessTokenTracker accessTokenTracker;
    Context c;

    FBLoginInterface fbLoginInterface;
    LoginButton loginButton;

    public interface FBLoginInterface{
        void OnClickSignUp();
        void OnClickSkip();
        void OnSuccessfulLogin();
    }


    public FragmentFBLogin() {

    }

    public FragmentFBLogin(Context c) {
        this.c = c;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Interface to pass Login events to Parent Activity
        fbLoginInterface = (FBLoginInterface) getActivity();
        FacebookSdk.sdkInitialize(getActivity().getApplicationContext());

        callbackManager = CallbackManager.Factory.create();
        callback = new FacebookCallback<LoginResult>() {

            //On successful Login
            @Override
            public void onSuccess(LoginResult loginResult) {

                fbLoginInterface.OnSuccessfulLogin();
            }

            //if user clicks back or closes the app
            @Override
            public void onCancel() {
                Toast.makeText(c,"Login Canceled", Toast.LENGTH_SHORT).show();
            }

            //if no internet or other errors
            @Override
            public void onError(FacebookException e) {
                Toast.makeText(c,"An error occured while trying to Login, please trying again.", Toast.LENGTH_SHORT).show();
            }
        };

        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                accessToken = currentAccessToken;
            }
        };
        accessTokenTracker.startTracking();

        profileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {
                if(currentProfile==null){
                    Log.d("FB profile", "problem with current profile");
                }else{
                    Log.d("FB profile", currentProfile.getName()+currentProfile.getLinkUri());
                }
            }
        };
        profileTracker.startTracking();
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
                fbLoginInterface.OnClickSignUp();
            }
        });
        tvSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fbLoginInterface.OnClickSkip();
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        profileTracker.stopTracking();
        accessTokenTracker.stopTracking();
    }
}