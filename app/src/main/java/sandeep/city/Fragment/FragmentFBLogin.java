package sandeep.city.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import java.util.ArrayList;
import java.util.List;

import sandeep.city.R;

/**
 * Created by sandeep on 22/5/15.
 */
public class FragmentFBLogin extends Fragment{

    private TextView tvSkip, signup;
    private CallbackManager callbackManager;
    private FacebookCallback<LoginResult> callback;

    private FBLoginInterface fbLoginInterface;
    private LoginButton loginButton;

    public interface FBLoginInterface{
        void OnClickSignUp();
        void OnClickSkip();
        void OnSuccessfulLogin(AccessToken accessToken);
        void OnCancelLogin();
        void OnLoginError();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Interface to pass Login events to Parent Activity
        fbLoginInterface = (FBLoginInterface) getActivity();

        callbackManager = CallbackManager.Factory.create(); //Callback Manager for FB Login
        callback = new FacebookCallback<LoginResult>() {

            //On successful Login
            @Override
            public void onSuccess(LoginResult loginResult) {
                fbLoginInterface.OnSuccessfulLogin(loginResult.getAccessToken());
            }

            //if user clicks back or closes the app
            @Override
            public void onCancel() {
                fbLoginInterface.OnCancelLogin();
            }

            //if no internet or other errors
            @Override
            public void onError(FacebookException e) {
                fbLoginInterface.OnLoginError();
            }
        };
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
        SpannableString content = new SpannableString("SKIP");
        content.setSpan(new UnderlineSpan(), 0, 4, 0);
        tvSkip.setText(content);

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
}