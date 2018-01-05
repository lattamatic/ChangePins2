package sandeep.city.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.common.SignInButton;

import java.util.ArrayList;
import java.util.List;

import sandeep.city.R;

/**
 * Created by sandeep on 22/5/15.
 */
public class FragmentLogin extends Fragment{

    private TextView tvSkip, signup;
    private CallbackManager callbackManager;
    private FacebookCallback<LoginResult> callback;

    private LoginInterface loginInterface;
    private LoginButton fbLoginButton;
    private SignInButton googleSignIn;

    public interface LoginInterface {
        void OnClickSignUp();
        void OnClickSkip();
        void OnSuccessfulFBLogin(AccessToken accessToken);
        void OnCancelFBLogin();
        void OnErrorFBLogin();
        void OnClickGoogleLogin();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginInterface = (LoginInterface) getActivity();        //Interface to pass Login events to Parent Activity(ActivityLogin)
        callbackManager = CallbackManager.Factory.create();     //Callback Manager for FB Login
        callback = new FacebookCallback<LoginResult>() {

            //On successful Login
            @Override
            public void onSuccess(LoginResult loginResult) {
                loginInterface.OnSuccessfulFBLogin(loginResult.getAccessToken());
            }

            //if user clicks back or closes the app
            @Override
            public void onCancel() {
                loginInterface.OnCancelFBLogin();
            }

            //if no internet or other errors
            @Override
            public void onError(FacebookException e) {
                loginInterface.OnErrorFBLogin();
            }
        };
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        List<String> permissions = new ArrayList<String>();
        permissions.add("public_profile");
        permissions.add("email");
        fbLoginButton.setReadPermissions(permissions);
        fbLoginButton.setFragment(this);
        fbLoginButton.registerCallback(callbackManager, callback);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_login, container, false);
        InitializeViews(view);
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void InitializeViews(View view){
        tvSkip = (TextView) view.findViewById(R.id.tvSkip);
        fbLoginButton = (LoginButton) view.findViewById(R.id.bFbLogin);
        googleSignIn = (SignInButton) view.findViewById(R.id.bGoogleLogin);
        signup = (TextView) view.findViewById(R.id.tvSignup);

        //Underline the SKIP Button
        SpannableString content = new SpannableString("SKIP");
        content.setSpan(new UnderlineSpan(), 0, 4, 0);
        tvSkip.setText(content);

        //Setting OnClickListeners to all the buttons
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginInterface.OnClickSignUp();
            }
        });
        tvSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginInterface.OnClickSkip();
            }
        });
        googleSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {loginInterface.OnClickGoogleLogin();
            }
        });
    }
}