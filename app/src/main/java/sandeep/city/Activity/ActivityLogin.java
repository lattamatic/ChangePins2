package sandeep.city.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.hasura.sdk.Hasura;
import io.hasura.sdk.HasuraClient;
import io.hasura.sdk.HasuraSocialLoginType;
import io.hasura.sdk.HasuraUser;
import io.hasura.sdk.exception.HasuraException;
import io.hasura.sdk.responseListener.AuthResponseListener;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import sandeep.city.Fragment.FragmentLogin;
import sandeep.city.Hasura.Endpoint;
import sandeep.city.Hasura.MyLoginApi;
import sandeep.city.Hasura.HasuraDBInterface;
import sandeep.city.Hasura.HasuraFBResponse;
import sandeep.city.Hasura.InsertNewUser;
import sandeep.city.Hasura.InsertUserResponse;
import sandeep.city.R;


public class ActivityLogin extends AppCompatActivity implements FragmentLogin.LoginInterface {

    private HasuraUser hasuraUser;
    private MyLoginApi api;
    private GoogleSignInClient mGoogleSignInClient;
    private int RC_SIGN_IN = 100;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        HasuraClient client = Hasura.getClient();
        hasuraUser = client.getUser();

        setContentView(R.layout.ac_login);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new FragmentLogin())
                    .commit();
        }

        //Google SignIn Client creation
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    //If navigated to this screen to logout, on back click go back to homescreen
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(isLoggedIn()){
            Intent i = new Intent(this,ActivityHome.class);
            startActivity(i);
            finish();
        }
    }

    //Checks if the User is logged in.
    public boolean isLoggedIn() {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        return accessToken != null;
    }

    //When clicked on the SignUp
    @Override
    public void OnClickSignUp() {
        Intent i = new Intent(this,ActivityCreateProfile.class);
        startActivity(i);
    }

    //When clicked on the SKIP
    @Override
    public void OnClickSkip() {
        Intent i = new Intent(this,ActivityHome.class);
        startActivity(i);
        finish();
    }

    //When clicked on the Google signIn Buttion
    @Override
    public void OnClickGoogleLogin() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        Log.d("Google","1");
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("Google","2");
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Log.d("Google","3");
            Toast.makeText(this,"1",Toast.LENGTH_SHORT).show();
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            onGoogleSignIn(task);
        }
    }

    //On successfully login using Facebook
    @Override
    public void OnSuccessfulFBLogin(final AccessToken accessToken) {

        hasuraUser.socialLogin(HasuraSocialLoginType.FACEBOOK, accessToken.getToken().toString(),
                new AuthResponseListener() {
                    @Override
                    public void onSuccess(String message) {
                        Gson gson = new GsonBuilder()
                                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                                .create();
                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl(MyLoginApi.BASE_URL)
                                .addConverterFactory(GsonConverterFactory.create(gson))
                                .build();
                        api = retrofit.create(MyLoginApi.class);
                        Callback<HasuraFBResponse> responseCallback = new Callback<HasuraFBResponse>() {
                            @Override
                            public void onResponse(Call<HasuraFBResponse> call, Response<HasuraFBResponse> response) {

                                HasuraFBResponse hasuraFBResponse = response.body();
                                Log.d("Sandeep", "Hasura response");
//                                if(!hasuraFBResponse.getIsNewUser()){

                                    Log.d("Sandeep", "not a new user");
                                    final Retrofit retrofitDB = new Retrofit.Builder()
                                            .baseUrl(Endpoint.DB_URL)
                                            .addConverterFactory(GsonConverterFactory.create())
                                            .build();
                                    HasuraDBInterface db = retrofitDB.create(HasuraDBInterface.class);

                                    db.insertUser(new InsertNewUser("Pradeep", "CH", "chit@gmail.com", 100, 23))
                                            .enqueue(new Callback<InsertUserResponse>() {
                                        @Override
                                        public void onResponse(Call<InsertUserResponse> call, Response<InsertUserResponse> response) {
                                            Log.d("Sandeep", "Insert");
                                            Log.d("Sandeep", response.errorBody().toString());
                                            Log.d("Sandeep", response.body().toString());
                                        }

                                        @Override
                                        public void onFailure(Call<InsertUserResponse> call, Throwable t) {
                                            Log.d("Sandeep", "Failed insert" + t.toString());
                                        }
                                    });
                                }
//                            }

                            @Override
                            public void onFailure(Call<HasuraFBResponse> call, Throwable t) {
                                Log.d("Sandeep", "Failed");
                            }
                        };
                        api.getUserStatus(accessToken.getToken().toString()).enqueue(responseCallback);
                    }

                    @Override
                    public void onFailure(HasuraException e) {
                        Log.e("Hasura",e.toString());
                    }
                });

        Intent i = new Intent(ActivityLogin.this,ActivityHome.class);
        i.putExtra("login", false);
        i.putExtra("loginMethod", "FB");
        startActivity(i);
    }

    //If User cancels Facebook Login
    @Override
    public void OnCancelFBLogin() {
        Toast.makeText(this,"FB Login Canceled", Toast.LENGTH_SHORT).show();
    }

    //If there's an error in login using Facebook
    @Override
    public void OnErrorFBLogin() {
        Toast.makeText(this,"An error occured while trying to Loggin in FB, please trying again.",
                Toast.LENGTH_SHORT).show();
    }

    //If Logged in using Google SignIn
    private void onGoogleSignIn(Task<GoogleSignInAccount> completedTask) {

        Log.d("Google","4");
        Toast.makeText(this,"2",Toast.LENGTH_SHORT).show();
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            Log.d("Google","5");
            // Signed in successfully, show authenticated UI.
           // updateUI(account);
            Toast.makeText(this,"3",Toast.LENGTH_SHORT).show();
            Intent i = new Intent(ActivityLogin.this,ActivityHome.class);
            i.putExtra("login", false);
            i.putExtra("loginMethod", "Google");
            startActivity(i);
        } catch (ApiException e) {
            Log.d("Google","6");
            Toast.makeText(this,"4",Toast.LENGTH_SHORT).show();
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("Google Login", "signInResult:failed code=" + e.getStatusCode() + e.getMessage());
           // updateUI(null);
        }
    }
}