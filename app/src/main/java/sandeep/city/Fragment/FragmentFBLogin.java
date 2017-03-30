package sandeep.city.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import sandeep.city.R;

/**
 * Created by sandeep on 22/5/15.
 */
public class FragmentFBLogin extends Fragment implements View.OnClickListener {

    private TextView tvSkip, signup;
    private CallbackManager callbackManager;
    private AccessToken accessToken;
    private Profile profile;
    private FacebookCallback<LoginResult> callback;
    private ProfileTracker profileTracker;
    Context c;

    SharedPreferences sharedPreferences;
    String Preferences;
    SharedPreferences.Editor editor;
    Uri img_value = null;
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
        fbLoginInterface = (FBLoginInterface) getActivity();
        Preferences = getString(R.string.user_preferences);
        FacebookSdk.sdkInitialize(getActivity().getApplicationContext());
        sharedPreferences = getActivity().getSharedPreferences(Preferences, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        callbackManager = CallbackManager.Factory.create();
        callback = new FacebookCallback<LoginResult>() {

            @Override
            public void onSuccess(LoginResult loginResult) {

                profileTracker = new ProfileTracker() {
                    @Override
                    protected void onCurrentProfileChanged(Profile profil, Profile profile2) {
                        profile = profile2;
                        editor.putString("user_name", profile.getName());
                        GraphRequest request = GraphRequest.newMeRequest(
                                accessToken,
                                new GraphRequest.GraphJSONObjectCallback() {
                                    @Override
                                    public void onCompleted(
                                            JSONObject object,
                                            GraphResponse response) {
                                        try {
                                            if (object.getString("email") != null) {
                                                editor.putString(getString(R.string.user_email),
                                                        object.getString("email"));
                                                //editor.commit();
                                            }

                                        } catch (JSONException e) {
                                            editor.putString(getString(R.string.user_email), "");
                                           // editor.commit();
                                        }
                                    }
                                });
                        Bundle parameters = new Bundle();
                        parameters.putString("fields", "id,name,link, email");
                        request.setParameters(parameters);
                        request.executeAsync();
                        img_value = profile.getProfilePictureUri(100, 100);
                        editor.putString(getString(R.string.user_image),img_value.toString());
                        editor.commit();
                        profileTracker.stopTracking();
                    }
                };

                profileTracker.startTracking();
                accessToken = loginResult.getAccessToken();

                Log.d("nm",accessToken.toString());
                if (profile != null) {
                    editor.putString(getString(R.string.username), profile.getName());
                    editor.putString(getString(R.string.user_image),
                            "https://graph.facebook.com/me/picture?access_token=" + accessToken);
                    editor.commit();
                   } else {
                    Log.d("Login", "Problem with profile");
                }
            }

            @Override
            public void onCancel() {
                Toast.makeText(c,"Login Canceled", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException e) {
                Toast.makeText(c,"An error occured while trying to Login, please trying again.", Toast.LENGTH_SHORT).show();
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
        signup.setOnClickListener(this);
        tvSkip.setOnClickListener(this);
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (callbackManager.onActivityResult(requestCode, resultCode, data))
            return;
    }

    @Override
    public void onClick(View v) {
        Intent i = null;
        switch (v.getId()){
            case R.id.tvSignup:
                fbLoginInterface.OnClickSignUp();
                break;
            case R.id.tvSkip:
                fbLoginInterface.OnClickSkip();
                break;
        }

    }

}